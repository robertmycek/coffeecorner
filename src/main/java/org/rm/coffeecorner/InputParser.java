package org.rm.coffeecorner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InputParser {

    private final String input;

    public InputParser(String input) {
        this.input = Objects.requireNonNull(input);
    }

    public List<String> parse() {
        var products = new ArrayList<String>();

        Arrays.stream(Objects.requireNonNull(input).split("[,.;:\\n]"))
                .map(String::strip)
                .filter(product -> product.length() > 0)
                .forEach(product -> {
                    if (product.contains("coffee")) {

                        boolean knownCoffeeSize = false;

                        if (product.contains("small")) {
                            products.add("small coffee");
                            knownCoffeeSize = true;
                        } else if (product.contains("large")) {
                            products.add("large coffee");
                            knownCoffeeSize = true;
                        } else if (product.contains("medium")) {
                            products.add("medium coffee");
                            knownCoffeeSize = true;
                        }

                        if (knownCoffeeSize) {
                            if (product.contains("extra milk")) {
                                products.add("extra milk");
                            }
                            if (product.contains("foamed milk")) {
                                products.add("foamed milk");
                            }
                            if (product.contains("special roast")) {
                                products.add("special roast");
                            }
                        }
                    } else if (product.contains("freshly squeezed orange juice")) {
                        products.add("freshly squeezed orange juice");
                    } else if (product.contains("bacon roll")) {
                        products.add("bacon roll");
                    }
                });

        return products;
    }
}
