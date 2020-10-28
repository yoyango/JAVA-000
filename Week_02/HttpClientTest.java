package com.httpserver;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

public class HttpClientTest {
    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8082");
        try {
            CloseableHttpResponse execute = httpclient.execute(httpGet);
            Header[] allHeaders = execute.getAllHeaders();
            System.out.println("打印响应头");
            for (Header allHeader : allHeaders) {
                String name = allHeader.getName();
                String value = allHeader.getValue();
                System.out.println(name+":"+value);
            }
            System.out.println("打印响应头结束");
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();
            byte[] buffer = new byte[512];
            int length = 0;
            if((length = content.read(buffer)) != -1){
                content.read(buffer,0,length);
                System.out.println(new String(buffer,"UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
