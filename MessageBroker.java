import java.util.concurrent.*;

public class MessageBroker {
    private static MessageBroker instance = new MessageBroker();
    private BlockingQueue<BookingRequest> requestQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<BookingConfirmation> confirmationQueue = new LinkedBlockingQueue<>();

    private MessageBroker() {}

    public static MessageBroker getInstance() {
        return instance;
    }

    public void sendRequest(BookingRequest request) {
        try {
            requestQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendConfirmation(BookingConfirmation confirmation) {
        try {
            confirmationQueue.put(confirmation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BookingRequest receiveRequest() {
        try {
            return requestQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BookingConfirmation receiveConfirmation() {
        try {
            return confirmationQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
