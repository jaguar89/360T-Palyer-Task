package org.sakkour;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Mohammad Sakkour
 *
 * This class represents the client.
 * it will do the following:
 * get the remote object.
 * invoke methods on server using the remote object (for sending messages).
 * receiving messages from server.
 * display results and save data to inbox.
 * reply to server.
 *
 */
public class Client {

    public static void main(String[] args) {
        try {

            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(1888);

            // Looking up the registry for the remote object.
            Dispatcher initiator = (Dispatcher) registry.lookup("initiator");

            // Create the receiver object and print his name.
            Player receiver = new Player("William");
            System.err.println("Player\'s name: " + receiver.getName());
            System.out.println("________________________");

            // We need a message object.
            var message = new Message(initiator, receiver, "");

            // Perform multiple sending operations.
            for (var i = 0; i < 20; i++) {

                // First we modify the message.
                message.setContent("This is message number " + (i + 1));

                // Check the stop condition.
                // This operation will be executed on the server side.
                if (initiator.getNumberOfSentMessages() == 10)
                    initiator.terminateConnection();

                // Call the remote object for sending a message and cache the returned result.
                var response = initiator.sendMessage(message);

                // Print out some information.
                System.out.println("You got a new message: {" + response.getContent() + "} From: " +
                        response.getFrom().getName());

                // Save the response
                saveMessageToInbox(receiver, response);

                // Reply to the server's message.
                receiver.replyToMessage(initiator);
            }

        } catch (RemoteException e) {
            System.err.println("Server is down.");
            System.exit(0);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveMessageToInbox(Dispatcher dispatcher, Message message) {
        var inbox = Inbox.getInboxInstance();
        inbox.addMessage(dispatcher.hashCode(), message);
    }
}
