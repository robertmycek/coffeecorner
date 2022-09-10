package org.rm.coffeecorner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Main {
    private final Map<String, BigDecimal> prices = Map.of(
            "small coffee", new BigDecimal("2.50"),
            "medium coffee", new BigDecimal("3.00"),
            "large coffee", new BigDecimal("3.50")
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

        BigDecimal total = BigDecimal.ZERO;
        for (String product : products) {
            BigDecimal price = prices.get(product);
            total = total.add(price);
            builder.append(String.format((Locale) null, "%-39.39s %6.2f", product, price)).append('\n');
        }

        builder.append("-".repeat(46)).append('\n');
        builder.append(String.format((Locale) null, "%-35.35s %10.2f", "Total CHF", total));

        return builder.toString();
    }
}
