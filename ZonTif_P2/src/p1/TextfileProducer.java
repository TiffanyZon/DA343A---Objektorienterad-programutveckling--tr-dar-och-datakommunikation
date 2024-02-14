package p1;

import javax.swing.*;
import java.io.*;

/**
 * Klassen TextfileProducer implementerar MessageProducer för att kunna skapa ett bildspel som ska synas på
 * Viewer. Förutom de instansvariabler som ärvts från den implementerade klassen finns även "currentIndex".
 * Den används i en metod längre ner för att kunna visa nästa meddelande i sekvensen. Klassen har en metod som
 * möjligör det att läsa innehållet från en given text fil. 
 * @author Tiffany Zon ah8512
 */
public class TextfileProducer implements MessageProducer {
    private Message[] messages;
    private int times = 0;
    private int size;
    private int delay = 0;
    private int currentIndex = -1;

    private String filename = "files/new.txt";

    /**
     * I metoden har en bufferedReader skapats för att kunna läsa text filen "new.txt".
     * @param filename : sökvägen för filen som metoden ska läsa.
     */
    public TextfileProducer(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            times = Integer.parseInt(br.readLine());
            delay = Integer.parseInt(br.readLine());
            size = Integer.parseInt(br.readLine());
            messages = new Message[size];
            for (int i = 0; i < size; i++) {
                messages[i] = new Message(br.readLine(), new ImageIcon(br.readLine()));
            }
        } catch (IOException e) {
            System.out.println("Error while reading file");
            e.printStackTrace();
        }
    }

    /**
     * <p>
     *    Metoden returnerar antal gånger som meddelande-sekvensen ska visas.
     * @return times : antal gånger meddelandet ska visas
     */
    public int times() {

        return times;
    }
    /**
     * <p>
     *     Metoden returnerar fördröjningen mellan byten av meddelanden.
     * @return delay : fördröjning mellan byten av meddelanden
     * </p>
     */
    public int delay() {
        return delay;
    }

    /**
     * <p>
     *      Metoden returnerar antal bilder
     * @return size : antal bilder i sekvensen
     * </p>
     */
    public int size() {
        return (messages == null) ? 0 : messages.length;
    }

    /**
     * Metoden returnerar ett meddelande som finns på tur.
     * @return nästa meddelande i sekvensen
     */
    @Override
    public Message nextMessage() {
        if (size() == 0)
            return null;
        currentIndex = (currentIndex + 1) % messages.length;
        return messages[currentIndex];
    }

}
