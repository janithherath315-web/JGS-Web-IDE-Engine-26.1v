# JGS Web IDE Engine 26.1v

### A Professional Desktop Software Development Tool

**Designed & Developed by JGS Lanka Co.**
**Author:** H. M Janitha Athma Herath

---

# 📌 Introduction

**JGS Web IDE Engine** is a powerful development framework that allows developers to run, preview, and convert modern **Web Applications (HTML, CSS, JavaScript)** into **native Windows Desktop Applications (.EXE)**.

The system includes:

* Embedded Browser Engine
* Live Web Preview
* Developer Console
* Native Windows `.EXE` Packaging System

This tool is designed for developers who want to convert web-based software into desktop applications easily.

---

# ⚙️ System Requirements

To run **JGS Web IDE Engine**, your computer must meet the following requirements.

| Requirement      | Details                          |
| ---------------- | -------------------------------- |
| Operating System | Windows 10 / Windows 11 (64-bit) |
| Java Environment | JDK 17 or Higher                 |
| RAM              | 4GB Minimum (8GB Recommended)    |
| Storage          | Minimum 500MB Free Space         |

⚠ **Note:** JDK 17 or higher is required for the `jpackage` build tool.

---
# Step 1
Download IDE 90MB : https://sinhaai.wuaze.com/app
# Step 2
Download Java SDK 


# 📂 Project Folder Structure

Before running the IDE, ensure your project directory structure looks like this:

```
JGS_Web_IDE/
│
├── JGS_Launcher.bat        <-- Double click this to Auto-Check & Run
├── src/                    <-- Java Source Code
├── lib/                    <-- JavaFX SDK .jar files
├── Data/                   <-- YOUR WEB APP GOES HERE
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── icon/
│   ├── app.icon            <-- Application Icon
│   └── splash.png          <-- Splash Screen (800x450px)
│
└── Output/                 <-- Generated .EXE files appear here
```

---

# 🚀 How to Use the IDE

### 1️⃣ Launch the IDE

Double-click:

```
JGS_Launcher.bat
```

The engine will automatically:

* Check system requirements
* Create missing folders
* Start the IDE

---

### 2️⃣ Add Your Web Application

Navigate to the **Data/** folder and place your files.

Example:

```
index.html
style.css
script.js
```

⚠ **Important:**
`index.html` must exist because it is the main entry point.

---

### 3️⃣ Live Preview

Inside the IDE:

1. Open **Live Preview Tab**
2. Click **▶ Run Preview**

You will see your web application running instantly inside the IDE.

---

# 🎨 Customize Application Settings

Inside the **Settings Panel** you can configure:

* Application Name
* Window Width & Height
* Theme (Premium Dark / Light)
* Fullscreen Mode
* Window Resize Permissions

---

# 🛠️ Building the Windows .EXE

Once your project is ready:

1️⃣ Add your files to the **icon/** folder:

```
app.icon
splash.png
```

2️⃣ Open **Build Panel**

3️⃣ Click:

```
⚙ BUILD .EXE NOW
```

The system will automatically:

* Compile Java runtime
* Package your web application
* Attach icon & splash screen
* Generate a standalone `.EXE` application

⏳ Wait for the **Build Success Notification**

Your software will appear in:

```
Output/
```

---

# 📦 Output

The final executable file will be generated in:

```
Output/
```

You can distribute this `.EXE` like any normal Windows software.

No additional installation required.

---

# 📞 Support & Contact

**Company:** JGS Lanka Co.

**Developer:** H. M Janitha Athma Herath

**Website:**
Search **Janith Graphic Studio** on Google

**Bug Reports (WhatsApp):**
+94 70 200 1859

---

# 🔄 Updates

To check for new versions:

Open the **About Section** in the IDE and click:

```
Check Update
```

---

# ⭐ Project Status

✔ Active Development
✔ Stable Version 26.1v
✔ Desktop Packaging Engine Included

---

# 📜 License

© 2026 JGS Lanka Co.
All Rights Reserved.
