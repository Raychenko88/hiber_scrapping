package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.Util.JsoupUtil;
import org.example.model.Item;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class RouterParserService extends Thread{
    private static final Logger LOG =
            Logger.getLogger(PromProductParserService.class.getName());

    private final List<Item> items;
    private final String url;
    private final Boolean isProxyRequired;
    private final List<Thread> threads;
    private  Document document;

    @Override
    public void run() {
        String host = null;
        Integer port = null;
        if (isProxyRequired){
            Map<String, String> proxy = ProxyService.getProxy();
            if (!proxy.isEmpty()){
                host = proxy.get(ProxyService.HOST_KEY);
                port = Integer.valueOf(proxy.get(ProxyService.PORT_KEY));
            }
        }
        document = JsoupUtil.getDocument(url, host, port);
        if (isProductPage()){
            PromProductParserService promProductParserService = new PromProductParserService(items,url,document);
            threads.add(promProductParserService);
            promProductParserService.start();
        }else if (isNavigationPage()){
            PromNavigationParserService promNavigationParserService = new PromNavigationParserService(url, items, threads, document, isProxyRequired);
            threads.add(promNavigationParserService);
            promNavigationParserService.start();
        }else if (isJson()) {
            NavigationJsonParserService navigationJsonParserService = new NavigationJsonParserService(items, url, isProxyRequired, threads);
            threads.add(navigationJsonParserService);
            navigationJsonParserService.start();
        }else {
            LOG.warning(String.format("Page with url %s was not recognised", url));
        }
    }

    private boolean isJson() {
        String string = document.toString();
        return string.startsWith("{");
    }

    private boolean isNavigationPage() {
       return document.getElementById("categoryList") != null;
    }

    private boolean isProductPage() {
        return !document.getElementsByAttributeValue("class", "product-card__right").isEmpty();
    }
}
