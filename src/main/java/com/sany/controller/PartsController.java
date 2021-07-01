package com.sany.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sany.service.PartsService;
import com.sany.utils.BaseMode;
import com.sany.utils.ExcelUtils;
import com.sany.utils.HintException;
import com.sany.utils.ModeBean;
import com.sany.utils.PUtils;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/parts")
@CrossOrigin
@Slf4j
public class PartsController {

	@Autowired
	PartsService service;
	
	

	// 导入Excel
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public BaseMode importExcel(MultipartFile file) {
		try {
			int headSize = 1;
			String[] fields = { "orderNumber", "partCode", "batch", "partSize", "route", "num" };
			if (file == null || file.isEmpty()) {
				throw new HintException("文件不存在！请确认选择");
			}
			List<String[]> list = ExcelUtils.readExcelFile(file, 0, headSize, 0, fields.length);
			// 用来装所有二维码的字符串列表
			List<String> parts = new ArrayList<String>();
			//int lineNum = 1;// 从第二行开始
			String val = null;
			for (String[] line : list) {
				//++lineNum;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < fields.length; i++) {

					val = line[i].trim();

					if (i != fields.length && i != 0) {
						sb.append("|" + val);
					} else {
						sb.append(val);
					}

				}
				parts.add(sb.toString());

			}
			if(PUtils.isEmpty(parts))
			{
				log.error("表格数据为空");
			}
			//生成二维码,到相应路径中
			service.createQrcode(parts);			
		    log.info(PUtils.TimeToString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss")+"二维码已生成！！");
		    
			return ModeBean.bulid(true, parts.toString());
		} catch (HintException e) {
			log.error(e.getMessage());
			return ModeBean.bulid(false, e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return ModeBean.bulid(false, e.getMessage());
		}

	}
	
	@ResponseBody
	@RequestMapping(value ="/print", method = { RequestMethod.GET, RequestMethod.POST })
	public void print()throws Exception
	{

//		Desktop.getDesktop().print(new File("D:\\20210512162547\\3EE94667BD354E838C68F463166B65BC.jpg"));
	}
	
	@RequestMapping(value="/info")
	public String getInfo()
	{
		return "qrcode";
	}
   
	//下载模板
	@ResponseBody
	@RequestMapping(value="/exportTemplate")
	public void exportExport(HttpServletRequest request, HttpServletResponse response) {
		try {


				StringBuilder fileName = new StringBuilder();
				fileName.append("importTemplate");
				OutputStream output = response.getOutputStream();
				response.setHeader("Content-disposition",
						"attachment;filename=" + new String(fileName.toString().getBytes("GB2312"), "ISO8859-1") + ".xls");
				response.setContentType("application/msexcel");

				HSSFWorkbook book = new HSSFWorkbook();
				createBomTemplate(book);// 填充excel内容

				book.write(output);
				book.close();
			} catch (Exception e) {
			}
	}
	
	
		private void createBomTemplate(HSSFWorkbook book) {
			String[] columns = { "生产订单号", "零件编码", "批次号", "标准零件大小", "工艺路线", "数量"};

			HSSFSheet sheet = book.createSheet("零件物料导入模板");
			for (int i = 0; i < columns.length; i++) {
				sheet.setColumnWidth(i, 20 * 256);
			}
			HSSFCellStyle style_title = book.createCellStyle();
			ExcelUtils.initCellStyle(style_title, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
					true, HSSFColorPredefined.WHITE.getIndex());
			HSSFFont font_title = book.createFont();// 生成一个字体样式
			font_title.setFontHeightInPoints((short) 12); // 字体高度(大小)
			font_title.setBold(true);// 加粗
			style_title.setFont(font_title);// 把字体应用到当前样式

			HSSFCellStyle style_column = book.createCellStyle();
			ExcelUtils.initCellStyle(style_column, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
					true, HSSFColorPredefined.WHITE.getIndex());
			HSSFFont font_column = book.createFont();// 生成一个字体样式
			font_column.setBold(true);// 加粗
			style_column.setFont(font_column);// 把字体应用到当前样式

			HSSFCellStyle style_value = book.createCellStyle();
			ExcelUtils.initCellStyle(style_value, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
					true, HSSFColorPredefined.WHITE.getIndex());
			for (int i = 0; i < 20; i++) {
				HSSFRow row = sheet.createRow(i);
				if (i == 0) {
					short height = 400;
					row.setHeight(height);
					for (int j = 0; j < columns.length; j++) {
						HSSFCell cell = row.createCell(j);
						cell.setCellStyle(style_column);
						cell.setCellValue(columns[j]);
					}
				} else {
					short height = 400;
					row.setHeight(height);
					for (int j = 0; j < columns.length; j++) {
						HSSFCell cell = row.createCell(j);
						cell.setCellStyle(style_value);
					}
				}
			}
		}
		
		
		//下载PDF文档
		@RequestMapping(value="/downloadPdf")
		public void downloadPdf(HttpServletRequest request,HttpServletResponse response)
		{    
			
			//去获取生成文件的生成的的名字			
		     try {
		    	 String fileName=PUtils.TimeToString2(System.currentTimeMillis())+".pdf";
					service.download(request, response,fileName);
			} catch (Exception e) {
				log.error(PUtils.excepitonToString(e));
				e.printStackTrace();
			}
			
		}
		
	
	
		

}
