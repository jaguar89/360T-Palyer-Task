package org.sakkour;

import java.util.*;

/**
 * @author Mohammad Sakkour
 *
 * This Inbox serves as data store for our App.
 * we use singelton pattern to make sure that we get the same instance of inbox.
 * it can:  (add message) to a specific dispatcher's inbox.
 * and retreive a specific dispatcher's inbox as well.
 */
public class Inbox {

    private static Inbox inbox;
    private Map<Integer, ArrayList<Message>> mapper = new HashMap<>();

    private Inbox() {
    }

    public static Inbox getInboxInstance() {
        if (inbox == null)
            inbox = new Inbox();
        return inbox;
    }

    public void addMessage(Integer dispatcher, Message message) {
        if (mapper.containsKey(dispatcher)) {
            mapper.get(dispatcher).add(message);
        } else {
            var list = new ArrayList<Message>();
            list.add(message);
            mapper.put(dispatcher, list);
        }
    }

    public ArrayList<Message> getDispatcherInbox(Integer dispatcher) {
        return mapper.get(dispatcher);
    }

}
