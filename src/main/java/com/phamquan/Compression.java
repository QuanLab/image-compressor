package com.phamquan;

import org.imgscalr.Scalr;

import java.io.*;
import java.util.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;


class Compression {

    public static void main (String [] args) {

        compressImage("image.png", "output", ".jpg", 500, 500, 0.8f);
    }

    public static void compressImage (String srcPath, String destPath, String format, int with, int height, float quality) {

        try {
            File input = new File(srcPath);
            BufferedImage image = ImageIO.read(input);

            image = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, with, height, Scalr.OP_ANTIALIAS);
            File compressedImageFile = new File(destPath + format );
            OutputStream os =new FileOutputStream(compressedImageFile);

            Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();

            ImageOutputStream ios = ImageIO.createImageOutputStream(os);

            writer.setOutput(ios);

            ImageWriteParam iwp = writer.getDefaultWriteParam();

            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(quality);
            writer.write(null, new IIOImage(image, null, null), iwp);

            os.close();
            ios.close();
            writer.dispose();

        } catch (IOException e) {
        }
    }

}