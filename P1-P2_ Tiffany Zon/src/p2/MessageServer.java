package p2;

import p1.Message;
import p1.MessageManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * MessageServer klassen är en server som klienter kan koppla upp sig till. Klassen ska ta emot Message objekt genom att den lyssnar
 * på MessageManagern.
 * @author Tiffany Zon AH8512
 */
public class MessageServer implements PropertyChangeListener {
    private MessageManager mm;
    private int port;
    private Thread thread;

    private ArrayList <ClientHandler> clienHandler;

    /**
     * Konstruktorn tar emot en MessageManager och port som argument för att kunna lägga till en lyssnare.
     * En uppkoppling skapas också genom SetUp instansen, tråden kommer acceptera klienter som vill koppla upp sig till servern.
     * @param mm MessageManager som servern lyssnar på
     * @param port porten servern lyssnar på för att kunna nå meddelanden
     */
    public MessageServer(MessageManager mm, int port) {
        this.mm = mm;
        this.port = port;
        mm.addListener(this);
        clienHandler = new ArrayList<>();
        thread = new SetUp(port);
        thread.start();
    }

    /**
     * Klassen ska skicka meddelanden som överförts från MessageManager till alla klienter genom att loopa igenom alla
     * klienter i clienthandler för att sedan kalla på put metoden som skickar meddelandet till varje klient samtidigt.
     * @param message
     */
    public void send(Message message)
    {
        for (int i = 0; i < clienHandler.size(); i++) {
            clienHandler.get(i).put(message);
        }

    }

    /**
     * Metoden som används för att lyssna på förändringar.
     * Meddelandet som tagits emot (som har förändrats) stoppas in i message
     * som sedan skickas ut till alla uppkopplade klienter.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Message message = (Message) evt.getNewValue();
        send(message);
    }

    /**
     * SetUp är en inre klass som har hand om uppkopplingen. För varje klient
     * som kopplar upp sig kommer en ny ClientHandler att skapas.
     * @port
     * @author Tiffany Zon AH8512
     */
    private class SetUp extends Thread
    {
        private int port;

        /**
         * Konstruktor som tar emot en port som argument.
         * @param port
         */
        public SetUp(int port) {
            this.port = port;
        }

        /**
         * När en tråd startas kommer även den här metoden att starta. En ServerSocket skapas för att
         * kunna ta emot klienter som vill koppla upp sig till servern. Alla klienter som kopplar upp sig
         * skapas en ny ClientHandler som placeras i en lista. Listan håller reda på alla ClientHandlers i servern.
         */
        public void run()
        {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clientHandler.start();
                    MessageServer.this.clienHandler.add(clientHandler);
                }
            } catch (IOException e) {
            }

        }
    }

    /**
     * ClientHandler är en inre klass som ska hantera anslutningen mellan en klient som kopplat upp sig och servern.
     * I klassen finns det metoder som gör att servern kan skicka meddelanden till klienten.
     * @author Tiffany Zon AH8512
     */

    private class ClientHandler extends Thread {

        private Socket socket;
        private ObjectOutputStream oos;

        public ClientHandler(Socket socket) {
            this.socket = socket;

        }

        /**
         * Metoden möjligör det att skicka meddelanden till klienten genom att skapa outputstream.
         */

        @Override
        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Metoden som skriver medddelanden via objectoutput strömmen.
         * @param message meddelandet som ska skickas till den uppkopplade klienten.
         */
        public void put (Message message)
        {
            try {
                oos.writeObject(message);
                oos.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}


