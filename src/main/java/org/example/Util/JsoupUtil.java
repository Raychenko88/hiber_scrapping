package org.example.Util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupUtil {

    public static synchronized Document getDocument(String url, String host, Integer port) {
        Connection connection = Jsoup.connect(url);
        // use proxy if required
        if (StringUtils.isNotBlank(host) && port != null) {
            connection.proxy(host, port);
        }
        connection.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connection.header("accept-encoding", "gzip, deflate, br");
        connection.header("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        connection.header("cache-control", "no-cache");
        connection.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        try {
            return connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Document(StringUtils.EMPTY);
    }
}
