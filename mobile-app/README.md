# Office Elevator Mobile App

## Running

### Android:

1. Open the `OfficeElevator` Gradle project in Android Studio
2. Run the app in the android module

### IOS:

The iOS application can only be built and run on macOS. You also need to have CocoaPods installed,
which can generally be done by running `brew install cocoapods` or `sudo gem install cocoapods`.

See [CocoaPods Overview and Setup](https://kotlinlang.org/docs/native-cocoapods.html)
and [Check your environment](https://kotlinlang.org/docs/multiplatform-mobile-setup.html#check-your-environment)
for more details.

##### From Android Studio

1. Install
   the [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
2. Open the `OfficeElevator` Gradle project in Android Studio
3. Run following command:

```bash
./gradlew uikit:iosDeployIPhone13Debug // or
./gradlew uikit:iosDeployIPhone13ProMaxDebu
```