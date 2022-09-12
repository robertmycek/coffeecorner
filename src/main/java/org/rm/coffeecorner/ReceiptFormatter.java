package org.rm.coffeecorner;

import java.util.Locale;
import java.util.Objects;

public class ReceiptFormatter {
    private final Receipt receipt;

    public ReceiptFormatter(Receipt receipt) {
        this.receipt = Objects.requireNonNull(receipt);
    }

    public String format() {
        var totalPrice = receipt.totalPrice();
        var discount = receipt.discount();
        var receiptItems = receipt.receiptItems();

        if (receipt.receiptItems().isEmpty()) {
            return "";
        }

        var builder = new StringBuilder();
        builder.append("Charlene's Coffee Corner").append('\n');
        builder.append("-".repeat(46)).append('\n');

        for (var item : receiptItems) {
            String itemLine = String.format((Locale) null, "%-30.30s %2d x%4.2f %6.2f", item.name(), item.count(), item.unitPrice(), item.totalPrice());
            builder.append(itemLine).append('\n');
        }

        builder.append("-".repeat(46)).append('\n');
        if (discount.signum() > 0) {
            builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Discount", discount.negate())).append('\n');
        }
        builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Total CHF", totalPrice));

        return builder.toString();
    }
}
