package com.thinkgem.jeesite.common.constant.Area;

public enum AreaType {
    COUNTRY("1","国"),PROVINCE("2","省"),CITY("3","市"),REGION("4","区");
    private String code;
    private String desc;

    AreaType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
