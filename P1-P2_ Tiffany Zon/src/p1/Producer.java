package p1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Klassen Producer ska med en konstruktor och tråd kunna hämta MessageProducer implenetering från en buffer
 * samt placera Message instanser i en annan buffer. Klassen implementerar Runnable då instanserna ska exekveras med tråd!
 * Instansvariabeln "Thread" används för att kunna starta en tråd.
 * @author Tiffany Zon ah8512
 */
public class Producer implements Runnable {

    private Buffer<MessageProducer> producerBuffer;
    private Buffer<Message> messageBuffer;
    private Thread thread;

    /**
     * <p>
     *     Konstruktor som används för att skapa en instans av en producer i andra klasser.
     * @param messageBuffer : referens till ett buffer objekt av typen Message
     * @param producerBuffer : referens till ett buffer objekt av typen MessageProducer
     * </p>
     */
    public Producer(Buffer<MessageProducer> producerBuffer, Buffer<Message> messageBuffer) {
        this.producerBuffer = producerBuffer;
        this.messageBuffer = messageBuffer;
    }

    /**
     * <p>
     * Metoden run kommer att köras när en tråd startat. Den kommer hämta en messageProducer för att slutligen
     * kunna skicka vidare meddelanden till en buffer med hjälp av messageProducer implementeringen som hämtades.
     * Metoden delay används för att en viss fördröjning ska finnas mellan placeringen av meddelanden i bufferten.
     * </p>
     */

        public void run() {
            MessageProducer mp;
            while (!Thread.interrupted()) {
                try {
                    mp = producerBuffer.get();
                    Message m;
                    for (int i = 0; i < mp.times(); i++) {
                        for (int j = 0; j < mp.size(); j++) {
                            m = mp.nextMessage();
                            messageBuffer.put(m);
                           Thread.sleep(mp.delay());
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    /**
     * <p>
     *    Metoden kollar ifall en tråd är aktiv, om inte så startas en ny tråd!
     * </p>
     */

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

}
