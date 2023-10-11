package com.tallerwebi.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String title;
    String message;
    @Column(name = "creation_date")
    Date creationDate;
    @Column(name = "is_read")
    boolean isRead;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MobileUser user;

    public Notification(String title, String message, Date creationDate, MobileUser user) {
        this.title = title;
        this.message = message;
        this.creationDate = creationDate;
        this.user = user;
        this.isRead = false;
    }

    public Notification() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public MobileUser getUser() {
        return user;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
