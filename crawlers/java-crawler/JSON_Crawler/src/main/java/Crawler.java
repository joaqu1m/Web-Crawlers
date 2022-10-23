import java.io.IOException;

import org.jsoup.Jsoup;

public class Crawler {
    public static void main(String[] args) throws IOException {
        System.out.println(Jsoup.connect("http://localhost:8085/data.json").ignoreContentType(true).execute().body());
    }
}