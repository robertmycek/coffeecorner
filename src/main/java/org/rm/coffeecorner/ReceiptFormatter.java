package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ReceiptFormatter {
    private final Map<String, BigDecimal> prices = Map.of(
            "small coffee", new BigDecimal("2.50"),
            "medium coffee", new BigDecimal("3.00"),
            "large coffee", new BigDecimal("3.50"),
            "freshly squeezed orange juice", new BigDecimal("3.95"),
            "bacon roll", new BigDecimal("4.50"),
            "extra milk", new BigDecimal("0.30"),
            "foamed milk", new BigDecimal("0.50"),
            "special roast", new BigDecimal("0.90")
    );
    private final List<String> products;

    public ReceiptFormatter(List<String> products) {
        this.products = Objects.requireNonNull(products);
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

        var total = BigDecimal.ZERO;

        for (var countByProduct : countsByProduct.entrySet()) {
            var product = countByProduct.getKey();
            var count = countByProduct.getValue();
            var unitPrice = prices.get(product);
            var price = unitPrice.multiply(new BigDecimal(count));
            builder.append(String.format((Locale) null, "%-30.30s %2d x%4.2f %6.2f", product, count, unitPrice, price)).append('\n');
            total = total.add(price);
        }

        builder.append("-".repeat(46)).append('\n');
        builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Total CHF", total));

        return builder.toString();
    }


}
