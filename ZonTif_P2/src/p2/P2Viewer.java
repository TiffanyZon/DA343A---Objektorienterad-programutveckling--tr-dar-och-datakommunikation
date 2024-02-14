package p2;

import p1.Message;
import p1.Viewer;

public class P2Viewer extends Viewer  {
    private MessageClient mc;

    public P2Viewer(MessageClient mc, int width, int height) {
        super(width, height);
        this.mc = mc;
        mc.addView(this);
    }

    public void receiveMessage(Message message)
    {
        setMessage(message);
    }

}
