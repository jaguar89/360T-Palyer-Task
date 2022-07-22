package org.sakkour;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Mohammad Sakkour
 *
 * This interface represents the contract for our dispatcher.
 * every dispatcher can:
 * send a message.
 * receive a message.
 * display his name.
 * get & update the total number of sent messages.
 * terminate the connection
 *
 */
public interface Dispatcher extends Remote {

    Message sendMessage(Message message) throws RemoteException;

    void receiveMessage(Message message) throws RemoteException;

    String getName() throws RemoteException;

    int getNumberOfSentMessages() throws RemoteException;

    void setNumberOfSentMessages(int number) throws RemoteException;

    void terminateConnection() throws RemoteException;
}
