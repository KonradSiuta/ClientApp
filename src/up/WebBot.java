package up;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class WebBot {
    public StringBuilder getWebPage(String adress) {
        try {
            URL url = new URL(adress);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

            BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = buff.readLine()) != null) {
                builder.append(line);
            }

            buff.close();

            return builder;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void startSearch(String address) {
        StringBuilder webBuilder = getWebPage(address);

        Document doc = Jsoup.parse(webBuilder.toString());
        Elements elements = doc.select("a[href]");

//        for (Element e : elements) {
////            System.out.println(e.attr("href"));
//            String link = e.text().contains("https://") ? e.text() : e.attr("href");
//            System.out.println(link);

        List<Element> httpsList = elements.stream().filter(e -> e.attr("href").contains("https://")).toList();

        for (Element e : httpsList) {
            System.out.println(e.attr("href"));
        }

    }

    public void findRate(String address) {
        try {
            Document doc = Jsoup.parse(new URL(address), 5000);
            Elements rateElements = doc.select("Rate");
            for (Element e : rateElements) {
//                    System.out.println(e.attr("Currency") + " " + e.attr("Code") + " " + e.attr("Mid"));
                System.out.println(e.text());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}