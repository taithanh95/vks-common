package com.bitsco.vks.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public static final String getString(Object o) {
        try {
            return o.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    // chuyen doi Date tu java.util.Date sang java.sql.Date

//    public static final String getSqlDate(java.util.Date date) {
//        try {
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            return dateFormat.format(date);
//        } catch (Exception ex) {
//            return null;
//        }
//    }

    public static final java.sql.Date getSqlDate(Date date) {
        try {
            return new java.sql.Date(date.getTime());
        } catch (Exception ex) {
            return null;
        }
    }

    // Chuyen doi Date tu java.sql.Date java.util.Date

    public static final Date getUtilDate(Timestamp date) {
        try {
            return new Date(date.getTime());
        } catch (Exception ex) {
            return null;
        }
    }

//    /*
//     * HungTD5 - 24/10/2012: Chuyá»ƒn Ä‘á»•i Date to String Ä‘á»‹nh dáº¡ng: dd/MM/yyyy (vÃ  ngÆ°á»£c láº¡i)
//     */
//
    public static final String convertDateToString(Date _date) {
        if (_date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(_date);
    }

    public static final String convertDateToString(Date _date, String pattern) {
        if (_date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(_date);
    }
//
//    public static final Date convertStringToDate(String str) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(Messages.getMessage(Constants.BUNDLE_QLA, "commons.dateFormat"));
//        return sdf.parse(str);
//    }

    public static final Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }


//    /*
//     * HungTD5 - 16/10/2012: Danh muc cac bang la dictionary cá»§a há»‡ thá»‘ng
//     */
//
//    public static final Map<String, String> getCategoryFromBundle(String _srcBundle, Constants.CATEGORIES _prefix) {
//        if (_srcBundle == null)
//            _srcBundle = Constants.BUNDLE_QLA;
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        ResourceBundle bundle = ctx.getApplication().getResourceBundle(ctx, _srcBundle);
//        Map<String, String> _buffer = new LinkedHashMap<String, String>();
//        try {
//            String _prefixValue = "value";
//            String _prefixText = "text";
//
//            //Lay danh sach key tu bundle
//            Object[] _keySet = bundle.keySet().toArray();
//            for (int i = 0; i < _keySet.length; i++) {
//                //Kiem tra key chua prefix can lay va key chua ky tu VALUE
//                if (_keySet[i].toString().indexOf(_prefix.toString() + ".") >= 0 &&
//                        _keySet[i].toString().indexOf(_prefixValue) >= 0) {
//                    //Lay ten key
//                    String _nameOfKey = _keySet[i].toString().replace(_prefixValue, "");
//                    _buffer.put(bundle.getString(_nameOfKey + _prefixText),
//                            bundle.getString(_nameOfKey + _prefixValue));
//                }
//            }
//
//            //Sáº¯p xáº¿p map theo thá»© tá»± tÄƒng dáº§n
//            _buffer = sortByComparator(_buffer);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return _buffer;
//    }
    /*
     * HungTD5 13/01/2013
     * Sáº¯p xáº¿p map theo thá»© tá»± tÄƒng dáº§n
     */

    private static final Map sortByComparator(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

//    public static final String getTextFromBundle(String _srcBundle, Constants.CATEGORIES _prefix, String value) {
//        Iterator it = getCategoryFromBundle(_srcBundle, _prefix).entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pairs = (Map.Entry)it.next();
//            if (pairs.getValue().toString().equalsIgnoreCase(value))
//                return pairs.getKey().toString();
//        }
//        return "";
//    }
    /*
     * HungTD5 - 26/10/2012: Láº¥y 1 giÃ¡ trá»‹ tá»« Bundle
     * Return: String
     */

//    public static final String getValueFromBundle(String _srcBundle, String key) {
//        if (_srcBundle == null)
//            _srcBundle = Constants.BUNDLE_QLA;
//        FacesContext ctx = FacesContext.getCurrentInstance();
//
//        return ctx.getApplication().getResourceBundle(ctx, key).toString();
//    }
    /*
     * HungTD5 - 16/10/2012: Copy gia tri tu doi tuong 1 sang doi tuong 2
     * Return: boolean
     */

    public static final boolean copyProperties(Object _obj1, Object _obj2, boolean _ignoreCase) {
        if (_obj2 == null || _obj1 == null)
            return true;
        try {
            List<Field> fields1 = Utils.getAllFields(_obj1.getClass());
            List<Field> fields2 = Utils.getAllFields(_obj2.getClass());
            String[] _exceptFields =
                    { "delBtnDisabled", "updBtnDisabled", "dataTable", "selectedItems", "data", "rnum", "rowcount",
                            "detailInfo", "prefix", "bundle", "dynamicColumns" };
            for (Field field1 : fields1) {
                if (!Modifier.isStatic(field1.getModifiers())) { // 25/04/2013
                    for (Field field2 : fields2) {
                        field1.setAccessible(true);
                        field2.setAccessible(true);
                        if (!StringInArray(_exceptFields, field1.getName())) {
                            if ((field1.getName() == field2.getName() && !_ignoreCase) ||
                                    (_ignoreCase && field1.getName().equalsIgnoreCase(field2.getName()))) {
                                field1.set(_obj1, field2.get(_obj2));
                                break;
                            }
                        }
                    }
                }
            }
            //BeanUtils.copyProperties(_obj1, _obj2);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static final boolean copyProperties(Object _obj1, Object _obj2, boolean _ignoreCase,
                                               boolean _whenProperObj1Null) {
        try {
            List<Field> fields1 = Utils.getAllFields(_obj1.getClass());
            List<Field> fields2 = Utils.getAllFields(_obj2.getClass());
            String[] _exceptFields =
                    { "delBtnDisabled", "updBtnDisabled", "dataTable", "selectedItems", "data", "rnum", "rowcount",
                            "detailInfo", "prefix", "bundle", "dynamicColumns" };
            for (Field field1 : fields1) {
                for (Field field2 : fields2) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (!StringInArray(_exceptFields, field1.getName())) {
                        if ((field1.getName() == field2.getName() && !_ignoreCase) ||
                                (_ignoreCase && field1.getName().equalsIgnoreCase(field2.getName()))) {
                            if (_whenProperObj1Null && field1.get(_obj1) != null)
                                continue;
                            field1.set(_obj1, field2.get(_obj2));
                            break;
                        }
                    }
                }
            }
            //BeanUtils.copyProperties(_obj1, _obj2);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static final boolean StringInArray(String[] arr, String name) {
        for (String item : arr) {
            if ((item == null && name != null) || (item != null && name == null))
                return false;
            else if ((item == null && name == null) || item.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public static final boolean StringIsNullOrEmpty(String str) {
        if (str == null)
            return true;

        str = str.trim();
        if (str.equalsIgnoreCase(""))
            return true;
        else
            return false;
    }

    /*HungTD5: so sanh 2 gia tri string co bang nhau k*/

    public static final boolean StringEquals(String str1, String str2) {
        if (StringIsNullOrEmpty(str1))
            str1 = "";
        if (StringIsNullOrEmpty(str2))
            str2 = "";

        return str1.equals(str2);
    }

    /*
     * HungTD5 - 17/10/2012: Lay cac field tu mot class
     * Return: List
     */

    public static final List<Field> getAllFields(Class _class) {
        Field[] fields_child = _class.getDeclaredFields();
        List<Field> fields = new ArrayList();
        Class _supperClass = _class.getSuperclass();
        fields.addAll(Arrays.asList(fields_child));

        while (_supperClass != null) {
            Field[] superFields = _supperClass.getDeclaredFields();
            fields.addAll(Arrays.asList(superFields));
            _supperClass = _supperClass.getSuperclass();
        }
        return fields;
    }

//    /*
//     * HungTD5 - 26/10/2012: Láº¥y giÃ¡ trá»‹ tá»« má»™t control
//     * Return: String
//     */
//
//    public static final String getControlValue(UIInput input) {
//        if (input == null)
//            return "";
//        else {
//            if (input.getClass().equals(HtmlInputHidden.class))
//                return ((HtmlInputHidden)input).getValue().toString();
//            else if (input.getClass().equals(HtmlInputText.class))
//                return ((HtmlInputText)input).getValue().toString();
//        }
//        return "";
//    }

    // hieutt10: (05/11/2012) tÃ¬m sá»‘ ngÃ y giá»¯a 2 thá»�i Ä‘iá»ƒm

    public static int diffDay(Date a, Date b) {
        int tempDifference = 0;
        int difference = 0;
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        if (a.compareTo(b) < 0) {
            earlier.setTime(a);
            later.setTime(b);
        } else {
            earlier.setTime(b);
            later.setTime(a);
        }

        while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
            tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
            tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        return difference;
    }

    /**
     * HieuTT10 23/1/2013
     * tráº£ vá»� ngÃ y Ä‘áº§u tiÃªn cá»§a thÃ¡ng dá»±a vÃ o ngÃ y truyá»�n vÃ o
     * */

    public static final Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);

        return c.getTime();
    }

    /**
     * HieuTT10 24/3/2014
     * tráº£ vá»� ngÃ y nÃ y nÄƒm trÆ°á»›c dá»±a vÃ o ngÃ y truyá»�n vÃ o
     * */

    public static final Date getDayOfPreviousYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        return c.getTime();
    }


    /*
     *  HungTD5 22/01/2013
     *  TÃ¬m sá»‘ ngÃ y giá»¯a 2 thá»�i Ä‘iá»ƒm (káº¿t quáº£ cÃ³ thá»ƒ Ã¢m náº¿u ngÃ y sau nhá»� hÆ¡n ngÃ y trÆ°á»›c - b < a)
     */

    public static final long dateDiff(Date a, Date b) {
        long _dayDiff = (b.getTime() - a.getTime()) / (1000 * 60 * 60 * 24);
        return _dayDiff;
    }
    //HungTD5 21/11/2012: láº¥y giÃ¡ trá»‹ String Ä‘Ã£ trim

    public static final String trimString(String str) {
        if (Utils.StringIsNullOrEmpty(str))
            return null;
        return str.trim();
    }
    // dacnv 22/05/2013

    public static final int soNgay(Date a, Date b) {
        float tong = (b.getTime() - a.getTime()) / (1000 * 60 * 60 * 24);
        int so_ngay = ((int)tong) % 30;
        return so_ngay;
    }

    public static final int soThang(Date a, Date b) {
        float tong = (b.getTime() - a.getTime()) / (1000 * 60 * 60 * 24);
        float so_thang = tong / 30;
        //
        int x = (int)so_thang;
        return x;
    }
    // end dacnv 22/05/2013

    public static final Date dateAdd(Date value, int month, int day, int year) {
        if (value == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + month));
        cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + day));
        cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + year));
        return cal.getTime();
    }

    /* Duyvv 25/05/2013: cá»™ng thÃªm giÃ¡ trá»‹ - ngÃ y hoáº·c thÃ¡ng hoáº·c nÄƒm vÃ o */

    public static final Date dateAddExe(Date value, int year, int month, int day) {
        if (value == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + year));
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + month));
        cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + day));
        return cal.getTime();
    }

    ///DUYVV tÃ­nh sá»‘ nÄƒm - thÃ¡ng  - ngÃ y trong quyáº¿t Ä‘á»‹nh thi hÃ nh Ã¡n

    public static final int getDateDifferenceInDD(Date from, Date to) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year, month, day;
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // DAY CALCULATION
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        // MONTH CALCULATION
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        return day;
    }

    public static final int getDateDifferenceInMM(Date from, Date to) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year, month, day;
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // DAY CALCULATION
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        // MONTH CALCULATION
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        return month;
    }

    public static final int getDateDifferenceInYY(Date from, Date to) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year, month, day;
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // DAY CALCULATION
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        // MONTH CALCULATION
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        return year;
    }

    ///END DUYVV


