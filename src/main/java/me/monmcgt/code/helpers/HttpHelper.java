package me.monmcgt.code.helpers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHelper {
    public static String getPath() {
        return "http://localhost:59741/";
    }

    public static HttpURLConnection getGet(String url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            return httpURLConnection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpURLConnection getPost(String url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            return httpURLConnection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> sendGet(HttpURLConnection httpURLConnection) throws RuntimeException {
        try {
            if (httpURLConnection.getResponseCode() != 200) {

                throw new RuntimeException("r_code:" + httpURLConnection.getResponseCode() + " | " + httpURLConnection.getURL());
            }

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                return bufferedReader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();

            return null;
        }
    }

    public static List<String> sendPost(HttpURLConnection httpURLConnection, String postData) throws RuntimeException {
        try {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8))) {
                bufferedWriter.write(postData);
            }

            if (httpURLConnection.getResponseCode() != 200) {
                throw new RuntimeException("r_code:" + httpURLConnection.getResponseCode());
            }

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                return bufferedReader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();

            return null;
        }
    }

    public static String sendGetString(HttpURLConnection httpURLConnection) throws RuntimeException {
        return String.join("", sendGet(httpURLConnection));
    }

    public static String sendPostString(HttpURLConnection httpURLConnection, String postData) throws RuntimeException {
        return String.join("", sendPost(httpURLConnection, postData));
    }
}
