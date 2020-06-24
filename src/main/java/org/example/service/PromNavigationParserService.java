package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PromNavigationParserService extends Thread {

    private static final Logger LOG =
            Logger.getLogger(PromNavigationParserService.class.getName());

    private static final Integer AMOUNT_ITEMS = 51;

    private final String url;
    private final List<Item> items;
    private final List<Thread> threads;
    private final Document document;
    private final Boolean isProxyRequired;


    @Override
    public void run() {
        // product links extraction
        Element productListElement = extractProductListElement();
        Set<String> urls = productListElement.getElementsByAttributeValue("class", "product-item__name")
                .stream()
                .map(it -> it.getElementsByTag("a").first())
                .map(it -> it.attr("href"))
                .collect(Collectors.toSet());

        LOG.info(String.format("%d items were found", urls.size()));

        for (String url: urls) {
            RouterParserService router = new RouterParserService(items, url, isProxyRequired, threads);
            threads.add(router);
            router.start();
//            only for test
            break;
        }
        // pagination
        // do on the first page only
        Integer lastPage = getLastPage();
        LOG.info(String.format("Last page is %d", lastPage));
        if (!StringUtils.containsIgnoreCase(url, "p=") && lastPage > 1){
            for (int i = 2; i < 56; i++) {
                String pageUrl = url + "?p=" + i;
                RouterParserService router = new RouterParserService(items, pageUrl, isProxyRequired, threads);
                threads.add(router);
                router.start();
                //            only for test
                break;
            }
        }
    }

    private Integer getLastPage() {
        String count = document.getElementById("productsCount").text();
        count = count.replaceAll("\\D", "");
        if (count.isEmpty()){
            return 0;
        }
        return Integer.valueOf(count) / AMOUNT_ITEMS;
    }

    private Element extractProductListElement() {
        return document.getElementById("categoryList");
    }
}