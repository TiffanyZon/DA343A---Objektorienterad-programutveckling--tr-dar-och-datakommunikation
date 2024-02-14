package p1;


import p2.MessageServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class MessageManager implements Runnable {
    private Buffer<Message> messageBuffer;
    private MessageServer messageServer;
    private Thread thread;
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public MessageManager(Buffer<Message> messageBuffer) {
        this.messageBuffer = messageBuffer;
    }
    public void addListener(PropertyChangeListener listener)
    {
        changes.addPropertyChangeListener(listener);
    }

        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Message m = messageBuffer.get();
                  changes.firePropertyChange("change",false,m);

                }catch (InterruptedException e) {
                    break;
                }
            }
        }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
