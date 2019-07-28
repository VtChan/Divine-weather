package com.vince.divineweather.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

/**
 * Created by : vince
 * Created at : 2019/7/28
 * Desc :
 */
public class County extends LitePalSupport {


    /**
     * id : 999
     * name : 杭州
     * weather_id : CN101210101
     * cityId : 所属市的Id值
     */

    private int id;
    @SerializedName("name")
    private String countyName;
    @SerializedName("weather_id")
    private String weatherId;
    private int cityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
