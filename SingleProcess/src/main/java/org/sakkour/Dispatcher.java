package org.sakkour;


/**
 * @author Mohammad Sakkour
 *
 * This interface represents the contract for our dispatcher.
 * every dispatcher can:
 * send a message.
 * receive a message.
 * display his name.
 * get & update the total number of sent messages.
 *
 */
public interface Dispatcher {

    void sendMessage(Message message);

    void receiveMessage(Message message);

    String getName();

    int getNumberOfSentMessages();

    void setNumberOfSentMessages(int number);
}
