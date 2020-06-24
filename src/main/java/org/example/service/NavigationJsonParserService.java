package org.example.service;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.example.Util.JsoupUtil;
import org.example.model.Item;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class NavigationJsonParserService extends Thread {
    private static final Logger LOG =
            Logger.getLogger(PromProductParserService.class.getName());


    private final List<Item> items;
    private final String url;
    private final Boolean isProxyRequired;
    private final List<Thread> threads;
    private  Document document;

    @Override
    public void run() {
        for (String url: urls()) {
            if (isProductPage()){
                PromProductParserService promProductParserService = new PromProductParserService(items,url,document);
                threads.add(promProductParserService);
                promProductParserService.start();
            }else if (isNavigationPage()){
                PromNavigationParserService promNavigationParserService = new PromNavigationParserService(url, items, threads, document, isProxyRequired);
                threads.add(promNavigationParserService);
                promNavigationParserService.start();
            }else {
                LOG.warning(String.format("Page with url %s was not recognised", url));
            }
        }

    }

    private Document parseJsonDocument() {
        Document parseDocument = (Document) JsonPath.parse(document.attr("products"));
        return parseDocument;
    }

    private List<String> urls() {
        Elements elements = parseJsonDocument().getElementsByAttribute("products");

        List<String> urls = new ArrayList<>();
        for (Element element : elements) {
            //TODO add main url for "link_url"
            urls.add(element.attr("link_url"));
        }
        return urls;
    }

    private boolean isNavigationPage() {
        return document.getElementById("categoryList") != null;
    }

    private boolean isProductPage() {
        return !document.getElementsByAttributeValue("class", "product-card__right").isEmpty();
    }
}
