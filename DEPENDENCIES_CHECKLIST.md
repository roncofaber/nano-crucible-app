# Dependency Checklist - Android App

## ✅ What You Actually Need

To build and run the Android app, you need:

### 1. Android Studio
- **Version:** Hedgehog (2023.1.1) or later
- **Download:** https://developer.android.com/studio
- **Includes:** Android SDK, Gradle, Android Emulator

### 2. JDK 17
- **Usually included** with Android Studio
- Or download from: https://adoptium.net/

### 3. Internet Connection
- **First build only:** To download ~800MB of Android dependencies
- After that: Only needed for API calls to Crucible

### 4. Android Device or Emulator
- **Physical device:** Android 8.0+ (API 26+)
- **Or emulator:** Can create one in Android Studio

## ✅ Dependency Verification

### Check Android Studio Installation

```bash
# Check if Android Studio is installed
which studio.sh
# Or look in: /usr/local/android-studio/ or ~/android-studio/

# Check Android SDK
ls ~/Android/Sdk/
# Should see: platforms, platform-tools, build-tools, etc.
```

### Check JDK

```bash
# Check Java version
java -version
# Should show: openjdk 17.x or similar

# Android Studio's bundled JDK
ls /opt/android-studio/jbr/  # or similar path
```

### Check Gradle

```bash
cd /home/roncofaber/Desktop/nano-crucible-app

# Gradle wrapper should exist
ls -la gradlew
# Should be executable

# First run will download Gradle automatically
./gradlew --version
# Will download Gradle 8.2 if not cached
```

## ❌ What You DON'T Need

### Python-Related (NOT NEEDED)
- ❌ Python interpreter
- ❌ pip or uv
- ❌ nano-crucible package
- ❌ requests library
- ❌ pandas
- ❌ Any Python dependencies

### Why Not?
The Android app is **pure Kotlin/Java** and uses **Android libraries** instead:

| Python Library | Android Equivalent | Purpose |
|----------------|-------------------|---------|
| `requests` | Retrofit + OkHttp | HTTP client |
| `json` | Moshi | JSON parsing |
| `pandas` | Kotlin collections | Data structures |
| Standard library | Android SDK | Everything else |

## 📦 How Dependencies Are Downloaded

When you open the project in Android Studio:

```
1. Android Studio detects Gradle project
   ↓
2. Gradle wrapper downloads Gradle 8.2 (~100MB)
   ↓
3. Gradle reads build.gradle.kts
   ↓
4. Downloads dependencies from:
   - Maven Central (Java/Kotlin libraries)
   - Google Maven (Android libraries)
   ↓
5. Caches in ~/.gradle/caches/
   ↓
✅ Ready to build!
```

**Time:** 3-7 minutes on first sync
**Subsequent syncs:** <30 seconds (cached)

## 🔍 Verify Dependencies After Sync

After opening the project in Android Studio and syncing:

```bash
cd /home/roncofaber/Desktop/nano-crucible-app

# List all dependencies
./gradlew app:dependencies

# Check if specific library is included
./gradlew app:dependencies | grep retrofit
# Should show: com.squareup.retrofit2:retrofit:2.9.0

./gradlew app:dependencies | grep moshi
# Should show: com.squareup.moshi:moshi:1.15.0
```

## 📊 Dependency Sizes

Approximate download sizes on first build:

| Component | Size | When Downloaded |
|-----------|------|----------------|
| Gradle wrapper | ~100MB | First ./gradlew command |
| Android SDK tools | ~200MB | Android Studio setup |
| Build tools | ~100MB | First Gradle sync |
| Kotlin compiler | ~50MB | First Gradle sync |
| Compose libraries | ~100MB | First Gradle sync |
| Retrofit/OkHttp | ~5MB | First Gradle sync |
| Other dependencies | ~50MB | First Gradle sync |
| **Total** | **~605MB** | **One-time download** |

**After first build:** Only downloads updates (rare, small)

## 🚨 Common Issues

### "SDK not found"
**Solution:**
```
Android Studio → File → Project Structure
→ SDK Location tab
→ Set Android SDK location (usually ~/Android/Sdk)
```

### "Unable to resolve dependency"
**Solution:**
```
1. Check internet connection
2. File → Invalidate Caches / Restart
3. Sync again
```

### "Gradle sync failed"
**Solution:**
```
1. Ensure internet not blocked by firewall
2. Check disk space (need ~1GB free)
3. Delete ~/.gradle/caches/ and retry
```

## ✅ Minimal Requirements Checklist

Before building, ensure:

- [ ] Android Studio installed
- [ ] JDK 17 available
- [ ] Internet connection active
- [ ] At least 2GB free disk space
- [ ] Android SDK installed (via Android Studio)

**NOT needed:**
- [ ] ~~Python~~
- [ ] ~~pip/uv~~
- [ ] ~~nano-crucible~~
- [ ] ~~Any .py files~~

## 🎯 Quick Test

To verify everything is ready:

```bash
cd /home/roncofaber/Desktop/nano-crucible-app

# This should work without ANY Python:
./gradlew clean build

# If successful, you're ready! ✅
# If it fails, check error message and follow solutions above
```

## 📚 Dependency Documentation

All dependencies are documented in:
- `app/build.gradle.kts` (lines 59-112)
- Each dependency has version, purpose, and source

To understand a dependency:
```bash
# Search for it online
# Example: "Retrofit Android" → Official docs at square.github.io/retrofit/
```

## 🔄 Updating Dependencies

Dependencies are locked to specific versions for stability.

To update (optional):
```kotlin
// In app/build.gradle.kts, change version number:
implementation("com.squareup.retrofit2:retrofit:2.9.0")
//                                           👆 update this

// Then sync
```

**Note:** Current versions are stable and tested. Only update if needed.

## Summary

### ✅ Required
- Android Studio + Android SDK
- JDK 17
- Internet (first build)
- ~2GB disk space

### ❌ NOT Required  
- Python (any version)
- pip/uv/conda
- nano-crucible package
- Any .py files
- Python dependencies

**The Android app is 100% self-contained with Java/Kotlin dependencies only!**

---

Still have dependency questions? Check `NO_PYTHON_DEPENDENCIES.md` for detailed explanation.
