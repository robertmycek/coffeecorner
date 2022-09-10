package org.rm.coffeecorner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class Main {
    private final Map<String, BigDecimal> prices = Map.of(
            "small coffee", new BigDecimal("2.50"),
            "medium coffee", new BigDecimal("3.00"),
            "large coffee", new BigDecimal("3.50"),
            "freshly squeezed orange juice", new BigDecimal("3.95"),
            "bacon roll", new BigDecimal("4.50")
    );
    private final List<String> products;

    public Main(String input) {
        this.products = Arrays.stream(Objects.requireNonNull(input).split(",")).collect(toList());
    }

    public static void main(String[] args) throws IOException {
        String input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        String receipt = new Main(input).generateReceipt();
        System.out.print(receipt);
        System.out.flush();
    }

    public String generateReceipt() {
        StringBuilder builder = new StringBuilder();

        builder.append("Charlene's Coffee Corner").append('\n');
        builder.append("-".repeat(46)).append('\n');

        Map<String, Long> countsPerProduct = products.stream()
                .collect(groupingBy(Function.identity(), LinkedHashMap::new, counting()));

        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<String, Long> countPerProduct : countsPerProduct.entrySet()) {
            String product = countPerProduct.getKey();
            long count = countPerProduct.getValue();
            BigDecimal unitPrice = prices.get(product);
            BigDecimal price = unitPrice.multiply(new BigDecimal(count));
            builder.append(String.format((Locale) null, "%-30.30s %2d x%4.2f %6.2f", product, count, unitPrice, price)).append('\n');
            total = total.add(price);
        }

        builder.append("-".repeat(46)).append('\n');
        builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Total CHF", total));

        return builder.toString();
    }
}
