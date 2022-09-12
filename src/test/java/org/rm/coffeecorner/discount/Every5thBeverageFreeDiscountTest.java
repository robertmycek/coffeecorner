package org.rm.coffeecorner.discount;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.rm.coffeecorner.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.rm.coffeecorner.Product.BACON_ROLL;
import static org.rm.coffeecorner.Product.SMALL_COFFEE;

class Every5thBeverageFreeDiscountTest {

    private static Stream<Arguments> calculate() {
        return Stream.of(
                arguments(null, Collections.nCopies(5, SMALL_COFFEE), ZERO),
                arguments(0, Collections.nCopies(5, SMALL_COFFEE), new BigDecimal("2.50")),
                arguments(0, Collections.nCopies(10, SMALL_COFFEE), new BigDecimal("5.00")),
                arguments(4, Collections.nCopies(1, SMALL_COFFEE), new BigDecimal("2.50")),
                arguments(4, List.of(BACON_ROLL, BACON_ROLL), ZERO),
                arguments(4, List.of(BACON_ROLL, BACON_ROLL, SMALL_COFFEE), new BigDecimal("2.50"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void calculate(Integer stampCount, List<Product> products, BigDecimal expectedDiscount) {
        assertEquals(expectedDiscount, new Every5thBeverageFreeDiscount(products, stampCount).calculate());
    }
}