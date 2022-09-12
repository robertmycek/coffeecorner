package org.rm.coffeecorner;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rm.coffeecorner.Product.*;

class DiscountCalculatorTest {

    @Test
    void shouldSumUpAllDiscounts() {
        // given
        List<Product> products = List.of(SMALL_COFFEE, BACON_ROLL, FOAMED_MILK);
        Integer stumpCount = 4;

        // when
        BigDecimal discount = new DiscountCalculator(products, stumpCount).calculate();

        // then
        assertEquals(new BigDecimal("3.00"), discount);
    }
}