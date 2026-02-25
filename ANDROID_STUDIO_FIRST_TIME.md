# Opening in Android Studio - First Time

## Step-by-Step Guide

### 1. Launch Android Studio

```bash
# If Android Studio is in your PATH:
studio.sh

# Or launch from Applications menu
```

### 2. Open Project

**Option A: From Welcome Screen**
- Click "Open"
- Navigate to: `/home/roncofaber/Software/nano-crucible-app`
- Click "OK"

**Option B: From Menu Bar**
- File → Open
- Navigate to: `/home/roncofaber/Software/nano-crucible-app`
- Click "OK"

### 3. Trust Project

Android Studio will ask: "Trust and Open Project?"
- Click **"Trust Project"**

### 4. Wait for Initial Setup

Android Studio will automatically:

1. **Detect Gradle Project** (5 seconds)
   ```
   ✓ Gradle wrapper detected
   ```

2. **Download Gradle** (30-60 seconds, first time only)
   ```
   ⏳ Downloading Gradle 8.2...
   ```

3. **Sync Gradle** (1-3 minutes)
   ```
   ⏳ Syncing Gradle...
   ⏳ Downloading dependencies...
   ```
   Progress shown in bottom status bar

4. **Index Files** (1-2 minutes)
   ```
   ⏳ Indexing...
   ```
   Progress shown in bottom right corner

5. **Run Lint** (30 seconds)
   ```
   ⏳ Running inspections...
   ```

**Total time: 3-7 minutes**

### 5. Verify Sync Success

Look for:
- ✅ No errors in "Build" tab (bottom)
- ✅ Green circle next to module name in Project view
- ✅ "Gradle sync finished" message
- ✅ No red underlines in build.gradle.kts

### 6. Ignore Pre-Sync Warnings

**Common messages you can ignore:**
- ❌ "The file name must end with .xml or .png" 
  → Disappears after sync
- ❌ "Cannot resolve symbol"
  → Disappears after indexing
- ❌ Red squiggles in code
  → Disappears after sync completes

### 7. Configure Run Configuration

After sync:
1. Top toolbar should show: "app" ▼
2. Next to it: Select your device/emulator
3. Green Run button (▶) should be enabled

### 8. First Build

Click the green Run button (▶) or press **Shift+F10**

First build takes 2-4 minutes:
```
⏳ Building APK...
⏳ Compiling Kotlin...
⏳ Processing resources...
✓ BUILD SUCCESSFUL in 3m 12s
```

### 9. App Launches

- On physical device: App installs and opens automatically
- On emulator: Emulator starts (if not running), app installs and opens

### 10. First Time App Setup

When app opens:
1. Tap Settings (⚙️ gear icon)
2. Enter your Crucible API key
3. Tap "Save API Key"
4. Go back and start scanning!

## Visual Timeline

```
Open Project
    ↓ (5 sec)
Trust Project Dialog
    ↓ (click Trust)
Gradle Detection
    ↓ (30 sec - first time only)
Download Gradle
    ↓ (1-3 min)
Sync Dependencies
    ↓ (1-2 min)
Index Files
    ↓ (30 sec)
Lint Checks
    ↓
✓ READY TO BUILD!
    ↓
Click Run (▶)
    ↓ (2-4 min first build)
App Launches!
```

## Project Structure View

After sync, you should see:
```
nano-crucible-app
├── app
│   ├── manifests
│   │   └── AndroidManifest.xml
│   ├── java
│   │   └── gov.lbl.crucible.scanner
│   │       ├── MainActivity.kt
│   │       ├── data
│   │       └── ui
│   └── res
│       ├── mipmap-*
│       ├── values
│       └── xml
└── Gradle Scripts
    ├── build.gradle.kts (Project)
    ├── build.gradle.kts (Module: app)
    └── settings.gradle.kts
```

## Troubleshooting First Open

### "SDK not found"
**Before building:**
1. File → Project Structure (Ctrl+Alt+Shift+S)
2. SDK Location tab
3. Android SDK location: Set to your SDK path
   (Usually: `/home/yourusername/Android/Sdk`)
4. Click "Apply" → "OK"

### "Gradle sync failed"
**Check:**
- Internet connection active
- Firewall not blocking Gradle downloads
- Disk space available (need ~500MB)

**Fix:**
- File → Invalidate Caches / Restart
- Try sync again

### "Build variant not found"
**Fix:**
- Build → Select Build Variant
- Ensure "debug" is selected

### Stuck on "Indexing"
**Normal:** Can take up to 5 minutes on first open
**If stuck >10 minutes:**
- File → Invalidate Caches / Restart

## What Android Studio Downloads

On first sync:
- Gradle 8.2 (~100MB)
- Kotlin compiler (~50MB)
- Android build tools (~200MB)
- Compose libraries (~100MB)
- Other dependencies (~50MB)

**Total: ~500MB**

## After First Successful Build

Subsequent builds are much faster:
- Clean builds: 30-60 seconds
- Incremental builds: 5-15 seconds
- Hot reload (Compose): <1 second

## Success Indicators

You know it's working when:
- ✅ No errors in Build output
- ✅ Green "Build successful" message
- ✅ App appears on your device
- ✅ Can interact with app UI

## Next Steps After First Launch

See: `BUILD_CHECKLIST.md` for testing steps

---

**Note:** The error "The file name must end with .xml or .png" is a PRE-SYNC warning that disappears after Gradle sync completes. Just wait for the sync to finish!
