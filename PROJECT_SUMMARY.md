# Crucible Scanner - Android App Project Summary

## Project Overview

A complete Android application for scanning QR codes containing UUIDs to fetch and display sample/dataset information from the Crucible API.

## ✅ What Was Created

### Core Functionality
- ✅ QR code scanner using CameraX + ML Kit
- ✅ Manual UUID entry interface
- ✅ REST API integration with Crucible
- ✅ Rich data display for Samples and Datasets
- ✅ Thumbnail image support
- ✅ Secure API key storage
- ✅ Material Design 3 UI

### Project Structure

```
nano-crucible-app/
├── README.md                          # Main documentation
├── SETUP_GUIDE.md                     # Detailed setup instructions
├── PROJECT_SUMMARY.md                 # This file
├── .gitignore                         # Git ignore rules
├── build.gradle.kts                   # Root build configuration
├── settings.gradle.kts                # Project settings
├── gradle.properties                  # Gradle properties
├── gradle/wrapper/                    # Gradle wrapper
│   └── gradle-wrapper.properties
└── app/
    ├── build.gradle.kts              # App module build config
    ├── proguard-rules.pro            # ProGuard rules
    └── src/main/
        ├── AndroidManifest.xml       # App manifest
        ├── java/gov/lbl/crucible/scanner/
        │   ├── MainActivity.kt                              # Main activity
        │   ├── data/
        │   │   ├── api/
        │   │   │   ├── ApiClient.kt                        # Retrofit client
        │   │   │   └── CrucibleApiService.kt               # API interface
        │   │   ├── model/
        │   │   │   └── CrucibleResource.kt                 # Data models
        │   │   ├── preferences/
        │   │   │   └── PreferencesManager.kt               # DataStore manager
        │   │   └── repository/
        │   │       └── CrucibleRepository.kt               # Data repository
        │   └── ui/
        │       ├── detail/
        │       │   └── ResourceDetailScreen.kt             # Detail view
        │       ├── home/
        │       │   └── HomeScreen.kt                       # Home screen
        │       ├── navigation/
        │       │   └── NavGraph.kt                         # Navigation
        │       ├── scanner/
        │       │   ├── BarcodeAnalyzer.kt                  # ML Kit analyzer
        │       │   └── QRScannerScreen.kt                  # Scanner UI
        │       ├── settings/
        │       │   └── SettingsScreen.kt                   # Settings UI
        │       ├── theme/
        │       │   ├── Theme.kt                            # Material theme
        │       │   └── Type.kt                             # Typography
        │       └── viewmodel/
        │           └── ScannerViewModel.kt                 # ViewModel
        └── res/
            ├── values/
            │   ├── colors.xml                              # Color definitions
            │   ├── strings.xml                             # String resources
            │   └── themes.xml                              # Theme definitions
            ├── xml/
            │   ├── backup_rules.xml                        # Backup rules
            │   └── data_extraction_rules.xml               # Data extraction
            └── mipmap-*/                                   # Launcher icons

```

## Technology Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material 3 |
| Architecture | MVVM (Model-View-ViewModel) |
| Navigation | Jetpack Navigation Compose |
| HTTP Client | Retrofit 2.9.0 + OkHttp |
| JSON Parsing | Moshi with Kotlin codegen |
| QR Scanning | CameraX 1.3.1 + ML Kit Barcode 17.2.0 |
| Image Loading | Coil 2.5.0 |
| Preferences | DataStore |
| Permissions | Accompanist 0.33.2 |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 (Android 14) |

## Key Features Implemented

### 1. QR Code Scanner
- Uses CameraX for camera preview
- ML Kit Barcode Scanning for QR detection
- Real-time detection and auto-navigation
- Camera permission handling with Accompanist

### 2. API Integration
- Automatic resource type detection (Sample vs Dataset)
- Fetches full resource details including:
  - Basic information (name, description, UUID)
  - Associated resources (parent samples, datasets)
  - Scientific metadata
  - Thumbnails
- Bearer token authentication
- Error handling and retry logic

### 3. Data Display
- Clean, card-based Material 3 UI
- Type-specific information display:
  - **Samples**: Type, project, datasets, parent samples
  - **Datasets**: Measurement type, instrument, format, samples, scientific metadata
- Expandable metadata sections
- Base64 thumbnail decoding and display
- Keywords display as chips
- UUID display in monospace font

### 4. Settings Management
- Secure API key storage using encrypted DataStore
- Show/hide API key toggle
- Save confirmation feedback
- About section

### 5. Navigation Flow
```
Home Screen
    ├─→ Scan QR → Scanner Screen → Detail Screen
    ├─→ Manual Entry → Detail Screen
    └─→ Settings → Settings Screen
```

