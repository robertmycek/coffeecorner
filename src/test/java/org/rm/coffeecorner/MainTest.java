package org.rm.coffeecorner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MainTest {

    private final PrintStream systemOut = System.out;
    private final InputStream systemIn = System.in;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    private static Stream<Arguments> shouldPrintReceiptForOneProduct() {
        return Stream.of(
                arguments(
                        "small coffee",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                small coffee                    1 x2.50   2.50
                                ----------------------------------------------
                                Total CHF                                 2.50"""
                ),
                arguments(
                        "medium coffee",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                medium coffee                   1 x3.00   3.00
                                ----------------------------------------------
                                Total CHF                                 3.00"""
                ),
                arguments(
                        "large coffee",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                large coffee                    1 x3.50   3.50
                                ----------------------------------------------
                                Total CHF                                 3.50"""
                ),
                arguments(
                        "freshly squeezed orange juice",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                freshly squeezed orange juice   1 x3.95   3.95
                                ----------------------------------------------
                                Total CHF                                 3.95"""
                ),
                arguments(
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
                arguments(
                        "small coffee with extra milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                extra milk                      1 x0.30   0.30
                                small coffee                    1 x2.50   2.50
                                ----------------------------------------------
                                Total CHF                                 2.80"""
                ),
                arguments(
                        "medium coffee with extra milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                extra milk                      1 x0.30   0.30
                                medium coffee                   1 x3.00   3.00
                                ----------------------------------------------
                                Total CHF                                 3.30"""
                ),
                arguments(
                        "large coffee with extra milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                extra milk                      1 x0.30   0.30
                                large coffee                    1 x3.50   3.50
                                ----------------------------------------------
                                Total CHF                                 3.80"""
                ),
                arguments(
                        "large coffee with foamed milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                foamed milk                     1 x0.50   0.50
                                large coffee                    1 x3.50   3.50
                                ----------------------------------------------
                                Total CHF                                 4.00"""
                ),
                arguments(
                        "medium coffee with extra milk and foamed milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                extra milk                      1 x0.30   0.30
                                foamed milk                     1 x0.50   0.50
                                medium coffee                   1 x3.00   3.00
                                ----------------------------------------------
                                Total CHF                                 3.80"""
                ),
                arguments(
                        "large coffee with special roast",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                large coffee                    1 x3.50   3.50
                                special roast                   1 x0.90   0.90
                                ----------------------------------------------
                                Total CHF                                 4.40"""
                ),
                arguments(
                        "medium coffee with special roast extra milk and foamed milk",
                        """
                                Charlene's Coffee Corner
                                ----------------------------------------------
                                extra milk                      1 x0.30   0.30
                                foamed milk                     1 x0.50   0.50
                                medium coffee                   1 x3.00   3.00
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
                bacon roll                      1 x4.50   4.50
                freshly squeezed orange juice   1 x3.95   3.95
                large coffee                    1 x3.50   3.50
                medium coffee                   1 x3.00   3.00
                small coffee                    1 x2.50   2.50
                ----------------------------------------------
                Total CHF                                17.45""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldAddDiscountOfTheCheapestExtrasWhenCustomerOrdersBeverageAndSnack() throws IOException {
        setInput("small coffee with special roast and extra milk, bacon roll");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                bacon roll                      1 x4.50   4.50
                extra milk                      1 x0.30   0.30
                small coffee                    1 x2.50   2.50
                special roast                   1 x0.90   0.90
                ----------------------------------------------
                Discount                                 -0.30
                Total CHF                                 7.90""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldAddDiscountForEvery5thBeverageForCustomerWithStampCard() throws IOException {
        //given
        var stumpCount = "12";
        var input = """
                small coffee
                medium coffee
                large coffee
                large coffee
                large coffee
                bacon roll
                bacon roll
                bacon roll
                bacon roll
                medium coffee
                large coffee
                medium coffee
                large coffee""";

        setInput(input);

        // when
        Main.main(new String[]{stumpCount});

        // then
        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                bacon roll                      4 x4.50  18.00
                large coffee                    5 x3.50  17.50
                medium coffee                   3 x3.00   9.00
                small coffee                    1 x2.50   2.50
                ----------------------------------------------
                Discount                                 -6.50
                Total CHF                                40.50""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldGroupTheSameProductOnReceipt() throws IOException {
        setInput("small coffee,medium coffee,large coffee,small coffee,medium coffee,small coffee");

        Main.main(new String[]{});

        var expectedReceipt = """
                Charlene's Coffee Corner
                ----------------------------------------------
                large coffee                    1 x3.50   3.50
                medium coffee                   2 x3.00   6.00
                small coffee                    3 x2.50   7.50
                ----------------------------------------------
                Total CHF                                17.00""";

        assertEquals(expectedReceipt, getOutput());
    }

    @Test
    void shouldIgnoreUnknownProducts() throws IOException {
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