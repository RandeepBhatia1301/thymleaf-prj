package org.ril.hrss.model.content.poll;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "content.poll_option")
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "poll_id")
    private Long pollId;

    @Column(name = "opt_text")
    private String optText;

    @Column(name = "opt_image")
    private String optImage;

    @Column(name = "created_at")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getOptText() {
        return optText;
    }

    public void setOptText(String optText) {
        this.optText = optText;
    }

    public String getOptImage() {
        return optImage;
    }

    public void setOptImage(String optImage) {
        this.optImage = optImage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
