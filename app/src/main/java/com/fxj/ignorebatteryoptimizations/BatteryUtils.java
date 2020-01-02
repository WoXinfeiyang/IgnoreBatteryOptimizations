package com.fxj.ignorebatteryoptimizations;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public  class BatteryUtils {

    /**是否已经忽略了电池优化*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(Context context) {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        return isIgnoring;
    }

    /**请求忽略电池优化*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestIgnoreBatteryOptimizations(Activity activity) {
        try{
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:"+ activity.getPackageName()));
            activity.startActivity(intent);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**跳转到自动启动管理页面*/
    public static void jumpAutoStartManagementActivity(Activity activity){
        if(DeviceUtils.isHuawei()){
            goHuaweiSetting(activity);
        }else if(DeviceUtils.isXiaomi()){
            goXiaomiSetting(activity);
        }else if(DeviceUtils.isOPPO()){
            goOPPOSetting(activity);
        }else if(DeviceUtils.isVIVO()){
            goVIVOSetting(activity);
        }
    }

    /**
     * 跳转到指定应用的首页
     */
    private static void showActivity(Activity activity,@NonNull String packageName) {
        Intent intent =activity.getPackageManager().getLaunchIntentForPackage(packageName);
        activity.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private static void showActivity(Activity activity,@NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private static void goHuaweiSetting(Activity activity) {
        try{
            showActivity(activity,"com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch(Exception e) {
            showActivity(activity,"com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }
    }

    private static void goXiaomiSetting(Activity activity) {
        showActivity(activity,"com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
    }

    private static void goOPPOSetting(Activity activity) {
        try{
            showActivity(activity,"com.coloros.phonemanager");
        } catch(Exception e1) {
            try{
                showActivity(activity,"com.oppo.safe");
            } catch(Exception e2) {
                try{
                    showActivity(activity,"com.coloros.oppoguardelf");
                } catch(Exception e3) {
                    showActivity(activity,"com.coloros.safecenter");
                }
            }
        }
    }

    private static void goVIVOSetting(Activity activity) {
        showActivity(activity,"com.iqoo.secure");
    }
}
