package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @GetMapping("/image")
    public ResponseEntity<byte[]> proxyImage(@RequestParam("url") String imageUrl) {
        HttpURLConnection connection = null;
        try {
            URL url = URI.create(imageUrl).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.status(responseCode).build();
            }

            // Read image data
            try (InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageData = outputStream.toByteArray();

                // Return the image with appropriate headers
                String contentType = connection.getContentType();
                var headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                headers.setCacheControl("max-age=3600");

                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
