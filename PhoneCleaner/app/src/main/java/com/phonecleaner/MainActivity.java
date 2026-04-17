package com.phonecleaner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.phonecleaner.models.ProcessInfo;
import com.phonecleaner.utils.ProcessUtils;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private TextView memoryUsageText;
    private TextView processCountText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        updateSystemInfo();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateSystemInfo();
    }
    
    private void initViews() {
        memoryUsageText = findViewById(R.id.memory_usage_text);
        processCountText = findViewById(R.id.process_count_text);
        
        // 后台程序管理按钮
        Button processBtn = findViewById(R.id.process_btn);
        processBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProcessManagerActivity.class);
            startActivity(intent);
        });
        
        // 一键清理按钮
        Button cleanBtn = findViewById(R.id.clean_btn);
        cleanBtn.setOnClickListener(v -> performQuickClean());
        
        // 设置按钮
        Button settingsBtn = findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
    
    private void updateSystemInfo() {
        // 更新内存使用情况
        int memoryUsage = ProcessUtils.getMemoryUsagePercentage(this);
        long availableMemory = ProcessUtils.getAvailableMemory(this);
        long totalMemory = ProcessUtils.getTotalMemory(this);
        
        memoryUsageText.setText(String.format("内存使用: %d%%\n可用: %dMB / 总: %dMB", 
            memoryUsage, availableMemory, totalMemory));
        
        // 更新进程数量
        int processCount = ProcessUtils.getRunningProcesses(this).size();
        processCountText.setText(String.format("运行进程: %d个", processCount));
    }
    
    private void performQuickClean() {
        // 执行快速清理
        new Thread(() -> {
            // 获取非系统进程
            List<ProcessInfo> processes = ProcessUtils.getRunningProcesses(this);
            List<String> packageNames = new ArrayList<>();
            
            for (ProcessInfo process : processes) {
                if (!process.isSystemProcess()) {
                    packageNames.add(process.getPackageName());
                }
            }
            
            // 清理进程
            ProcessUtils.cleanBackgroundProcesses(this, packageNames);
            
            // 更新UI
            runOnUiThread(this::updateSystemInfo);
        }).start();
        
        // 显示清理完成提示
        Toast.makeText(this, "清理完成！", Toast.LENGTH_SHORT).show();
    }
}