package com.debatz.gifts.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * .
 */
public class HttpImageProvider {

    private static final int LIMIT_IMAGES = 100;

    private static final int MINIMUM_WIDTH = 15;

    private static final int MINIMUM_HEIGHT = 10;

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static List<String> getImages(String url) throws IOException {
        List<String> results = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements img = doc.getElementsByTag("img");
        List<Element> elements = img.subList(0, Math.min(img.size(), LIMIT_IMAGES));

        for (Element el : elements) {
            String src = el.attr("src");
            if (src != null && src.startsWith("http")) {
                URLConnection conn = new URL(src).openConnection();
                InputStream in = conn.getInputStream();
                BufferedImage image = ImageIO.read(in);

                if (image.getWidth() >= MINIMUM_WIDTH && image.getHeight() >= MINIMUM_HEIGHT) {
                    results.add(src);
                }
            }
        }

        return results;
    }
}
