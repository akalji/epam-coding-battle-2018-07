package com.epam.server.controller;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Steam {
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0";
    private BasicCookieStore cookieStore;
    private CloseableHttpClient httpClient;
    private Header[] HEADERS;


    public String getUserIdByName(String name) {
        String URI = "https://steamcommunity.com/id/" + name + "?xml=1";

        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setUserAgent(USER_AGENT)
                .build();
        HEADERS = new Header[]{
                new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0"),
                new BasicHeader("Accept", "application/json, text/javascript, */*; q=0.01"),
                new BasicHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3"),
                new BasicHeader("Accept-Encoding", "gzip, deflate, br"),
                new BasicHeader("DNT", "1"),
                new BasicHeader("Connection", "keep-alive"),
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
        };

        String page = getPage(URI);
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode root = htmlCleaner.clean(page);
        try {
            Object[] steamID64s = root.evaluateXPath("//steamID64");
            TagNode steamID64ss = (TagNode) steamID64s[0];
            return steamID64ss.getText().toString();
        } catch (XPatherException e) {
            e.printStackTrace();
        }


        return "";
    }

    private String getPage(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpEntity responseEntity = null;
        String content = "";
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            responseEntity = response.getEntity();
            content = new BufferedReader(new InputStreamReader(responseEntity.getContent()))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
}
