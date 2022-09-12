package org.rm.coffeecorner;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rm.coffeecorner.Product.BACON_ROLL;
import static org.rm.coffeecorner.Product.SMALL_COFFEE;

class ReceiptBuilderTest {

    @Test
    void shouldGroupTheSameProducts() {
        // given
        List<Product> products = List.of(SMALL_COFFEE, SMALL_COFFEE);
        BigDecimal discount = BigDecimal.valueOf(123);

        // when
        Receipt actual = new ReceiptBuilder(products, discount).build();

        // then
        ReceiptItem receiptItem = new ReceiptItem("small coffee", new BigDecimal("2.50"), 2L);
        Receipt expected = new Receipt(List.of(receiptItem), BigDecimal.valueOf(123));

        assertEquals(expected, actual);
    }

    @Test
    void shouldAddAllProductsSortedByName() {
        // given
        List<Product> products = List.of(SMALL_COFFEE, BACON_ROLL);
        BigDecimal discount = BigDecimal.valueOf(123);

        // when
        Receipt actual = new ReceiptBuilder(products, discount).build();

        // then
        ReceiptItem smallCoffee = new ReceiptItem("small coffee", new BigDecimal("2.50"), 1L);
        ReceiptItem baconRoll = new ReceiptItem("bacon roll", new BigDecimal("4.50"), 1L);
        Receipt expected = new Receipt(List.of(baconRoll, smallCoffee), BigDecimal.valueOf(123));

        assertEquals(expected, actual);
    }
}