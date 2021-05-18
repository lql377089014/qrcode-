package com.sany.utils;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
import javax.print.attribute.standard.Sides;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintUtil {

	// private static final String FILEPATH = "D:\\pdf\\";

	//private static final String linuxFILEPATH = "pdf/";

	// 创建windows的打印驱动
	public static void WindowsPrint(File file) {
		// JFileChooser fileChooser = new JFileChooser(); // 创建打印作业
//		File file = new File("f:/111.txt"); // 获取选择的文件
		// 构建打印请求属性集
		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		// 设置打印格式，因为未确定类型，所以选择autosense
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// 定位默认的打印服务
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
		InputStream fis = null;
		try {
			DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
			fis = new FileInputStream(file); // 构造待打印的文件流
			DocAttributeSet das = new HashDocAttributeSet();
			Doc doc = new SimpleDoc(fis, flavor, das);
			job.print(doc, pras);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 传入文件和打印机名称

	public static void JPGPrint(File file) throws PrintException {
		if (file == null) {
			System.err.println("缺少打印文件");
		}
		InputStream fis = null;
		try {
			// 设置打印格式，如果未确定类型，可选择autosense
			DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
			// 设置打印参数
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			aset.add(new Copies(1)); // 份数
			// aset.add(MediaSize.ISO.A4); //纸张
			// aset.add(Finishings.STAPLE);//装订
			// aset.add(Sides.DUPLEX);// 单双面
			// 定位打印服务
			// PrintService printService = null;
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
//			if (printerName != null) {
//				// 获得本台电脑连接的所有打印机
//				PrintService[] printServices = PrinterJob.lookupPrintServices();
//				if (printServices == null || printServices.length == 0) {
//					System.out.print("打印失败，未找到可用打印机，请检查。");
//					return;
//				}
//				// 匹配指定打印机
//				for (int i = 0; i < printServices.length; i++) {
//					System.out.println(printServices[i].getName());
//					if (printServices[i].getName().contains(printerName)) {
//						printService = printServices[i];
//						break;
//					}
//				}
//				if (printService == null) {
//					System.out.print("打印失败，未找到名称为" + printerName + "的打印机，请检查。");
//					return;
//				}
//			}	
			if (file.isDirectory()) {
				String[] fileList = file.list();
				for (String line : fileList) {
					String realPath = file.getAbsolutePath() + "\\" + line;
					File files = new File(realPath);
					fis = new FileInputStream(files); // 构造待打印的文件流
					Doc doc = new SimpleDoc(fis, flavor, null);
					DocPrintJob job = printService.createPrintJob(); // 创建打印作业
					job.print(doc, aset);
				}
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			// 关闭打印的文件流
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// PDF打印
	public static void PDFprint(File file) throws Exception {
		PDDocument document = null;
		try {
			document = PDDocument.load(file);
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setJobName(file.getName());
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
//			if (printerName != null) {
			// 查找并设置打印机
			// 获得本台电脑连接的所有打印机
//				PrintService[] printServices = PrinterJob.lookupPrintServices();
//				if (printServices == null || printServices.length == 0) {
//					System.out.print("打印失败，未找到可用打印机，请检查。");
//					return;
//				}
//				PrintService printService = null;
//				// 匹配指定打印机
//				for (int i = 0; i < printServices.length; i++) {
//					System.out.println(printServices[i].getName());
//					if (printServices[i].getName().contains(printerName)) {
//						printService = printServices[i];
//						break;
//					}
//				}
			if (printService != null) {
				printJob.setPrintService(printService);
			} else {
				System.out.print("打印失败，未找到名称为" + "的打印机，请检查。");
				return;
			}
//			}

			// 设置纸张及缩放
			PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
			// 设置多页打印
			Book book = new Book();
			PageFormat pageFormat = new PageFormat();
			// 设置打印方向
			pageFormat.setOrientation(PageFormat.PORTRAIT);// 纵向
			pageFormat.setPaper(getPaper());// 设置纸张
			book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
			printJob.setPageable(book);
			printJob.setCopies(1);// 设置打印份数
			// 添加打印属性
			HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
			pars.add(Sides.DUPLEX); // 设置单双页
			printJob.print(pars);
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Paper getPaper() {
		Paper paper = new Paper();
		// 默认为A4纸张，对应像素宽和高分别为 595, 842
		int width = 595;
		int height = 842;
		// 设置边距，单位是像素，10mm边距，对应 28px
		int marginLeft = 10;
		int marginRight = 0;
		int marginTop = 10;
		int marginBottom = 0;
		paper.setSize(width, height);
		// 下面一行代码，解决了打印内容为空的问题
		paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight),
				height - (marginTop + marginBottom));
		return paper;
	}

	/**
	 * 
	 * @param fileName   生成的PDF名称
	 * @param imagesPath 图片的路径
	 * @return
	 */
	public static String imagesToPdf(String fileName, String imagesPath) {
		Document document = new Document();
		try {
			fileName = fileName + ".pdf";

			File file = new File(fileName);
//	            File  fileDir=new File(linuxFILEPATH);
//	            if(!fileDir.exists())
//	            {
//	            	 fileDir.mkdir();
//	            }
			// 第一步：创建一个document对象。
			// Document document = new Document();
			document.setMargins(0, 0, 0, 0);
			// 第二步：
			// 创建一个PdfWriter实例，
			PdfWriter.getInstance(document, new FileOutputStream(file));
			// 第三步：打开文档。
			document.open();
			// 第四步：在文档中增加图片。
			File files = new File(imagesPath);
			String[] images = files.list();
			int len = images.length;

			for (int i = 0; i < len; i++) {
				if (images[i].toLowerCase().endsWith(".bmp") || images[i].toLowerCase().endsWith(".jpg")
						|| images[i].toLowerCase().endsWith(".jpeg") || images[i].toLowerCase().endsWith(".gif")
						|| images[i].toLowerCase().endsWith(".png")) {
					String temp = imagesPath + images[i];
					Image img = Image.getInstance(temp);
					img.setAlignment(Image.ALIGN_CENTER);
					// 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
					document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
					document.newPage();
					document.add(img);
				}
			}
			// 第五步：关闭文档。
			document.close();
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void pdfPrint(File file) {

		PDDocument document = null;
		try {
			log.info("文件名称为" + file.toString());
			// 加载PDF文档
			document = PDDocument.load(file);
			log.info("pdf文档加载完毕");
			// 创建打印任务
			PrinterJob job = PrinterJob.getPrinterJob();
			// 获取默认的打印机
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
			log.info(printService.toString());
			if (printService != null) {
				job.setPrintService(printService);
			}
			job.setPageable(new PDFPageable(document));
			PageFormat pageFormat = new PageFormat();
			log.info("文档格式设置");
			pageFormat.setPaper(getPaper());
			Book book = new Book();
			book.append(new PDFPrintable(document, Scaling.ACTUAL_SIZE), pageFormat, document.getNumberOfPages());
			job.setPageable(book);
			job.print();
			log.info("打印成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(PUtils.excepitonToString(e));
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
