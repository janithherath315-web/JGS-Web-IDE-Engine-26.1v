JGS Web IDE Engine 26.1v
A Professional Desktop Software Development Tool
Designed & Developed by JGS Lanka Co. | H. M Janitha Athma Herath

📌 1. Introduction
JGS Web IDE Engine is a professional framework designed to run, preview, and build modern Web Applications (HTML, CSS, JavaScript) as native Windows Desktop Software (.EXE). It features a built-in embedded browser, live preview, developer console, and a native .EXE packaging engine.

⚙️ 2. System Requirements
To run the IDE Engine efficiently, the host machine must meet the following requirements:

Operating System: Windows 10 or Windows 11 (64-bit)

Java Environment: JDK 17 or higher (Essential for the jpackage build tool).

RAM: 4GB Minimum (8GB Recommended for large builds).

Storage: 500MB Free Space.

📂 3. Folder Structure
Ensure your project directory looks like this before launching:

Plaintext
JGS_Web_IDE/
│
├── JGS_Launcher.bat        <-- Double click this to Auto-Check & Run
├── src/                    <-- Contains the Java Source Code and file
├── lib/                    <-- Contains JavaFX SDK .jar files
├── Data/                   <-- YOUR WEB APP GOES HERE
│   ├── index.html
│   ├── style.css
│   └── script.js
├── icon/
│   ├── app.icon            <-- Custom Application Icon
│   └── splash.png          <-- Splash Screen Image (800x450px)
└── Output/                 <-- Generated .EXE files will appear here
🚀 4. How to Use the IDE
Launch the Software: Double-click JGS_Launcher.bat. The engine will automatically verify system requirements, repair missing directories, and launch the application.

Add Your Code: Navigate to the Data/ folder. Write or paste your HTML, CSS, and JS files here. index.html is the mandatory entry point.

Live Preview: Inside the IDE, open the Live Preview tab and click ▶ Run Preview to see your changes in real-time.

Customize Settings: Go to the Settings tab. Here you can change:

Application Name

Window Dimensions (Width/Height)

Theme (Premium Dark / Light)

Fullscreen & Resize permissions

🛠️ 5. Building the .EXE Application
Once your web app is completely tested and ready to be converted into Desktop Software:

Ensure you have added your custom app.icon and splash.png to the icon/ folder.

Navigate to the Build Panel tab inside the IDE.

Click the ⚙ BUILD .EXE NOW button.

The system will invoke native compilers, embed the Java Runtime, attach your icons, and generate a standalone Windows executable.

Wait for the Success notification. Your final software will be located in the Output/ folder.

📞 6. Support & Contact
Company: JGS Lanka Co.

Website: Janith Graphic Studio (Search on Google)

Bug Reports (WhatsApp): +94702001859

Updates: Click the Check Update button in the IDE's About Section.