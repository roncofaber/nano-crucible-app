# Build Checklist - Crucible Scanner

## ✅ Pre-Build Status

### Project Setup
- ✅ Android project structure created
- ✅ Gradle configuration files in place
- ✅ Dependencies configured (Compose, Retrofit, CameraX, ML Kit, etc.)
- ✅ AndroidManifest.xml with permissions

### Source Code
- ✅ 18 Kotlin source files created
- ✅ MVVM architecture implemented
- ✅ API client with Crucible integration
- ✅ QR scanner with CameraX + ML Kit
- ✅ Material 3 UI screens (Home, Scanner, Detail, Settings)
- ✅ Navigation graph
- ✅ ViewModel with state management
- ✅ Repository pattern for data access

### Resources
- ✅ Launcher icons (all densities: mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- ✅ Adaptive icons configured
- ✅ String resources
- ✅ Color scheme
- ✅ Themes configured

### Documentation
- ✅ README.md
- ✅ SETUP_GUIDE.md
- ✅ PROJECT_SUMMARY.md
- ✅ APP_FLOW.md
- ✅ BUILD_CHECKLIST.md (this file)

## 🚀 Ready to Build!

### Next Steps

#### 1. Open in Android Studio

```bash
# Method 1: From Android Studio
File → Open → Navigate to:
/home/roncofaber/Software/nano-crucible-app

# Method 2: From command line (if studio.sh is in PATH)
studio.sh /home/roncofaber/Software/nano-crucible-app
```

#### 2. First Sync
- Android Studio will detect the project
- Click **"Sync Now"** when prompted
- Wait for Gradle to download dependencies (~2-5 minutes)

#### 3. Connect Device or Start Emulator

**Physical Device:**
```
1. Enable Developer Options (Settings → About → tap Build 7x)
2. Enable USB Debugging (Settings → Developer Options)
3. Connect via USB
4. Accept debugging prompt
```

**Emulator:**
```
Tools → Device Manager → Create Device
Recommended: Pixel 5 with API 34 (Android 14)
```

#### 4. Build & Run

```
1. Select device from dropdown (top toolbar)
2. Click green Run button (▶) or Shift+F10
3. Wait for build (~1-3 minutes first time)
4. App launches automatically
```

#### 5. First Launch Setup

```
1. Tap Settings (⚙️ icon)
2. Enter Crucible API key
   Get from: https://crucible.lbl.gov/api/v1/user_apikey
3. Tap Save
4. Return to Home
```

#### 6. Test the App

**Test QR Scanning:**
- [ ] Tap "Scan QR Code"
- [ ] Grant camera permission
- [ ] Scan a QR code with a Crucible UUID
- [ ] Verify data loads and displays correctly

**Test Manual Entry:**
- [ ] Enter a known UUID in text field
- [ ] Tap "Look Up"
- [ ] Verify data loads correctly

**Test Sample Display:**
- [ ] Check sample name appears
- [ ] Check sample type/project shown
- [ ] Check associated datasets listed
- [ ] Check parent samples listed (if any)

**Test Dataset Display:**
- [ ] Check dataset name appears
- [ ] Check measurement type shown
- [ ] Check thumbnails load (if available)
- [ ] Check scientific metadata displays
- [ ] Check associated samples listed

## 📦 Building APK for Distribution

### Debug APK (for testing)

```bash
cd /home/roncofaber/Software/nano-crucible-app
./gradlew assembleDebug
```

Output location:
```
app/build/outputs/apk/debug/app-debug.apk
```

### Installing APK Manually

**Transfer to device:**
```bash
# Via USB
adb install app/build/outputs/apk/debug/app-debug.apk

# Or transfer file and install from device
```

**On device:**
```
1. Settings → Security → Enable "Unknown Sources"
2. Open APK file in file manager
3. Tap Install
```

## 🎨 Icon Preview

Your Crucible icon has been integrated! It will appear as:
- App launcher icon on home screen
- In app drawer
- In recent apps
- As adaptive icon on Android 8.0+

The icon shows:
- A crucible (container) in dark blue/teal
- Orange liquid/data inside
- Tongs tool
- Binary digits (0s and 1s) floating above
- Light blue-gray background

Perfect representation of the Crucible data system!

## 🔍 Verification Checklist

Before distributing to users, verify:

### Functionality
- [ ] QR scanner opens camera
- [ ] QR codes are detected and scanned
- [ ] Manual UUID entry works
- [ ] Sample data displays correctly
- [ ] Dataset data displays correctly
- [ ] Thumbnails load properly
- [ ] Scientific metadata formats correctly
- [ ] Navigation works (back buttons, etc.)
- [ ] Settings save API key
- [ ] API key persists after app restart

### Performance
- [ ] App starts in < 3 seconds
- [ ] QR detection is responsive
- [ ] API calls complete reasonably fast
- [ ] No crashes or ANRs (App Not Responding)
- [ ] Smooth scrolling in detail view

### UI/UX
- [ ] All text is readable
- [ ] Icons display correctly
- [ ] Colors are consistent
- [ ] No UI elements overlap
- [ ] Buttons are easy to tap
- [ ] Loading states display correctly
- [ ] Error messages are helpful

### Edge Cases
- [ ] Handles invalid UUID gracefully
- [ ] Handles network errors
- [ ] Handles missing API key
- [ ] Handles resources with no metadata
- [ ] Handles resources with no thumbnails
- [ ] Handles empty datasets/samples lists

## 📊 File Statistics

```
Total Kotlin files: 18
Total resource files: 25+
Total launcher icons: 15 (5 densities × 3 types)
Lines of code: ~2000+
Dependencies: 20+
```

## 🐛 Common Issues & Solutions

### "SDK not found"
**Fix:** File → Project Structure → SDK Location → Set Android SDK path

### "Plugin with id 'com.android.application' not found"
**Fix:** File → Invalidate Caches / Restart → Invalidate and Restart

### "Could not resolve dependencies"
**Fix:** Check internet connection, sync again

### Icons not showing
**Fix:** Build → Clean Project → Rebuild Project

### Camera permission denied
**Fix:** Settings → Apps → Crucible Scanner → Permissions → Enable Camera

## 📞 Support

If you encounter issues:
1. Check SETUP_GUIDE.md for detailed instructions
2. Check Android Studio's Logcat for error messages
3. Verify API key is correct
4. Email: roncoroni@lbl.gov

## 🎉 Congratulations!

Your Crucible Scanner app is ready to build and deploy!

Key achievements:
✅ Modern Android app with Jetpack Compose
✅ QR code scanning functionality
✅ Beautiful Material Design 3 UI
✅ Full Crucible API integration
✅ Secure credential storage
✅ Professional launcher icons
✅ Comprehensive documentation

Ready to scan some samples! 📱🔬

---

**Built with**: Kotlin, Jetpack Compose, CameraX, ML Kit, Retrofit, Material 3
**Last Updated**: 2026-02-24
