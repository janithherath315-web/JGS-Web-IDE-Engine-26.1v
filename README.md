# JGS Web IDE Engine 26.1v — Professional Documentation

---

## 🌎 Overview / දළ විශ්ලේෂණය

### [English]
**JGS Web IDE Engine 26.1v** is a high-performance Integrated Development Environment (IDE) designed to transform modern web applications (HTML5, CSS3, JavaScript) into lightweight, standalone native Windows Desktop applications (`.exe`). Powered by the **Neutralino.js** engine, it bypasses the heavy overhead of traditional frameworks like Electron, offering a lightning-fast experience with a minimal footprint.

### [සිංහල]
**JGS Web IDE Engine 26.1v** යනු නවීන වෙබ් තාක්ෂණයන් (HTML5, CSS3, JavaScript) භාවිතයෙන් නිපදවන මෘදුකාංග, ඉතා සැහැල්ලු සහ ස්වාධීන වින්ඩෝස් පද්ධතිවල ධාවනය වන මෘදුකාංග (`.exe`) බවට පත් කරන උසස් මට්ටමේ IDE එකකි. **Neutralino.js** තාක්ෂණයෙන් ක්‍රියාත්මක වන නිසා, අනවශ්‍ය බරකින් තොරව ඉතා වේගවත් සහ වෘත්තීය මට්ටමේ ප්‍රතිඵලයක් මෙයින් ලබාගත හැකිය.

---

## 🚀 Key Features / ප්‍රධාන විශේෂාංග

- **Native Neutralino Engine:** 100% Chromium-compatible rendering for complex web apps (3D, Canvas, LocalStorage).
- **90% Size Optimization:** Custom Slim JRE (50MB) reduces the IDE's total footprint by over 550MB compared to standard JDK.
- **Persistent Data:** Static port mapping ensures LocalStorage, Sessions, and Cookies survive application restarts.
- **Professional Branding:** Built-in settings for App Versioning, Company Name, Developer ID, and Custom Copyright.
- **Instant Preview:** Launches a native debug window (Neutralino Run) for real-time edge-case testing.
- **Zero Runtime Requirement:** Built applications run on any Windows PC without requiring Java or Node.js to be installed.
- **Zero Runtime Requirement:** Built applications run on any Windows PC without requiring Java or Node.js to be installed.

---

- **Native Neutralino Engine:** Chromium මත පදනම් වූ නිසා 3D, Canvas වැනි ඕනෑම දියුණු වෙබ් තාක්ෂණයක් සමඟ 100% ගැළපේ.
- **90% Size Optimization:** 630MB JDK එක වෙනුවට 50MB Slim JRE එකක් භාවිතා කර project එකේ විශාලත්වය 90% කින් අඩු කර ඇත.
- **Persistent Data:** සෑම ඇප් එකකටම ස්ථිර Port එකක් ලබා දෙන නිසා දත්ත මැකී නොයයි (Login, Sessions, LocalStorage සුරක්ෂිතයි).
- **Professional Branding:** ඇප් එකේ නම, Version එක, සමාගම සහ Copyright විස්තර ඔබේ කැමැත්ත පරිදි වෙනස් කළ හැකිය.
- **Instant Preview:** "Run Preview" මගින් සැබෑ ඇන්ජිම (Native Engine) තුළම ඔබේ නිර්මාණය පරීක්ෂා කළ හැකිය.

---

## 🛠 User Guide / පරිශීලක අත්පොත

# Step 1
Download IDE 90MB : https://sinhaai.wuaze.com/app

### 1. Project Organization (ව්‍යාපෘති කළමනාකරණය)
- Place all your HTML, CSS, JS, and image files inside the `Data/` folder.
- Your main entry point must be named `index.html`.
- ඔබේ සියලුම වෙබ් ෆයිල් `Data/` ෆෝල්ඩරය තුළ තබන්න. ප්‍රධාන පිටුව `index.html` ලෙස නම් කළ යුතුය.

### 2. Configuration (සැකසුම්)
- Use the **Settings** tab to define:
    - **Window Size:** Default width/height.
    - **Metadata:** Version, Company, and Developer info.
- **Settings** Tab එක හරහා ඇප් එකේ නම, Version එක, icon එක සහ window එකේ ප්‍රමාණය සකසා ගන්න.

#### 🎨 App Branding (අයිකන් සහ නාමය)
- **App Name:** Your software name.
- **Window Title:** The title shown in the top bar.
- **App Icon (Important):** 
  - For the **Window Icon**, provide a `.png` file.
  - For the **EXE Explorer Icon (Professional look)**, provide a `.ico` file.
  - **Window Icon:** සඳහා `.png` ගොනුවක් ලබා දෙන්න.
  - **EXE Explorer Icon:** (පරිගණකයේ ෆයිල් එකක් ලෙස පෙන්වන අයිකනය) සඳහා `.ico` ගොනුවක් ලබා දෙන්න.🎯

### 3. Running & Testing (ධාවනය සහ පරීක්ෂා කිරීම)
- Click **"▶ Run Preview"** in the toolbar to see exactly how your app looks in native mode.
- **"Run Preview"** බටන් එක එබූ විට එය වෙනම Native Window එකකින් ඔබේ නිර්මාණය පෙන්වනු ඇත.

### 4. Advanced Support (වැඩිදුර සහාය)

#### SQLite Database
- The IDE now bundles the **Neutralino SQLite Extension**. 
- To use it, use `Neutralino.extensions.dispatch('js.neutralino.sqlite', { action: 'open', database: 'my.db' })`.
- SQLite සහාය දැන් ඇතුළත් කර ඇත. `js.neutralino.sqlite` extension එක හරහා දත්ත ගබඩා හැසිරවිය හැකිය.

#### React / Vue / Angular
- **Compatibility:** Use static builds (e.g., `npm run build`).
- **Important (React):** Use **`HashRouter`** instead of `BrowserRouter` to ensure navigation works correctly in a file-based native environment.
- React භාවිතා කරන්නේ නම්, `BrowserRouter` වෙනුවට **`HashRouter`** භාවිතා කිරීමට වග බලා ගන්න. එවිට පිටු මාරු කිරීමේදී ගැටලු ඇති නොවේ.

### 5. Final Build (අවසන් මෘදුකාංගය සෑදීම)
- Go to the **Build Panel** and click **"CONFIGURE & BUILD NATIVE APP"**.
- The IDE will scaffold a project and create a standalone bundle in the `Output/` directory.
- **Build Panel** එකට ගොස් **"CONFIGURE & BUILD NATIVE APP"** බටන් එක ඔබන්න. ඉන්පසු `Output/` ෆෝල්ඩරය තුළ ඔබේ සැබෑ මෘදුකාංගය සැකසෙනු ඇත.

---

## 💻 System Specifications / තාක්ෂණික පිරිවිතර

| Component | Specification |
| :--- | :--- |
| **Backend Engine** | Neutralino.js (Native binary) |
| **Frontend Renderer** | Windows WebView2 (Chromium-based) |
| **Runtime Environment** | Optimized Slim JRE 17 (JavaFX 17) |
| **Metadata Support** | Full Versioning & Company Branding |
| **Auto Cleanup** | Temp file removal on exit |

---

## 📞 Support & Credits / සහාය සහ ස්තුතිය

**Created & Designed By:**
**JGS Lanka Co. | H. M Janitha Athma Herath**

- **WhatsApp/Contact:** +94702001859
- **Website:** Janith Graphic Studio
- **Update Portal:** [JGS Software Solution](https://jgslankasoftwaresolution.wuaze.com/)

---
*(C) 2026 JGS Lanka Co. All Rights Reserved.*
