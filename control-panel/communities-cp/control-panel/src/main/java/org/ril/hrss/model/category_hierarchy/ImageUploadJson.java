package org.ril.hrss.model.category_hierarchy;

import javax.persistence.Embeddable;

@Embeddable
public class ImageUploadJson {

    private String imgUrl;
    private String bannerImgUrl;
    private String svgImgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public String getSvgImgUrl() {
        return svgImgUrl;
    }

    public void setSvgImgUrl(String svgImgUrl) {
        this.svgImgUrl = svgImgUrl;
    }
}
