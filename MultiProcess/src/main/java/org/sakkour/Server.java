package org.sakkour;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author  Mohammad Sakkour
 *
 * This class represents the server.
 * it will create & export the remote object, so later it can be used for sending messages.
 */
public class Server {

    public static void main(String[] args) {

        // Create the initiator.
        Player initiator = new Player("Carlos");

        try {

            // Here, we export the remote object to the stub (which is the representation of the remote
            // object on the client side).
            Dispatcher stub = (Dispatcher) UnicastRemoteObject.exportObject(initiator, 0);

            // Create a registry.
            Registry registry = LocateRegistry.createRegistry(1888);

            // Bind stub to registry.
            registry.rebind("initiator", stub);

            // Print out some information.
            System.err.println("Server is running.");
            System.out.println("________________________");
            System.err.println("Player\'s name: " + initiator.getName());

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Here, we can execute cleanup code if needed.
            System.err.println("Server has stopped.");
            var counter = 15;
            while (counter > 0){
                System.out.println("terminating in " + (counter--) + " ...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }));

    }
}
