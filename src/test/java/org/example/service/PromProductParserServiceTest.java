package org.example.service;

import org.example.Util.JsoupUtil;
import org.example.model.Item;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromProductParserServiceTest {

//    @Test
//    void extractProduct() {
//        String url = "https://comfy.ua/noutbuk-asus-x543ma-gq496-silver.html";
//        Document document = JsoupUtil.getDocument(url, null, null);
//        List<Item> items = new ArrayList<>();
//        PromProductParserService promProductParserService = new PromProductParserService(items, url, document);
//        promProductParserService.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        assertFalse(items.isEmpty());
//    }

//    @Test
//   {
//        String url = "https://comfy.ua/notebook/";
//        Document document = JsoupUtil.getDocument(url, null, null);
//        List<Item> items = new ArrayList<>();
//        List<Thread> threads = new ArrayList<>();
//        PromNavigationParserService promNavigationParserService = new PromNavigationParserService(url, items, threads, document, true);
//        promNavigationParserService.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        assertFalse(items.isEmpty());
//    }

    @Test
    void extractwebsite(){

    }

}