package com.seatrend.cd.electricbicyclesalesystem.util;

import android.graphics.Bitmap;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.seatrend.cd.electricbicyclesalesystem.common.Constants;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by seatrend on 2018/8/21.
 */

public class StringUtils {

    /**
     * 时间戳转换为字符串类型
     *
     * @return
     */
    public static String longToStringData(long date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA); // "yyyy-MM-dd HH:mm:ss"
            return sdf.format(new Date(date));
        } catch (Exception e) {
            return null;
        }
    }
    public static String longToStringDataNoHour(long date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); // "yyyy-MM-dd HH:mm:ss"
            return sdf.format(new Date(date));
        } catch (Exception e) {
            return null;
        }
    }
    public static long dateToStamp(String s){

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
            Date   date = simpleDateFormat.parse(s);
          return  date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 处理空字符串
     */
    public static String isNull(Object obj) {
        String content = "--";
        if (obj != null&& !obj.toString().equals("") && !obj.toString().equals("null"))
            content = obj.toString();
        return content;
    }

    /**
     * 处理空字符串
     */
    public static String isNulls(String obj) {
        String content = "--";
        if (obj != null && !obj.equals("") && !obj.equals("null"))
            content = obj.toString();
        return content;
    }

    public static String getProcessingResultsByCode(int code){
        switch (code){
            case 0:
                return "未处理";
            case 1:
                return "已处理";
                default:
                    return "未知状态";
        }

    }

    /**
     * 带有*号的字符
     * @param s 处理字符串
     * @param start 开始的下标
     * @param number *号的个数
     * @return
     */
    public static String StringShowStar(String s,int start,int number){
        try{
            char[] chars = s.toCharArray();
            StringBuilder builder=new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                if(i>=start && i<start+number){
                    builder.append("*");
                }else {
                    builder.append(chars[i]);
                }
            }
            return builder.toString();
        }catch (Exception e){
            return e.getMessage();
        }

    }

    public static String getKskmByNumber(String number){
        switch (number){
            case "1":
                return "科目一";
            case "2":
                return "科目二";
            case "3":
                return "科目三";
            case "4":
                return "科目四";
                default:
                    return "未知";
        }
    }

    public static String getNumberBykskm(String kskm){
        switch (kskm){
            case "科目一":
                return "1";
            case "科目二":
                return "2";
            case "科目三":
                return "3";
            case "科目四":
                return "4";
            default:
                return "1";
        }
    }
    public static String getToDayTime(){
       String toDay = longToStringDataNoHour(System.currentTimeMillis());
        //String toDay ="2018-09-07";

        return toDay;

    }

   /* *
     * 用字符串生成二维码
     *
     * @param
     * @return
     * @throws*/
    public static Bitmap createQRCode(String str) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 4);//设置白色边框的距离
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 640, 640, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static boolean isPhoneNumber(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public static String getPhotoUrl(String photoId){
        String photoUrl = "http://"+ SharedPreferencesUtils.getIpAddress() + ":" + SharedPreferencesUtils.getPort()+
                Constants.Companion.getDOWNLOAD_PHOTO()+"?fileId="+photoId;
        return photoUrl;
    }

    public static String getGlideUrl(String lsh,String xh,String zplx){

        String photoUrl = "http://"+ SharedPreferencesUtils.getIpAddress() + ":" + SharedPreferencesUtils.getPort()+
                Constants.Companion.getDOWNLOAD_PHOTO()+"?lsh="+lsh+"&xh="+xh+"&zplx="+zplx;

        /*GlideUrl glideUrl = new GlideUrl(photoUrl, new LazyHeaders.Builder()
                .addHeader("lsh", lsh)
                .addHeader("xh", xh)
                .addHeader("zplx", zplx)
                .build());*/
        return photoUrl;
    }

}
