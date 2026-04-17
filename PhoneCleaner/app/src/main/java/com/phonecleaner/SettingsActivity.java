package com.phonecleaner;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    
    private Switch autoCleanSwitch;
    private Switch notificationSwitch;
    private TextView versionText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        autoCleanSwitch = findViewById(R.id.autoCleanSwitch);
        notificationSwitch = findViewById(R.id.notificationSwitch);
        versionText = findViewById(R.id.versionText);
        
        // 设置版本信息
        versionText.setText("版本: 1.0");
        
        // 设置默认值
        autoCleanSwitch.setChecked(true);
        notificationSwitch.setChecked(true);
    }
}