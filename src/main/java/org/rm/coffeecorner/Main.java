package org.rm.coffeecorner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws IOException {
        var input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        var products = new InputParser(input).parse();
        var discount = new DiscountCalculator(products).calculate();
        var receipt = new ReceiptFormatter(products, discount).format();
        System.out.print(receipt);
        System.out.flush();
    }
}
