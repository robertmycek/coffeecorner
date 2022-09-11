package org.rm.coffeecorner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private final PrintStream systemOut = System.out;
    private final InputStream systemIn = System.in;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    private static Stream<Arguments> shouldPrintReceiptForOneProduct() {
        return Stream.of(
                Arguments.of(
                        "small coffee",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                small coffee                    1 x2.50   2.50
                                ----------------------------------------------
                                Total CHF                                 2.50"""
                ),
                Arguments.of(
                        "medium coffee",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                medium coffee                   1 x3.00   3.00
                                ----------------------------------------------
                                Total CHF                                 3.00"""
                ),
                Arguments.of(
                        "large coffee",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                large coffee                    1 x3.50   3.50
                                ----------------------------------------------
                                Total CHF                                 3.50"""
                ),
                Arguments.of(
                        "freshly squeezed orange juice",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                freshly squeezed orange juice   1 x3.95   3.95
                                ----------------------------------------------
                                Total CHF                                 3.95"""
                ),
                Arguments.of(
                        "bacon roll",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                bacon roll                      1 x4.50   4.50
                                ----------------------------------------------
                                Total CHF                                 4.50"""
                )
        );
    }

    private static Stream<Arguments> shouldPrintReceiptForCoffeeWithExtras() {
        return Stream.of(
                Arguments.of(
                        "small coffee with extra milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                small coffee                    1 x2.50   2.50
                                extra milk                      1 x0.30   0.30
                                ----------------------------------------------
                                Total CHF                                 2.80"""
                ),
                Arguments.of(
                        "medium coffee with extra milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                medium coffee                   1 x3.00   3.00
                                extra milk                      1 x0.30   0.30
                                ----------------------------------------------
                                Total CHF                                 3.30"""
                ),
                Arguments.of(
                        "large coffee with extra milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                large coffee                    1 x3.50   3.50
                                extra milk                      1 x0.30   0.30
                                ----------------------------------------------
                                Total CHF                                 3.80"""
                ),
                Arguments.of(
                        "large coffee with foamed milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                large coffee                    1 x3.50   3.50
                                foamed milk                     1 x0.50   0.50
                                ----------------------------------------------
                                Total CHF                                 4.00"""
                ),
                Arguments.of(
                        "medium coffee with extra milk and foamed milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                medium coffee                   1 x3.00   3.00
                                extra milk                      1 x0.30   0.30
                                foamed milk                     1 x0.50   0.50
                                ----------------------------------------------
                                Total CHF                                 3.80"""
                ),
                Arguments.of(
                        "large coffee with special roast",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                large coffee                    1 x3.50   3.50
                                special roast                   1 x0.90   0.90
                                ----------------------------------------------
                                Total CHF                                 4.40"""
                ),
                Arguments.of(
                        "medium coffee with special roast extra milk and foamed milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                medium coffee                   1 x3.00   3.00
                                extra milk                      1 x0.30   0.30
                                foamed milk                     1 x0.50   0.50
                                special roast                   1 x0.90   0.90
                                ----------------------------------------------
                                Total CHF                                 4.70"""
                )
        );
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    @ParameterizedTest
    @MethodSource
    void shouldPrintReceiptForOneProduct(String input, String expectedReceipt) throws IOException {
        setInput(input);

        Main.main(new String[]{});

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldPrintReceiptForManyProducts() throws IOException {
        setInput("small coffee,medium coffee,large coffee,freshly squeezed orange juice,bacon roll");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                    1 x2.50   2.50
                medium coffee                   1 x3.00   3.00
                large coffee                    1 x3.50   3.50
                freshly squeezed orange juice   1 x3.95   3.95
                bacon roll                      1 x4.50   4.50
                ----------------------------------------------
                Total CHF                                17.45""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldGroupTheSameProductOnReceipt() throws IOException {
        setInput("small coffee,medium coffee,large coffee,small coffee,medium coffee,small coffee");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                small coffee                    3 x2.50   7.50
                medium coffee                   2 x3.00   6.00
                large coffee                    1 x3.50   3.50
                ----------------------------------------------
                Total CHF                                17.00""";

        assertEquals(expectedReceipt, getOutput());
    }

    @ParameterizedTest
    @ValueSource(strings = {",", ";", ":", "\n", "."})
    void shouldSplitInputListBySeparator(String separator) throws IOException {
        setInput("bacon roll" + separator + "bacon roll");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                bacon roll                      2 x4.50   9.00
                ----------------------------------------------
                Total CHF                                 9.00""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldStripInputAndRemoveEmptyValues() throws IOException {
        setInput(",.;:\n  bacon roll    ,  bacon roll\n     ");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                bacon roll                      2 x4.50   9.00
                ----------------------------------------------
                Total CHF                                 9.00""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldIgnoreUnknownProduct() throws IOException {
        setInput("iphone 14, bacon roll");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                bacon roll                      1 x4.50   4.50
                ----------------------------------------------
                Total CHF                                 4.50""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldNotGenerateReceiptWhenAllProductAreUnknown() throws IOException {
        setInput("iphone 14, keyboard");

        Main.main(new String[]{});

        assertEquals("", getOutput());
    }

    @ParameterizedTest
    @MethodSource
    void shouldPrintReceiptForCoffeeWithExtras(String input, String expectedReceipt) throws IOException {
        setInput(input);

        Main.main(new String[]{});

        assertEquals(expectedReceipt, getOutput());
    }

    private void setInput(String value) {
        System.setIn(new ByteArrayInputStream(value.getBytes()));
    }

    private String getOutput() {
        return output.toString().replace("\r", "");
    }

}