package com.example.app;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpDownloader {
    public String get(String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                response1.getEntity().writeTo(buf);
                return buf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
