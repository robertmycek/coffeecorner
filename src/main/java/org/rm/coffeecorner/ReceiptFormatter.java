package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ReceiptFormatter {
    private final List<Product> products;
    private final BigDecimal discount;

    public ReceiptFormatter(List<Product> products, BigDecimal discount) {
        this.products = Objects.requireNonNull(products);
        this.discount = Objects.requireNonNull(discount);
    }

    public String format() {
        if (products.isEmpty()) {
            return "";
        }

        var builder = new StringBuilder();

        builder.append("Charlene's Coffee Corner").append('\n');
        builder.append("-".repeat(46)).append('\n');

        var countsByProduct = products.stream()
                .collect(groupingBy(identity(), LinkedHashMap::new, counting()));

        var total = ZERO;

        for (var countByProduct : countsByProduct.entrySet()) {
            var product = countByProduct.getKey();
            var count = countByProduct.getValue();
            var unitPrice = product.getUnitPrice();
            var price = unitPrice.multiply(new BigDecimal(count));
            builder.append(String.format((Locale) null, "%-30.30s %2d x%4.2f %6.2f", product.getName(), count, unitPrice, price)).append('\n');
            total = total.add(price);
        }

        builder.append("-".repeat(46)).append('\n');
        if (discount.signum() > 0) {
            builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Discount", discount.negate())).append('\n');
        }
        builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Total CHF", total.subtract(discount)));

        return builder.toString();
    }


}
