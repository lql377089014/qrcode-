package com.sany.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;



public class CreateImageUtil {
    private static  BufferedImage image;
    private static int imageWidth = 300;  //图片的宽度
    private static int imageHeight = 300; //图片的高度
    //编码
    private static final String CHARSET = "utf-8";
//    //文件格式
//    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 250;
    //生成图片文件
    public static  void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if(image != null){
            try {
                bos = new BufferedOutputStream(new FileOutputStream(fileLocation));
                 ImageIO.write(image,"jpg",bos);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(bos!=null){//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *
     *
     * @param codeimg 二维码图片
     * @param words 插入的文字信息
     * @param outurl 输出的路径
     */
    public String graphicsGeneration(  BufferedImage codeimg,String words,String outurl) {
       
        
       
        int W_CodeImg=255;   //定义二维码的宽度
        int H_CodeImg=255;   //高度
        

        //创建主模板图片
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D main = image.createGraphics();
        //设置图片的背景色
        main.setColor(Color.white); //白色
        main.fillRect(0, 0, imageWidth, imageHeight);
     
        //***************插入二维码图片***********************************************
        Graphics codePic = image.getGraphics();
      //  BufferedImage codeimg = null;
//        try {
//            codeimg = javax.imageio.ImageIO.read(new java.io.File(qrcode));
//        } catch (Exception e) {}

        if(codeimg!=null){
            codePic.drawImage(codeimg, 20, 10, W_CodeImg, H_CodeImg, null);
            codePic.dispose();
        }
        //**********************底部文字*********
        Graphics centerword = image.createGraphics();
        //设置区域颜色
        centerword.setColor(new Color(255, 255, 255));
        //填充区域并确定区域大小位置，向右偏移,向下偏移
        //centerword.fillRect(10, 10, 250, H_Words);
        //设置字体颜色，先设置颜色，再填充内容
        centerword.setColor(Color.black);
        //设置字体
        Font FontCenterword = new Font("仿宋", Font.BOLD, 21);
 
        centerword.setFont(FontCenterword);
        ((Graphics2D) centerword).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //设置文字开始的位置，(图片宽度-字体长度*字体大小)/2
        int startX=0;
     //   int startX=((imageWidth-40)-words.length()*FontCenterword.getSize())/2;
        //设置下面字体的初始位置
        if(words.length()<10)
        {   
        	startX=50+words.length()*7;
        	centerword.drawString(words, startX,  H_CodeImg+15);
        }else if(words.length()<19)
        {   
        	startX=50;
        	centerword.drawString(words, startX, H_CodeImg+15);
        }else {
        	startX=50;
        	centerword.drawString(words.substring(0,18), startX, H_CodeImg+15);
			centerword.drawString(words.substring(18,words.length()), startX, H_CodeImg+30);
		}
        	
        //图片输出存放的位置
        String path=outurl+getUUID()+".jpg";
        createImage(path);
       
       return path;
       
    }
    
    //生成二维码
    public static BufferedImage createImage(String content,  boolean needCompress) throws Exception {
        Hashtable<EncodeHintType,Object> hints = new Hashtable<EncodeHintType,Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
    
    /*@param contents 二维码内容
     *@param filePath 二维码保存的路径 
     */   
    //生成二维码
    public static void  createQrcode(String contents,String filePath)throws Exception
    {	
    	HashMap<EncodeHintType,Object>  map=new HashMap<EncodeHintType, Object>();
    	//设置二维码编码方式
    	map.put(EncodeHintType.CHARACTER_SET, CHARSET);
    	map.put(EncodeHintType.MARGIN,1);
    	map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
    	QRCodeWriter  writer=new QRCodeWriter();
    	BitMatrix bitMatrix=writer.encode(contents, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, map);
    	Path path=FileSystems.getDefault().getPath(filePath);
    	MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);
    }
  
    //生成UUID
    public static String getUUID()
    {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    	
    }

    public static void main(String[] args) {
//        CreateImageUtil cg = new CreateImageUtil();
//        /**
//         *
//         * @param imglogo logo图片
//         * @param imgcode 二维码图片
//         * @param words 文字信息
//         * @param outurl 输出的Url路径
//         */
//        String words="IMEI:467890765432136&KEY";
//        try {
//        	 BufferedImage image=createImage("1321312",false);
//            cg.graphicsGeneration(image , words, "D:\\ccc.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	System.out.println(getUUID());
    }

}
