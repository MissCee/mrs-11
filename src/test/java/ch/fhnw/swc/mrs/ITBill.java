package ch.fhnw.swc.mrs;

import ch.fhnw.swc.mrs.data.SimpleMRSServices;
import ch.fhnw.swc.mrs.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.fhnw.swc.mrs.view.RentMovieController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ITBill {

    private User mickey;
    private Movie movie;
    private LocalDate today;

    SimpleMRSServices sms;
    Statement statement;

    @BeforeEach
    public void setUp() {
        sms = new SimpleMRSServices();
        PriceCategory.init();
        today = LocalDate.now();
    }

    @Test
    public void testPrintBill() {
        mickey = sms.createUser("Mouse", "Mickey", LocalDate.of(1990, 10, 10));
        movie = sms.createMovie("Joker", today, "Regular", 0);

        PrintStream previousConsole = System.out;
        ByteArrayOutputStream newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

       boolean rent = sms.createRental(mickey.getUserid(), movie.getMovieid(), today);
       assertEquals(true, rent);
        String[] lines = newConsole.toString().split("\n");
        
        assertEquals("Statement", lines[0]);
        assertEquals("=========", lines[1]);
        assertEquals("for: Mouse Mickey", lines[2]);
        assertEquals("", lines[3]);
        assertEquals("Days   Price  Title", lines[4]);
        assertEquals("-------------------", lines[5]);
        assertEquals("   0    0.00  Joker", lines[6]);

    }
}
