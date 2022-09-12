package org.rm.coffeecorner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.rm.coffeecorner.Product.*;

public class InputParser {
    private final String input;

    public InputParser(String input) {
        this.input = Objects.requireNonNull(input);
    }

    public List<Product> parse() {
        var products = new ArrayList<Product>();

        Arrays.stream(Objects.requireNonNull(input).split("[,.;:\\n]"))
                .map(String::strip)
                .filter(product -> product.length() > 0)
                .forEach(product -> {
                    if (product.contains("freshly squeezed orange juice")) {
                        products.add(FRESHLY_SQUEEZED_ORANGE_JUICE);
                    } else if (product.contains("bacon roll")) {
                        products.add(BACON_ROLL);
                    } else if (product.contains("coffee")) {

                        boolean knownCoffeeSize = false;

                        if (product.contains("small")) {
                            products.add(SMALL_COFFEE);
                            knownCoffeeSize = true;
                        } else if (product.contains("medium")) {
                            products.add(MEDIUM_COFFEE);
                            knownCoffeeSize = true;
                        } else if (product.contains("large")) {
                            products.add(LARGE_COFFEE);
                            knownCoffeeSize = true;
                        }

                        if (knownCoffeeSize) {
                            if (product.contains("extra milk")) {
                                products.add(EXTRA_MILK);
                            }
                            if (product.contains("foamed milk")) {
                                products.add(FOAMED_MILK);
                            }
                            if (product.contains("special roast")) {
                                products.add(SPECIAL_ROAST);
                            }
                        }
                    }
                });

        return products;
    }
}
