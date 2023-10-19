package com.blob.image.task;

import javax.imageio.ImageIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class SaveImageFromLocalToLocal {
    public static void main(String[] args) {
        try {
            // Load an image from a file
            File imageFile = new File("C:\\Users\\NikhilDubey\\Downloads\\IMG_20231005_180146.jpg");
            BufferedImage image = ImageIO.read(imageFile);
            System.out.println("Image ::"+image);
            
            // Convert the image to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(imageBytes);
            String outputPath = "C:\\Users\\NikhilDubey\\Downloads\\output.jpg";
            
            BufferedImage savedImage = ImageIO.read(byteArrayInputStream);
            ImageIO.write(savedImage, "jpg", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

