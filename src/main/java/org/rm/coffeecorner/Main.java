package org.rm.coffeecorner;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {

    public static void main(String[] args) throws IOException {
        var input = new String(System.in.readAllBytes(), UTF_8);
        var products = new InputParser(input).parse();
        var discount = new DiscountCalculator(products).calculate();
        var receipt = new ReceiptBuilder(products, discount).build();
        var output = new ReceiptFormatter(receipt).format();
        System.out.print(output);
        System.out.flush();
    }
}
