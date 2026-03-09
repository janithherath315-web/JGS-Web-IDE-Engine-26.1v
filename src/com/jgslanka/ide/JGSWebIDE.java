package com.jgslanka.ide;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.util.Properties;

public class JGSWebIDE extends Application {

    private final String APP_NAME_DEFAULT = "JGS Web IDE Engine 26.1v";
    private final String APP_DESC = "A Professional IDE Framework to run, preview, and build HTML, CSS, JavaScript Web Applications as Desktop Software.";
    private final String DESIGN_BY = "JGS Lanka Co. | H. M Janitha Athma Herath";
    private final String CONTACT = "+94702001859 (WhatsApp)";
    private final String WEBSITE_SEARCH = "Janith Graphic Studio";
    private final String UPDATE_URL = "https://jgslankasoftwaresolution.wuaze.com/";

    private TextArea consoleLog;
    private WebEngine webEngine;
    private Properties appSettings;
    private final File settingsFile = new File("Data/settings.properties");
    
    // UI Controls
    private TextField txtAppName, txtWidth, txtHeight, txtWindowTitle, txtAppUrl;
    private CheckBox chkResizable, chkFullscreen;
    private ComboBox<String> cmbTheme, cmbAppMode;
    // අලුතින් එකතු වූ UI Controls (Icon සහ Splash Screen සඳහා)
    private TextField txtIconPath, txtSplashPath, txtSplashWidth, txtSplashHeight;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        checkAndCreateFolders();
        loadSettings();
        initMainWindow(primaryStage);
    }

    private void checkAndCreateFolders() {
        new File("Data").mkdirs();
        new File("icon").mkdirs();
        new File("Output").mkdirs();
        File indexFile = new File("Data/index.html");
        if (!indexFile.exists()) {
            try (PrintWriter writer = new PrintWriter(indexFile)) {
                writer.println("<!DOCTYPE html><html><body style='background:#222; color:white; font-family:sans-serif;'><h1>JGS Web IDE Ready!</h1><p>Add your files to the Data folder or set a Live URL in Settings.</p></body></html>");
            } catch (Exception ignored) {}
        }
    }

