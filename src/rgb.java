/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andriansyah
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class rgb {
    int nilair = 0;
    int nilaig = 0;
    int nilaib = 0;
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH,img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
    public void getFeature(BufferedImage img) {
        int tempr=0, tempg=0, tempb=0;
        int pembagi=41250;
        
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int pixel = img.getRGB(i, j);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    if (red==255&&green==255&&blue==255){
                        red=0;
                        green=0;
                        blue=0;
                        pembagi--;
                    }

                    tempr+=red;
                    tempg+=green;
                    tempb+=blue;
                }
            }
            nilair=tempr/pembagi;
            nilaig=tempg/pembagi;
            nilaib=tempb/pembagi;
    }
    public BufferedImage colorSwitch(BufferedImage img){
        for(int i=0; i < img.getWidth(); i++){
            for(int j=0; j < img.getHeight(); j++){
                int pxl = img.getRGB(i, j);
                int red = (pxl >> 16) & 0xFF;
                int green = (pxl >> 8) & 0xFF;
                int blue = (pxl) & 0xFF;
                if(red==0&&green==0&&blue==0){
                    red = 255;
                    green = 255;
                    blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    img.setRGB(i, j, val);
                }else{
                    red = 0;
                    green = 0;
                    blue = 0;
                    int val = blue + (green << 8) + (red << 16);
                    img.setRGB(i, j, val);
                }
//                System.out.println(red+" "+green+" "+blue);
            }
        }
        return img;
    }
    public BufferedImage konvolusi(BufferedImage citra1, BufferedImage citra2) throws IOException{
        BufferedImage citra3 = new BufferedImage(citra1.getWidth(), citra2.getHeight(), 
                BufferedImage.TYPE_INT_RGB);
        int width = citra1.getWidth();
        int height = citra1.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	int c1 = citra1.getRGB(x, y);
            	int c2 = citra2.getRGB(x, y);
                //image 1
                int red1 = (c1 >> 16) & 0xFF;
                int green1 = (c1 >> 8) & 0xFF;
                int blue1 = (c1) & 0xFF;
                //image 2
                int red2 = (c2 >> 16) & 0xFF;
                int green2 = (c2 >> 8) & 0xFF;
                int blue2 = (c2) & 0xFF;
                //
                int red_konv = red1 + red2;
                red_konv = valueCheck(red_konv);
                int green_konv = green1 + green2;
                green_konv = valueCheck(green_konv);
                int blue_konv = blue1 + blue2;
                blue_konv = valueCheck(blue_konv);
                int pxl = blue_konv + (green_konv << 8) + (red_konv << 16);
                citra3.setRGB(x, y, pxl);
            }
        }
        return citra3;
    }
    public static int valueCheck(int nilai){
    	return (nilai>=255)?255:nilai;
    }
}

