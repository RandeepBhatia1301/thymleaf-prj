package org.ril.hrss.model.content.poll;

import java.util.Date;

public class PollCount {

    private Date label;

    private Long y;

    public PollCount(Date label, Long y) {
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
        return "PollCount{" +
                "label=" + label +
                ", y=" + y +
                '}';
    }
}
