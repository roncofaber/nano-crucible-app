# Crucible Scanner - Android App

An Android application for scanning QR codes containing UUIDs to quickly access sample and dataset information from the Molecular Foundry's Crucible data system.

## Features

- 📷 **QR Code Scanner** - Scan QR codes containing Crucible resource UUIDs
- 🔍 **Manual UUID Entry** - Enter UUIDs manually for quick lookups
- 📊 **Rich Data Display** - View detailed information about samples and datasets
- 🖼️ **Thumbnail Support** - Display dataset thumbnails and images
- 🔐 **Secure API Key Storage** - Encrypted storage of your Crucible API credentials
- 📱 **Material Design 3** - Modern, beautiful UI following Google's latest design guidelines

## Screenshots

*(Screenshots to be added)*

## Requirements

- Android 8.0 (API 26) or higher
- Crucible API key (obtain at: https://crucible.lbl.gov/api/v1/user_apikey)
- Camera permission (for QR scanning)
- Internet connection

## Building the App

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK 34

### Build Steps

1. Clone or open the project in Android Studio:
   ```bash
   cd /home/roncofaber/Software/nano-crucible-app
   ```

2. Open the project in Android Studio:
   - File → Open → Select the `nano-crucible-app` folder

3. Let Android Studio sync Gradle dependencies

4. Build the APK:
   - Build → Build Bundle(s) / APK(s) → Build APK(s)

5. Or run directly on a device/emulator:
   - Run → Run 'app'

### Building from Command Line

```bash
cd /home/roncofaber/Software/nano-crucible-app
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

## Installation

### From Android Studio
- Connect your Android device via USB (with USB debugging enabled)
- Click the "Run" button in Android Studio

### Manual APK Installation
1. Transfer the APK to your Android device
2. Enable "Install from Unknown Sources" in your device settings
3. Open the APK file on your device to install

## Usage

### First Time Setup

1. Open the app
2. Tap the **Settings** icon (gear icon in top-right)
3. Enter your Crucible API key
4. Tap **Save API Key**

### Scanning QR Codes

1. From the home screen, tap **Scan QR Code**
2. Grant camera permission if prompted
3. Point your camera at a QR code containing a Crucible UUID
4. The app will automatically fetch and display the resource information

### Manual UUID Entry

1. From the home screen, enter a UUID in the text field
2. Tap **Look Up**
3. View the resource details

### Viewing Resource Details

The detail screen shows:
- **Sample Information**: Sample name, type, description, associated datasets, parent samples
- **Dataset Information**: Dataset name, measurement type, instrument, scientific metadata, thumbnails
- **Keywords**: Tagged keywords for the resource
- **UUID**: Full unique identifier for reference

## API Reference

This app uses the Crucible REST API:
- Base URL: `https://crucible.lbl.gov/api/v1/`
- Endpoints:
  - `GET /samples/{uuid}` - Fetch sample information
  - `GET /datasets/{uuid}` - Fetch dataset information
  - `GET /datasets/{uuid}/scientific_metadata` - Fetch scientific metadata
  - `GET /datasets/{uuid}/thumbnails` - Fetch dataset thumbnails

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit + OkHttp + Moshi
- **QR Scanning**: CameraX + ML Kit Barcode Scanning
- **Image Loading**: Coil
- **Storage**: DataStore (for API key)
- **Permissions**: Accompanist Permissions

## Project Structure

```
app/src/main/java/gov/lbl/crucible/scanner/
├── data/
│   ├── api/              # API service interfaces and client
│   ├── model/            # Data models (Sample, Dataset)
│   ├── preferences/      # DataStore preferences manager
│   └── repository/       # Repository layer
├── ui/
│   ├── detail/           # Resource detail screen
│   ├── home/             # Home screen
│   ├── navigation/       # Navigation graph
│   ├── scanner/          # QR scanner screen
│   ├── settings/         # Settings screen
│   ├── theme/            # Material theme
│   └── viewmodel/        # ViewModels
└── MainActivity.kt
```

## Permissions

The app requires the following permissions:
- `CAMERA` - For scanning QR codes
- `INTERNET` - For fetching data from the Crucible API
- `ACCESS_NETWORK_STATE` - For checking network connectivity

## Troubleshooting

### Camera not working
- Ensure camera permission is granted in app settings
- Check that your device has a working camera

### API errors
- Verify your API key is correct in Settings
- Check your internet connection
- Ensure the UUID is valid

### Build errors
- Clean and rebuild: Build → Clean Project, then Build → Rebuild Project
- Invalidate caches: File → Invalidate Caches / Restart

## Related Projects

- [nano-crucible](../nano-crucible) - Python client library for Crucible
- [crucible-graph-explorer](../crucible_graph_explorer) - Web-based graph explorer

## License

BSD-3-Clause License

## Contact

For issues or questions:
- Email: roncoroni@lbl.gov, mkwall@lbl.gov
- Molecular Foundry: https://foundry.lbl.gov/

---

**Crucible Scanner** is developed by the Data Group at the Molecular Foundry at Lawrence Berkeley National Laboratory.
