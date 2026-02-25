# Fix Android Studio Errors

## Error: "The file name must end with .xml or .png"

This is a common error that appears in Android Studio before the project is properly synced. Here's how to fix it:

### Solution 1: Sync Gradle (Most Common Fix)

1. Open the project in Android Studio
2. Wait for indexing to complete
3. Click **File → Sync Project with Gradle Files**
4. Wait for sync to complete (~2-5 minutes)
5. Error should disappear

### Solution 2: Invalidate Caches

If syncing doesn't work:

1. **File → Invalidate Caches / Restart**
2. Select **"Invalidate and Restart"**
3. Wait for Android Studio to restart
4. Let it re-index the project
5. Sync again if needed

### Solution 3: Clean and Rebuild

1. **Build → Clean Project**
2. Wait for completion
3. **Build → Rebuild Project**
4. Error should be resolved

### Solution 4: Delete Build Directories

From terminal:
```bash
cd /home/roncofaber/Software/nano-crucible-app
rm -rf .gradle app/build build
```

Then in Android Studio:
- File → Sync Project with Gradle Files

### Solution 5: Check Resource Files

All resource files have been verified:
- ✅ All icon files are valid PNG images
- ✅ All XML files are properly formatted
- ✅ No invalid files in res/ directory
- ✅ File structure is correct

### Verification

After fixing, verify:
```bash
cd /home/roncofaber/Software/nano-crucible-app
find app/src/main/res -type f -name "*" | wc -l
# Should show: 22 files
```

All files should be:
- `.xml` files (manifests, configs, drawable resources)
- `.png` files (launcher icons)

## Other Common First-Time Errors

### "SDK not found"
**Fix:** File → Project Structure → SDK Location → Set Android SDK path

### "Plugin version too low"
**Fix:** This project uses Gradle 8.2 and Android Gradle Plugin 8.2.0, which are current

### "Cannot resolve symbol"
**Fix:** Let Android Studio complete indexing (bottom right corner shows progress)

## Still Having Issues?

1. Make sure you have:
   - Android Studio Hedgehog (2023.1.1) or later
   - JDK 17
   - Android SDK 34

2. Check Android Studio's Build output (View → Tool Windows → Build)

3. Check Logcat for detailed error messages

4. Try creating a new simple project first to verify Android Studio installation

## Quick Verification Checklist

Before building, ensure:
- [ ] Project opened in Android Studio (not just the folder)
- [ ] Gradle sync completed without errors
- [ ] Indexing completed (bottom right corner)
- [ ] Android SDK is properly configured
- [ ] No red underlines in build.gradle.kts files
- [ ] "app" module appears in project structure

## Expected First Sync

On first open:
1. Android Studio detects Gradle project
2. Downloads Gradle wrapper (if needed)
3. Downloads dependencies (~150MB)
4. Indexes project files
5. Runs lint checks
6. Ready to build!

This takes 2-5 minutes on first sync.

---

**Note**: The error message you're seeing is typically just Android Studio's pre-sync warning. Once Gradle sync completes, it should resolve automatically.
