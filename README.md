# Capacitor Firebase Analytics Plugin

Capacitory community plugin for firebase analytics.

## Maintainers

| Maintainer    | GitHub                                      | Social                                           | Sponsoring Company |
| ------------- | ------------------------------------------- | ------------------------------------------------ | ------------------ |
| Priyank Patel | [priyankpat](https://github.com/priyankpat) | [@priyankpat\_](https://twitter.com/priyankpat_) | Ionic              |

Mainteinance Status: Actively Maintained

## Installation

To use npm

```bash
npm install @capacitor/firebase-analytics
```

To use yarn

```bash
yarn add @capacitor/firebase-analytics
```

Sync native files

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

No configuration required for this plugin.

## Supported methods

| Name             | Android | iOS | Web |
| :--------------- | :------ | :-- | :-- |
| setUserId        | ✅      | ✅  | ❌  |
| setUserProperty  | ✅      | ✅  | ❌  |
| getAppInstanceId | ✅      | ✅  | ❌  |
| setScreenName    | ✅      | ✅  | ❌  |
| reset            | ✅      | ✅  | ❌  |
| logEvent         | ✅      | ✅  | ❌  |

## Usage

```typescript
// Must import the package once to make sure the web support initializes
import "@capacitor/firebase-analytics";

import { Plugins } from "@capacitor/core";

const { FirebaseAnalytics } = Plugins;

/**
 * This method will allow you to set user id.
 * @param userId - unique identifier of a user
 * @returns void
 * https://firebase.google.com/docs/analytics/userid
 */
FirebaseAnalytics.setUserId({
  userId: "john_doe_123",
});

/**
 * This method will allow you to set user property.
 * @param userId - unique identifier of a user
 * @returns void
 * https://firebase.google.com/docs/analytics/user-properties
 */
FirebaseAnalytics.setUserProperty({
  name: "favorite_food",
  value: "pizza",
});

/**
 * This method will allow you to set user id.
 * @param none
 * @returns instanceId - individual instance id value
 * https://firebase.google.com/docs/analytics/user-properties
 */
FirebaseAnalytics.getAppInstanceId();

/**
 * This method will allow you to track screens.
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
 * This method will clear all analytics data and reset the app instance id.
 * @param none
 * @returns void
 */
FirebaseAnalytics.reset();

/**
 * This method will log an app event.
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
```
