package com.sany.service;

import java.io.FileInputStream;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;

import com.sany.utils.PrintUtil;

public class PrintTest {
	 public static void printImg(String fileName, int count) {
	        try {
	        	//DocFlavor dof = DocFlavor.INPUT_STREAM.AUTOSENSE;
	       	
	            DocFlavor dof = null;
	            if (fileName.endsWith(".gif")) {
	                dof = DocFlavor.INPUT_STREAM.GIF;
	            } else if (fileName.endsWith(".jpg")) {
	                dof = DocFlavor.INPUT_STREAM.JPEG;
	            } else if (fileName.endsWith(".png")) {
	                dof = DocFlavor.INPUT_STREAM.PNG;
	            }
	            PrintService pservice  = PrintServiceLookup.lookupDefaultPrintService();
				
				//打印属性
	            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	            
	            pras.add(OrientationRequested.PORTRAIT);
	            pras.add(new Copies(count));
	            pras.add(PrintQuality.HIGH);
	            pras.add(Chromaticity.MONOCHROME);
	            
				//文档属性
	            DocAttributeSet das = new HashDocAttributeSet();
	            FileInputStream fis = new FileInputStream(fileName);
	             	// 设置打印纸张的大小（将像素转化为mm）
	            das.add(new MediaPrintableArea(0, 0, 110, 148, MediaPrintableArea.MM));
	           
	            Doc doc = new SimpleDoc(fis, dof, das);

	            DocPrintJob job = pservice.createPrintJob();
	            job.print(doc, pras);

	            fis.close();
	                
	        } catch (IOException ie) {
	            ie.printStackTrace();
	        } catch (PrintException pe) {
	            pe.printStackTrace();
	        }
	    }
	
	public static void main(String[] args)
	{
		PrintUtil.imagesToPdf("123", "D:\\20210512172119\\");
		//printImg("d:\\qrcode\\6A48C1C1A46E45F79B0C638AB60166C1.jpg", 1);
		
		
	}
}


