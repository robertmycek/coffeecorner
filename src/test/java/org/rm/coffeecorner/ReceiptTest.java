package org.rm.coffeecorner;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptTest {

    @Test
    void totalPrice() {
        // given
        List<ReceiptItem> receiptItems = List.of(receiptItem(30), receiptItem(50));
        BigDecimal discount = BigDecimal.valueOf(15);

        // when
        BigDecimal totalPrice = new Receipt(receiptItems, discount).totalPrice();

        // then
        assertEquals(BigDecimal.valueOf(65), totalPrice);

    }

    private static ReceiptItem receiptItem(int val) {
        return new ReceiptItem("foo", BigDecimal.valueOf(val), 1);
    }
}