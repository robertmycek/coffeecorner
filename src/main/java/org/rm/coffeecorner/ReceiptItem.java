package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.Objects;

public record ReceiptItem(String name, BigDecimal unitPrice, long count) {
    public ReceiptItem(String name, BigDecimal unitPrice, long count) {
        this.name = Objects.requireNonNull(name);
        this.unitPrice = Objects.requireNonNull(unitPrice);
        this.count = count;
    }

    public BigDecimal totalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(count));
    }
}
