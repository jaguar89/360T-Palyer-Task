package org.sakkour;

import java.rmi.RemoteException;
import java.util.stream.Collectors;

/**
 * @author Mohammad Sakkour
 * <p>
 * This class is Abstract basically because we don't want it to be instantiated, plus it
 * provides common code that must be shared to all childs.
 * <p>
 * This class is responsible for:
 * sending a message.
 * replying to a message.
 */

public abstract class MessageHandler implements Dispatcher {
    /**
     * This is the reference to inbox which is our data store.
     */
    private Inbox inbox = Inbox.getInboxInstance();

    /**
     * @param message
     * @return
     * @throws RemoteException This method handles sending messages.
     *                         first: we extract sender and receiver from the message object.
     *                         then: we save our message into inbox, mapped to the receiver.
     *                         then: we update the number of messages sent by the sender.
     *                         then: we modify the type of the message.
     *                         and finally: we return the message, so the client can get it.
     */
    @Override
    public Message sendMessage(Message message) throws RemoteException {
        var sender = message.getFrom();
        var target = message.getTo();
        inbox.addMessage(target.hashCode(), message);
        sender.setNumberOfSentMessages(sender.getNumberOfSentMessages() + 1);
        message.setMessageType(MessageType.FROM_SERVER);
        return message;
    }

    /**
     * @param replyTo
     * @throws RemoteException This method gets called by the receiver (client) who wants to reply
     *                         with the received message, attached to the number of messages he
     *                         received from sender (server).
     *                         first: we get the inbox that belongs to receiver.
     *                         then: we calculate number of messages and last message he got from that sender.
     *                         finally: we form the required reply and delegate receiving to the sender
     *                         which is our remote object.
     */
    public void replyToMessage(Dispatcher replyTo) throws RemoteException {
        var replierInbox = inbox.getDispatcherInbox(this.hashCode());
        var messages = replierInbox.stream()
                .filter(message -> message.getFrom().hashCode() == replyTo.hashCode())
                .map(message -> message.getContent())
                .collect(Collectors.toList());
        var counter = messages.size();
        var lastMessage = messages.get(counter - 1);

        var message = new Message(this, replyTo, "");
        message.setMessageType(MessageType.FROM_DISPATCHER);
        message.setContent("From: " + this.getName() +
                " -->  last message was: {" + lastMessage +
                "}, counter is:  (" + counter + ").");
        replyTo.receiveMessage(message);
    }

}
