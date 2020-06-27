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

| Maintainer     | GitHub                                                  | Social                                           | Sponsoring Company |
| -------------- | ------------------------------------------------------- | ------------------------------------------------ | ------------------ |
| Priyank Patel  | [priyankpat](https://github.com/priyankpat)             | [@priyankpat\_](https://twitter.com/priyankpat_) | Ionic              |
| Stewan Silva   | [stewwan](https://github.com/stewwan)                   | [@StewanSilva](https://twitter.com/StewanSilva)  | Ionic              |
| Daniel Pereira | [danielprrazevedo](https://github.com/danielprrazevedo) | [@DandanPrr](https://twitter.com/DandanPrr)      | Ionic              |

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
| setScreenName             | ✅      | ✅  | ❌  |
| reset                     | ✅      | ✅  | ✅  |
| logEvent                  | ✅      | ✅  | ✅  |
| setCollectionEnabled      | ✅      | ✅  | ✅  |
| setSessionTimeoutDuration | ✅      | ✅  | ✅  |

## Usage

```typescript
// Must import the package once to make sure the web support initializes
import "@capacitor/firebase-analytics";

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
 * Platform: Android/iOS
 * Sets the current screen name, which specifies the current visual context in your app.
 * @param screenName - name of the current screen to track
 *        nameOverride - name of the screen class to override
 * @returns instanceId - individual instance id value
 * https://firebase.google.com/docs/analytics/screenviews
 */
FirebaseAnalytics.setScreenName({
  screenName: "login",
  nameOverride: "LoginScreen",
});

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
 * Sets the duration of inactivity that terminates the current session.
 * @param duration - duration in seconds (default - 18000)
 * @returns void
 */
FirebaseAnalytics.setSessionTimeoutDuration({
  duration: 10000,
});
```