//    /* HungTD5 03/12/2012: run java script*/
//
//    public static final void executeJs(String js) {
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute(js);
//    }

//    /*HungTD5: 02/12/2012: show popup */
//
//    public static final void openPopup(Constants.PopupUrl popup, int width, int height) {
//        executeJs(getPopupScript(popup, width, height));
//    }

//    /* HungTD5: 02/12/2012: lay javascript string de show popup (cho oncomplete)*/
//
//    public static final String getPopupScript(Constants.PopupUrl popup, int width, int height) {
//        if (width == 0)
//            width = 900;
//        if (height == 0)
//            height = 400;
//        StringBuilder sbd = new StringBuilder();
//        if (width > 0 & height > 0) {
//            sbd.append("window.open('" + popup.getUrl() + "', '" + popup.toString() +
//                    "', 'location=no, directories=no, status=no, scrollbars=no, dependent=yes, menubar=no, toolbar=no, width=" +
//                    width + ", height=" + height + "');");
//        } else
//            sbd.append("window.open('" + popup.getUrl() + "', '" + popup.toString() +
//                    "', 'location=no, directories=no, status=no, scrollbars=no, dependent=yes, menubar=no, toolbar=no');");
//
//        return sbd.toString();
//    }
//
//    public static final String getPopupScript1(Constants.PopupUrl popup, int width, int height) {
//        if (width == 0)
//            width = 900;
//        if (height == 0)
//            height = 400;
//        StringBuilder sbd = new StringBuilder();
//        if (width > 0 & height > 0) {
//            sbd.append("window.open('" + popup.getUrl() + "', '" + popup.toString() +
//                    "', 'location=no, directories=no, status=no, scrollbars=no, dependent=yes, menubar=no, toolbar=no, width=" +
//                    "screen.width" + ", height=" + "screen.height" + "');");
//        } else
//            sbd.append("window.open('" + popup.getUrl() + "', '" + popup.toString() +
//                    "', 'location=no, directories=no, status=no, scrollbars=no, dependent=yes, menubar=no, toolbar=no');");
//
//        return sbd.toString();
//    }

    /*HieuTT10 10/12/2012 remove hashmap item by value*/

    public static Map<String, String> removeMapItemByValue(Map<String, String> hm, String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            if (!pairs.getValue().equals(value))
                map.put((String)pairs.getKey(), (String)pairs.getValue());
        }
        return map;
    }

