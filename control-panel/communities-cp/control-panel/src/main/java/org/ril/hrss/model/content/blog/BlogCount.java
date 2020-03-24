package org.ril.hrss.model.content.blog;

import java.util.Date;

public class BlogCount {

    private Date label;

    private Long y;


    public BlogCount(Date label, Long y) {
        super();
        this.label = label;
        this.y = y;
    }

    public Date getLabel() {
        return label;
    }

    public void setLabel(Date label) {
        this.label = label;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "BlogCount{" +
                "label=" + label +
                ", y=" + y +
                '}';
    }
}
