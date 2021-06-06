<p align="center"><br><img src="https://user-images.githubusercontent.com/236501/85893648-1c92e880-b7a8-11ea-926d-95355b8175c7.png" width="128" height="128" /></p>
<h3 align="center">Firebase Analytics</h3>
<p align="center"><strong><code>@capacitor-community/firebase-analytics</code></strong></p>
<p align="center">
  Capacitor community plugin for native <a href="https://firebase.google.com/docs/analytics">Firebase Analytics</a>.
</p>

<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2020?style=flat-square" />
  <a href="https://github.com/capacitor-community/firebase-analytics/actions?query=workflow%3A%22Test+and+Build+Plugin%22"><img src="https://img.shields.io/github/workflow/status/capacitor-community/firebase-analytics/Test%20and%20Build%20Plugin?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/firebase-analytics"><img src="https://img.shields.io/npm/l/@capacitor-community/firebase-analytics?style=flat-square" /></a>
<br>
  <a href="https://www.npmjs.com/package/@capacitor-community/firebase-analytics"><img src="https://img.shields.io/npm/dw/@capacitor-community/firebase-analytics?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/firebase-analytics"><img src="https://img.shields.io/npm/v/@capacitor-community/firebase-analytics?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<a href="#contributors-"><img src="https://img.shields.io/badge/all%20contributors-3-orange?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:END -->
</p>

## Maintainers

