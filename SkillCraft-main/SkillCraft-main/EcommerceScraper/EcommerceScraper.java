import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EcommerceScraper {
    public static void main(String[] args) throws Exception {
        String url = args.length > 0 ? args[0] : "https://books.toscrape.com/catalogue/page-1.html";
        String outputFile = args.length > 1 ? args[1] : "products.csv";

        String html = fetchHtml(url);
        List<Product> products = extractProducts(html);
        saveAsCsv(products, outputFile);

        System.out.println("Extracted " + products.size() + " products.");
        System.out.println("Saved to " + outputFile);
    }

    private static String fetchHtml(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        return response.body();
    }

    private static List<Product> extractProducts(String html) {
        List<Product> products = new ArrayList<>();

        Pattern blockPattern = Pattern.compile("(?is)<article class=\"product_pod\">(.*?)</article>");
        Matcher blockMatcher = blockPattern.matcher(html);

        while (blockMatcher.find()) {
            String block = blockMatcher.group(1);

            String name = extract(block, "(?is)<a[^>]*title=\"([^\"]+)\"");
            String price = extract(block, "(?is)<p class=\"price_color\">([^<]+)</p>");
            String rating = extract(block, "(?is)<p class=\"star-rating ([A-Za-z]+)\"");

            products.add(new Product(name, price, rating));
        }

        return products;
    }

    private static String extract(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }

    private static void saveAsCsv(List<Product> products, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, StandardCharsets.UTF_8))) {
            writer.write("Name,Price,Rating");
            writer.newLine();
            for (Product p : products) {
                writer.write("\"" + p.name.replace("\"", "\"\"") + "\",\"" + p.price + "\",\"" + p.rating + "\"");
                writer.newLine();
            }
        }
    }

    static class Product {
        String name;
        String price;
        String rating;

        Product(String name, String price, String rating) {
            this.name = name;
            this.price = price;
            this.rating = rating;
        }
    }
}