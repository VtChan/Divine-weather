package com.vince.divineweather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vince.divineweather.Const;
import com.vince.divineweather.R;
import com.vince.divineweather.WeatherActivity;
import com.vince.divineweather.db.City;
import com.vince.divineweather.db.County;
import com.vince.divineweather.db.Province;
import com.vince.divineweather.util.HttpUtil;
import com.vince.divineweather.util.LogUtil;
import com.vince.divineweather.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by : vince
 * Created at : 2019/7/28
 * Desc :
 */
public class ChooseAreaFragment extends Fragment {

    public static final String TAG = "ChooseAreaFragment";
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.back_bt)
    Button backBt;
    @BindView(R.id.list_view)
    ListView listView;
    private ProgressDialog progressDialog;

    private ArrayAdapter<String> adapter; //ListView适配器

    private List<String> dataList = new ArrayList<>();

    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 选中的县
     */
    private County selectedCounty;
    /**
     * 当前选中的级别
     */
    private int currentLevel;
    private Unbinder unbinder;

    /**
     * 获取控价实例，初始化ArrayAdapter，设置为ListView的适配器
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    /**
     * 设置点击事件
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == Const.LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == Const.LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == Const.LEVEL_COUNTY){
                    String weatherId = countyList.get(position).getWeatherId();
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weatherid", weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == Const.LEVEL_COUNTY){
                    queryCities();
                } else if (currentLevel == Const.LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询选中的市内所有的县区，优先从数据库查询，如果没有查询到再去服务器上查找
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backBt.setVisibility(View.VISIBLE);
        countyList = LitePal.where("cityid = ?", String.valueOf(selectedCity
                .getId())).find(County.class);
        if (countyList.size() > 0){
            dataList.clear();
            for (County county : countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = Const.LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String addr = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(addr, "county");
        }
    }

    /**
     * 查询选中的省内所有的城市，优先从数据库查询，如果没有查询到再去服务器上查找
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backBt.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceid = ?", String.valueOf(selectedProvince
            .getId())).find(City.class);
        if (cityList.size() > 0){
            dataList.clear();
            for (City city : cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = Const.LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String addr = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(addr, "city");
        }
    }

    /**
     *查询全国所有的城市，优先从数据库查询，如果没有查询到再去服务器上查找
     */
    private void queryProvinces() {
        titleText.setText("中国");
        backBt.setVisibility(View.GONE);
        provinceList = LitePal.findAll(Province.class);

        if (provinceList.size() > 0){
            dataList.clear();
            for (Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = Const.LEVEL_PROVINCE;
        } else {
            String addr = "http://guolin.tech/api/china";
            queryFromServer(addr, "province");
        }
    }

    private void queryFromServer(String addr, final String type) {
        LogUtil.d(TAG, "queryFromServer >>>" );
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(addr, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handlerProvinceResponse(responseText);
                } else if ("city".equals(type)){
                    result = Utility.handlerCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)){
                    result = Utility.handlerCountyResponse(responseText, selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            } else if ("city".equals(type)){
                                queryCities();
                            } else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }



}
