public class BookingRequest {
    private String hotelName;
    private int numRooms;

    public BookingRequest(String hotelName, int numRooms) {
        this.hotelName = hotelName;
        this.numRooms = numRooms;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getNumRooms() {
        return numRooms;
    }
}

