package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ReceiptBuilder {

    private final List<Product> products;
    private final BigDecimal discount;

    public ReceiptBuilder(List<Product> products, BigDecimal discount) {
        this.products = Objects.requireNonNull(products);
        this.discount = Objects.requireNonNull(discount);
    }

    public Receipt build() {
        return new Receipt(createReceiptItems(), discount);
    }

    private List<ReceiptItem> createReceiptItems() {
        var receiptItems = new ArrayList<ReceiptItem>();

        products.stream()
                .collect(groupingBy(identity(), counting()))
                .forEach(((product, count) ->
                        receiptItems.add(new ReceiptItem(product.getName(), product.getUnitPrice(), count))));

        return receiptItems.stream()
                .sorted(Comparator.comparing(ReceiptItem::name))
                .toList();
    }
}
