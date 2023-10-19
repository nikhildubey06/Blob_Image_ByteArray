package com.blob.image.task;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.image.BufferedImage;

public class SaveImageFromUrlToBlob {
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

                // Azure Blob Storage configuration
                String connectionString = "DefaultEndpointsProtocol=https;AccountName=storageimage0981;AccountKey=qJziXhR4cquve5wJ0svxpNp1tgS5iRgIl+6kHjZ111iphTiuJ4jaPMRoOaXDdbwXTKyRL4b8FxLl+AStJClzXA==;EndpointSuffix=core.windows.net";
                String containerName = "democontainer";
                String blobName = "Goku.jpg"; // Specify the blob name along with the extension

                // Create a BlobServiceClient
                BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

                // Get a BlobContainerClient
                BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

                // Get a BlobClient
                BlobClient blobClient = containerClient.getBlobClient(blobName);

                // Upload the image to Azure Blob Storage
                blobClient.upload(new ByteArrayInputStream(imageBytes), imageBytes.length, true);

                System.out.println("Image saved to Azure Blob Storage: " + blobClient.getBlobUrl());
            } else {
                System.out.println("Failed to fetch the image. HTTP status code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

