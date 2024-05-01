import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Lese Konfiguration
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int arrivalRate = Integer.parseInt(properties.getProperty("arrivalRate"));
        int processingTimeMean = Integer.parseInt(properties.getProperty("processingTimeMean"));
        int processingTimeStdDev = Integer.parseInt(properties.getProperty("processingTimeStdDev"));
        int messageDelayMean = Integer.parseInt(properties.getProperty("messageDelayMean"));
        int messageDelayStdDev = Integer.parseInt(properties.getProperty("messageDelayStdDev"));
        double failureProbability = Double.parseDouble(properties.getProperty("failureProbability"));

        // Starte Hotel Services
        ExecutorService hotelExecutor = Executors.newFixedThreadPool(2);
        hotelExecutor.submit(new HotelService("UrlaubInDenBergen", processingTimeMean, processingTimeStdDev, failureProbability));
        hotelExecutor.submit(new HotelService("FluchtAnDieSee", processingTimeMean, processingTimeStdDev, failureProbability));

        // Simuliere Buchungsanfragen
        Random rand = new Random();
        TravelBroker travelBroker = new TravelBroker();
        for (int i = 0; i < 3; i++) {
            BookingRequest[] bookingRequests = {
                new BookingRequest("UrlaubInDenBergen", 2),
                new BookingRequest("FluchtAnDieSee", 3),
                new BookingRequest("UrlaubInDenBergen", 1)
            };

            travelBroker.bookTravel(bookingRequests);

            // Wartezeit zwischen Buchungen entsprechend der Ankunftsrate
            try {
                TimeUnit.SECONDS.sleep((long) (1.0 / arrivalRate));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Warte auf Buchungsbestätigungen
        for (int i = 0; i < 3; i++) {
            BookingConfirmation confirmation = MessageBroker.getInstance().receiveConfirmation();
            System.out.println("Confirmation: " + confirmation.getMessage());
        }

        // Beende Executor
        hotelExecutor.shutdown();
        try {
            if (!hotelExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                hotelExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            hotelExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}


