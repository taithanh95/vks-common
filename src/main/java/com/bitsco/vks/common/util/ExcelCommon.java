package com.bitsco.vks.common.util;


import com.bitsco.vks.common.constant.Constant;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : TruongNQ
 * @date created : Apr 7, 2018
 * @describe : Expression des is undefined on line 13, column 25 in
 * Templates/Classes/Class.java.
 */
public class ExcelCommon {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.COMMON);

    /*
     * Author:TruongNQ
     * Date created: 09/02/2017
     * Purpose: Doc file excel return  1 list
     */
    public static List readExcelFile(String excelFilePath,
                                     int beginRow,
                                     int fromCol,
                                     int toCol) throws Exception {
        List lst = new ArrayList();
        if (StringCommon.isNullOrBlank(excelFilePath)) {
            return lst;
        }
        if (excelFilePath.trim().toLowerCase().endsWith("xls")) {
            try {
                jxl.Workbook workbook = jxl.Workbook.getWorkbook(new File(excelFilePath));
                Sheet sheet = null;
                if (sheet == null) {
                    sheet = workbook.getSheet(0);
                }

                int blankCount = 0;
                for (int i = beginRow; i < sheet.getRows(); i++) {
                    Object[] obj = new Object[toCol - fromCol + 1];
                    int icount = 0;
                    int icountNull = 0;

                    for (int j = fromCol; j <= toCol; j++) {
                        try {
                            obj[icount] = sheet.getCell(j, i).getContents();
                        } catch (Exception ex) {
                            obj[icount] = "";
                            continue;
                        }
                        if (obj[icount] == null || obj[icount].equals("")) {
                            icountNull += 1;
                        }
                        icount += 1;
                    }
                    if (icount == icountNull) {
                        blankCount++;

                    } else {
                        lst.add(obj);
                    }
                    if (blankCount > 3) {
                        break;
                    }
                }
            } catch (IOException | BiffException | IndexOutOfBoundsException e) {
                LOGGER.error("Exception when readExcelFile: ", e);
            }
        } else {
            throw new IllegalArgumentException("The specified file is not Excel XLS file");
        }
        return lst;
    }

    /*
     * Author:TruongNQ
     * Date created: 09/02/2017
     * Purpose: Doc file excel return  1 list
     */
    public static List readExcelFile(File file,
                                     int beginRow,
                                     int fromCol,
                                     int toCol) throws Exception {
        List lst = new ArrayList();
        if (file == null) {
            return lst;
        }
        if (file.getName().toLowerCase().endsWith("xls")) {
            try {
                jxl.Workbook workbook = jxl.Workbook.getWorkbook(file);
                Sheet sheet = null;
                if (sheet == null) {
                    sheet = workbook.getSheet(0);
                }

                int blankCount = 0;
                for (int i = beginRow; i < sheet.getRows(); i++) {
                    Object[] obj = new Object[toCol - fromCol + 1];
                    int icount = 0;
                    int icountNull = 0;

                    for (int j = fromCol; j <= toCol; j++) {
                        try {
                            obj[icount] = sheet.getCell(j, i).getContents();
                        } catch (Exception ex) {
                            obj[icount] = "";
                            continue;
                        }
                        if (obj[icount] == null || obj[icount].equals("")) {
                            icountNull += 1;
                        }
                        icount += 1;
                    }
                    if (icount == icountNull) {
                        blankCount++;

                    } else {
                        lst.add(obj);
                    }
                    if (blankCount > 3) {
                        break;
                    }
                }
            } catch (IOException | BiffException | IndexOutOfBoundsException e) {
                LOGGER.error("Exception when readExcelFile: ", e);
            }
        } else {
            throw new IllegalArgumentException("The specified file is not Excel XLS file");
        }
        return lst;
    }

    public static String exportObjectCollectionMultiSheet(Map<String, String> mapParams, List listReport, String templateFileName, String templateFilePath, String outputFilePath) {
        long id = System.currentTimeMillis();
        InputStream is = null;
        OutputStream os = null;
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePathOutput = outputFilePath + templateFileName + "_" + sdf.format(new Date()) + ".xls";
        String filePathInput = templateFilePath + templateFileName + (templateFileName.toLowerCase().endsWith("xls") ? "" : ".xls");
        mapParams.put("dateReport", DateCommon.convertDateTimeToString(new Date()));
        LOGGER.info("[B][" + id + "] exportObjectCollectionMultiSheet " + filePathInput + " >>> " + filePathOutput);
        try {
            Map beans = new HashMap();
            for (Map.Entry thisEntry : mapParams.entrySet()) {
                beans.put(thisEntry.getKey(), thisEntry.getValue());
            }
            List sheetNames = new ArrayList();
            List tempNames = new ArrayList();
            List maps = new ArrayList();
            String sheetName;
            String tempName;
            int rownumInSheet = 50000;
            int size = listReport.size();
            int sheetNum = 1 + (int) Math.ceil(size / rownumInSheet);
            List listReportForSingleSheet = new ArrayList();
            int begin = 0;
            int end = rownumInSheet;
            for (int i = 0; i < sheetNum; i++) {
                //Lay gia tri bat dau va ket thuc ung voi thu tu dong tren listReport
                begin = rownumInSheet * i;
                end = rownumInSheet * (i + 1);
                if (begin >= size) {
                    break;
                }
                if (end > size) {
                    end = size;
                }
                //Chia listReport bao cao thanh nhieu list ung voi tung Sheet
                listReportForSingleSheet = listReport.subList(begin, end);
                sheetName = "Sheet_" + (begin + 1) + "." + end; //Bat dau tu Sheet1
                tempName = "Sheet";
                tempNames.add(tempName);
                sheetNames.add(sheetName);
                Map bean = new HashMap();
                bean.put("listResult", listReportForSingleSheet);
                maps.add(bean);
            }
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            XLSTransformer transformer = new XLSTransformer();
            is = new BufferedInputStream(new FileInputStream(filePathInput));
            hssfWorkbook = transformer.transformMultipleSheetsList(is, maps, sheetNames, "map", beans, 0);
            os = new BufferedOutputStream(new FileOutputStream(filePathOutput));
            hssfWorkbook.write(os);
        } catch (Exception e) {
            LOGGER.error("Exception[" + id + "][Duration = " + (System.currentTimeMillis() - id) + "] when exportObjectCollectionMultiSheet: ", e);
            return filePathOutput;
        } finally {
            FileCommon.close(is, os);
            LOGGER.info("[E][" + id + "][Duration = " + (System.currentTimeMillis() - id) + "] exportObjectCollectionMultiSheet >>> " + filePathOutput);
        }
        return filePathOutput;
    }
}