private void loadSettings() {
        appSettings = new Properties();
        try {
            if (settingsFile.exists()) appSettings.load(new FileInputStream(settingsFile));
            else {
                appSettings.setProperty("appName", APP_NAME_DEFAULT);
                appSettings.setProperty("windowTitle", "JGS Desktop Software");
                appSettings.setProperty("width", "1280");
                appSettings.setProperty("height", "720");
                appSettings.setProperty("resizable", "true");
                appSettings.setProperty("fullscreen", "false");
                appSettings.setProperty("appMode", "Local Directory (Data Folder)");
                appSettings.setProperty("appUrl", "https://jgslankasoftwaresolution.wuaze.com/");
                appSettings.setProperty("iconPath", "");
                appSettings.setProperty("splashPath", "");
                appSettings.setProperty("splashWidth", "600");
                appSettings.setProperty("splashHeight", "400");
                appSettings.store(new FileOutputStream(settingsFile), "Settings");
            }
        } catch (Exception e) {}
    }

    private void saveSettings() {
        try {
            appSettings.setProperty("appName", txtAppName.getText());
            appSettings.setProperty("windowTitle", txtWindowTitle.getText());
            appSettings.setProperty("width", txtWidth.getText());
            appSettings.setProperty("height", txtHeight.getText());
            appSettings.setProperty("resizable", String.valueOf(chkResizable.isSelected()));
            appSettings.setProperty("fullscreen", String.valueOf(chkFullscreen.isSelected()));
            appSettings.setProperty("appMode", cmbAppMode.getValue());
            appSettings.setProperty("appUrl", txtAppUrl.getText());
            appSettings.setProperty("iconPath", txtIconPath.getText());
            appSettings.setProperty("splashPath", txtSplashPath.getText());
            appSettings.setProperty("splashWidth", txtSplashWidth.getText());
            appSettings.setProperty("splashHeight", txtSplashHeight.getText());
            appSettings.store(new FileOutputStream(settingsFile), "Settings");
            log("SUCCESS: Configuration Saved.");
        } catch (Exception e) { log("ERROR: Failed to save settings."); }
    }

    private void initMainWindow(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setTop(createToolbar());

        VBox leftPanel = new VBox(new Label(" Project Explorer"), createProjectTree());
        leftPanel.setPrefWidth(220);
        leftPanel.setStyle("-fx-background-color: #252526; -fx-text-fill: white; -fx-padding: 10px;");
        root.setLeft(leftPanel);

        TabPane workspace = new TabPane();
        Tab settingsTab = new Tab("Settings", createSettingsPanel());
        Tab buildTab = new Tab("Build Panel", createBuildPanel());
        settingsTab.setClosable(false); buildTab.setClosable(false);
        workspace.getTabs().addAll(settingsTab, buildTab);
        root.setCenter(workspace);

        consoleLog = new TextArea();
        consoleLog.setEditable(false);
        consoleLog.setPrefHeight(150);
        consoleLog.setStyle("-fx-control-inner-background: #1e1e1e; -fx-text-fill: #00ff00; -fx-font-family: 'Consolas';");
        log("JGS Web IDE Engine Started Successfully.");

        VBox bottomPanel = new VBox(new Label(" Developer Console & Logs"), consoleLog);
        bottomPanel.setStyle("-fx-background-color: #2d2d30; -fx-text-fill: white;");
        root.setBottom(bottomPanel);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(createPremiumDarkTheme());
        
        primaryStage.setTitle(APP_NAME_DEFAULT + " - Workspace");
        primaryStage.setScene(scene);
        File myIcon = new File("icon/logo.png");
if(myIcon.exists()) primaryStage.getIcons().add(new javafx.scene.image.Image(myIcon.toURI().toString()));
        primaryStage.show();
    }

    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #333337;");

        Button btnRun = new Button("▶ Run Preview");
        btnRun.setStyle("-fx-background-color: #007acc; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRun.setOnAction(e -> launchStandalonePreview());

        toolbar.getChildren().addAll(btnRun);
        return toolbar;
    }

    private void launchStandalonePreview() {
        log("Launching Standalone Preview Window...");
        Stage previewStage = new Stage();
        WebView webView = new WebView();
        
        String mode = appSettings.getProperty("appMode", "Local Directory (Data Folder)");
        if (mode.equals("Live Web URL")) {
            String url = appSettings.getProperty("appUrl", "https://");
            webView.getEngine().load(url);
            log("Previewing Live URL: " + url);
        } else {
            File indexFile = new File("Data/index.html");
            if (indexFile.exists()) {
                webView.getEngine().load(indexFile.toURI().toString());
            } else {
                webView.getEngine().loadContent("<h2>Error: index.html not found!</h2>");
            }
        }

        int w = Integer.parseInt(appSettings.getProperty("width", "1280"));
        int h = Integer.parseInt(appSettings.getProperty("height", "720"));
        Scene scene = new Scene(webView, w, h);

        previewStage.setTitle(appSettings.getProperty("windowTitle", "Preview Mode"));
        previewStage.setScene(scene);
        previewStage.show();
    }

    private TreeView<String> createProjectTree() {
        TreeItem<String> rootItem = new TreeItem<>("Data/");
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(new TreeItem<>("index.html"), new TreeItem<>("style.css"), new TreeItem<>("script.js"));
        TreeView<String> treeView = new TreeView<>(rootItem);
        treeView.setStyle("-fx-background-color: transparent;");
        return treeView;
    }