//    /*
//     * 08.01.2013 (dd.MM.yyyy)
//     * QuangLA
//     * Purpose: Download file
//     */
//
//    public static final void doDownload(File downloadFile) throws FileNotFoundException, IOException {
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        ExternalContext exctx = ctx.getExternalContext();
//
//        HttpServletResponse response = (HttpServletResponse)exctx.getResponse();
//
//        response.reset();
//
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
//        response.setContentType("charset=UTF-8;application/force-download");
//
//        InputStream inputStream = new FileInputStream(downloadFile);
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        byte[] buffer = new byte[response.getBufferSize()];
//
//        int length = 0;
//
//        while ((length = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, length);
//        }
//
//        // delete downloadFile after download.
//        if (downloadFile != null && !downloadFile.delete()) {
//            downloadFile.delete();
//        }
//
//        // close input stream
//        inputStream.close();
//
//        // close output stream
//        outputStream.close();
//
//        // important: set response complete.
//        ctx.responseComplete();
//    }

//    public static final void doDownload(InputStream inputStream, String fileName) throws IOException {
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        ExternalContext exctx = ctx.getExternalContext();
//
//        HttpServletResponse response = (HttpServletResponse)exctx.getResponse();
//
//        response.reset();
//
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//        response.setContentType("charset=UTF-8;application/x-download");
//
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        byte[] buffer = new byte[response.getBufferSize()];
//
//        int length = 0;
//
//        while ((length = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, length);
//        }
//
//        // close input stream
//        inputStream.close();
//
//        // close output stream
//        outputStream.close();
//
//        // important: set response complete.
//        ctx.responseComplete();
//    }
//    // End Download file.
//    //bacnv new download for jsf 2.0
//
//    public static final void doDownloadnew(InputStream inputStream, String fileName) throws IOException {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        ExternalContext response = fc.getExternalContext();
//        response.responseReset();
//        response.setResponseContentType("charset=UTF-8;application/x-download");
//        response.addResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//
//        OutputStream out = null;
//        try {
//            byte[] buffer = new byte[response.getResponseBufferSize()];
//            out = response.getResponseOutputStream();
//
//            for (int length; (length = inputStream.read(buffer)) != -1; ) {
//                out.write(buffer, 0, length);
//            }
//            out.flush();
//            inputStream.close();
//            out.close();
//            fc.getResponseComplete();
//        } catch (IOException err) {
//            err.printStackTrace();
//        }
//    }
//    // End Download file.
//
//    /**
//     * TuTD2 09/04/2013 Download file BLOB from Database
//     * **/
//    public static void writeBlob(Blob myBlob, String name, String type) throws SQLException, IOException {
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        ExternalContext exctx = ctx.getExternalContext();
//
//        HttpServletResponse response = (HttpServletResponse)exctx.getResponse();
//
//        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "." + type + "\"");
//        if (type.equals("doc") || type.equals("docx")) {
//            response.setContentType("charset=UTF-8;application/msword");
//        } else if (type.equals("xls") || type.equals("xlsx")) {
//            response.setContentType("charset=UTF-8;application/vnd.ms-excel");
//        } else if (type.equals("ppt") || type.equals("pptx")) {
//            response.setContentType("charset=UTF-8;application/ppt");
//        } else if (type.equals("pdf")) {
//            response.setContentType("charset=UTF-8;application/pdf");
//        } else if (type.equals("txt")) {
//            response.setContentType("charset=UTF-8;text/plain");
//        } else if (type.equals("png") || type.equals("jpg") || type.equals("jpeg") || type.equals("gif")) {
//            response.setContentType("charset=UTF-8;image/jpeg");
//        } else if (type.equals("rar") || type.equals("zip")) {
//            response.setContentType("charset=UTF-8;application/rar");
//        } else {
//            response.setContentType("charset=UTF-8;application/force-download");
//        }
//        int length = (int)myBlob.length();
//        ServletOutputStream op = response.getOutputStream();
//        op.write(myBlob.getBytes(1, length));
//        op.flush();
//        op.close();
//    }
//    /*
//     * HungTD5 14/01/2013
//     * Láº¥y viewId hiá»‡n thá»�i
//     */
//
//    public static final String getCurrentViewId() {
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        return ctx.getViewRoot().getViewId();
//    }

    /*
     * HieuTT10 22/02/2013
     * Implode List<String> - Chuyá»ƒn List<String> thÃ nh String ngÄƒn cÃ¡ch bá»Ÿi glue
     * */

    public static String implodeString(List<String> input, String glue) {
        /** Output variable */
        String output = "";

        if (input != null && input.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(input.get(0));

            for (int i = 1; i < input.size(); i++) {
                sb.append(glue);
                sb.append(input.get(i));
            }

            output = sb.toString();
        }

        return output;
    }

    /*
     * HieuTT10 22/02/2013
     * Implode String[] - Chuyá»ƒn String[] thÃ nh String ngÄƒn cÃ¡ch bá»Ÿi glue
     * */

    public static String implodeString(String[] input, String glue) {
        /** Output variable */
        String output = "";

        if (input != null && input.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(input[0]);

            for (int i = 1; i < input.length; i++) {
                sb.append(glue);
                sb.append(input[i]);
            }

            output = sb.toString();
        }

        return output;
    }
    public static Boolean toBoolean(String input){
        if (input == null)
            return  false;
        input = input.trim().toLowerCase();
        return input == "t" || input == "true" || input == "y" || input == "yes" || input == "c" || input == "co" || input == "có";
    }
}
