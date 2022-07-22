package org.sakkour;

import java.util.concurrent.CompletableFuture;

/**
 * @author Mohammad Sakkour
 *
 * This is a Demo of our App.
 */
public class Demo {

    public static void show() {

        // We create the initiator.
        Player initiator = new Player("Carlos");

        // We create the target
        Player target = new Player("William");

        // We need a message object.
        var message = new Message(initiator, target, "");

        // We start by returning a CompletableFuture object, so later we can compose
        // multiple sending operations with it.
        var future = CompletableFuture
                .supplyAsync(() -> (initiator.getNumberOfSentMessages() + 1));

        // Let's try to initiate 20 messages and test the stop condition if it's working!
        for (var i = 0; i < 20; i++) {
            future = future.thenCompose((number) -> CompletableFuture.supplyAsync(() -> {
                // we modify the message
                message.setContent("This is message number " + number);

                // then we send it.
                return initiateMessage(message);
            }));
        }
    }

    /**
     *
     * @param message
     * @return number of messages sent by a dispatcher.
     */
    private static int initiateMessage(Message message) {
        var sender = message.getFrom();

        // here we check the stop condition, before sending a message.
        if (sender.getNumberOfSentMessages() == 10)
            System.exit(0);

        // send the message.
        sender.sendMessage(message);

        return sender.getNumberOfSentMessages() + 1;
    }

}
