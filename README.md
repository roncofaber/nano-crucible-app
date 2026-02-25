# Crucible Lens

Android app for scanning QR codes to view samples and datasets from the Molecular Foundry's Crucible data system.

## Features

- 📷 QR code scanning for Crucible resource UUIDs
- 🔍 Manual UUID lookup
- 📊 Detailed sample and dataset information
- 🖼️ Dataset thumbnails and images
- 🔗 Navigate through parent/child relationships and linked resources
- 🔐 Secure API key storage

## Requirements

- Android 8.0 (API 26) or higher
- Crucible API key from https://crucible.lbl.gov/api/v1/user_apikey

## Setup

1. Clone and open in Android Studio
2. Build and install the APK
3. Open app → Settings → Enter your API key

## Usage

**Scan QR Code**: Tap "Scan QR Code" and point camera at a Crucible QR code

**Manual Entry**: Enter a UUID in the text field and tap "Look Up"

**Browse**: Navigate between samples, datasets, and their relationships

## Tech Stack

- Kotlin + Jetpack Compose + Material 3
- CameraX + ML Kit (QR scanning)
- Retrofit + Moshi (API)
- Coil (image loading)

## API

Base URL: `https://crucible.lbl.gov/api/v1/`

Endpoints: samples, datasets, scientific metadata, thumbnails, parent/child relationships

## License

BSD-3-Clause

## Contact

- Email: roncoroni@lbl.gov, mkwall@lbl.gov
- Molecular Foundry: https://foundry.lbl.gov/

---

Developed by the Data Group at the Molecular Foundry, Lawrence Berkeley National Laboratory.
