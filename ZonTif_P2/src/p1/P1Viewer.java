package p1;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class P1Viewer extends Viewer implements PropertyChangeListener {
    private MessageManager mm;

    public P1Viewer(MessageManager mm , int width, int height) {
        super(width, height);
        mm.addListener(this);
    }
    public void setMessage(Message message)
    {
        super.setMessage(message);
    }
    public void propertyChange(PropertyChangeEvent e)
    {
        if (e.getPropertyName().equals("change")){
            Message m = (Message) e.getNewValue();
            setMessage(m);
        }
    }
}
