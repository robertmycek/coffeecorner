package org.rm.coffeecorner;

import org.rm.coffeecorner.discount.Discount;
import org.rm.coffeecorner.discount.Every5thBeverageFreeDiscount;
import org.rm.coffeecorner.discount.OneOfTheExtrasFreeDiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public class DiscountCalculator {
    private final List<Discount> discounts = new ArrayList<>();

    public DiscountCalculator(List<Product> products, Integer stumpCount) {
        Objects.requireNonNull(products);
        discounts.add(new OneOfTheExtrasFreeDiscount(products));
        discounts.add(new Every5thBeverageFreeDiscount(products, stumpCount));
    }

    public BigDecimal calculate() {
        return discounts.stream()
                .map(Discount::calculate)
                .reduce(ZERO, BigDecimal::add);
    }
}
