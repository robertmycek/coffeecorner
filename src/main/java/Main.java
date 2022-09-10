import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Charlene's Coffee Corner");
        System.out.println("-".repeat(46));
        System.out.printf((Locale) null, "%-39.39s %6.2f%n", input, 2.5);
        System.out.println("-".repeat(46));
        System.out.printf((Locale) null, "%-35.35s %10.2f", "Total CHF", 2.5);
        System.out.flush();
    }
}
