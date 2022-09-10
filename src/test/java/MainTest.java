import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private final PrintStream systemOut = System.out;
    private final InputStream systemIn = System.in;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    @Test
    void shouldPrintReceiptForOneSmallCoffee() throws IOException {
        setInput("small coffee");

        Main.main(new String[]{});

        String expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                              2.50
                ----------------------------------------------
                Total CHF                                 2.50""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldPrintReceiptForManySmallCoffee() throws IOException {
        setInput("small coffee,small coffee");

        Main.main(new String[]{});

        String expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                              2.50
                small coffee                              2.50
                ----------------------------------------------
                Total CHF                                 5.00""";

        assertEquals(expected, getOutput());
    }

    private void setInput(String value) {
        System.setIn(new ByteArrayInputStream(value.getBytes()));
    }

    private String getOutput() {
        return output.toString().replace("\r", "");
    }
}