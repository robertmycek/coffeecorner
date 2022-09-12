package org.rm.coffeecorner.discount;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.rm.coffeecorner.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.rm.coffeecorner.Product.*;

class OneOfTheExtrasFreeDiscountTest {

    @ParameterizedTest
    @MethodSource
    void calculate(List<Product> products, BigDecimal expectedDiscount) {
        assertEquals(expectedDiscount, new OneOfTheExtrasFreeDiscount(products).calculate());
    }

    private static Stream<Arguments> calculate() {
        return Stream.of(
                arguments(List.of(BACON_ROLL), ZERO),
                arguments(List.of(SMALL_COFFEE), ZERO),
                arguments(List.of(BACON_ROLL, SMALL_COFFEE), ZERO),
                arguments(List.of(BACON_ROLL, SMALL_COFFEE, SPECIAL_ROAST), new BigDecimal("0.90")),
                arguments(List.of(BACON_ROLL, SMALL_COFFEE, SPECIAL_ROAST, FOAMED_MILK), new BigDecimal("0.50"))
        );
    }
}