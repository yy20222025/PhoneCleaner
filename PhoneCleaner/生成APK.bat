@echo off
echo ========================================
echo    PhoneCleaner APK 生成脚本
echo ========================================
echo.
echo 正在检查环境...

:: 检查是否在正确目录
if not exist "app\src\main\java\com\phonecleaner\MainActivity.java" (
    echo 错误：请确保在PhoneCleaner项目根目录运行此脚本
    echo 当前目录：%cd%
    pause
    exit /b 1
)

echo 项目结构检查通过！
echo.
echo 请选择生成APK的方法：
echo.
echo 1. 使用命令行构建（需要Java和Android SDK）
echo 2. 使用Android Studio构建（推荐，需要安装Android Studio）
echo 3. 查看详细使用说明
echo.
set /p choice=请选择 (1-3): 

if "%choice%"=="1" (
    echo.
    echo 正在使用命令行构建APK...
    echo 这可能需要几分钟时间...
    echo.
    gradlew assembleDebug
    if %errorlevel% equ 0 (
        echo.
        echo ✅ APK生成成功！
        echo APK文件位置：app\build\outputs\apk\debug\app-debug.apk
        echo.
        echo 📱 安装方法：
        echo 1. 将APK文件复制到手机
        echo 2. 在手机文件管理器中找到并安装
        echo 3. 需要允许"未知来源"安装
    ) else (
        echo.
        echo ❌ 构建失败，请检查错误信息
        echo 可能需要安装Android SDK或配置环境变量
    )
) else if "%choice%"=="2" (
    echo.
    echo 📱 Android Studio构建指南：
    echo.
    echo 1. 下载安装Android Studio
    echo 2. 打开项目：File → Open → 选择此文件夹
    echo 3. 等待Gradle同步完成
    echo 4. Build → Build Bundle(s)/APK(s) → Build APK
    echo 5. APK位置：app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 详细说明请查看：PhoneCleaner使用说明.md
) else if "%choice%"=="3" (
    start "" "PhoneCleaner使用说明.md"
) else (
    echo 无效选择
)

pause