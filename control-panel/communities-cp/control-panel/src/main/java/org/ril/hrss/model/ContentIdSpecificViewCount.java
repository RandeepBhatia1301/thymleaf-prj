package org.ril.hrss.model;

public class ContentIdSpecificViewCount {
    private Integer contentId;
    private Integer total;
    private String viewedAt;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }

    @Override
    public String toString() {
        return "ContentIdSpecificViewCount{" +
                "contentId=" + contentId +
                ", total=" + total +
                ", viewedAt='" + viewedAt + '\'' +
                '}';
    }
}
