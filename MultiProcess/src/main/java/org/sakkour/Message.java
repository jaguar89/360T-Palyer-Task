package org.sakkour;

import java.io.Serializable;

/**
 * @author Mohammad Sakkour
 *
 * This class represnets a message, which holds the following inforamtion:
 * content of a message.
 * the sender.
 * the receiver.
 * the message type
 *
 * it performs the appropriate getters and setters according to our needs.
 *
 */

public class Message implements Serializable {

    private String content;
    private Dispatcher from;
    private Dispatcher to;

    private MessageType messageType;

    public Message(Dispatcher from, Dispatcher to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public Dispatcher getFrom() {
        return from;
    }

    public Dispatcher getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