private ScrollPane createSettingsPanel() {
        GridPane grid = new GridPane();
        grid.setVgap(15); grid.setHgap(15);
        grid.setPadding(new Insets(30));
        grid.setStyle("-fx-background-color: #1e1e1e;");

        String inputStyle = "-fx-background-color: #3c3c3c; -fx-text-fill: white; -fx-border-color: #007acc; -fx-border-width: 0 0 2 0; -fx-padding: 8px;";
        String btnStyle = "-fx-background-color: #3e3e42; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 8px;";

        txtAppName = new TextField(appSettings.getProperty("appName")); txtAppName.setStyle(inputStyle);
        txtWindowTitle = new TextField(appSettings.getProperty("windowTitle")); txtWindowTitle.setStyle(inputStyle);
        txtWidth = new TextField(appSettings.getProperty("width")); txtWidth.setStyle(inputStyle);
        txtHeight = new TextField(appSettings.getProperty("height")); txtHeight.setStyle(inputStyle);
        
        cmbAppMode = new ComboBox<>();
        cmbAppMode.getItems().addAll("Local Directory (Data Folder)", "Live Web URL");
        cmbAppMode.setValue(appSettings.getProperty("appMode"));
        cmbAppMode.setStyle(inputStyle);
        
        txtAppUrl = new TextField(appSettings.getProperty("appUrl"));
        txtAppUrl.setStyle(inputStyle); txtAppUrl.setPrefWidth(300);
        txtAppUrl.setDisable(!cmbAppMode.getValue().equals("Live Web URL"));
        cmbAppMode.setOnAction(e -> txtAppUrl.setDisable(!cmbAppMode.getValue().equals("Live Web URL")));

        // අලුත්: App Icon Upload
        txtIconPath = new TextField(appSettings.getProperty("iconPath")); txtIconPath.setStyle(inputStyle);
        Button btnIcon = new Button("Browse .ico"); btnIcon.setStyle(btnStyle);
        btnIcon.setOnAction(e -> {
            javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
            fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Icon Files", "*.ico"));
            File f = fc.showOpenDialog(null);
            if(f != null) txtIconPath.setText(f.getAbsolutePath());
        });
        HBox iconBox = new HBox(10, txtIconPath, btnIcon);

        // අලුත්: Splash Screen Upload
        txtSplashPath = new TextField(appSettings.getProperty("splashPath")); txtSplashPath.setStyle(inputStyle);
        Button btnSplash = new Button("Browse Image"); btnSplash.setStyle(btnStyle);
        btnSplash.setOnAction(e -> {
            javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
            fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
            File f = fc.showOpenDialog(null);
            if(f != null) txtSplashPath.setText(f.getAbsolutePath());
        });
        HBox splashBox = new HBox(10, txtSplashPath, btnSplash);

        txtSplashWidth = new TextField(appSettings.getProperty("splashWidth")); txtSplashWidth.setStyle(inputStyle); txtSplashWidth.setPromptText("W (e.g. 600)");
        txtSplashHeight = new TextField(appSettings.getProperty("splashHeight")); txtSplashHeight.setStyle(inputStyle); txtSplashHeight.setPromptText("H (e.g. 400)");
        HBox splashSizeBox = new HBox(10, txtSplashWidth, txtSplashHeight);

        chkResizable = new CheckBox("Allow Window Resizing"); chkResizable.setSelected(Boolean.parseBoolean(appSettings.getProperty("resizable"))); chkResizable.setStyle("-fx-text-fill: white;");
        chkFullscreen = new CheckBox("Launch in Fullscreen"); chkFullscreen.setSelected(Boolean.parseBoolean(appSettings.getProperty("fullscreen"))); chkFullscreen.setStyle("-fx-text-fill: white;");

        Button btnSave = new Button("💾 Save Configurations");
        btnSave.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10px;");
        btnSave.setOnAction(e -> saveSettings());

        grid.addRow(0, createLabel("Software Name:"), txtAppName);
        grid.addRow(1, createLabel("Window Title:"), txtWindowTitle);
        grid.addRow(2, createLabel("App Build Source:"), cmbAppMode);
        grid.addRow(3, createLabel("Live Web URL:"), txtAppUrl);
        grid.addRow(4, createLabel("App Icon (.ico):"), iconBox);
        grid.addRow(5, createLabel("Splash Screen Image:"), splashBox);
        grid.addRow(6, createLabel("Splash Size (W x H):"), splashSizeBox);
        grid.addRow(7, createLabel("Window Width:"), txtWidth);
        grid.addRow(8, createLabel("Window Height:"), txtHeight);
        grid.addRow(9, chkResizable, chkFullscreen);
        grid.add(btnSave, 0, 10, 2, 1);

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setStyle("-fx-background-color: #1e1e1e; -fx-border-color: #1e1e1e;");
        scroll.setFitToWidth(true);
        return scroll;
    }

    private Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px; -fx-font-weight: bold;");
        return lbl;
    }

    private Label createStyledLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: #cccccc;");
        return lbl;
    }

    private VBox createBuildPanel() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2d2d30;");

        Label lblTitle = new Label("Build Windows Desktop Application");
        lblTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnBuild = new Button("⚙ BUILD SOFTWARE NOW");
        btnBuild.setStyle("-fx-font-size: 18px; -fx-background-color: #007acc; -fx-text-fill: white; -fx-padding: 15 40 15 40;");
        
        ProgressBar progress = new ProgressBar(0);
        progress.setPrefWidth(500);
        progress.setVisible(false);

        btnBuild.setOnAction(e -> executeBuildProcess(progress));
        layout.getChildren().addAll(lblTitle, btnBuild, progress);
        return layout;
    }