| Maintainer    | GitHub                                      | Social                                           |
| ------------- | ------------------------------------------- | ------------------------------------------------ |
| Priyank Patel | [priyankpat](https://github.com/priyankpat) | [@priyankpat\_](https://twitter.com/priyankpat_) |
| Stewan Silva  | [stewwan](https://github.com/stewwan)       | [@StewanSilva](https://twitter.com/StewanSilva)  |

Maintenance Status: Actively Maintained

## Installation

Using npm:

```bash
npm install @capacitor-community/firebase-analytics
```

Using yarn:

```bash
yarn add @capacitor-community/firebase-analytics
```

Sync native files:

```bash
npx cap sync
```

On iOS, no further steps are needed.

On Android, register the plugin in your main activity:

```java
import com.getcapacitor.community.firebaseanalytics.FirebaseAnalytics;

public class MainActivity extends BridgeActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(
        savedInstanceState,
        new ArrayList<Class<? extends Plugin>>() {

          {
            // Additional plugins you've installed go here
            // Ex: add(TotallyAwesomePlugin.class);
            add(FirebaseAnalytics.class);
          }
        }
      );
  }
}
```

## Configuration

No configuration is required for this plugin.

## Examples

[Click here](https://github.com/priyankpat/capacitor-plugins-example/tree/firebase-analytics) for an example on how to implement this plugin.

You can also clone the repository:

```bash
git clone https://github.com/priyankpat/capacitor-plugins-example
git checkout -b firebase-analytics
```

## Supported methods

| Name                      | Android | iOS | Web |
| :------------------------ | :------ | :-- | :-- |
| setUserId                 | ✅      | ✅  | ✅  |
| setUserProperty           | ✅      | ✅  | ✅  |
| getAppInstanceId          | ✅      | ✅  | ❌  |
| reset                     | ✅      | ✅  | ✅  |
| logEvent                  | ✅      | ✅  | ✅  |
| setCollectionEnabled      | ✅      | ✅  | ✅  |
| setSessionTimeoutDuration | ✅      | ✅  | ✅  |
| enable                    | ✅      | ✅  | ❌  |
| disable                   | ✅      | ✅  | ❌  |

## Usage

```typescript
// Must import the package once to make sure the web support initializes
import "@capacitor-community/firebase-analytics";

import { Plugins } from "@capacitor/core";

const { FirebaseAnalytics } = Plugins;

/**
 * Platform: Web
 * Configure and initialize the firebase app.
 * @param options - firebase web app configuration options
 * */
FirebaseAnalytics.initializeFirebase({
  apiKey: "...",
  authDomain: "...",
  databaseURL: "...",
  projectId: "...",
  storageBucket: "...",
  messagingSenderId: "...",
  appId: "...",
  measurementId: "...",
});

/**
 * Platform: Web/Android/iOS
 * Sets the user ID property.
 * @param userId - unique identifier of a user
 * @returns void
 * https://firebase.google.com/docs/analytics/userid
 */
FirebaseAnalytics.setUserId({
  userId: "john_doe_123",
});

/**
 * Platform: Web/Android/iOS
 * Sets a user property to a given value.
 * @param userId - unique identifier of a user
 * @returns void
 * https://firebase.google.com/docs/analytics/user-properties
 */
FirebaseAnalytics.setUserProperty({
  name: "favorite_food",
  value: "pizza",
});

/**
 * Platform: Android/iOS
 * Retrieves the app instance id from the service.
 * @param none
 * @returns instanceId - individual instance id value
 * https://firebase.google.com/docs/analytics/user-properties
 */
FirebaseAnalytics.getAppInstanceId();

/**
 * Platform: Web/Android/iOS
 * Clears all analytics data for this app from the device and resets the app instance id.
 * @param none
 * @returns void
 */
FirebaseAnalytics.reset();

/**
 * Platform: Web/Android/iOS
 * Logs an app event.
 * @param name - name of the event to log
 *        params - key/value pairs of properties (25 maximum per event)
 * @returns void
 */
FirebaseAnalytics.logEvent({
  name: "select_content",
  params: {
    content_type: "image",
    content_id: "P12453",
    items: [{ name: "Kittens" }],
  },
});

/**
 * Platform: Web/Android/iOS
 * Sets whether analytics collection is enabled for this app on this device.
 * @param name - enabled - boolean true/false
 * @returns void
 */
FirebaseAnalytics.setCollectionEnabled({
  enabled: false,
});

/**
 * Platform: Web/Android/iOS
 * Deprecated - use setCollectionEnabled() instead
 * Enable analytics collection for this app on this device.
 * @param none
 * @returns void
 */
FirebaseAnalytics.enable();

/**
 * Platform: Web/Android/iOS
 * Deprecated - use setCollectionEnabled() instead
 * Disable analytics collection for this app on this device.
 * @param none
 * @returns void
 */
FirebaseAnalytics.disable();

/**
 * Platform: Web/Android/iOS
 * Sets the duration of inactivity that terminates the current session.
 * @param duration - duration in seconds (default - 18000)
 * @returns void
 */
FirebaseAnalytics.setSessionTimeoutDuration({
  duration: 10000,
});
```

## Setup

Navigate to the project settings page for your app on Firebase.

### iOS

Download the `GoogleService-Info.plist` file. In Xcode right-click on the yellow folder named "App" and select the `Add files to "App"`.

> Tip: if you drag and drop your file to this location, Xcode may not be able to find it.

### Android

Download the `google-services.json` file and copy it to `android/app/` directory of your capacitor project.

## iOS setup

- `ionic start my-cap-app --capacitor`
- `cd my-cap-app`
- `npm install --save @capacitor-community/firebase-analytics`
- `mkdir www && touch www/index.html`
- `sudo gem install cocoapods` (only once)
- `npx cap add ios`
- `npx cap sync ios` (every time you run `npm install`)
- `npx cap open ios`
- sign your app at xcode (general tab)
- add `GoogleService-Info.plist` to the app folder in xcode

### Enable debug view

1. In Xcode, select Product > Scheme > Edit scheme
2. Select Run from the left menu
3. Select the Arguments tab
4. In the Arguments Passed On Launch section, add `-FIRAnalyticsDebugEnabled`

> Tip: every time you change a native code you may need to clean up the cache (Product > Clean build folder) and then run the app again.

## Android setup

- `ionic start my-cap-app --capacitor`
- `cd my-cap-app`
- `npm install --save @capacitor-community/firebase-analytics`
- `mkdir www && touch www/index.html`
- `npx cap add android`
- `npx cap sync android` (every time you run `npm install`)
- `npx cap open android`
- add `google-services.json` to your `android/app` folder
- `[extra step]` in android case we need to tell Capacitor to initialise the plugin:

> on your `MainActivity.java` file add `import com.getcapacitor.community.firebaseanalytics.FirebaseAnalytics;` and then inside the init callback `add(AnalyticsPlugin.class);`

Now you should be set to go. Try to run your client using `ionic cap run android --livereload --address=0.0.0.0`.

> Tip: every time you change a native code you may need to clean up the cache (Build > Clean Project | Build > Rebuild Project) and then run the app again.

## Updating

For existing projects you can upgrade all capacitor related packages (including this plugin) with this single command

`npx npm-upgrade '*capacitor*' && npm install`

## Migration

If you were previously using the `capacitor-analytics` package from npm

1. rename dep in package.json from `capacitor-analytics` to `@capacitor-community/firebase-analytics`
2. on android's _MainActivity.java_ change the import path from `io.stewan.capacitor.analytics.AnalyticsPlugin;` to `com.getcapacitor.community.firebaseanalytics.FirebaseAnalytics;`
3. public api changes
   - `instance()` is now `getAppInstanceId()`
   - `setScreenName()` removed, see https://firebase.googleblog.com/2020/08/google-analytics-manual-screen-view.html
   - `setUserID()` is now `setUserId()`
   - `setUserProp()` us now `setUserProperty()`

## Further info

- [Android](https://firebase.google.com/docs/android/setup)
- [iOS](https://firebase.google.com/docs/analytics/get-started?platform=ios)
- [Web](https://firebase.google.com/docs/analytics/get-started?platform=web)
