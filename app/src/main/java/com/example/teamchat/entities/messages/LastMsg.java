package com.example.teamchat.entities.messages;

public class LastMsg {
    private int id;
    private String created;
    private String content;

    public LastMsg(int id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
