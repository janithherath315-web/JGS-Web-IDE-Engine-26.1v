@echo off
setlocal enabledelayedexpansion
title JGS Web IDE Engine - Boot Manager
color 0B

echo ===================================================
echo       JGS Web IDE Engine - System Initialization
echo ===================================================
echo.

if not exist "Data" mkdir Data
if not exist "Output" mkdir Output
if not exist "icon" mkdir icon

set "LOCAL_JAVAC=%~dp0jdk\bin\javac.exe"
set "LOCAL_JAVA=%~dp0jdk\bin\java.exe"

if not exist "%LOCAL_JAVAC%" (
    echo [INFO] Downloading Core JDK...
    powershell -Command "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://aka.ms/download-jdk/microsoft-jdk-17.0.10-windows-x64.zip' -OutFile 'jdk.zip'"
    powershell -Command "Expand-Archive -Path 'jdk.zip' -DestinationPath 'jdk_temp' -Force"
    for /d %%i in (jdk_temp\*) do move "%%i" "jdk" >nul
    rmdir /s /q "jdk_temp"
    del jdk.zip
)

if not exist "jfx_sdk\lib\javafx.controls.jar" (
    echo [INFO] Downloading JavaFX Graphics Engine...
    powershell -Command "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/17.0.10/openjfx-17.0.10_windows-x64_bin-sdk.zip' -OutFile 'jfx.zip'"
    powershell -Command "Expand-Archive -Path 'jfx.zip' -DestinationPath 'jfx_temp' -Force"
    for /d %%i in (jfx_temp\javafx-sdk-*) do move "%%i" "jfx_sdk" >nul
    rmdir /s /q "jfx_temp"
    del jfx.zip
)

:: [JVM Error Fix] Compiler එක සඳහා JMOD ගොනු බාගත කිරීම
if not exist "jfx_jmods\javafx.controls.jmod" (
    echo [INFO] Downloading JavaFX JMODs for Compiler...
    powershell -Command "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/17.0.10/openjfx-17.0.10_windows-x64_bin-jmods.zip' -OutFile 'jmods.zip'"
    powershell -Command "Expand-Archive -Path 'jmods.zip' -DestinationPath 'jmods_temp' -Force"
    for /d %%i in (jmods_temp\javafx-jmods-*) do move "%%i" "jfx_jmods" >nul
    rmdir /s /q "jmods_temp"
    del jmods.zip
)

set "PATH=%~dp0jfx_sdk\bin;%~dp0jdk\bin;%PATH%"

echo [INFO] Compiling Source Code...
"%LOCAL_JAVAC%" -encoding UTF-8 --module-path "%~dp0jfx_sdk\lib" --add-modules javafx.controls,javafx.web src\com\jgslanka\ide\JGSWebIDE.java

if %errorlevel% neq 0 (
    echo [ERROR] Compilation Failed!
    pause
    exit
)

echo [INFO] Packaging Application...
"%LOCAL_JAVAC%\..\jar.exe" cfe App.jar com.jgslanka.ide.JGSWebIDE -C src .

echo [SUCCESS] Launching IDE Workspace...
start "" "%LOCAL_JAVA%" --module-path "%~dp0jfx_sdk\lib" --add-modules javafx.controls,javafx.web -cp App.jar com.jgslanka.ide.JGSWebIDE

exit