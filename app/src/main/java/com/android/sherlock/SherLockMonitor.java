package com.android.sherlock;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Environment;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 示例：
 *         XposedHelpers.findAndHookMethod(
 *                 "需要hook的方法所在类的完整类名",
 *                 lpparam.classLoader,      // 类加载器，固定这么写就行了
 *                 "需要hook的方法名",
 *                 参数类型.class,
 *                 new XC_MethodHook() {
 *                     @Override
 *                     protected void beforeHookedMethod(MethodHookParam param) {
 *                         XposedBridge.log("调用getDeviceId()获取了imei");
 *                     }
 *
 *                     @Override
 *                     protected void afterHookedMethod(MethodHookParam param) throws Throwable {
 *                         XposedBridge.log(getMethodStack());
 *                         super.afterHookedMethod(param);
 *                     }
 *                 }
 *         );
 *
 */
public class SherLockMonitor  implements IXposedHookLoadPackage {

    @SuppressLint("PrivateApi")
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

        if (lpparam == null) {
            return;
        }

//        //hook获取设备信息方法
//        XposedHelpers.findAndHookMethod(
//                android.telephony.TelephonyManager.class.getName(),
//                lpparam.classLoader,
//                "getDeviceId",
//                int.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                       // XposedBridge.log("调用getDeviceId(int)获取了imei");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log(getMethodStack());
//                        super.afterHookedMethod(param);
//                    }
//                }
//        );
//
//        //hook imsi获取方法
//        XposedHelpers.findAndHookMethod(
//                android.telephony.TelephonyManager.class.getName(),
//                lpparam.classLoader,
//                "getSubscriberId",
//                int.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                      //  XposedBridge.log("调用getSubscriberId获取了imsi");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log(getMethodStack());
//                        super.afterHookedMethod(param);
//                    }
//                }
//        );
//        //hook低版本系统获取mac地方方法
//        XposedHelpers.findAndHookMethod(
//                android.net.wifi.WifiInfo.class.getName(),
//                lpparam.classLoader,
//                "getMacAddress",
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                      //  XposedBridge.log("调用getMacAddress()获取了mac地址");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log(getMethodStack());
//                        super.afterHookedMethod(param);
//                    }
//                }
//        );
//        //hook获取mac地址方法
//        XposedHelpers.findAndHookMethod(
//                java.net.NetworkInterface.class.getName(),
//                lpparam.classLoader,
//                "getHardwareAddress",
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                     //   XposedBridge.log("调用getHardwareAddress()获取了mac地址");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log(getMethodStack());
//                        super.afterHookedMethod(param);
//                    }
//                }
//        );

        //hook定位方法
        XposedHelpers.findAndHookMethod(
                LocationManager.class.getName(),
                lpparam.classLoader,
                "getLastKnownLocation",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getLastKnownLocation获取了GPS地址");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );


        //hook 读
//        XposedHelpers.findAndHookMethod(
//                Environment.class.getName(),
//                lpparam.classLoader,
//                "getExternalStorageDirectory",
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                        XposedBridge.log("getExternalStorageDirectory");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log(getMethodStack());
//                        super.afterHookedMethod(param);
//                    }
//                }
//        );
//


        //(Class.forName("android.app.ApplicationPackageManager")
        XposedHelpers.findAndHookMethod(
                PackageManager.class.getName(),
                lpparam.classLoader,
                "getPackageInfoAsUser",
                String.class,int.class,int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("发现了违规调用，PackageManager#getPackageInfoAsUser().....");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );



        try {
            XposedHelpers.findAndHookMethod(
                    Class.forName("android.app.ApplicationPackageManager").getName(),
                    lpparam.classLoader,
                    "getPackageInfoAsUser",
                    String.class,int.class,int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            XposedBridge.log("发现了违规调用,ApplicationPackageManager#getPackageInfoAsUser()");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log(getMethodStack());
                            super.afterHookedMethod(param);
                        }
                    }
            );
        }catch (Exception e){

        }





    }

    private String getMethodStack() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StringBuilder stringBuilder = new StringBuilder();

        for (StackTraceElement temp : stackTraceElements) {
            stringBuilder.append(temp.toString() + "\n");
        }

        return stringBuilder.toString();

    }
}
