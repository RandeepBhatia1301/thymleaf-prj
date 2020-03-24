package org.ril.hrss.model.content.blog;

import java.util.Date;

public class Count {

    private Date label;

    private Long blogCount;
    private Long pollCount;


    public Long getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Long blogCount) {
        this.blogCount = blogCount;
    }

    @Override
    public String toString() {
        return "Count{" +
                "label=" + label +
                ", blogCount=" + blogCount +
                ", pollCount=" + pollCount +
                '}';
    }

    public Date getLabel() {
        return label;
    }

    public void setLabel(Date label) {
        this.label = label;
    }


    public Long getPollCount() {
        return pollCount;
    }

    public void setPollCount(Long pollCount) {
        this.pollCount = pollCount;
    }
}
