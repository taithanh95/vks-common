package com.bitsco.vks.common.util;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : TruongNQ
 * @date created : Apr 7, 2018
 * @describe : Expression des is undefined on line 13, column 25 in
 * Templates/Classes/Class.java.
 */
public class FileCommon {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.COMMON);

    public static void close(InputStream is, OutputStream os) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception ex) {
                LOGGER.error("Exception when closing InputStream: ", ex);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (Exception ex) {
                        LOGGER.error("Exception when closing InputStream: ", ex);
                    }
                }
            }
        }
    }

    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception ex) {
                LOGGER.error("Exception when closing InputStream: ", ex);
            }
        }
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

    public static ArrayList<String> readStringFileFromTo(File file, int recentRow, int row) throws Exception {
        if (file == null)
            throw new CommonException(Response.DATA_NOT_FOUND);
        else if (!file.exists())
            throw new CommonException(Response.FILE_NOT_FOUND);
        ArrayList<String> list = new ArrayList<String>();
        FileInputStream fileInputStream = null;
        DataInputStream dataInput = null;
        BufferedReader bufferReadre = null;
        String line = null;
        try {
            fileInputStream = new FileInputStream(file);
            dataInput = new DataInputStream(fileInputStream);
            bufferReadre = new BufferedReader(new InputStreamReader(dataInput));
            int lineNum = 0;
            while (lineNum < recentRow) {
                line = bufferReadre.readLine();
                lineNum += 1;
            }
            while ((line = bufferReadre.readLine()) != null) {
                if (lineNum >= recentRow && lineNum < recentRow + row) {
                    if (line != null) {
                        list.add(line);
                    } else {
                        return list;
                    }
                }
                lineNum = lineNum + 1;
            }
        } catch (Exception ex) {
            //System.out.println(ex.toString());
        } finally {
            bufferReadre.close();
            dataInput.close();
            fileInputStream.close();
        }
        return list;
    }

    public static void addTargetFile(ZipOutputStream zos, File file, String fileName) throws Exception {
        int EOF = -1;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        ZipEntry target = new ZipEntry(fileName);
        zos.putNextEntry(target);

        byte buf[] = new byte[1024];
        int count;
        while ((count = bis.read(buf, 0, 1024)) != EOF) {
            zos.write(buf, 0, count);
        }
        bis.close();
        zos.closeEntry();
    }

    private static void folderZip(String path, String fileName, ZipOutputStream zos, String zipFileName)
            throws Exception {
        File file = new File(path);

        if (file.isFile()) {
            addTargetFile(zos, file, fileName);
        } else {
            int point = file.getPath().lastIndexOf(zipFileName);
            String zipfolderPath = file.getPath().substring(point + zipFileName.length());
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                folderZip(fileList[i].getPath(), zipfolderPath + "\\" + fileList[i].getName(), zos, zipFileName);
            }
        }
    }

    public static void zip(String path) throws Exception {
        int point = path.lastIndexOf(".");
        zip(path, path.substring(0, point));
    }

    public static void zip(String path, String zipFileName) throws Exception {
        File file = new File(path);
        File zipFile = new File(zipFileName + ".zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

        if (file.isFile()) {
            addTargetFile(zos, file, file.getName());

        } else {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                int point = zipFile.getName().lastIndexOf(".");
                folderZip(fileList[i].getPath(), fileList[i].getName(), zos, zipFile.getName().substring(0, point));
            }
        }
        zos.close();

    }

    public static String convertFile2Base64(File file) throws Exception {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] imageBytes = new byte[(int) file.length()];
            is.read(imageBytes, 0, imageBytes.length);
            return Base64.encodeBase64String(imageBytes);
        } catch (Exception e) {
            LOGGER.error("Exception when convertFile2Base64 ", e);
            throw e;
        } finally {
            close(is);
        }
    }

    public static List<String> getAllFileNamesOfDirectory(File folder) throws Exception {
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
            for (int i = 0; i < listOfFiles.length; i++)
                if (listOfFiles[i].isFile())
                    list.add(listOfFiles[i].getName());

        return list;
    }

    public static List<String> getAllFolderNamesOfDirectory(File folder) throws Exception {
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
            for (int i = 0; i < listOfFiles.length; i++)
                if (listOfFiles[i].isDirectory())
                    list.add(listOfFiles[i].getName());

        return list;
    }

    public static List<String> getAllChildrenNamesOfDirectory(File folder) throws Exception {
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
            for (int i = 0; i < listOfFiles.length; i++)
                list.add(listOfFiles[i].getName());

        return list;
    }

    public static List<String> getAllFilePathsOfDirectory(File folder) throws Exception {
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
            for (int i = 0; i < listOfFiles.length; i++)
                if (listOfFiles[i].isFile())
                    list.add(listOfFiles[i].getAbsolutePath());

        return list;
    }

    public static List<String> getAllFolderPathsOfDirectory(File folder) throws Exception {
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
            for (int i = 0; i < listOfFiles.length; i++)
                if (listOfFiles[i].isDirectory())
                    list.add(listOfFiles[i].getAbsolutePath());

        return list;
    }

    public static List<String> getAllChildrenPathsOfDirectory(File folder) throws Exception {
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
            for (int i = 0; i < listOfFiles.length; i++)
                list.add(listOfFiles[i].getAbsolutePath());
        return list;
    }
}
