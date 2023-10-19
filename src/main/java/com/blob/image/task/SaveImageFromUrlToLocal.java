package com.blob.image.task;

import javax.imageio.ImageIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.awt.image.BufferedImage;

public class SaveImageFromUrlToLocal {
    public static void main(String[] args) {
        try {
            // Define the URL of the image you want to fetch
            String imageUrl = "https://wallpapers.com/images/hd/anime-dragon-ball-z-75wgv18nbd0zop9r.jpg"; // Replace with the actual URL

            // Open a connection to the URL and fetch the image
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Check if the request was successful (HTTP status 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	
                // Read the image data from the connection's input stream
                InputStream imageInputStream = connection.getInputStream();

                // Convert the image to a byte array
                BufferedImage image = ImageIO.read(imageInputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // Save the image to a file
                String outputPath = "C:\\Users\\NikhilDubey\\Downloads\\cartoon.jpg";
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                BufferedImage savedImage = ImageIO.read(byteArrayInputStream);
                ImageIO.write(savedImage, "jpg", new File(outputPath));
                System.out.println("Image saved to: " + outputPath);
            } else {
                System.out.println("Failed to fetch the image. HTTP status code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
