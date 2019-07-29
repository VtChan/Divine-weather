package com.vince.divineweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by : vince
 * Created at : 2019/7/29
 * Desc : 创建一个总的天气实例类来引用各个实例类
 */
public class Weather {

    public String status; //当前接口的状态，正常返回数据会返回ok，若未能返回数据，会给出具体的错误码，不同的错误码请参考https://dev.heweather.com/docs/refer/status-code

    public Basic basic;

    public Update update; //接口更新时间为当前接口的更新时间，包括城市/地区所在地的当地时间和UTC时间

    public Now now;

    @SerializedName("daily_forecast")
    public List<DailyForecast> dailyForecastList;

    @SerializedName("hourly")
    public List<HourlyForecast> hourlyForecast;

    @SerializedName("lifestyle")
    public List<LifeStyle> lifeStyle;

    @SerializedName("lifestyle_forecast ")
    public List<LifeStyleForecast> lifeStyleForecast;
}
