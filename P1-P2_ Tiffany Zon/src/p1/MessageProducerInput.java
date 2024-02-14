package p1;

public class MessageProducerInput extends Thread {

    private Buffer<MessageProducer> producerBuffer;

    public MessageProducerInput(Buffer<MessageProducer> producerBuffer) {
        this.producerBuffer = producerBuffer;
    }

    public void addMessageProducer(MessageProducer m)
    {
            try {
                Thread.sleep(m.delay());
                producerBuffer.put(m);
            }
            catch(InterruptedException e) {
                e.printStackTrace();

            }
        }
    }


