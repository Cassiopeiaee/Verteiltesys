public class BookingConfirmation {
    private boolean success;
    private String message;

    public BookingConfirmation(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
