package com.thinkgem.jeesite.modules.shop.shop.bean;

/**
 * 用于存储商户上传三张照片的K-V数据结构
 * @author xiangnick
 */
public class UploadFileRecord {

    private String license;
    private String idCardFront;
    private String idCardContrary;

    public UploadFileRecord() {
        this.license = "";
        this.idCardFront = "";
        this.idCardContrary = "";
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardContrary() {
        return idCardContrary;
    }

    public void setIdCardContrary(String idCardContrary) {
        this.idCardContrary = idCardContrary;
    }
}
