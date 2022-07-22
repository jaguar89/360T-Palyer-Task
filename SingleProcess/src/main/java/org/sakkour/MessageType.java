package org.sakkour;

/**
 * @author Mohammad Sakkour
 *
 * This class helps us distinguish between messages coming from dispatcher (sender)
 * and messages generated by handler (server).
 */
public enum MessageType {
    FROM_SERVER,
    FROM_DISPATCHER
}
