package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster {
    private Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals) {
        temporals = temporals.clone();
        Arrays.sort(temporals, this::compare);
        this.temporals = temporals;
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        Temporal result = null;

        int index = getGreaterOfLeastIndex(temporal);

        if (index >= 0) {
            result = temporal
                .with(ChronoField.YEAR, temporals[index].get(ChronoField.YEAR))
                .with(ChronoField.MONTH_OF_YEAR, temporals[index].get(ChronoField.MONTH_OF_YEAR))
                .with(ChronoField.DAY_OF_MONTH, temporals[index].get(ChronoField.DAY_OF_MONTH));
        }

        return result;
    }

    private int getGreaterOfLeastIndex(Temporal temporal) {
        int begin = 0;
        int end = temporals.length - 1;

        while (begin <= end) {
            int mid = (begin + end) / 2;
            int cmp = compare(temporals[mid], temporal);

            if (cmp < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return end;
    }

    private int compare(Temporal t1, Temporal t2) {
        long range = t2.until(t1, ChronoUnit.DAYS);
        return range == 0 ? 0 : range > 0 ? 1 : -1;
    }
}
