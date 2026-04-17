# 📱 PhoneCleaner - 智能手机清理助手

[![Android CI](https://github.com/your-username/PhoneCleaner/actions/workflows/android-build.yml/badge.svg)](https://github.com/your-username/PhoneCleaner/actions)

一个功能强大的Android手机清理应用，可以实时监控后台运行程序，优化手机性能。

## ✨ 功能特性

- 🔍 **实时监控** - 显示所有后台运行的应用进程
- 📊 **性能统计** - 内存、CPU使用率实时显示
- 🧹 **智能清理** - 一键清理无用进程和缓存
- 🎨 **现代化界面** - Material Design设计风格
- 🔒 **隐私保护** - 区分系统进程和非系统进程

## 🚀 快速开始

### 方法一：GitHub Actions自动构建（推荐）

1. **Fork此仓库**到您的GitHub账户
2. **启用Actions**：在仓库设置中启用GitHub Actions
3. **推送代码**：任何push到main分支都会触发自动构建
4. **下载APK**：在Actions页面找到最新的构建，下载APK文件

### 方法二：本地构建

```bash
# 克隆项目
git clone https://github.com/your-username/PhoneCleaner.git

# 进入项目目录
cd PhoneCleaner

# 构建APK
./gradlew assembleDebug
```

### 方法三：Android Studio

1. 使用Android Studio打开项目
2. 等待Gradle同步完成
3. Build → Build Bundle(s)/APK(s) → Build APK

## 📱 安装使用

1. 下载APK文件到手机
2. 允许"未知来源"安装
3. 打开应用，授予必要权限
4. 开始监控和清理手机

## 🔧 技术栈

- **语言**：Java
- **框架**：Android SDK
- **构建工具**：Gradle
- **CI/CD**：GitHub Actions
- **最低版本**：Android 5.0 (API 21)

## 📁 项目结构

```
PhoneCleaner/
├── app/
│   ├── src/main/java/com/phonecleaner/
│   │   ├── MainActivity.java          # 主界面
│   │   ├── ProcessManagerActivity.java # 进程管理
│   │   └── utils/ProcessUtils.java    # 进程工具类
│   ├── src/main/res/layout/
│   │   └── activity_main.xml          # 界面布局
│   └── build.gradle                   # 应用配置
├── .github/workflows/
│   └── android-build.yml              # CI/CD配置
└── README.md                          # 项目说明
```

## 🔒 所需权限

- `GET_TASKS` - 获取任务信息
- `KILL_BACKGROUND_PROCESSES` - 结束进程
- `PACKAGE_USAGE_STATS` - 使用统计（可选）

## 🤝 贡献指南

欢迎提交Issue和Pull Request！

1. Fork本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 📄 许可证

本项目采用MIT许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系方式

- 项目主页：https://github.com/your-username/PhoneCleaner
- Issues：https://github.com/your-username/PhoneCleaner/issues
- 邮箱：your-email@example.com

---

**注意**：首次构建可能需要较长时间下载依赖，请耐心等待。