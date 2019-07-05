# capacitor-analytics [![npm version](https://badge.fury.io/js/capacitor-analytics.svg)](https://badge.fury.io/js/capacitor-analytics)

Capacitor plugin to enable features from Firebase Analytics

![Capacitor Firebase Analytics Plugin](https://i.imgur.com/TpyqoLv.png)

## API

- `instance(): Promise<{ id: string }>`
- `reset(): Promise<void>`
- `setScreen({ name: string; class?: string }): Promise<void>`
- `setUserID({ value: string }): Promise<void>`
- `setUserProp({ key: string; value: string }): Promise<void>`
- `logEvent({ name: string; params?: object }): Promise<void>`

> For more information check the [`definitions`](/src/definitions.ts) file

## Usage

```js
import { Analytics } from 'capacitor-analytics';
const analytics = new Analytics();

//
// set user id
analytics.setUserID({ value: '1337' });

//
// log some event
analytics.logEvent({
  name: 'purchase',
  params: { items: '[1, 2, 3]', total: 254.5 }
});
```

## Add Google config files

Navigate to the project settings page for your app on Firebase.

### iOS

Download the `GoogleService-Info.plist` file. In Xcode right-click on the yellow folder named "App" and select the `Add files to "App"`.

> Tip: if you drag and drop your file to this location, Xcode may not be able to find it.

### Android

Download the `google-services.json` file and copy it to `android/app/` directory of your capacitor project.

## iOS setup

- `ionic start my-cap-app --capacitor`
- `cd my-cap-app`
- `npm install --save capacitor-analytics`
- `mkdir www && touch www/index.html`
- `sudo gem install cocoapods` (only once)
- `npx cap add ios`
- `npx cap sync ios` (every time you run `npm install`)
- `npx cap open ios`
- sign your app at xcode (general tab)
- add `GoogleService-Info.plist` to the app folder in xcode
- enable debug view

1. In Xcode, select Product > Scheme > Edit scheme
2. Select Run from the left menu
3. Select the Arguments tab
4. In the Arguments Passed On Launch section, add `-FIRAnalyticsDebugEnabled`

> Tip: every time you change a native code you may need to clean up the cache (Product > Clean build folder) and then run the app again.

## Android setup

- `ionic start my-cap-app --capacitor`
- `cd my-cap-app`
- `npm install --save capacitor-analytics`
- `mkdir www && touch www/index.html`
- `npx cap add android`
- `npx cap sync android` (every time you run `npm install`)
- `npx cap open android`
- add `google-services.json` to your `android/app` folder
- `[extra step]` in android case we need to tell Capacitor to initialise the plugin:

> on your `MainActivity.java` file add `import io.stewan.capacitor.analytics.AnalyticsPlugin;` and then inside the init callback `add(AnalyticsPlugin.class);`

Now you should be set to go. Try to run your client using `ionic cap run android --livereload --address=0.0.0.0`.

> Tip: every time you change a native code you may need to clean up the cache (Build > Clean Project | Build > Rebuild Project) and then run the app again.

## Sample app

https://github.com/stewwan/capacitor-analytics-demo

## You may also like

- [capacitor-fcm](https://github.com/stewwan/capacitor-fcm)
- [capacitor-media](https://github.com/stewwan/capacitor-media)
- [capacitor-intercom](https://github.com/stewwan/capacitor-intercom)
- [capacitor-twitter](https://github.com/stewwan/capacitor-twitter)

Cheers 🍻

Follow me [@Twitter](https://twitter.com/StewanSilva)

## License

MIT
