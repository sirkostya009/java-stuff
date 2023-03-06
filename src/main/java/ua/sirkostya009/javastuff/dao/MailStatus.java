package ua.sirkostya009.javastuff.dao;

import lombok.Getter;

@Getter
public enum MailStatus {
    SENT,
    PENDING;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