## API Endpoints Used

| Endpoint | Purpose |
|----------|---------|
| `GET /samples/{uuid}` | Fetch sample details |
| `GET /datasets/{uuid}` | Fetch dataset details |
| `GET /datasets/{uuid}/scientific_metadata` | Fetch scientific metadata |
| `GET /datasets/{uuid}/thumbnails` | Fetch dataset thumbnails |

## Dependencies Summary

### Core Android
- androidx.core:core-ktx
- androidx.lifecycle:lifecycle-runtime-ktx
- androidx.activity:activity-compose

### Compose
- androidx.compose.ui (BOM 2024.01.00)
- androidx.compose.material3
- androidx.compose.material:material-icons-extended

### Camera & ML Kit
- androidx.camera:camera-camera2
- androidx.camera:camera-lifecycle
- androidx.camera:camera-view
- com.google.mlkit:barcode-scanning

### Networking
- com.squareup.retrofit2:retrofit
- com.squareup.retrofit2:converter-moshi
- com.squareup.okhttp3:okhttp
- com.squareup.okhttp3:logging-interceptor

### JSON & Data
- com.squareup.moshi:moshi-kotlin
- androidx.datastore:datastore-preferences

### Utilities
- io.coil-kt:coil-compose (image loading)
- com.google.accompanist:accompanist-permissions
- androidx.navigation:navigation-compose

## Next Steps

### To Build the App

1. **Open in Android Studio**
   ```bash
   # Open the project folder in Android Studio
   ```

2. **Sync Gradle**
   - Android Studio will auto-sync on open
   - All dependencies will be downloaded

3. **Add Launcher Icons** (Optional)
   - Generate at: https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html
   - Replace placeholders in `mipmap-*` folders

4. **Build APK**
   ```bash
   ./gradlew assembleDebug
   ```

5. **Install on Device**
   - Connect Android device via USB
   - Click Run in Android Studio

### To Use the App

1. **Get API Key**
   - Visit: https://crucible.lbl.gov/api/v1/user_apikey
   - Sign in with ORCID
   - Copy your API key

2. **Configure App**
   - Open Settings in app
   - Paste API key
   - Save

3. **Start Scanning**
   - Scan QR codes with UUIDs
   - Or enter UUIDs manually

## Design Decisions

### Why These Technologies?

1. **Jetpack Compose**: Modern declarative UI, less boilerplate than XML
2. **MVVM**: Separation of concerns, testable, lifecycle-aware
3. **Retrofit**: Industry standard, reliable, good error handling
4. **ML Kit**: Google's official barcode library, performant, accurate
5. **Moshi**: Faster than Gson, Kotlin-native, type-safe
6. **DataStore**: Modern replacement for SharedPreferences

### Architecture Highlights

- **Sealed Classes** for type-safe state management (UiState, ResourceResult)
- **Kotlin Coroutines** for async operations
- **Flow** for reactive data streams
- **Repository Pattern** for data abstraction
- **Single Activity** with Compose Navigation

## Testing Recommendations

### Manual Testing Checklist
- [ ] QR scanner opens camera
- [ ] QR codes are detected correctly
- [ ] Manual UUID entry works
- [ ] Sample data displays correctly
- [ ] Dataset data displays correctly
- [ ] Thumbnails load properly
- [ ] Scientific metadata formats correctly
- [ ] API key saves and persists
- [ ] Error states display appropriately
- [ ] Navigation works smoothly

### Example UUIDs for Testing
(Replace with actual test UUIDs from your Crucible instance)
- Sample: `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`
- Dataset: `yyyyyyyy-yyyy-yyyy-yyyy-yyyyyyyyyyyy`

## Known Limitations

1. **Launcher Icons**: Placeholder icons need to be replaced with proper assets
2. **Offline Mode**: No offline caching of fetched data
3. **Search History**: No history of scanned/searched UUIDs
4. **Export**: No ability to export or share resource data
5. **Favorites**: No bookmarking/favorites feature

## Future Enhancement Ideas

- 📊 Add data visualization for scientific metadata
- 📜 Scan history and recent items
- ⭐ Favorites/bookmarks
- 📤 Share resource details
- 🔍 Advanced search within resources
- 📱 Widget for quick scanning
- 🌙 Dark mode improvements
- 📶 Offline caching with Room database
- 🔔 Push notifications for resource updates

## Support

For questions or issues:
- **Email**: roncoroni@lbl.gov, mkwall@lbl.gov
- **Documentation**: See `README.md` and `SETUP_GUIDE.md`
- **Crucible Web**: https://crucible.lbl.gov

---

**Status**: ✅ Ready for building and testing
**Last Updated**: 2026-02-24
