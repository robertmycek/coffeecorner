package org.rm.coffeecorner;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptFormatterTest {

    @Test
    void shouldReturnEmptyStringWhenNotProducts() {
        // given
        Receipt receipt = new Receipt(List.of(), ZERO);

        // when
        String actual = new ReceiptFormatter(receipt).format();

        // then
        assertEquals("", actual);
    }

    @Test
    void shouldFormatReceipt() {
        // given
        ReceiptItem foo = new ReceiptItem("foo", new BigDecimal("1.11"), 3L);
        ReceiptItem bar = new ReceiptItem("bar", new BigDecimal("2.22"), 2L);
        Receipt receipt = new Receipt(List.of(foo, bar), ZERO);

        // when
        String actual = new ReceiptFormatter(receipt).format();

        // then
        String expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                foo                             3 x1.11   3.33
                bar                             2 x2.22   4.44
                ----------------------------------------------
                Total CHF                                 7.77""";

        assertEquals(expected, actual);
    }

    @Test
    void shouldAddDiscountLineIfDiscountIsPositive() {
        // given
        ReceiptItem foo = new ReceiptItem("foo", new BigDecimal("1.11"), 3L);
        ReceiptItem bar = new ReceiptItem("bar", new BigDecimal("2.22"), 2L);
        Receipt receipt = new Receipt(List.of(foo, bar), ONE);

        // when
        String actual = new ReceiptFormatter(receipt).format();

        // then
        String expected = """
                Charlene's Coffee Corner
                ----------------------------------------------
                foo                             3 x1.11   3.33
                bar                             2 x2.22   4.44
                ----------------------------------------------
                Discount                                 -1.00
                Total CHF                                 6.77""";

        assertEquals(expected, actual);
    }

}