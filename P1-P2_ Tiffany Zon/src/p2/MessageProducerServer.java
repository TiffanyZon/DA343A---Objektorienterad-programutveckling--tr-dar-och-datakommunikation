package p2;

import p1.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;

public class MessageProducerServer extends Thread {
    private MessageProducerInput ipManager;
    private int port;

    public MessageProducerServer(MessageProducerInput ipManager, int port) {
        this.ipManager = ipManager;
        this.port = port;

    }

    @Override
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ArrayProducer arrayProducer = (ArrayProducer) ois.readObject();
                    ipManager.addMessageProducer(arrayProducer);
                }
            } catch(IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

        public void startServer () {
            start();
        }

    }



