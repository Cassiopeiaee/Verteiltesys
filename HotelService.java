import java.util.concurrent.TimeUnit;
import java.util.Random;

public class HotelService implements Runnable {
    private String name;
    private int processingTimeMean;
    private int processingTimeStdDev;
    private double failureProbability;

    public HotelService(String name, int processingTimeMean, int processingTimeStdDev, double failureProbability) {
        this.name = name;
        this.processingTimeMean = processingTimeMean;
        this.processingTimeStdDev = processingTimeStdDev;
        this.failureProbability = failureProbability;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Simuliere Bearbeitungszeit
                TimeUnit.SECONDS.sleep(getProcessingTime());
                // Simuliere zufälligen Fehler
                handleFailure();
                // Simuliere erfolgreiche Buchung
                System.out.println(name + ": Buchung erfolgreich");
                // Sende Bestätigung an Broker
                MessageBroker.getInstance().sendConfirmation(new BookingConfirmation(true, "Buchung erfolgreich"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getProcessingTime() {
        Random rand = new Random();
        return (int) Math.round(rand.nextGaussian() * processingTimeStdDev + processingTimeMean);
    }

    private void handleFailure() {
        Random rand = new Random();
        double randomValue = rand.nextDouble();
        if (randomValue < failureProbability) {
            System.out.println(name + ": Buchung fehlgeschlagen");
            // Hier implementieren Sie die Logik für den Umgang mit Fehlern
        }
    }
}

