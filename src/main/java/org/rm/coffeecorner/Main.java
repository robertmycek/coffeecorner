package org.rm.coffeecorner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Main {
    private final Map<String, BigDecimal> prices = Map.of(
            "small coffee", new BigDecimal("2.50"),
            "medium coffee", new BigDecimal("3.00"),
            "large coffee", new BigDecimal("3.50"),
            "freshly squeezed orange juice", new BigDecimal("3.95"),
            "bacon roll", new BigDecimal("4.50"),
            "extra milk", new BigDecimal("0.30")
    );
    private final List<String> products = new ArrayList<>();

    public Main(String input) {
        Arrays.stream(Objects.requireNonNull(input).split("[,.;:\\n]"))
                .map(String::strip)
                .filter(product -> product.length() > 0)
                .forEach(product -> {
                    if (product.contains("small coffee")) {
                        products.add("small coffee");
                        if (product.contains("extra milk")) {
                            products.add("extra milk");
                        }
                    } else if (product.contains("medium coffee")) {
                        products.add("medium coffee");
                        if (product.contains("extra milk")) {
                            products.add("extra milk");
                        }
                    } else if (product.contains("large coffee")) {
                        products.add("large coffee");
                        if (product.contains("extra milk")) {
                            products.add("extra milk");
                        }
                    } else {
                        products.add(product);
                    }
                });
    }

    public static void main(String[] args) throws IOException {
        var input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        var receipt = new Main(input).generateReceipt();
        System.out.print(receipt);
        System.out.flush();
    }

    public String generateReceipt() {
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
