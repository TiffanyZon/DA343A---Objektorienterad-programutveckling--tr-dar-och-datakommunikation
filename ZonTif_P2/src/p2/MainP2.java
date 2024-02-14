package p2;

import javax.swing.ImageIcon;

import p1.Buffer;
import p1.Message;
import p1.MessageManager;
import p1.MessageProducer;
import p1.MessageProducerInput;
import p1.P1Viewer;
import p1.Producer;
import p1.TextfileProducer;
import p1.Viewer;

import java.io.Serializable;

public class MainP2 {
	public static void main(String[] args) {
		Buffer<Message> messageBuffer = new Buffer<Message>();
		Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();

		MessageManager messageManager = new MessageManager(messageBuffer);
		P1Viewer v1 = new P1Viewer(messageManager, 300, 200);
		P1Viewer v2 = new P1Viewer(messageManager, 320, 320);
		Viewer.showPanelInFrame(v1, "Viewer 1", 100, 50);
		Viewer.showPanelInFrame(v2, "Viewer 2", 450, 50);

		MessageServer messageServer = new MessageServer(messageManager, 1010); // start av server
		MessageClient messageClient1 = new MessageClient("127.0.0.1", 5678); // start av client1

		P2Viewer v3 = new P2Viewer(messageClient1, 300, 200);
		P2Viewer v4 = new P2Viewer(messageClient1, 320, 320);
		Viewer.showPanelInFrame(v3, "Viewer 3", 100, 400);
		Viewer.showPanelInFrame(v4, "Viewer 4", 450, 400);
		MessageClient messageClient2 = new MessageClient("127.0.0.1", 1234); // start av client2
		P2Viewer v5 = new P2Viewer(messageClient2, 250, 320);
		Viewer.showPanelInFrame(v5, "Viewer 5", 800, 400);

		messageManager.start();

		Producer producer = new Producer(producerBuffer,messageBuffer);
		producer.start();

        MessageProducerInput mpInput = new MessageProducerInput(producerBuffer);
		//mpInput.addMessageProducer(new TextfileProducer("files/new.txt"));

        MessageProducerServer mpServer = new MessageProducerServer(mpInput,3343);
        mpServer.startServer();
        MessageProducerClient mpClient1 = new MessageProducerClient("127.0.0.1",3343);
		TestP2Input testP2Input = new TestP2Input();
        mpClient1.send(testP2Input.getArrayProducer(10,100));
        mpClient1.send(new ShowGubbe(5000));
		mpClient1.send(new TextfileProducer("files/new.txt"));

	}
}

class ShowGubbe implements MessageProducer, Serializable {
	private int delay;

	public ShowGubbe(int delay) {
		this.delay = delay;
	}

	@Override
	public int delay() {
		return delay;
	}

	@Override
	public int times() {
		return 1;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Message nextMessage() {
		return new Message("Hi folks...",new ImageIcon("images/gubbe.jpg"));
	}


}
