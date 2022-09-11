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
                    if (product.contains("small coffee")) {
                        products.add("small coffee");
                        if (product.contains("extra milk")) {
                            products.add("extra milk");
                        }
                    } else if (product.contains("medium coffee")) {
                        products.add("medium coffee");
                        if (product.contains("extra milk")) {
                            products.add("extra milk");
                        }
                    } else if (product.contains("large coffee")) {
                        products.add("large coffee");
                        if (product.contains("extra milk")) {
                            products.add("extra milk");
                        }
                    } else {
                        products.add(product);
                    }
                });

        return products;
    }
}
