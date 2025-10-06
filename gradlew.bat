@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Check for running Android emulators and set up automatic deployment
call :checkAndroidEmulator

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar


@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

@rem If assembleDebug was successful and we have an emulator, install and run the app
if "%ERRORLEVEL%"=="0" (
    if "%1"=="assembleDebug" (
        call :installAndRunApp
    )
)

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%GRADLE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

:checkAndroidEmulator
@rem Check for Android SDK and running emulators
if exist "D:\Android\Sdk\platform-tools\adb.exe" (
    echo.
    echo Checking Android emulators...
    for /f "tokens=1" %%i in ('D:\Android\Sdk\platform-tools\adb.exe devices ^| findstr "device"') do (
        if not "%%i"=="List" (
            echo Found running emulator: %%i
            set ANDROID_EMULATOR=%%i
            goto :emulatorFound
        )
    )
    echo No Android emulators found running.
    echo Please start an Android emulator before building the app.
    goto :end
) else (
    echo Android SDK not found at D:\Android\Sdk\
    echo Please check your Android SDK installation.
    goto :end
)

:emulatorFound
echo Will deploy to emulator: %ANDROID_EMULATOR%
goto :eof

:installAndRunApp
@rem Install and run the app on the detected emulator
if defined ANDROID_EMULATOR (
    echo.
    echo Installing app on emulator: %ANDROID_EMULATOR%
    if exist "%APP_HOME%\app\build\outputs\apk\debug\app-debug.apk" (
        D:\Android\Sdk\platform-tools\adb.exe -s %ANDROID_EMULATOR% install -r "%APP_HOME%\app\build\outputs\apk\debug\app-debug.apk"
        if "%ERRORLEVEL%"=="0" (
            echo App installed successfully!
            echo Starting SmartCitizenClub app...
            D:\Android\Sdk\platform-tools\adb.exe -s %ANDROID_EMULATOR% shell am start -n com.example.smartcitizenclub/.MainActivity
            echo App launched on emulator: %ANDROID_EMULATOR%
        ) else (
            echo Failed to install app on emulator.
        )
    ) else (
        echo APK file not found. Build may have failed.
    )
) else (
    echo No emulator detected. Skipping app installation.
)
goto :eof
