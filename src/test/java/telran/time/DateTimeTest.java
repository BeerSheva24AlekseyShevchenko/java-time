package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class DateTimeTest {

    @Test
    void nextFriday13Test() {
        LocalDate current = LocalDate.of(2024, 8, 11);
        LocalDate expected = LocalDate.of(2024, 9, 13);
        NextFriday13 adjuster = new NextFriday13();

        assertEquals(expected, current.with(adjuster));
        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));
    }

    @Test
    void pastTemporalDateProximityTest() {
        LocalDate date5Jan = LocalDate.of(2024, 1, 5);
        LocalDate date10Feb = LocalDate.of(2024, 2, 10);
        LocalDate date15Mar = LocalDate.of(2024, 3, 15);
        LocalDate date20Apr = LocalDate.of(2024, 4, 20);
        LocalDate date25May = LocalDate.of(2024, 5, 25);
        LocalDate date30Jun = LocalDate.of(2024, 6, 30);

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(new Temporal[]{
            date20Apr, date25May, date10Feb
        });

        assertNull(date5Jan.with(adjuster));
        assertNull(date10Feb.with(adjuster));
        assertEquals(date10Feb, date15Mar.with(adjuster));
        assertEquals(date10Feb, date20Apr.with(adjuster));
        assertEquals(date20Apr, date25May.with(adjuster));
        assertEquals(date25May, date30Jun.with(adjuster));

        assertEquals(MinguoDate.from(date10Feb), MinguoDate.from(date20Apr).with(adjuster));
        assertEquals(JapaneseDate.from(date10Feb), JapaneseDate.from(date20Apr).with(adjuster));

        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));
    }

}
