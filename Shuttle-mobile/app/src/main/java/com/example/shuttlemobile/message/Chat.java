package com.example.shuttlemobile.message;

import com.example.shuttlemobile.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private User sender;
    private User recipient;
    private List<Message> messages = new ArrayList<>();

    // sender and recipient are a controlled redundancy.
    // For all messages, the sender and recipient can be only
    // either of the two inside of this class.

    /**
     * @return Last message in this chat or <code>null</code> if none.
     */
    public Message getLastMessage() {
        if (messages == null)
            return null;
        if (messages.size() == 0)
            return null;
        return messages.get(messages.size() - 1);
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
