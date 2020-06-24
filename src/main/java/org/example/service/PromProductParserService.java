package org.example.service;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.Util.JsoupUtil;
import org.example.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class PromProductParserService extends Thread {

    private static final Logger LOG =
            Logger.getLogger(PromProductParserService.class.getName());

    private final List<Item> items;
    private final String url;
    private final Document document;

    @Override
    public void run() {

        Element productElement = extractProductElement();
        BigDecimal price = extractPrice();
        Item item = Item.builder()
                .itemCode(extractItemCode(productElement))
                .name(extractName(productElement))
                .url(url)
                .imageUrl(extractImageUrl())
                .price(price)
                .initialPrice(extractInitPrice(price))
                .availability(extractAvailability(productElement))
                .build();
        items.add(item);
    }

    private String extractItemCode(Element productElement) {
        Element itemElement =  productElement.getElementsByAttributeValue("itemprop", "productID").first();
        return StringUtils.trim(itemElement.text());
    }


    private Element extractProductElement() {
        return document.getElementsByAttributeValue("class", "product-card__right").first();
    }

    private String extractImageUrl() {
        Element productElement = document.getElementsByAttributeValue("class", "product-card__left").first();
        String result = "";
        try {
            result = productElement.getElementById("galleryList").getElementsByTag("img").first().attr("src");
        } catch (Exception e) {
            LOG.severe(String.format("Item imageUrl by URL %s was not extracted", url));
        }
        return result;
    }

    private BigDecimal extractPrice() {
        BigDecimal result = null;
        try {
            String resultAsText = document.
                    getElementsByAttributeValue("property", "product:sale_price:amount").
                    first().attr("content");
            result = new BigDecimal(resultAsText).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            LOG.severe(String.format("Item price by URL %s was not extracted", url));
        }
        return result;
    }

    private String extractAvailability(Element productInfo) {
        String result = "";
        try {
            result = productInfo.getElementById("productHeaderInformerSource").text();
        } catch (Exception e) {
            LOG.severe(String.format("Item availability by URL %s was not extracted", url));
        }
        return StringUtils.trim(result);
    }

    private String extractName(Element productInfo) {
        String result = "";
        try {
            result = productInfo.getElementsByTag("h1").
                    first().text();
        } catch (Exception e) {
            LOG.severe(String.format("Item name by URL %s was not extracted", url));
        }
        return StringUtils.trim(result);
    }

    private BigDecimal extractInitPrice(BigDecimal price) {
        BigDecimal result = price;
        try {
            String resultAsText = document.
                    getElementsByAttributeValue("property", "product:original_price:amount").
                    first().attr("content");
            result = new BigDecimal(resultAsText).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            if (price == null) {
                LOG.severe(String.format("Item init price by URL %s was not extracted", url));
            }
        }
        return result;
    }

}
