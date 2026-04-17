package com.phonecleaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.phonecleaner.models.ProcessInfo;
import java.util.List;

public class ProcessAdapter extends BaseAdapter {
    
    private Context context;
    private List<ProcessInfo> processes;
    private LayoutInflater inflater;
    
    public ProcessAdapter(Context context, List<ProcessInfo> processes) {
        this.context = context;
        this.processes = processes;
        this.inflater = LayoutInflater.from(context);
    }
    
    @Override
    public int getCount() {
        return processes.size();
    }
    
    @Override
    public ProcessInfo getItem(int position) {
        return processes.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_process, parent, false);
            holder = new ViewHolder();
            holder.appIcon = convertView.findViewById(R.id.app_icon);
            holder.appName = convertView.findViewById(R.id.app_name);
            holder.packageName = convertView.findViewById(R.id.package_name);
            holder.memoryUsage = convertView.findViewById(R.id.memory_usage);
            holder.cpuUsage = convertView.findViewById(R.id.cpu_usage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ProcessInfo process = processes.get(position);
        
        // 设置应用图标
        if (process.getIcon() != null) {
            holder.appIcon.setImageDrawable(process.getIcon());
        } else {
            holder.appIcon.setImageResource(R.drawable.ic_default_app);
        }
        
        // 设置应用名称
        holder.appName.setText(process.getAppName());
        
        // 设置包名（简化显示）
        String packageName = process.getPackageName();
        if (packageName.length() > 20) {
            packageName = packageName.substring(0, 17) + "...";
        }
        holder.packageName.setText(packageName);
        
        // 设置内存使用
        holder.memoryUsage.setText(String.format("%dMB", process.getMemoryUsage()));
        
        // 设置CPU使用
        holder.cpuUsage.setText(String.format("%.1f%%", process.getCpuUsage()));
        
        // 根据是否是系统进程设置不同背景色
        if (process.isSystemProcess()) {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        
        return convertView;
    }
    
    public List<ProcessInfo> getProcesses() {
        return processes;
    }
    
    private static class ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView packageName;
        TextView memoryUsage;
        TextView cpuUsage;
    }
}