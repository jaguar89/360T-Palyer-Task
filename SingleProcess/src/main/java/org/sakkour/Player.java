package org.sakkour;

import java.io.Serializable;

/**
 * @author Mohammad Sakkour
 *
 * This class represents a player which is a concrete implementation of Dispatcher interface.
 * Each player has:
 * A name.
 * A counter for sent messages, later we will check this counter's value
 * to perform the stop condition which is (10) messages.
 *
 * Each player can receive a message and reply to it, so here we implement the receiveMessage method.
 */
public class Player extends MessageHandler{

    private String name;
    private int numberOfSentMessages = 0;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getNumberOfSentMessages() {
        return numberOfSentMessages;
    }

    @Override
    public void setNumberOfSentMessages(int numberOfSentMessages) {
        this.numberOfSentMessages = numberOfSentMessages;
    }

    @Override
    public void receiveMessage(Message message) {
        System.out.println(message.getContent());
        if (message.getMessageType() == MessageType.FROM_SERVER)
            replyToMessage(message.getFrom());
    }

}
