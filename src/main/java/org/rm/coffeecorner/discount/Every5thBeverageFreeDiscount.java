package org.rm.coffeecorner.discount;

import org.rm.coffeecorner.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;
import static org.rm.coffeecorner.Product.ProductType.BEVERAGE;

public class Every5thBeverageFreeDiscount implements Discount {
    private final List<Product> products;
    private final Integer stumpCount;

    public Every5thBeverageFreeDiscount(List<Product> products, Integer stumpCount) {
        this.products = Objects.requireNonNull(products);
        this.stumpCount = stumpCount;
    }

    @Override
    public BigDecimal calculate() {
        if (Objects.isNull(stumpCount)) {
            return ZERO;
        }

        var discount = ZERO;
        var count = stumpCount;

        for (Product product : products) {
            if (product.getProductType() == BEVERAGE) {
                count++;
                if (count % 5 == 0) {
                    discount = discount.add(product.getUnitPrice());
                }
            }
        }

        return discount;
    }
}
