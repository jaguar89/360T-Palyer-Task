package org.sakkour;

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
     * @param message This method handles sending messages.
     *                first: we extract sender and receiver from the message object.
     *                then: we save our message into inbox, mapped to the receiver.
     *                then: we create a notification to inform receiver of his new message.
     *                then: we delegate to receiver the task of receiving the notification.
     *                finally: we update the number of messages sent by the sender.
     */
    @Override
    public void sendMessage(Message message) {
        var sender = message.getFrom();
        var target = message.getTo();
        inbox.addMessage(target.hashCode(), message);
        var notification = new Message(sender, target, "Server --> " + target.getName() +
                ", you got a new message: {" + message.getContent() + "} from: " + sender.getName());
        notification.setMessageType(MessageType.FROM_SERVER);
        target.receiveMessage(notification);
        sender.setNumberOfSentMessages(sender.getNumberOfSentMessages() + 1);
    }

    /**
     * @param receivedFrom This method gets called by the receiver who wants to reply
     *                     with the received message, attached to the number of
     *                     messages (from a specific sender).
     *                     first: we get the inbox that belongs to receiver.
     *                     then: we calculate number of messages and last message he got from that sender.
     *                     finally: we form the required reply and delegate receiving to the sender.
     */
    public void replyToMessage(Dispatcher receivedFrom) {
        var receiverInbox = inbox.getDispatcherInbox(this.hashCode());
        var messages = receiverInbox.stream()
                .filter(message -> message.getFrom() == receivedFrom)
                .map(message -> message.getContent())
                .collect(Collectors.toList());
        var counter = messages.size();
        var lastMessage = messages.get(counter - 1);
        var message = new Message(this, receivedFrom, "From: " + this.getName() + " --> " +
                " last message was: {" + lastMessage + "} " +
                ", counter is : (" + counter + ").");
        message.setMessageType(MessageType.FROM_DISPATCHER);
        receivedFrom.receiveMessage(message);
    }

}
