package org.sakkour;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author Mohammad Sakkour
 *
 * This class represents a player which is a concrete implementation of Dispatcher interface.
 * Each player has:
 * A name.
 * A counter for sent messages, later we will check this counter's value
 * to perform the stop condition which is (10) messages.
 *
 * Each player can:
 * receive a message.
 * terminate the connection.
 *
 */
public class Player extends MessageHandler implements Serializable {

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
    public void receiveMessage(Message message) throws RemoteException {
        System.out.println(message.getContent());
    }

    public void terminateConnection() throws RemoteException {
        System.exit(0);
    }
}
