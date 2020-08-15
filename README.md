# Smart-Railway-QR-Android
This app is part of my major project 'Smart Railway Ticket Booking and Authentication System'. In this, the passenger can generate a QR Code(after booking the train ticket) along with registering their fingerprint using the android app which will contain his PNR number. At the time of boarding the train, the passenger has to get the QR code and fingerprint scanned(using the same app and biometric scanner), which will send a signal to the arduino(connected to the app via bluetooth) to open the coach gates. The app also tracks the location of passenger in realtime on Firebase.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You you need android studio to run this project:

[Download Link](https://developer.android.com/studio)


### Installing

Clone this repository:

```
git clone https://github.com/shashwat19/Smart-Railway-QR-Android.git
```

Open in android studio

## Deployment

You can generate .apk files(either signed or unsigned) using Android Studio. 

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc


