<p align="center">
  <img src="..\assets\logo.jpg" width="" alt="light bulb icon" height="250">
</p>

# Effective-Office-EffectiveTablet

## Goal :dart:
The main goal of our project is to create a simple and convenient application for booking meeting rooms on a tablet. We aim to provide employees with the ability to quickly and efficiently find available meeting rooms and book them for the desired time. Our goal is to simplify the meeting organization process, save time, and create a comfortable working environment for our users. We strive to provide a reliable and user-friendly system for booking meeting rooms on a tablet, in order to improve workflow and enhance productivity.

## Screenshots 	:camera_flash:

## Application

### Main screen

|                        Main                         |
|:---------------------------------------------------:|
| <img height="500" src="..\assets\main-screen.png"/> |

### Meeting selection screen

|                        Meeting selection                         |
|:----------------------------------------------------------------:|
| <img height="500" src="..\assets\meeting-selection-screen.png"/> |

### Data Change Screen

|                        Data Change                         |
|:----------------------------------------------------------:|
| <img height="500" src="..\assets\data-change-screen.png"/> |

# Features :fire:

## Viewing available meeting rooms

Allows users to view a list of available meeting rooms with information on their availability and other details.

## Booking a meeting room

Enables users to select a desired meeting room and book it for a specific time.

## Modifying reservations

Provides users with the ability to make changes to existing reservations, such as changing the time or duration of the meeting.

## Managing reservations

Allows users to view and manage their existing reservations, including cancelling and changing meeting details.

## Used libraries 📚

A list of technologies used within the project:
* DI: [Hilt](https://dagger.dev/hilt/)
* Notion: [Notion-sdk-jvm](https://github.com/seratch/notion-sdk-jvm)
* Multithreading: [Kotlin Flow](https://kotlinlang.org/docs/flow.html) and [Coroutines](https://kotlinlang.org/docs/flow.html)
* Image loading: [Coil](https://coil-kt.github.io/coil/)
* Network client: [okHttp](https://square.github.io/okhttp/)
* REST API: [retrofit](https://square.github.io/retrofit/)
* JSON: [moshi](https://github.com/square/moshi)

## Authors: :writing_hand:

- [Margarita Ginjolia](https://github.com/MargaritaDj)
- [Maksim Mishenko](https://github.com/UserNameMax)
- [Victor Konyshev](https://github.com/DireRaven-exe)
- [Angelika Semenova](https://github.com/UserNameMax)

## Guidelines for starting up: :computer:

In order to build all parts of project, you will need JDK and Android Studio. We are using this versions:

Android Studio Electric Eel: Version 2022.1.1

JDK: Version 11.0.15

## Running an application using gradle: :arrow_forward:

1. Open the terminal and go to the root directory of the project.

2. Run the ./gradlew assembleDebug command to assemble the project in debug mode.

3. To run the application on an emulator or connected device, run the ./gradlew installDebug command.

4. If you want to run tests, run the command ./gradlew test.

5. If you want to assemble the APK file for the release version, run the command ./gradlew assembleRelease.