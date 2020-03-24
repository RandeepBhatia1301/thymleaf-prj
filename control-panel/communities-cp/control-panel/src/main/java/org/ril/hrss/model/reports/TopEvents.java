package org.ril.hrss.model.reports;

public class TopEvents {

    private Long eventId;
    private Long count;
    private String title;

    public TopEvents() {
        super();
    }

    public TopEvents(Long eventId, Long count) {
        this();
        this.eventId = eventId;
        this.count = count;
    }

    public TopEvents(Long eventId, Long count, String title) {
        this(eventId, count);
        this.title = title;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TopPolls [ eventId = " + eventId + ", title = " + title + ", count = " + count + "]";
    }
}
