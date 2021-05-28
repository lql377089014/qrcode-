package com.sany.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.sany.utils.CreateImageUtil;
import com.sany.utils.PUtils;
import com.sany.utils.PrintUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PartsService {
	
	
	//创建二维码图片
	public void  createQrcode(List<String> parts)
	{
		try {
			
	  //  String filePath="D:\\"+String.valueOf(PUtils.TimeToString(System.currentTimeMillis()))+"\\";
	    String linuxFilePath=String.valueOf(PUtils.TimeToString(System.currentTimeMillis()))+"/";
	    File  file=new File(linuxFilePath);
		if(!file.exists())
		{
			file.mkdir();
		}		
			for(String d:parts) {								
				CreateImageUtil  cg=new CreateImageUtil();
				String []str=d.split("\\|");				
				cg.graphicsGeneration(CreateImageUtil.createImage(d,false), str[1], linuxFilePath);																
			}
			//把当前任务的图片生成PDF文档		
			String fileName=PrintUtil.imagesToPdf(PUtils.TimeToString2(System.currentTimeMillis()), linuxFilePath);
			if(fileName!=null)
			{
				log.info("pdf文档已生成");
			}
			
			//download(request, response, fileName);
			//下载文档
			//download(new File(fileName));
			//PrintUtil.pdfPrint(new File(fileName));
			
			
		} catch (Exception e) {
			log.error(PUtils.excepitonToString(e));
			e.printStackTrace();
		}
	}
	
	
	/**
     * 打印二维码
     *
     * @param fileName
     * @param count
     */
    public  void printImage(String fileName, int count,HttpServletRequest request) {
    	
        String realPath = request.getSession().getServletContext().getRealPath("/") + "\\qrcode\\";
        try {
            DocFlavor dof = null;
  
            if (fileName.endsWith(".gif")) {
                dof = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(".jpg")) {
                dof = DocFlavor.INPUT_STREAM.JPEG;
            } else if (fileName.endsWith(".png")) {
                dof = DocFlavor.INPUT_STREAM.PNG;
            }
            // 获取默认打印机 
            PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
  
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            //          pras.add(OrientationRequested.PORTRAIT); 
            //          pras.add(PrintQuality.HIGH); 
            pras.add(new Copies(count));
            pras.add(MediaSizeName.ISO_A10); // 设置打印的纸张 
  
            DocAttributeSet das = new HashDocAttributeSet();
            das.add(new MediaPrintableArea(0, 0, 1, 1, MediaPrintableArea.INCH));
            FileInputStream fin = new FileInputStream(fileName+realPath);
            Doc doc = new SimpleDoc(fin, dof, das);
            DocPrintJob job = ps.createPrintJob();
  
            job.print(doc, pras);
            fin.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (PrintException pe) {
            pe.printStackTrace();
        }
    }
    
    	/**
    	 * 下载PDF文档
    	 * @param fileName  文档名称
    	 */
       public  void download(HttpServletRequest  request,HttpServletResponse response,String fileName)
       {
    	   
    	  // String fileName=PUtils.TimeToString2(System.currentTimeMillis())+".pdf";
    	   response.setCharacterEncoding("UTF-8");
           response.setContentType("application/x-msdownload");
           try {
               String encodenickname = URLEncoder.encode(fileName,"UTF-8");//转Unicode不然ie会乱码
               response.setHeader("Content-Disposition", "attachment;fileName=" + new String(encodenickname.getBytes("UTF-8"), "ISO8859-1"));
  
               File file = new File(fileName);
  
               if (!file.exists()) {
                   response.sendError(404, "File not found!");
                   return;
               }
               long fileLength = file.length();
               response.setHeader("Content-Length", String.valueOf(fileLength));
               BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
               BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
               byte[] buff = new byte[2048];
               int bytesRead;
               while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                   bos.write(buff, 0, bytesRead);
               }
               bis.close();
               bos.close();
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
  
       }
}
