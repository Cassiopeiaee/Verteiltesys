public class TravelBroker {
    public void bookTravel(BookingRequest[] bookingRequests) {
        for (BookingRequest request : bookingRequests) {
            MessageBroker.getInstance().sendRequest(request);
        }
    }
}

