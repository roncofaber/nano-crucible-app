# Setup Guide for Crucible Scanner Android App

This guide will help you set up and build the Crucible Scanner Android app.

## Prerequisites

1. **Install Android Studio**
   - Download from: https://developer.android.com/studio
   - Version: Hedgehog (2023.1.1) or later

2. **Install JDK 17**
   - Can be installed via Android Studio's SDK Manager
   - Or download from: https://adoptium.net/

3. **Get a Crucible API Key**
   - Visit: https://crucible.lbl.gov/api/v1/user_apikey
   - Sign in with your ORCID credentials
   - Copy your API key for later use

## Initial Setup

### Step 1: Open Project in Android Studio

1. Launch Android Studio
2. Click "Open" or File → Open
3. Navigate to `/home/roncofaber/Software/nano-crucible-app`
4. Click "OK"

### Step 2: Sync Gradle

1. Android Studio will automatically detect the Gradle project
2. Click "Sync Now" when prompted
3. Wait for Gradle sync to complete (may take a few minutes on first run)
4. Android Studio will download all required dependencies

### Step 3: Add Launcher Icons (Optional but Recommended)

The app currently uses placeholder launcher icons. To add proper icons:

**Option A: Generate Online**
1. Visit: https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html
2. Upload an image or design an icon
3. Download the generated icon pack
4. Extract and copy all `mipmap-*` folders to `app/src/main/res/`

**Option B: Use Default**
- The app will work with the placeholder icons, but they won't look professional

### Step 4: Configure Android Device/Emulator

**For Physical Device:**
1. Enable Developer Options on your Android device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging
3. Connect device via USB
4. Accept USB debugging prompt on device

**For Emulator:**
1. Tools → Device Manager
2. Create a new device (recommended: Pixel 5, API 34)
3. Wait for emulator to start

### Step 5: Build and Run

1. Select your device/emulator from the device dropdown (top toolbar)
2. Click the green "Run" button (▶) or press Shift+F10
3. Wait for build to complete
4. App will launch automatically on your device

## Post-Installation Setup

### First Launch Configuration

1. **Open the app** on your device
2. **Tap Settings** (gear icon in top-right corner)
3. **Enter your Crucible API key**
4. **Tap "Save API Key"**

The API key will be securely stored on your device.

### Test the App

**Option 1: Test with Manual UUID Entry**
1. Return to home screen
2. Enter a known UUID in the text field
3. Tap "Look Up"
4. Verify that data loads correctly

**Option 2: Test QR Scanning**
1. Tap "Scan QR Code"
2. Grant camera permission when prompted
3. Point camera at a QR code containing a Crucible UUID
4. Verify that data loads correctly

## Building APK for Distribution

### Debug APK (for testing)

```bash
# From terminal in project directory
./gradlew assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK (for production)

1. **Generate Signing Key** (first time only):
   ```bash
   keytool -genkey -v -keystore crucible-release-key.jks \
     -keyalg RSA -keysize 2048 -validity 10000 -alias crucible-key
   ```

2. **Configure Signing** in `app/build.gradle.kts`:
   ```kotlin
   android {
       signingConfigs {
           create("release") {
               storeFile = file("../crucible-release-key.jks")
               storePassword = "your-keystore-password"
               keyAlias = "crucible-key"
               keyPassword = "your-key-password"
           }
       }
       buildTypes {
           release {
               signingConfig = signingConfigs.getByName("release")
               // ... rest of config
           }
       }
   }
   ```

3. **Build Release APK**:
   ```bash
   ./gradlew assembleRelease
   ```

Output: `app/build/outputs/apk/release/app-release.apk`

## Troubleshooting

### Gradle Sync Fails

**Problem**: "Could not resolve all files for configuration"

**Solution**:
1. Check internet connection
2. File → Invalidate Caches / Restart
3. Sync again

### Build Fails with "SDK not found"

**Problem**: Android SDK not configured

**Solution**:
1. File → Project Structure
2. SDK Location tab
3. Set Android SDK location (usually `~/Android/Sdk`)
4. Click OK

### Camera Not Working in Emulator

**Problem**: Emulator doesn't have camera access

**Solution**:
- Use a physical device for QR scanning features
- Or configure emulator with camera:
  1. AVD Manager → Edit device
  2. Camera: Set to "Webcam" or "VirtualScene"

### API Calls Failing

**Symptoms**: "Error loading data" or network errors

**Check**:
1. ✅ Internet connection active
2. ✅ API key is correct
3. ✅ UUID is valid
4. ✅ Crucible API is accessible: https://crucible.lbl.gov/api/v1/

### App Won't Install on Device

**Problem**: "App not installed" error

**Solution**:
1. Uninstall any existing version
2. Enable "Install from Unknown Sources"
3. Check device has sufficient storage

## Development Tips

### Enable Logging

In `ApiClient.kt`, the logging interceptor is already configured to show API requests/responses in Logcat.

View logs: View → Tool Windows → Logcat

### Hot Reload

Compose supports hot reload:
- Make UI changes
- Android Studio will automatically update without full rebuild

### Code Structure

```
├── data/           # Data layer (API, models, repository)
├── ui/             # UI layer (screens, components)
│   ├── detail/    # Resource detail screen
│   ├── home/      # Home screen
│   ├── scanner/   # QR scanner
│   ├── settings/  # Settings screen
│   └── viewmodel/ # ViewModels
└── MainActivity.kt
```

## Next Steps

1. ✅ Build and run the app
2. ✅ Test scanning functionality
3. 🎨 Customize app icons
4. 📱 Deploy to test users
5. 🚀 Publish to Google Play Store (optional)

## Support

For issues:
- Check `README.md` for general documentation
- Email: roncoroni@lbl.gov

---

Happy scanning! 📱🔬
