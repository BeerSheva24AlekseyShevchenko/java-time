package telran.time;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class PastTemporalDateProximity implements TemporalAdjuster {
    private Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals) {
        this.temporals = temporals.clone();
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        int begin = 0;
        int end = temporals.length - 1;

        while (begin <= end) {
            int mid = (begin + end) / 2;
            int cmp = compare(temporals[mid], temporal);

            if (cmp <= 0) {
                end = mid - 1;
            } else {
                begin = mid + 1;
            }
        }

        return end < 0 ? null : temporals[end];
        
    }

    private int compare(Temporal t1, Temporal t2) {
        return (int) t1.until(t2, ChronoUnit.DAYS);
    }
}
