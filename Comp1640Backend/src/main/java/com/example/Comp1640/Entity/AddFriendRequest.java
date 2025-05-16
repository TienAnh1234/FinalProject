package com.example.Comp1640.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "addfriendrequest")
public class AddFriendRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "senderId")
    private Long senderId;

    @Column(name = "recipientId")
    private Long recipientId;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    public AddFriendRequest() {}

    public AddFriendRequest(Long senderId, Long recipientId, String content, String status) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
