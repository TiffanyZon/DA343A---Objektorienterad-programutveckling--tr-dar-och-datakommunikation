package p1;

import javax.swing.*;
import java.io.*;

public class ObjectFileProducer implements MessageProducer{

    private int times = 0;
    private int delay = 0;
    private int size;
    private Message messages [];

    private int currentIndex = -1;
    private String filename = "files/new.dat";
    public ObjectFileProducer (String filename)
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/new.dat"))){
            times = ois.readInt();
            delay = ois.readInt();
            size = ois.readInt();
            messages = new Message[size];
            for (int i = 0; i<size; i++){
                try{
                    messages[i] = (Message) ois.readObject();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int delay() {
        return delay;
    }

    @Override
    public int times() {
        return times;
    }
    @Override
    public int size() {
        return (messages == null) ? 0 : messages.length;
    }
    @Override
    public Message nextMessage() {
        if(size()==0)
            return null;
        currentIndex = (currentIndex+1) % messages.length;
        return messages[currentIndex];
    }
}
