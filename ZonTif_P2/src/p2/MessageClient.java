package p2;

import p1.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * MessageClient klassen ska kunna ta emot Message objekt från servern om uppkopplingen funkar! När ett meddelande
 * mottagits ska det sedan överföras till ett P2Viewer objekt som visar meddelandet i ett fönster. Överföringen i
 * denna delen sker via callback. Istället för att implementera ett helt nytt interface används ArrayList.
 * @author Tiffany Zon AH8512
 */
public class MessageClient {

    private String address;
    private int port;
    private ArrayList<P2Viewer> p2Viewer;

    /**
     * Konstruktor som används för att kunna koppla upp sig till servern. Argumenten som tas emot
     * stoppas in i en metod i den privara inre klassen som möjligör uppkopplingen.
     * @param address serverns adress
     * @param port serverns port
     */
    public MessageClient(String address, int port) {
        SetUp setUp = new SetUp(address, port);
        setUp.start();
        p2Viewer = new ArrayList<>();
    }

    /**
     * Metoden används för att kunna stoppa in P2Viewer objekt i en arraylist
     * @param p2Viewer en viewer som ska visa överförda meddelanden
     */
    public void addView(P2Viewer p2Viewer) {
        this.p2Viewer.add(p2Viewer); // metoden add kommer från ArrayList interface (callback)
    }

    /**
     * SetUp är en inre klass som möjligör uppkoppling till servern.
     * @author Tiffany Zon AH8512
     */
    private class SetUp extends Thread {
        private String address;
        private int port;

        /**
         * Konstruktor som tar emot serverns adress och port som argument.
         * @param address serverns adress
         * @param port serverns port
         */
        public SetUp(String address, int port) {
            this.address = address;
            this.port = port;
        }

        /**
         * När en tråd startas kommer även denna metoden starta. En Socket skapas för att kunna ansluta till servern.
         * ObjectInputStream skapas föra tt kunna lösa de meddalanden som mottagits från servern. Alla meddelanden som lästs
         * av ois lagras i message. Därefter överförs message till viewern.
         */
        @Override
        public void run() {
            try (Socket socket = new Socket(address, port)) {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    Message message = (Message) ois.readObject();
                    for (P2Viewer view : p2Viewer) {
                        view.receiveMessage(message);
                    }
                }
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
