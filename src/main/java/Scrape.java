import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Scrape {

    public static void main(String[] args) {
        JSONArray scrapedData = new JSONArray();

        try{
            String baseUrl ="https://scholar.google.com";
            String url = "https://scholar.google.com/citations?user=9w1rY-AAAAAJ&hl=en&cstart=20&pagesize=80";
            Document page = Jsoup.connect(url).userAgent("Jsoup Scraper").get();
            Element tabel = page.select("#gsc_a_t").first();
            Elements tabelRow = tabel.select(".gsc_a_tr");


            int i = 0;
            for (Element tabelD : tabelRow){
                JSONObject detailData = new JSONObject();
                i++;
                Element judul = tabelD.select(".gsc_a_t a").first();
                Element penulis = tabelD.selectFirst(".gsc_a_t div");
                Element published = tabelD.select(".gsc_a_t div").last();

                System.out.println(i);
                System.out.println("Judul : "+judul.text());
                System.out.println("Link : "+baseUrl+judul.attr("data-href"));
                System.out.println("Penulis : "+penulis.text());
                System.out.println("Published: "+published.text());

                detailData.put("Judul", judul.text());
                detailData.put("Link", baseUrl+judul.attr("data-href"));
                detailData.put("Penulis",penulis.text());
                detailData.put("Published", published.text());
                System.out.println();
                scrapedData.put(detailData);
            }
            System.out.println("\n JSON: " + scrapedData);
            saveResult(scrapedData);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveResult(JSONArray scraped_data){
        try (FileWriter file = new FileWriter("Solution.json")) {

            file.write(String.valueOf(scraped_data));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
