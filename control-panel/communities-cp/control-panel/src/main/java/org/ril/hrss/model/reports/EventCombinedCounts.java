package org.ril.hrss.model.reports;

public class EventCombinedCounts {

    private Long reactions;
    private Long going;
    private Long notGoing;
    private Long interested;

    public EventCombinedCounts() {
        super();
    }

    public EventCombinedCounts(Long reactions, Long going, Long notGoing, Long interested) {
        this();
        this.reactions = reactions;
        this.going = going;
        this.notGoing = notGoing;
        this.interested = interested;
    }

    public Long getReactions() {
        return reactions;
    }

    public void setReactions(Long reactions) {
        this.reactions = reactions;
    }

    public Long getGoing() {
        return going;
    }

    public void setGoing(Long going) {
        this.going = going;
    }

    public Long getNotGoing() {
        return notGoing;
    }

    public void setNotGoing(Long notGoing) {
        this.notGoing = notGoing;
    }

    public Long getInterested() {
        return interested;
    }

    public void setInterested(Long interested) {
        this.interested = interested;
    }

    @Override
    public String toString() {
        return "PollParticipantsCount [ reactions = " + reactions + ", going = " + going + ", notGoing = " + notGoing
                + ", interested = " + interested + "]";
    }
}
