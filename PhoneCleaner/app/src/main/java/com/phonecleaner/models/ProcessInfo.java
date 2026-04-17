package com.phonecleaner.models;

import android.graphics.drawable.Drawable;

public class ProcessInfo {
    public String packageName;
    public String appName;
    public int pid;
    public long memoryUsage; // MB
    public double cpuUsage;
    public Drawable icon;
    public boolean isSystemProcess;
    
    public ProcessInfo() {}
    
    public ProcessInfo(String packageName, String appName, int pid, long memoryUsage) {
        this.packageName = packageName;
        this.appName = appName;
        this.pid = pid;
        this.memoryUsage = memoryUsage;
    }
    
    // Getter方法
    public String getPackageName() { return packageName; }
    public String getAppName() { return appName; }
    public int getPid() { return pid; }
    public long getMemoryUsage() { return memoryUsage; }
    public double getCpuUsage() { return cpuUsage; }
    public Drawable getIcon() { return icon; }
    public boolean isSystemProcess() { return isSystemProcess; }
    
    // Setter方法
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public void setAppName(String appName) { this.appName = appName; }
    public void setPid(int pid) { this.pid = pid; }
    public void setMemoryUsage(long memoryUsage) { this.memoryUsage = memoryUsage; }
    public void setCpuUsage(double cpuUsage) { this.cpuUsage = cpuUsage; }
    public void setIcon(Drawable icon) { this.icon = icon; }
    public void setSystemProcess(boolean systemProcess) { isSystemProcess = systemProcess; }
}