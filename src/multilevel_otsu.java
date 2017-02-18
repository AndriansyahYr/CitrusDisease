

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
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class multilevel_otsu {
    static final int NGRAY = 256;
    
    public BufferedImage multilevelOTSU(BufferedImage img, int level) throws IOException{
        int MLEVEL = level;
        
        int [] threshold = new int[MLEVEL]; 
        float [] h = new float[NGRAY];
        float[][] P = new float[NGRAY][NGRAY];
        float[][] S = new float[NGRAY][NGRAY];
        float[][] H = new float[NGRAY][NGRAY]; 
        
        int width = img.getWidth();
        int height = img.getHeight();
        
        int[] pixels = new int[(height*width)];
        for(int i=0; i < width*height; i++){
            pixels[i] = img.getRGB(i%width, i/width);
        }
        
//        int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        buildHistogram(h, pixels, width, height);
        
        
        buildLookUp(P, S, H, h);
        
        float maxSig = findMaxSigma(MLEVEL, H, threshold);
        String msg = "thresholds: ";;
        for (int i=0; i < MLEVEL; ++i)
            msg += i + "=" + threshold[i] + ", ";
        msg += " maxSig = " + maxSig;
        
//        System.out.println(msg);
        
        BufferedImage imageMultiOtsu = setThresholdingMultiOtsu(img, MLEVEL, threshold, pixels, width, height);
        
//        imageMultiOtsu = colorSwitch(imageMultiOtsu);
//        ImageIO.write(imageMultiOtsu, "jpg", new File("sources/otsu.jpg"));
        return imageMultiOtsu;
    }
    public BufferedImage setRGBtoGrayLevel(BufferedImage img){
        BufferedImage citra = new BufferedImage(img.getWidth(), img.getHeight(), 
                BufferedImage.TYPE_INT_RGB);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	int c1 = img.getRGB(x, y);
                int red = (c1 >> 16) & 0xFF;
                int green = (c1 >> 8) & 0xFF;
                int blue = (c1) & 0xFF;
                double gray = (red+green+blue)/3;
                red = (int) gray;
                green = (int) gray;
                blue = (int) gray;
                int pxl = blue + (green << 8) + (red << 16);
                citra.setRGB(x, y, pxl);
            }
        }
        return citra;
    }
    public void buildLookUp(float [][] P, float [][] S, float [][] H, float [] h){
        // initialize
        for (int j=0; j < NGRAY; j++)
            for (int i=0; i < NGRAY; ++i){
                P[i][j] = 0.f;
                S[i][j] = 0.f;
                H[i][j] = 0.f;
            }
        // diagonal 
        for (int i=1; i < NGRAY; ++i){
            P[i][i] = h[i];
            S[i][i] = ((float) i)*h[i];
        }
        // calculate first row (row 0 is all zero)
        for (int i=1; i < NGRAY-1; ++i){
            P[1][i+1] = P[1][i] + h[i+1];
            S[1][i+1] = S[1][i] + ((float) (i+1))*h[i+1];
        }
        // using row 1 to calculate others
        for (int i=2; i < NGRAY; i++)
            for (int j=i+1; j < NGRAY; j++){
                P[i][j] = P[1][j] - P[1][i-1];
                S[i][j] = S[1][j] - S[1][i-1];
            }
        // now calculate H[i][j]
        for (int i=1; i < NGRAY; ++i)
            for (int j=i+1; j < NGRAY; j++){
                if (P[i][j] != 0)
                    H[i][j] = (S[i][j]*S[i][j])/P[i][j];
                else
                    H[i][j] = 0.f;
      }
    }
    public float findMaxSigma(int mlevel, float [][] H, int [] t){
        t[0] = 0;
        float maxSig= 0.f;
        switch(mlevel){
            case 2 :
                for (int i= 1; i < NGRAY-mlevel; i++){ // t1
                    float Sq = H[1][i] + H[i+1][255];
                    if (maxSig < Sq){
                        t[1] = i;
                        maxSig = Sq;
                    }
                }
            break;
            case 3:
                for (int i= 1; i < NGRAY-mlevel; i++) // t1
                    for (int j = i+1; j < NGRAY-mlevel +1; j++){ // t2
                        float Sq = H[1][i] + H[i+1][j] + H[j+1][255];
                        if (maxSig < Sq){
                            t[1] = i;
                            t[2] = j;
                            maxSig = Sq;
                        }
                    }
            break;
            case 4 :
                for (int i= 1; i < NGRAY-mlevel; i++) // t1
                    for (int j = i+1; j < NGRAY-mlevel +1; j++) // t2
                        for (int k = j+1; k < NGRAY-mlevel + 2; k++){ // t3
                            float Sq = H[1][i] + H[i+1][j] + H[j+1][k] + H[k+1][255];
                            if (maxSig < Sq){
                                t[1] = i;
                                t[2] = j;
                                t[3] = k;
                                maxSig = Sq;
                            }
                        }
            break;
            case 5 :
                for (int i= 1; i < NGRAY-mlevel; i++) // t1
                    for (int j = i+1; j < NGRAY-mlevel +1; j++) // t2
                        for (int k = j+1; k < NGRAY-mlevel + 2; k++) // t3
                            for (int m = k+1; m < NGRAY-mlevel + 3; m++){ // t4
                                float Sq = H[1][i] + H[i+1][j] + H[j+1][k] + H[k+1][m] + H[m+1][255];
                                    if (maxSig < Sq){
                                        t[1] = i;
                                        t[2] = j;
                                        t[3] = k;
                                        t[4] = m;
                                        maxSig = Sq;
                                    }
                            }
            break;
            case 6 :
                for (int i= 1; i < NGRAY-mlevel; i++) // t1
                    for (int j = i+1; j < NGRAY-mlevel +1; j++) // t2
                        for (int k = j+1; k < NGRAY-mlevel + 2; k++) // t3
                            for (int m = k+1; m < NGRAY-mlevel + 3; m++) // t4
                                for (int n = m+1; n < NGRAY-mlevel + 4; n++){ // t5
                                    float Sq = H[1][i] + H[i+1][j] + H[j+1][k] + H[k+1][m] + H[m+1][n] + H[n+1][255];
                                    if (maxSig < Sq){
                                        t[1] = i;
                                        t[2] = j;
                                        t[3] = k;
                                        t[4] = m;
                                        t[5] = n;
                                        maxSig = Sq;
//                                        System.out.println(t[5]);
                                    }
                                }
        }
    return maxSig; 
    }
    public void buildHistogram(float [] h, int [] pixels, int width, int height){
        for (int i=0; i < width*height; ++i){
            int val = 0xff & pixels[i];
            if(val<=255){
                h[(int) (pixels[i]&0xff)]++;
            }
        }
        // note the probability of grey i is h[i]/(width*height)
        float [] bin = new float[NGRAY];
        float hmax = 0.f;
        for (int i=0; i < NGRAY; ++i){
            bin[i] = (float) i;
            h[i] = i*(h[i]/((float) (width*height)));
            if (hmax < h[i])
                hmax = h[i];
        }
//        PlotWindow histogram = new PlotWindow("Histogram", "grey", "hist", bin, h);
//        histogram.setLimits(0.f, (float) NGRAY, 0.f, hmax);
//        histogram.draw();
    }
    public BufferedImage setThresholdingMultiOtsu(BufferedImage citra, int mlevel, int [] t, int [] pixels, int width, int height)throws IOException{
        BufferedImage dest = new BufferedImage(width, height, citra.TYPE_INT_RGB);
        for(int i=0; i < width*height; i++){
            int pxl = 0xff & pixels[i];
            if(mlevel == 1){
                if(pxl < t[0]){
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
                else{
                    int val = pixels[i];
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
            }
            else if(mlevel == 2){
                if(pxl < t[1]){
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
                else{
                    int val = pixels[i];
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
            }
            else if(mlevel == 3){
                if(pxl < t[1] || pxl > t[2]){
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
                else{
                    int val = pixels[i];
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
            }
            else if(mlevel == 4){
                if(pxl < t[1] || pxl > t[3]){
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
                else{
                    int val = pixels[i];
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
            }
            else if(mlevel == 5){
                if(pxl < t[1] || pxl > t[4]){
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
                else{
                    int val = pixels[i];
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
            }
            else if(mlevel == 6){
                if(pxl < t[1] || pxl > t[5]){
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    int val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
                else{
                    int val = pixels[i];
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    val = blue + (green << 8) + (red << 16);
                    dest.setRGB(i%width, i/width, val);
                }
            }
        }
        return dest;
    }
}