private void executeBuildProcess(ProgressBar progress) {
        progress.setVisible(true);
        log("--- Starting Target App Build Process ---");
        
        new Thread(() -> {
            try {
                Platform.runLater(() -> progress.setProgress(0.1));
                
                String rawName = appSettings.getProperty("appName", "JGS_App").replaceAll("[^a-zA-Z0-9_-]", "");
                final String appName = rawName.isEmpty() ? "JGS_App" : rawName;
                final String appMode = appSettings.getProperty("appMode", "Local Directory (Data Folder)");
                final String liveUrl = appSettings.getProperty("appUrl", "https://");
                
                File buildDir = new File("build_temp");
                if(!buildDir.exists()) buildDir.mkdir();
                
                log("[1/5] Preparing App Structure...");
                if (appMode.equals("Local Directory (Data Folder)")) {
                    new ProcessBuilder("cmd", "/c", "xcopy Data build_temp\\Data /E /I /Y").start().waitFor();
                }
                
                // Splash Screen පින්තූරය කොපි කිරීම
                String splashPath = appSettings.getProperty("splashPath", "");
                if (!splashPath.isEmpty() && new File(splashPath).exists()) {
                    new ProcessBuilder("cmd", "/c", "copy \"" + splashPath + "\" build_temp\\Data\\splash_img.png /Y").start().waitFor();
                }
                
                Platform.runLater(() -> progress.setProgress(0.3));
                log("[2/5] Generating Intelligent Web Wrapper...");
                
                File wrapperFile = new File("build_temp/WebWrapper.java");
                try (PrintWriter out = new PrintWriter(wrapperFile)) {
                    out.println("import javafx.application.Application; import javafx.scene.Scene; import javafx.scene.web.WebView; import javafx.scene.web.WebEngine; import javafx.stage.Stage; import java.io.File; import java.net.CookieManager; import java.net.CookieHandler;");
                    if (appMode.equals("Local Directory (Data Folder)")) {
                        out.println("import java.io.OutputStream; import java.net.InetSocketAddress; import java.nio.file.Files; import java.nio.file.Path; import java.nio.file.Paths; import com.sun.net.httpserver.HttpServer;");
                    }
                    out.println("public class WebWrapper extends Application {");
                    out.println("    public static void main(String[] args) { System.setProperty(\"sun.net.http.allowRestrictedHeaders\", \"true\"); launch(args); }");
                    out.println("    @Override public void start(Stage stage) {");
                    out.println("        CookieHandler.setDefault(new CookieManager()); WebView webView = new WebView(); WebEngine engine = webView.getEngine(); engine.setJavaScriptEnabled(true);");
                    out.println("        File dataDir = new File(System.getProperty(\"user.home\"), \".\" + \"" + appName + "\"); if(!dataDir.exists()) dataDir.mkdir(); engine.setUserDataDirectory(dataDir);");
                    
                    // Splash Screen Logic
                    out.println("        Stage splashStage = new Stage(javafx.stage.StageStyle.UNDECORATED);");
                    out.println("        File splashFile = new File(\"app/Data/splash_img.png\"); if(!splashFile.exists()) splashFile = new File(\"Data/splash_img.png\");");
                    out.println("        if(splashFile.exists()) {");
                    out.println("            javafx.scene.image.ImageView splashView = new javafx.scene.image.ImageView(new javafx.scene.image.Image(splashFile.toURI().toString()));");
                    out.println("            splashView.setFitWidth(" + appSettings.getProperty("splashWidth", "600") + "); splashView.setFitHeight(" + appSettings.getProperty("splashHeight", "400") + ");");
                    out.println("            splashStage.setScene(new Scene(new javafx.scene.layout.StackPane(splashView)));");
                    out.println("            splashStage.show();");
                    out.println("        }");
                    
                    if (appMode.equals("Live Web URL")) {
                        out.println("        engine.load(\"" + liveUrl + "\");");
                    } else {
                        out.println("        try { HttpServer server = HttpServer.create(new InetSocketAddress(0), 0); server.createContext(\"/\", exchange -> {");
                        out.println("            String uri = exchange.getRequestURI().getPath(); if (uri.equals(\"/\")) uri = \"/index.html\"; Path file = Paths.get(\"app/Data\", uri); if (!Files.exists(file)) file = Paths.get(\"Data\", uri);");
                        out.println("            if (Files.exists(file)) { String mime = \"text/plain\";");
                        out.println("                if(uri.endsWith(\".html\")) mime=\"text/html\"; else if(uri.endsWith(\".css\")) mime=\"text/css\"; else if(uri.endsWith(\".js\")) mime=\"application/javascript\";");
                        out.println("                else if(uri.endsWith(\".svg\")) mime=\"image/svg+xml\"; else if(uri.endsWith(\".woff\")) mime=\"font/woff\"; else if(uri.endsWith(\".woff2\")) mime=\"font/woff2\"; else if(uri.endsWith(\".ttf\")) mime=\"font/ttf\";");
                        out.println("                else if(uri.endsWith(\".png\")) mime=\"image/png\"; else if(uri.endsWith(\".jpg\")) mime=\"image/jpeg\";");
                        out.println("                exchange.getResponseHeaders().add(\"Content-Type\", mime); exchange.getResponseHeaders().add(\"Access-Control-Allow-Origin\", \"*\");");
                        out.println("                byte[] bytes = Files.readAllBytes(file); exchange.sendResponseHeaders(200, bytes.length); OutputStream os = exchange.getResponseBody(); os.write(bytes); os.close();");
                        out.println("            } else { exchange.sendResponseHeaders(404, -1); exchange.getResponseBody().close(); } }); server.start();");
                        out.println("            engine.load(\"http://localhost:\" + server.getAddress().getPort()); } catch (Exception ex) { ex.printStackTrace(); }");
                    }
                    
                    out.println("        engine.setOnAlert(e -> { javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION); alert.setHeaderText(null); alert.setContentText(e.getData()); alert.showAndWait(); });");
                    out.println("        Scene scene = new Scene(webView, " + appSettings.getProperty("width", "1280") + ", " + appSettings.getProperty("height", "720") + ");");
                    out.println("        stage.setTitle(\"" + appSettings.getProperty("windowTitle", appName) + "\"); stage.setScene(scene);");
                    
                    // [BUG FIX - 01] Background Process එක මරා දැමීම (System.exit)
                    out.println("        stage.setOnCloseRequest(e -> System.exit(0));");
                    out.println("        splashStage.setOnCloseRequest(e -> System.exit(0));");
                    
                    // Splash එක ඉවර වූ පසු Main Window එක පෙන්වීම
                    out.println("        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {");
                    out.println("            if (newState == javafx.concurrent.Worker.State.SUCCEEDED || newState == javafx.concurrent.Worker.State.FAILED) {");
                    out.println("                if(splashStage.isShowing()) splashStage.close();");
                    out.println("                if(!stage.isShowing()) stage.show();");
                    out.println("            }");
                    out.println("        });");
                    out.println("        if(!splashFile.exists()) stage.show();");
                    out.println("    }");
                    out.println("}");
                }
                
                Platform.runLater(() -> progress.setProgress(0.5));
                log("[3/5] Compiling Client Application...");
                
                ProcessBuilder compilePb = new ProcessBuilder(
                    "jdk\\bin\\javac", "-encoding", "UTF-8", "--module-path", "jfx_sdk\\lib", "--add-modules", "javafx.controls,javafx.web,jdk.httpserver", "build_temp\\WebWrapper.java"
                );
                compilePb.redirectErrorStream(true).start().waitFor();

                log("[4/5] Creating Application JAR...");
                ProcessBuilder jarPb = new ProcessBuilder(
                    "jdk\\bin\\jar", "cfe", "build_temp\\WebApp.jar", "WebWrapper", "-C", "build_temp", "WebWrapper.class"
                );
                jarPb.start().waitFor();

                Platform.runLater(() -> progress.setProgress(0.7));
                log("[5/5] Generating Windows Native .EXE...");
                log("PLEASE WAIT: Building Native Package. Check Terminal for details...");
                
                new ProcessBuilder("cmd", "/c", "rmdir /s /q Output\\" + appName).start().waitFor();
                
                String iconPath = appSettings.getProperty("iconPath", "");
                ProcessBuilder pb = new ProcessBuilder(
                    "jdk\\bin\\jpackage",
                    "--type", "app-image",
                    "--dest", "Output",
                    "--name", appName,
                    "--input", "build_temp",
                    "--main-jar", "WebApp.jar",
                    "--main-class", "WebWrapper",
                    "--module-path", "jfx_jmods", 
                    "--add-modules", "java.base,java.desktop,java.xml,jdk.jsobject,javafx.controls,javafx.web,javafx.media,javafx.swing,jdk.httpserver,jdk.crypto.ec,java.scripting,java.naming",
                    "--verbose"
                );
                
                // .EXE Icon එක Set කිරීම
                if (!iconPath.isEmpty() && new File(iconPath).exists()) {
                    pb.command().add("--icon");
                    pb.command().add(iconPath);
                    log("Attaching Custom App Icon: " + iconPath);
                }
                
                pb.redirectErrorStream(true);
                Process process = pb.start();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) { log("Compiler Log: " + line); }
                
                int exitCode = process.waitFor();
                
                Platform.runLater(() -> progress.setProgress(0.9));
                new ProcessBuilder("cmd", "/c", "rmdir /s /q build_temp").start().waitFor();
                
                Platform.runLater(() -> {
                    progress.setProgress(1.0);
                    if (exitCode == 0) {
                        log("[SUCCESS] App Generated Successfully!");
                        log("Go to Output -> " + appName + " -> Run " + appName + ".exe");
                    } else {
                        log("[ERROR] Build failed with exit code: " + exitCode);
                    }
                    progress.setVisible(false);
                });
                
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    log("[ERROR] Build Exception: " + ex.getMessage());
                    progress.setVisible(false);
                });
            }
        }).start();
    }

    private void log(String message) {
        if (consoleLog != null) {
            Platform.runLater(() -> {
                consoleLog.appendText(message + "\n");
                consoleLog.setScrollTop(Double.MAX_VALUE);
            });
        }
    }

   private String createPremiumDarkTheme() {
        return "data:text/css," + 
               ".root { -fx-background-color: #1e1e1e; }" +
               ".tab-pane .tab-header-area .tab-header-background { -fx-background-color: #252526; }" +
               ".tab { -fx-background-color: #2d2d30; -fx-text-base-color: #cccccc; -fx-padding: 5px 20px; }" +
               ".tab:selected { -fx-background-color: #1e1e1e; -fx-text-base-color: white; -fx-border-color: #007acc; -fx-border-width: 2 0 0 0; }" +
               ".button { -fx-background-color: #3e3e42; -fx-text-fill: white; -fx-cursor: hand; }" +
               ".button:hover { -fx-background-color: #505050; }" +
               ".text-field, .combo-box { -fx-background-color: #3e3e42; -fx-text-fill: white; }" +
               ".scroll-pane { -fx-background-color: #1e1e1e; -fx-background: #1e1e1e; }";
    }
}