package com.phonecleaner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.phonecleaner.adapters.ProcessAdapter;
import com.phonecleaner.models.ProcessInfo;
import com.phonecleaner.utils.ProcessUtils;
import java.util.ArrayList;
import java.util.List;

public class ProcessManagerActivity extends AppCompatActivity {
    
    private ListView processListView;
    private TextView memoryInfoText;
    private ProcessAdapter adapter;
    private List<ProcessInfo> processes;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);
        
        initViews();
        loadProcesses();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadProcesses();
    }
    
    private void initViews() {
        processListView = findViewById(R.id.process_list_view);
        memoryInfoText = findViewById(R.id.memory_info_text);
        
        // 设置一键清理按钮
        Button cleanAllBtn = findViewById(R.id.clean_all_btn);
        cleanAllBtn.setOnClickListener(v -> cleanAllProcesses());
        
        // 设置刷新按钮
        Button refreshBtn = findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(v -> loadProcesses());
    }
    
    private void loadProcesses() {
        processes = ProcessUtils.getRunningProcesses(this);
        long availableMemory = ProcessUtils.getAvailableMemory(this);
        long totalMemory = ProcessUtils.getTotalMemory(this);
        
        // 更新内存信息
        memoryInfoText.setText(String.format("可用内存: %dMB / 总内存: %dMB", availableMemory, totalMemory));
        
        // 设置适配器
        adapter = new ProcessAdapter(this, processes);
        processListView.setAdapter(adapter);
        
        // 设置列表项点击事件
        processListView.setOnItemClickListener((parent, view, position, id) -> {
            ProcessInfo process = processes.get(position);
            showProcessDetails(process);
        });
    }
    
    private void cleanAllProcesses() {
        if (processes == null || processes.isEmpty()) {
            Toast.makeText(this, "没有可清理的进程", Toast.LENGTH_SHORT).show();
            return;
        }
        
        List<String> packageNames = new ArrayList<>();
        int cleanedCount = 0;
        
        for (ProcessInfo process : processes) {
            if (!process.isSystemProcess()) { // 不清理系统进程
                packageNames.add(process.getPackageName());
                cleanedCount++;
            }
        }
        
        if (packageNames.isEmpty()) {
            Toast.makeText(this, "没有非系统进程可清理", Toast.LENGTH_SHORT).show();
            return;
        }
        
        ProcessUtils.cleanBackgroundProcesses(this, packageNames);
        Toast.makeText(this, "已清理 " + cleanedCount + " 个进程", Toast.LENGTH_SHORT).show();
        
        // 延迟重新加载进程列表
        processListView.postDelayed(this::loadProcesses, 1000);
    }
    
    private void showProcessDetails(ProcessInfo process) {
        String details = String.format(
            "应用名称: %s\n包名: %s\n进程ID: %d\n内存占用: %dMB\nCPU使用: %.1f%%\n系统进程: %s",
            process.getAppName(),
            process.getPackageName(),
            process.getPid(),
            process.getMemoryUsage(),
            process.getCpuUsage(),
            process.isSystemProcess() ? "是" : "否"
        );
        
        Toast.makeText(this, details, Toast.LENGTH_LONG).show();
    }
}