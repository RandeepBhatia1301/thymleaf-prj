package org.ril.hrss.model.category_hierarchy;

public class GraphContentType {
    Integer contentTypeId;
    Integer count;
    String activityTime;

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "GraphContentType{" +
                "contentTypeId=" + contentTypeId +
                ", count=" + count +
                ", activityTime='" + activityTime + '\'' +
                '}';
    }
}
