### Office Elevator

Note: Project is in active development as part of internship at Student Labs & Effective.

I’m developing it individually for the company at which I am currently doing an internship. The
company’s office is located on the 5th floor of a business center. To access the floor the company’s
staff need to use a special key in the elevator cabin. The thing is people tend to forget the key.
So, I thought it would be great to develop a mobile app enabling the elevator to go up. No key – no
problem.

Wanted UX: An employee wishing to access the office needs to just tap a button inside the app. App
uses Google Sign in and secure communication on the backend side to keep the office safe from
unauthorized access. That’s it.

I deeply love Kotlin and KMM. So my challenge is to make the entire project on Kotlin, including
Android+iOS apps with Compose Multiplatform, ktor(jvm) on server side, ktor(both, jvm and native)
and ktgpio(native) on RPI.

For debug/dev purpose, I’m using RPI with led indicator to test and develop the system without
connection to real elevator controller.

<a href="https://youtu.be/L9RUq4YG8sM"><img src="images/demo_thumbnail.png" /><a/>

If you’re not familiar with KMM, please understand that Compose Multiplatform and Kotlin Native is
in active development, so you can have some random issues trying to build the project. In case of
any issues, feel free to contact me via telegram: @StanislavRadchenko.

### Architecture diagram

<img src="images/interaction_diagram.png"/>

Basic idea of communication:

- Client authorizes within the app with the Google Sign In
- Client touches elevator google with the app
    - App makes request to server (running in Digital Ocean)
    - Requests includes id token, current time, and signature hash (that depends on current time and
      private secret shared between server and RPI)
    - Server checks that token is correct and call RPI web hook
    - RPI checks that signature hash is correct and current time is within 1 minute from now
    - In case of check success, RPI makes signal to GPIO which is connected with the elevator system
      and returns success to server. In case of check failure, RPI returns error response to server.
    - Server proxies RPI response back to the client app.
- Note: both, server and RPI and publically available on the Internet.

### How to build project

Project is organized as mono repo. I’m using OS X Monterey and Macbook Pro (with M1 Pro processor)
for development. Also, build is tested on Macbook Pro with Intel x64 processor.

In order to build all parts of project, you will need JDK, Android SDK, and XCode. I’m using OpenJDK
11, Android SDK install together with Android Studio Dolphin 2021.3, XCode 14.2. If you have
something slightly different, the latest stable versions should work.

Also, project relies on RPI Model B+ (which works as endpoint to physically interconnect with
elevator controller). You won’t be able to run project end-to-end locally you don’t have RPI. But
you should be able to run all other parts of the projects.

You can run only mobile part. After build you will get app that’s connected with cloud-server
deployed to Digital Ocean. So you don’t need to deploy it by youself, if you’re just exploring the
mobile part.

The below is a guide to build all parts of the app one by one.

**Build Android app**

```bash
$ cd mobile-app
$ ./gradlew :android:assembleDebug
```

The build process can take about 5 minutes. The output APK will be placed
to `mobile-app/android/build/outputs/apk/debug` folder. You can install and run it on the device
using adb.

I’m using Nothing Phone for development. Minimal supported API is 21 (Android 5.0 Lollipop). Though
I didn’t test it enough on versions below API 30. So please use API 30 as well.

Also, you can use Android Studio or Intellij IDEA to build the project using its wizards. I’m using
Intellij IDEA CE to development.

**Build iOS app**

```bash
$ cd mobile-app/iosApp
$ pod install
$ open iosApp.xcworkspace
```

Next you will need to build the app using XCode wizard. I’m not iOS developer, so iOS signing
process is the most hard for me. The build process is working find on my machine. But I’m not sure
how it will work for others, and it wasn’t tested enough atm. If you have any issues with iOS build,
please report it to github issues.

**Build Server**

Server is based on ktor. Netty is used as a server engine.

```bash
$ cd mobile-app/cloud-server
$ ./gradlew build
```

The server jar will be places to `cloud-server/build/libs/cloud-server-all.jar`

**Build RPI part**

RPI part is currently implemented with Ktor (JVM). Right now, I’m exploring the options to move it
to pure Kotlin Native, including both server/ktor part and gpio control. It’s not stable and located
inside `develop` branch. More stable option is usage of ktor (jvm) and control of gpio with Python.
That’s what you can find in `rpi_ktor_server_jvm`  branch.

In order to build rpi part use

```bash
$ cd rpi-server
$ ./gradlew build
```

### Major Dependencies

mobile-app

- ktor-client
- compose multiplatform
- google sign-in

cloud-server

- ktor
- krypto
- google-api-client
- shadow jar

rpi-server

- ktor
- kotlin/native