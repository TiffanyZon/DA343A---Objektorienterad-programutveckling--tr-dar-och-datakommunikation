package p2;
import p1.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
public class MessageProducerClient {
    private String address;
    private int port;
    private int times;
    private int delay;
    private int size;

    public MessageProducerClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void send(MessageProducer messageProducer) {

        times = messageProducer.times();
        delay = messageProducer.delay();
        size = messageProducer.size();
        Message[] messages = new Message[size];
        for (int i = 0; i < size; i++) {
            messages[i] = messageProducer.nextMessage();
        }
        ArrayProducer arrayProducer = new ArrayProducer(messages, times, delay);
        try (Socket socket = new Socket(address, port)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(arrayProducer);
            oos.flush();


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
