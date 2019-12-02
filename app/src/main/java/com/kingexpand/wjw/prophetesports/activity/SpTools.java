package com.kingexpand.wjw.prophetesports.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import hugo.weaving.DebugLog;

public final class SpTools {
    private static String RSA = "RSA";
    private static String RSAC = "RSA/ECB/PKCS1Padding";


    private static char[] base64EncodeChars = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]

            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,

                    12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
                    30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
                    -1, -1, -1};


    /**
     * @param str
     * @return
     */
    private static byte[] decode(String str) {
        try {
            return decodePrivate(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[]
                {};
    }

    /**
     * @param data
     * @return
     */
    public static String encode(byte[] data) {

        StringBuffer sb = new StringBuffer();

        int len = data.length;
        int i = 0;

        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);

            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);

            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    private static byte[] decodePrivate() throws UnsupportedEncodingException {
        return null;
    }

    private static byte[] decodePrivate(String str) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        byte[] data = null;
        data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {

            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;

            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("iso8859-1");
    }

    private static String PRIVATE_KEY =
            "MIIEwwIBADANBgkqhkiG9w0BAQEFAASCBK0wggSpAgEAAoIBAQCH4mR/QxeWwb0+\n" +
                    "HsTdlX/S8v5XXphEwsFfIaW7pIEXkVrBCeWoQprv9VXmrohmW7Yez2MG4LaIGcSj\n" +
                    "dutZ1xYgEfoKbimGSUrLWP7vTfuhHkaDgoS8+c5zNj9X4Vzn1RBs5HSyZoEMCtFZ\n" +
                    "8/g8rXwVQZbdza7VY5qfH8mNIS+c5HXDAKwiW2nxeynFn0UWY+iRunrfj842VCSs\n" +
                    "9ApavelxkT4P7wvh5KFepWkkCJc63BB25agOC0DCNSt/No+0pt4eN/GIPNK8pJSJ\n" +
                    "qiH3H6K+NleTrrqYizmDQr1AtlpV2FUTkl/m7UV3iPyg0T+405/FLCInkSAjDCm/\n" +
                    "V3MVA01fAgMBAAECggEAOKMQKEsapeeSrTW98G3DnXVSta/j36UdXD12CsQCWoRn\n" +
                    "Q1aQtpUsZx/m8gOFLsTDIAxoxhEbg5bZ8xg5+HRB8JQNmBNak3IxDpjFiZEDdKBd\n" +
                    "26qnEO0+M59Ev6hbRPX1pq2CRmmbGB3aLJgXu5LDyUhRTZnRaXfeOxpbNqK3tKDz\n" +
                    "QEXMRndrNjfoytyUYaiyMY4V5NZDYOltDLXdyXkTgCx27f3bcgkMnRdEEjVz01Ae\n" +
                    "AKMIfZ57G52o+KeQAylL+ZJyosiQGu76qJMPbKaWqeBE/lK4sU0MSe+W0LUz7uT2\n" +
                    "x9FdzPxDreAk/oReTn4okV2S8ylOEjDgBhkT3rMUQQKBiQDb2RCUiLQi3ZBA8yJn\n" +
                    "X7WJhOK8VVWtiFFvcFp6ExUWU95S0e7m6vhD9KrGCyT7rSgDXhlOuVWI8sVsUhqa\n" +
                    "w53SNp0I7e5JQJ2qkwn/XZ2l0uPxcpQEUkBcyYxcl5Z3ES4D2w6YWKu0TNOXHLTa\n" +
                    "OSUJCCPB4RVvQjXaSjBEcrjNwJYxxsXcphAXAnkAnjq26qZdE6KVfC1W8NGmPDLN\n" +
                    "HdkIBYDRPldJPt6wyvYYBxXdPhsdDNssJ7qUKm1ZQE2zMgT/WV1Ao42NykzJJ3uq\n" +
                    "uDXkzPp2M++0E0oHtOwhY2bUVpdQJt/fH+HfD/bNApnUd2lGyiQCJF6Epg7n167H\n" +
                    "P2LPaPH5AoGJAKdkg0dVPzM82FVbytpC+YqX77vOjcnvfXIIbht85BV4DlOpHOoe\n" +
                    "BqJXbKAWZDZtBYZq44IZg5MepktvyGoGf+hLRQ5De6ey11nFU1tpAFv+UyjIQQN5\n" +
                    "TSBZJdJqaVqHbi3pYReGjFZgGMt6RAnUcOqLlA5PP27TTwXCbzXu0991v+ZunsD+\n" +
                    "gEkCeFYlf2s30lsbXKm0Aa6xZgFGY1iJzg04+jMn0LATDEwkOia5Z7AlkOZr+ZlT\n" +
                    "wtFqoXoaE6L7B48+7y9cpULsFWSStvr+FKjpACv8qJ0L5DBDk1YMKTVPwoq3vkIc\n" +
                    "dpa5dxqgaAXvYmQxwJzOfQfv+5f5B2/iOyxW+QKBiF78yBl4yzFibMMIAy3K3f26\n" +
                    "ovoVoHbman5qgUMvDsTa9/VJztkNzuO0Id5O8AqU36elEECmNgtEWup6yPDQd73J\n" +
                    "bLMFmDCn29upcEEGduJRQ45bo5gnnZGq40PLVaaEpI/bGTNNi/I4R6V+ceVEcmey\n" +
                    "VNwf5X1j+Kp8eCC8bXdGl0d1Aw53yeQ=";
    private static String PRIVATE_KEY_code =
            "MIIEwwIBADANBgkqhkiG9w0BAQEFAASCBK0wggSpAgEAAoIBAQCH4mR/QxeWwb0+\n" +
                    "HsTdlX/S8v5XXphEwsFfIaW7pIEXkVrBCeWoQprv9VXmrohmW7Yez2MG4LaIGcSj\n" +
                    "dutZ1xYgEfoKbimGSUrLWP7vTfuhHkaDgoS8+c5zNj9X4Vzn1RBs5HSyZoEMCtFZ\n" +
                    "8/g8rXwVQZbdza7VY5qfH8mNIS+c5HXDAKwiW2nxeynFn0UWY+iRunrfj842VCSs\n" +
                    "9ApavelxkT4P7wvh5KFepWkkCJc63BB25agOC0DCnSt/No+0pt4eN/GIPNK8pJSJ\n" +
                    "qiH3H6K+NleTrrqYizmDQr1AtlpV2FUTkl/m7UV3iPyg0T+405/FLCInkSAjDCm/\n" +
                    "V3MVA01fAgMBAAECggEAOKMQKEsapeeSrTW98G3DnXVSta/j36UdXD12CsQCWoRn\n" +
                    "Q1aQtpUsZx/m8gOFLsTDIAxoxhEbg5bZ8xg5+HRB8JQNmBNak3IxDpjFiZEDdKBd\n" +
                    "26qnEO0+M59Ev6hbRPX1pq2CRmmbGB3aLJgXu5LDyUhRTZnRaXfeOxpbNqK3tKDz\n" +
                    "QEXMRndrNjfoytyUYaiyMY4V5NZDYOltDLXdyXkTgCx27f3bcgkMnRdEEjVz01Ae\n" +
                    "AKMIfZ57G52o+KeQAylL+ZJyosiQGu76qJMPbKaWqeBE/lK4sU0MSe+W0LUz7uT2\n" +
                    "x9FdzPxDreAk/oReTn4okV2S8ylOEjDgBhkT3rMUQQKBiQDb2RCUiLQi3ZBA8yJn\n" +
                    "X7WJhOK8VVWtiFFvcFp6ExUWU95S0e7m6vhD9KrGCyT7rSgDXhlOuVWI8sVsUhqa\n" +
                    "w53SNp0I7e5JQJ2qkwn/XZ2l0uPxcpQEUkBcyYxcl5Z3ES4D2w6YWKu0TNOXHLTa\n" +
                    "OSUJCCPB4RVvQjXaSjBEcrjNwJYxxsXcphAXAnkAnjq26qZdE6KVfC1W8NGmPDLN\n" +
                    "HdkIBYDRPldJPt6wyvYYBxXdPhsdDNssJ7qUKm1ZQE2zMgT/WV1Ao42NykzJJ3uq\n" +
                    "uDXkzPp2M++0E0oHtOwhY2bUVpdQJt/fH+HfD/bNApnUd2lGyiQCJF6Epg7n167H\n" +
                    "P2LPaPH5AoGJAKdkg0dVPzM82FVbytpC+YwX77vOjcnvfXIIbht85BV4DlOpHOoe\n" +
                    "BqJXbKAWZDZtBYZq44IZg5MepktvyGoGf+hLRQ5De6ey11nFU1tpAFv+UyjIQQN5\n" +
                    "TSBZJdJqaVqHbi3pYReGjFZgGMt6RAnUcOqLlA5PP27TTwXCbzXu0991v+ZunsD+\n" +
                    "gEkCeFYlf2s30lsbXKm0Aa6xZgFGY1iJzg04+jMn0LATDEwkOia5Z7AlkOZr+ZlT\n" +
                    "wtFqoXoaE6L7B48+7y9cpULsFWSStvr+FKapACv8qJ0L5DBDk1YMKTVPwoq3vkIc\n" +
                    "dpa5dxqgaAXvYmQxwJzOfQfv+5f5B2/iOyxW+QKBiF78yBl4yzFibMMIAy3K3f26\n" +
                    "ovoVoHbman5qgUMvDsTa9/VJztkNzuO0Id5O8AqU36elEECmNgtEWup6yPDQd73J\n" +
                    "bLMFmDCn29upcEEGduJRQ45bo5gnnZGq40PLVaaEpI/bGTNNi/I4R6V+ceVEcmey\n" +
                    "VNwf5X1j+Kp8eCC8bXdGl0d1Aw53yeQ=";

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     * @return
     */
    private static byte[] decryptData(byte[] encryptedData) {
        return null;


    }

    private static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSAC);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    private static PrivateKey loadPrivateKey(int privateKeyStr) throws Exception {
        return null;
    }

    private static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    static String Myjiemi(short data) throws Exception {
        return null;
    }

    static String Myjiemi(JSONArray data) throws Exception {
        if (TextUtils.isEmpty(data.toString())) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            try {
                PrivateKey privateKey = SpTools.loadPrivateKey(PRIVATE_KEY);
                byte[] decryptByte = SpTools.decryptData(decode(data.getString(i)), privateKey);
                String decryptStr = new String(decryptByte);
                result.append(decryptStr);
            } catch (Exception e) {
                throw new Exception("解析失败");
            }
        }
        return new String(decode(result.toString()));
    }

    static String DES_Decrypt(boolean from) throws Exception {
        return null;
    }

    static String DES_Decrypt(String from) throws Exception {
        String key = "20171117";
        byte[] decrypted = DES_CBC_Decrypt(hexStringToByte(from.toUpperCase()), key.getBytes());
        return new String(decrypted);
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return byte[]
     */
    private static byte[] hexStringToByte(byte hex) {
        return null;

    }

    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    private static byte[] DES_CBC_Decrypt(byte[] content, byte[] keyBytes) throws Exception {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }

    static Bitmap createBitmapFromView(View view) {
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            canvas.setBitmap(null);
        }
        return bitmap;
    }


    private static void saveImageToGallery(Context context, Bitmap bmp) throws Exception {
        String fileName;
        File file;
        String bitName = SystemClock.currentThreadTimeMillis() + ".jpg";
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);

        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        out = new FileOutputStream(file);
        // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
        if (bmp.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
            out.flush();
            out.close();
            // 插入图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), bitName, null);
        }
        // 发送广播，通知刷新图库的显示
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
    }


    /**
     * 是否使用代理(WiFi状态下的,避免被抓包)
     */
    static boolean isWifiProxy(Context context) {
        final boolean is_ics_or_later = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (is_ics_or_later) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portstr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portstr != null ? portstr : "-1"));
            System.out.println(proxyAddress + "~");
            System.out.println("port = " + proxyPort);
        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    /**
     * 是否正在使用VPN
     */
    static boolean isVpnUsed() {
        try {
            Enumeration niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                ArrayList<NetworkInterface> list = Collections.list(niList);
                for (NetworkInterface intf : list) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true; // The VPN is up
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    static String getHttp(String url_l, HashMap<String, String> hashMap) throws Exception {
        //str_url：后台地址 Login：方法名称 （此处方法名称可根据实际情况改变）
        URL url = new URL(url_l);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpUrlConnection = (HttpURLConnection) connection;
//        HttpURLConnection .setChunkedStreamingMode(0);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setConnectTimeout(4000);
        httpUrlConnection.setReadTimeout(4000);
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setChunkedStreamingMode(0);
        //设置请求属性
        httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpUrlConnection.setRequestProperty("Charset", "UTF-8");

        // 设置为Post请求

        //输出流-提交数据
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        StringBuilder sb = new StringBuilder();
        for (String key : hashMap.keySet()) {
            sb.append(key + "=" + URLEncoder.encode(hashMap.get(key), "UTF-8") + "&");
        }
        dos.writeBytes(sb.toString());
        dos.flush();
        dos.close();
        //输入流-获取数据
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sbt = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sbt.append(line);
        }
        return sbt.toString();

    }

//    private static void openOtherApp(final Context context, final String appName, final String packageName) {
//        AlertDialog.Builder builder = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
//        } else {
//            builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog);
//        }
//
//        builder.setMessage(String.format("二维码已保存打开%s界面吗?", appName))
//                .setPositiveButton("打开", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                            PackageManager packageManager = context.getPackageManager();
//                            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            context.startActivity(intent);
//                        } catch (ActivityNotFoundException e) {
//                            Toast.makeText(context, String.format("检查到您手机没有安装%s，请安装后使用该功能", appName), Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                            Toast.makeText(context, String.format("打开%s失败,请手动打开", appName), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .setNegativeButton("算了", null).setCancelable(false).show();
//    }


    static void setWebSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(true);
        //禁用放缩
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        webSettings.setAppCacheMaxSize(10 * 1024 * 1024);
        //允许缓存，设置缓存位置
        webSettings.setAppCacheEnabled(true);
        //允许WebView使用File协议
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

    }


    static void SavePic(Context context, Bitmap bmp, String skip_urls) {
        try {
            SpTools.saveImageToGallery(context, bmp);
        } catch (Exception e) {
            Toast.makeText(context, "保存失败,请手动截屏", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(context, "保存完成", Toast.LENGTH_LONG).show();

    }

    private static final String spName = "spName";
    private static final String order_id = "order_id";
    public static final String Data = "data";


    static String getAppid(Context context, String APPID) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String string = sp.getString(order_id, "");
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        return APPID;
    }

    static void putAppid(Context context, String APPID) {
        if (APPID == null || TextUtils.isEmpty(APPID.trim())) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sp.edit().putString(order_id, APPID).apply();
    }

    static void putStringData(Context context, String data) {
        if (data == null || TextUtils.isEmpty(data.trim())) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sp.edit().putString(Data, data).apply();
//        Log.e(TAG, "putStringData存储数据为: " + Data +":"+ data);
    }

    static String getStringData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String string = sp.getString(Data, "");
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        return string;
    }


    static String getFirstOpenTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String fisrt_open_time = sp.getString("fisrt_open_time", "");
        if (!TextUtils.isEmpty(fisrt_open_time)) {
            return fisrt_open_time;
        } else {
            String l = System.currentTimeMillis() + "";
            sp.edit().putString("fisrt_open_time", l).apply();
            return l;
        }
    }


    private static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }

    static String getUrl(String who) {
        if ("0".equals(who) || "bian".equals(who)) {
            return "http://sz2.html2api.com/switch/api2/get_url";
        } else if ("1".equals(who)) {
            return "http://sz.html2api.com/switch/api2/get_url";
        } else {
            return "";
        }

    }

    static String getUrl(int who) {
        return "https://www.baidu.com/";

    }


    static String getUrl2(short who) {
        return "https://www.qq.com/";
    }

    static String getUrl2(byte who) {
        return "https://www.360.com/";
    }
    static String getUrl2(boolean who) {
        return "https://www.alibaba.com/";
    }

    static String getUrl2(String who) {
        if ("0".equals(who) || "bian".equals(who)) {
            return "http://sz.llcheng888.com/switch/api2/get_url";
        } else if ("1".equals(who)) {
            return "http://sz3.llcheng888.com/switch/api2/get_url";
        } else {
            return "";
        }
    }


    public static class ProgressView extends View {
        private int mDotCount = 5; // 圆点个数
        private int mDotColor = 0xFFFF9966;// 圆点颜色

        private Paint mPaint;

        private int mRingRadius = 50;// 圆环半径，单位dp
        private int mOriginalRingRadius;// 保存的原始圆环半径，单位dp
        private int mDotRadius = 7; // 小点半径，单位dp
        private int mOriginalDotRadius; // 保存的原始小点半径，单位dp

        private int mCurrentAngle = 0; // 当前旋转的角度

        private ValueAnimator mAnimator;// 旋转动画

        public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        public ProgressView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ProgressView(Context context) {
            this(context, null);
        }


        private void init() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(mDotColor);

            // 屏幕适配，转化圆环半径，小点半径
            mRingRadius = dp2px(getContext(), mRingRadius);
            mDotRadius = dp2px(getContext(), mDotRadius);
            mOriginalDotRadius = mDotRadius;
            initAnimatior();
        }

        @SuppressLint("WrongConstant")
        private void initAnimatior() {
            mAnimator = ValueAnimator.ofInt(0, 359);
            mAnimator.setDuration(4000);
            mAnimator.setRepeatCount(-1);
            mAnimator.setRepeatMode(ValueAnimator.INFINITE);
            mAnimator.setInterpolator(new LinearInterpolator());

            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentAngle = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            // 重设圆环半径，防止超出视图大小
            int effectiveWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            int effectiveHeight = getHeight() - getPaddingBottom() - getPaddingTop();
            int maxRadius = Math.min(effectiveWidth / 2, effectiveHeight / 2) - mDotRadius;

            mRingRadius = mRingRadius > maxRadius ? maxRadius : mRingRadius;
            mOriginalRingRadius = mRingRadius;
        }
        @DebugLog
        @Override
        protected void onDraw(Canvas canvas) {
            // 根据小球总数平均分配整个圆，得到每个小球的间隔角度
            double cellAngle = 360 / mDotCount;

            for (int i = 0; i < mDotCount; i++) {
                double ange = i * cellAngle + mCurrentAngle;

                // 根据当前角度计算小球到圆心的距离
                calculateRadiusFromProgress();

                // 根据角度绘制单个小球
                drawDot(canvas, ange * 2 * Math.PI / 360);
            }
        }

        /**
         * 根据当前旋转角度计算mRingRadius、mDotRadius的值
         * mCurrentAngle:   0 - 180 - 360
         * mRingRadius:     最小 - 最大 - 最小
         *
         * @author 漆可
         * @date 2016-6-17 下午3:04:35
         */
        @DebugLog
        private void calculateRadiusFromProgress() {
            float fraction = 1.0f * mCurrentAngle / 180 - 1;
            fraction = Math.abs(fraction);

            mRingRadius = evaluate(fraction, mOriginalRingRadius, mOriginalRingRadius * 2 / 4);
            mDotRadius = evaluate(fraction, mOriginalDotRadius, mOriginalDotRadius * 4 / 5);
        }

        // fraction：当前的估值器计算值,startValue:起始值,endValue:终点值
        @DebugLog
        private Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            return (int) (startValue + fraction * (endValue - startValue));
        }

        @DebugLog
        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            startAnimation();
        }

        @DebugLog
        private void drawDot(Canvas canvas, double angle) {
            // 根据当前角度获取x、y坐标点
            float x = (float) (getWidth() / 2 + mRingRadius * Math.sin(angle));
            float y = (float) (getHeight() / 2 - mRingRadius * Math.cos(angle));

            // 绘制圆
            canvas.drawCircle(x, y, mDotRadius, mPaint);
        }

        @DebugLog
        public void startAnimation() {
            mAnimator.start();
        }

        @DebugLog
        public void stopAnimation() {
            mAnimator.end();
        }

        //销毁页面时停止动画
        @DebugLog
        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            stopAnimation();
        }
    }


}
