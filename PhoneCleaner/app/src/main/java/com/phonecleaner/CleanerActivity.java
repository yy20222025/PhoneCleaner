package com.phonecleaner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CleanerActivity extends AppCompatActivity {
    
    private TextView memoryInfoText;
    private Button cleanButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner);
        
        memoryInfoText = findViewById(R.id.memoryInfoText);
        cleanButton = findViewById(R.id.cleanButton);
        
        // 初始化界面
        updateMemoryInfo();
        
        cleanButton.setOnClickListener(v -> {
            // 执行清理操作
            performCleanup();
        });
    }
    
    private void updateMemoryInfo() {
        // 更新内存信息
        memoryInfoText.setText("当前内存使用情况...");
    }
    
    private void performCleanup() {
        // 执行清理逻辑
        memoryInfoText.setText("清理完成！");
    }
}