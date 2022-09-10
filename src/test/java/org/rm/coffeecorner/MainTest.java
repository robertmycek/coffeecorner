package org.rm.coffeecorner;

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

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                    1 x2.50   2.50
                ----------------------------------------------
                Total CHF                                 2.50""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldPrintReceiptForManyProducts() throws IOException {
        setInput("small coffee,medium coffee,large coffee,freshly squeezed orange juice,bacon roll");

        Main.main(new String[]{});

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                    1 x2.50   2.50
                medium coffee                   1 x3.00   3.00
                large coffee                    1 x3.50   3.50
                freshly squeezed orange juice   1 x3.95   3.95
                bacon roll                      1 x4.50   4.50
                ----------------------------------------------
                Total CHF                                17.45""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldPrintReceiptForMediumCoffee() throws IOException {
        setInput("medium coffee");

        Main.main(new String[]{});

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                medium coffee                   1 x3.00   3.00
                ----------------------------------------------
                Total CHF                                 3.00""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldPrintReceiptForLargeCoffee() throws IOException {
        setInput("large coffee");

        Main.main(new String[]{});

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                large coffee                    1 x3.50   3.50
                ----------------------------------------------
                Total CHF                                 3.50""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldPrintReceiptForFreshlySqueezedOrangeJuice() throws IOException {
        setInput("freshly squeezed orange juice");

        Main.main(new String[]{});

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                freshly squeezed orange juice   1 x3.95   3.95
                ----------------------------------------------
                Total CHF                                 3.95""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldPrintReceiptForBaconRoll() throws IOException {
        setInput("bacon roll");

        Main.main(new String[]{});

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                bacon roll                      1 x4.50   4.50
                ----------------------------------------------
                Total CHF                                 4.50""";

        assertEquals(expected, getOutput());
    }

    @Test
    void shouldGroupTheSameProductOnReceipt() throws IOException {
        setInput("small coffee,medium coffee,large coffee,small coffee,medium coffee,small coffee");

        Main.main(new String[]{});

        var expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                    3 x2.50   7.50
                medium coffee                   2 x3.00   6.00
                large coffee                    1 x3.50   3.50
                ----------------------------------------------
                Total CHF                                17.00""";

        assertEquals(expected, getOutput());
    }

    private void setInput(String value) {
        System.setIn(new ByteArrayInputStream(value.getBytes()));
    }

    private String getOutput() {
        return output.toString().replace("\r", "");
    }
}