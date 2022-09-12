package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;
import static org.rm.coffeecorner.Product.ProductType.*;

public class DiscountCalculator {
    private final List<Product> products;

    public DiscountCalculator(List<Product> products) {
        this.products = Objects.requireNonNull(products);
    }

    public BigDecimal calculate() {
        var hasSnack = products.stream().anyMatch(product -> product.getProductType() == SNACK);
        var hasBeverage = products.stream().anyMatch(product -> product.getProductType() == BEVERAGE);

        if (!hasSnack || !hasBeverage) {
            return ZERO;
        }

        return products.stream()
                .filter(product -> product.getProductType() == EXTRAS)
                .map(Product::getUnitPrice)
                .min(BigDecimal::compareTo)
                .orElse(ZERO);
    }
}
