import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Main {
    private final List<String> products;

    public Main(String input) {
        this.products = Arrays.stream(Objects.requireNonNull(input).split(",")).collect(toList());
    }

    public static void main(String[] args) throws IOException {
        String input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        String receipt = new Main(input).generateReceipt();
        System.out.print(receipt);
        System.out.flush();
    }

    public String generateReceipt() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Charlene's Coffee Corner").append('\n');
        buffer.append("-".repeat(46)).append('\n');

        products.forEach(smallCoffee ->
                buffer.append(String.format((Locale) null, "%-39.39s %6.2f", smallCoffee, 2.5)).append('\n'));

        buffer.append("-".repeat(46)).append('\n');
        buffer.append(String.format((Locale) null, "%-35.35s %10.2f", "Total CHF", 2.5 * products.size()));

        return buffer.toString();
    }
}
