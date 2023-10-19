package com.blob.image.task;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class SaveImageFromLocalToBlob {
    public static void main(String[] args) {
        try {
            // Load an image from a file
            File imageFile = new File("C:\\Users\\NikhilDubey\\Downloads\\IMG_20231005_180146.jpg");
            BufferedImage image = ImageIO.read(imageFile);

            // Convert the image to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Azure Blob Storage configuration
            String connectionString = "DefaultEndpointsProtocol=https;AccountName=storageimage0981;AccountKey=qJziXhR4cquve5wJ0svxpNp1tgS5iRgIl+6kHjZ111iphTiuJ4jaPMRoOaXDdbwXTKyRL4b8FxLl+AStJClzXA==;EndpointSuffix=core.windows.net";
            String containerName = "democontainer";
            String blobName = "IMG_20231005_180146.jpg"; // Specify the blob name along with the file extension

            // Create a BlobServiceClient
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

            // Get a BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            // Get a BlobClient
            BlobClient blobClient = containerClient.getBlobClient(blobName);

            // Upload the image to Azure Blob Storage
            blobClient.upload(new ByteArrayInputStream(imageBytes), imageBytes.length, true);

            System.out.println("Image saved to Azure Blob Storage: " + blobClient.getBlobUrl());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

