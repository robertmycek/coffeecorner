package org.rm.coffeecorner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rm.coffeecorner.Product.BACON_ROLL;

class InputParserTest {

    @ParameterizedTest
    @ValueSource(strings = {",", ";", ":", "\n", "."})
    void shouldSplitInputListBySeparator(String separator) {
        var input = ("bacon roll" + separator + "bacon roll");

        List<Product> products = new InputParser(input).parse();

        assertEquals(List.of(BACON_ROLL, BACON_ROLL), products);
    }

    @Test
    void shouldIgnoreLeadingAndTrailingWhiteSpaces() {
        var input = "  \tbacon roll  \t  ";

        List<Product> products = new InputParser(input).parse();

        assertEquals(List.of(BACON_ROLL), products);
    }

    @Test
    void shouldIgnoreEmptyProducts() {
        var input = " ,,  ,";

        List<Product> products = new InputParser(input).parse();

        assertEquals(List.of(), products);
    }

    @Test
    void shouldIgnoreUnknownProducts() {
        var input = "foo,bar";

        List<Product> products = new InputParser(input).parse();

        assertEquals(List.of(), products);
    }
}