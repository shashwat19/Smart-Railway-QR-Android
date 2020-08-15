# Smart-Railway-QR-Android
This app is part of my major project 'Smart Railway Ticket Booking and Authentication System'. In this, the passenger can generate a QR Code(after booking the train ticket) along with registering their fingerprint using the android app which will contain his PNR number. At the time of boarding the train, the passenger has to get the QR code and fingerprint scanned(using the same app and biometric scanner), which will send a signal to the arduino(connected to the app via bluetooth) to open the coach gates. The app also tracks the location of passenger in realtime on Firebase.

## Getting Started

Clone this repository:

```
git clone https://github.com/shashwat19/Smart-Railway-QR-Android.git
```

Open in android studio

### Prerequisites

You you need Android Studio to run this project:

[Download Link](https://developer.android.com/studio)


## Deployment

You can generate .apk files (either signed or unsigned) using Android Studio. 

## Built With

* [Firebase](http://firebase.google.com/) - The web framework used
* [Mantra MFS 100](https://download.mantratecapp.com/Forms/UserDownload) - SDK used for fingerprint scanner integration
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds




