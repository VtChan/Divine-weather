package com.vince.divineweather.gson;

/**
 * Created by : vince
 * Created at : 2019/7/29
 * Desc :
 */
public class LifeStyleForecast {


    /**
     * date : 2019-06-06
     * type : comf
     * brf : 较舒适
     * txt : 白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。
     */

    private String date;
    private String type;
    private String brf;
    private String txt;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
