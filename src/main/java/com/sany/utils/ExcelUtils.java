package com.sany.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtils {
	
	private static NumberFormat numberFormat = NumberFormat.getInstance();
	
	public static List<String[]> readExcelFile(MultipartFile file, int sheetNum, int startRow, int startCell,
			int stopCell) {
		List<String[]> result = new ArrayList<>();
		try {
			Workbook workbook = getWorkBook(file);
			// 获得当前sheet工作表
			Sheet sheet = workbook.getSheetAt(sheetNum);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			if (sheet != null) {
				int rowCount = sheet.getLastRowNum() + 1;
				for (int i = startRow; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (row != null) {
						String[] temp = new String[stopCell];
						int index = 0;
						for (int j = startCell; j < stopCell; j++) {
							Cell cell = row.getCell(j);
							temp[index++] = getCellValue(evaluator,cell).trim();
						}
						boolean boo = false;
						for (String str : temp) {
							if (!str.equals(""))
								boo = true;
						}
						if (boo)
							result.add(temp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void initCellStyle(HSSFCellStyle style, HorizontalAlignment hAlign, VerticalAlignment vAlign,
			BorderStyle border, boolean wrapped, short bg) {
		style.setAlignment(hAlign);
		style.setVerticalAlignment(vAlign);
		style.setBorderBottom(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
		style.setBorderTop(border);
		style.setWrapText(wrapped);

		style.setFillForegroundColor(bg);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	/**
	 * 创建workbook
	 */
	public static Workbook getWorkBook(MultipartFile file) {
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 获取excel文件的io流
			InputStream is = file.getInputStream();
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith("xls")) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith("xlsx")) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public static String getCellValue(FormulaEvaluator formulaEvaluator, Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case NUMERIC: // 数字
			if (DateUtil.isCellDateFormatted(cell)) {// 日期也属于数字
				cellValue = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
			} else {
				Object inputValue = null;// 单元格值
				Long longVal = Math.round(cell.getNumericCellValue());
				Double doubleVal = cell.getNumericCellValue();
				if (Double.parseDouble(longVal + ".0") == doubleVal) { // 判断是否含有小数位.0
					inputValue = longVal;

					return String.valueOf((inputValue)); // 返回String类型
				} else {
					inputValue = doubleVal;
					DecimalFormat df = new DecimalFormat("#.############"); // 格式化为12位小数，按自己需求选择；
					return String.valueOf(df.format(inputValue)); // 返回String类型
				}

			}
			break;
		case STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue()).trim();
			break;
		case BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case FORMULA: // 公式

			try {
				cellValue = numberFormat.format(cell.getNumericCellValue());// 使用这个不会出现导入double类型出现精度丢失
//				cellValue = String.valueOf(cell.getNumericCellValue());
			} catch (IllegalStateException e) {
				cellValue = String.valueOf(cell.getRichStringCellValue());
			}
//				cellValue = String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
			break;
		case BLANK: // 空值
			cellValue = "";
			break;
		case ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}

}
