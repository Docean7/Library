package Server.Tags;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ExpirationFunc  {
    private static final double FORFEIT = 10;
    public static double calculateForfeit(String expDate){
        LocalDate elDate = LocalDate.parse(expDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate now = LocalDate.now();
        int isAfter = now.compareTo(elDate);
        if(isAfter > 0){
            long days = ChronoUnit.DAYS.between(elDate, now);
            return days * FORFEIT;
        }
        return 0;
    }
}
