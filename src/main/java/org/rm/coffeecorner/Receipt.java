package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public record Receipt(List<ReceiptItem> receiptItems, BigDecimal discount) {
    public Receipt {
        Objects.requireNonNull(receiptItems);
        Objects.requireNonNull(discount);
    }

    public BigDecimal totalPrice() {
        return receiptItems.stream()
                .map(ReceiptItem::totalPrice)
                .reduce(ZERO, BigDecimal::add)
                .subtract(discount);
    }
}
