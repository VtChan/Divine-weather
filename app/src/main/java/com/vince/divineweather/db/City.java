package com.vince.divineweather.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

/**
 * Created by : vince
 * Created at : 2019/7/28
 * Desc :
 */
public class City extends LitePalSupport {


    /**
     * id : 126
     * cityName : 杭州
     * cityCoed : 城市代号
     * provinceId : 当前市所属省
     */

    private int id;
    @SerializedName("name")
    private String cityName;
    private int cityCode;
    private int provinceId;

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
