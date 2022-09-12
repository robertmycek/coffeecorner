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


        Arrays.stream(input.split("[,.;:\\n]"))
                .forEach(value -> {
                    if (value.contains("freshly squeezed orange juice")) {
                        products.add(FRESHLY_SQUEEZED_ORANGE_JUICE);
                    } else if (value.contains("bacon roll")) {
                        products.add(BACON_ROLL);
                    } else if (value.contains("coffee")) {
                        Product coffee = parseCoffeeSize(value);
                        if (coffee != null) {
                            products.add(coffee);
                            if (value.contains("extra milk")) {
                                products.add(EXTRA_MILK);
                            }
                            if (value.contains("foamed milk")) {
                                products.add(FOAMED_MILK);
                            }
                            if (value.contains("special roast")) {
                                products.add(SPECIAL_ROAST);
                            }
                        }
                    }
                });

        return products;
    }

    private Product parseCoffeeSize(String value) {
        if (value.contains("small")) {
            return SMALL_COFFEE;
        } else if (value.contains("medium")) {
            return MEDIUM_COFFEE;
        } else if (value.contains("large")) {
            return LARGE_COFFEE;
        } else {
            return null;
        }
    }
}
