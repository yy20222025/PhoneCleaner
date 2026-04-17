package com.phonecleaner.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.util.Log;

import com.phonecleaner.models.ProcessInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {
    private static final String TAG = "ProcessUtils";
    
    /**
     * 获取所有运行中的进程信息
     */
    public static List<ProcessInfo> getRunningProcesses(Context context) {
        List<ProcessInfo> processes = new ArrayList<>();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            
            if (runningProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    ProcessInfo info = new ProcessInfo();
                    info.setPackageName(processInfo.processName);
                    info.setPid(processInfo.pid);
                    
                    // 获取内存使用情况
                    Debug.MemoryInfo[] memoryInfoArray = am.getProcessMemoryInfo(new int[]{processInfo.pid});
                    if (memoryInfoArray.length > 0) {
                        info.setMemoryUsage(memoryInfoArray[0].getTotalPss() / 1024); // 转换为MB
                    }
                    
                    // 获取CPU使用率
                    info.setCpuUsage(getProcessCpuUsage(processInfo.pid));
                    
                    // 获取应用信息
                    try {
                        ApplicationInfo appInfo = pm.getApplicationInfo(processInfo.processName, 0);
                        info.setAppName(pm.getApplicationLabel(appInfo).toString());
                        info.setIcon(pm.getApplicationIcon(appInfo));
                        
                        // 判断是否是系统进程
                        if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                            info.setSystemProcess(true);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        info.setAppName(processInfo.processName);
                        info.setSystemProcess(false);
                    }
                    
                    processes.add(info);
                }
            }
        }
        
        return processes;
    }
    
    /**
     * 获取进程CPU使用率
     */
    private static double getProcessCpuUsage(int pid) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/" + pid + "/stat"));
            String line = reader.readLine();
            reader.close();
            
            if (line != null) {
                String[] parts = line.split(" ");
                if (parts.length > 13) {
                    long utime = Long.parseLong(parts[13]);
                    long stime = Long.parseLong(parts[14]);
                    return (utime + stime) / 100.0; // 简化计算
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "获取CPU使用率失败", e);
        }
        return 0.0;
    }
    
    /**
     * 清理后台进程
     */
    public static void cleanBackgroundProcesses(Context context, List<String> packageNames) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        
        if (am != null) {
            for (String packageName : packageNames) {
                am.killBackgroundProcesses(packageName);
                Log.i(TAG, "清理进程: " + packageName);
            }
        }
    }
    
    /**
     * 获取可用内存
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        
        if (am != null) {
            am.getMemoryInfo(memoryInfo);
            return memoryInfo.availMem / (1024 * 1024); // 转换为MB
        }
        
        return 0;
    }
    
    /**
     * 获取总内存
     */
    public static long getTotalMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        
        if (am != null) {
            am.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem / (1024 * 1024); // 转换为MB
        }
        
        return 0;
    }
    
    /**
     * 获取内存使用率
     */
    public static int getMemoryUsagePercentage(Context context) {
        long total = getTotalMemory(context);
        long available = getAvailableMemory(context);
        
        if (total > 0) {
            long used = total - available;
            return (int) ((used * 100) / total);
        }
        
        return 0;
    }
}