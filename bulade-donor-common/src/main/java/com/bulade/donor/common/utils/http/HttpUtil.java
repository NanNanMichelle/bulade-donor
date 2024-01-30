package com.bulade.donor.common.utils.http;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;

@Slf4j
public class HttpUtil {
    private HttpUtil() {
    }

    private static final Integer SECONDS = 10;

    private static final Integer BYTE = 1024;

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static String get(String url) throws Exception {
        return get(url, Map.of());
    }

    public static String post(String url, String requestBody) throws Exception {
        return post(url, requestBody, Map.of());
    }

    public static String post(String url, Map<String, Object> requestBody) throws Exception {
        return post(url, JSON.toJSONString(requestBody), Map.of());
    }

    public static byte[] postForArrayBuffer(String url, Map<String, Object> requestBody) throws Exception {
        return postForArrayBuffer(url, JSON.toJSONString(requestBody), Map.of());
    }

    public static String get(String url, Map<String, String> headers) throws Exception {
        var requestBuilder = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(SECONDS));
        headers.forEach(requestBuilder::header);
        var request = requestBuilder
                .uri(new URI(url))
                .GET()
                .build();

        var response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofByteArray());

        return extractResponse(response);
    }

    public static String post(String url, String requestBody, Map<String, String> headers) throws Exception {
        var requestBuilder = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(SECONDS));
        headers.forEach(requestBuilder::header);
        var request = requestBuilder
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofByteArray());

        return extractResponse(response);
    }

    public static CompletableFuture<String> postAsync(String url, String requestBody, Map<String, String> headers)
            throws Exception {
        var requestBuilder = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(SECONDS));
        headers.forEach(requestBuilder::header);
        var request = requestBuilder
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return HTTP_CLIENT
                .sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply((response) -> {
                    try {
                        return extractResponse(response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static byte[] postForArrayBuffer(String url, String requestBody, Map<String, String> headers)
            throws Exception {
        var requestBuilder = HttpRequest.newBuilder();
        headers.forEach(requestBuilder::header);
        var request = requestBuilder
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofByteArray());

        return response.body();
    }

    private static String extractResponse(HttpResponse<byte[]> response) throws Exception {
        var contentEncoding = response.headers().firstValue("Content-Encoding");
        if (contentEncoding.isPresent() && contentEncoding.get().equals("gzip")) {
            return new String(gZipDecompress(response.body()));
        }
        return new String(response.body());
    }

    private static byte[] gZipDecompress(byte[] compressedData) throws IOException {
        var byteArrayInputStream = new ByteArrayInputStream(compressedData);
        var gzipIn = new GZIPInputStream(byteArrayInputStream);
        var outputStream = new ByteArrayOutputStream();
        var buffer = new byte[BYTE];
        int length;
        while ((length = gzipIn.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        gzipIn.close();
        return outputStream.toByteArray();
    }

}
