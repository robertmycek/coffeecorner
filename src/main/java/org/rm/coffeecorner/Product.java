package org.rm.coffeecorner;

import java.math.BigDecimal;
import java.util.Locale;

import static org.rm.coffeecorner.Product.ProductType.*;

public class Product {

    public static Product SMALL_COFFEE = new Product("small coffee", new BigDecimal("2.50"), BEVERAGE);
    public static Product MEDIUM_COFFEE = new Product("medium coffee", new BigDecimal("3.00"), BEVERAGE);
    public static Product LARGE_COFFEE = new Product("large coffee", new BigDecimal("3.50"), BEVERAGE);
    public static Product FRESHLY_SQUEEZED_ORANGE_JUICE = new Product("freshly squeezed orange juice", new BigDecimal("3.95"), BEVERAGE);
    public static Product BACON_ROLL = new Product("bacon roll", new BigDecimal("4.50"), SNACK);
    public static Product EXTRA_MILK = new Product("extra milk", new BigDecimal("0.30"), EXTRAS);
    public static Product FOAMED_MILK = new Product("foamed milk", new BigDecimal("0.50"), EXTRAS);
    public static Product SPECIAL_ROAST = new Product("special roast", new BigDecimal("0.90"), EXTRAS);

    private final String name;
    private final BigDecimal unitPrice;
    private final ProductType productType;

    private Product(String name, BigDecimal unitPrice, ProductType productType) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public ProductType getProductType() {
        return productType;
    }

    @Override
    public String toString() {
        return String.format((Locale) null, "Product{'%s', %.2f, %s}'", name, unitPrice, productType);
    }

    public enum ProductType {
        SNACK, EXTRAS, BEVERAGE
    }
}
