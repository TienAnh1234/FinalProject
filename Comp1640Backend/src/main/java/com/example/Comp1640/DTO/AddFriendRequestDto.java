package com.example.Comp1640.DTO;

public class AddFriendRequestDto {

    private Long id;
    private String sender;
    private String recipient;
    private String content;
    private String status;

    public AddFriendRequestDto(Long id,String sender, String recipient, String content, String status) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.status = status;
    }

    public AddFriendRequestDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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
