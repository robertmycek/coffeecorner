package org.rm.coffeecorner;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptItemTest {

    @Test
    void totalPrice() {
        assertEquals(BigDecimal.valueOf(6), new ReceiptItem("name", BigDecimal.valueOf(3), 2).totalPrice());
    }
}