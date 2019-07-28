package com.vince.divineweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by : vince
 * Created at : 2019/7/28
 * Desc :
 * Version : 1.0
 */
public class Province extends LitePalSupport {


    /**
     * id : 1
     * provinceName : 北京
     * provinceCode :
     */

    private int id;
    private String provinceName;
    private int provinceCode;

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
