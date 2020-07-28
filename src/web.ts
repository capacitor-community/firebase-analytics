import { WebPlugin } from "@capacitor/core";

import { FirebaseAnalyticsPlugin, FirebaseInitOptions } from "./definitions";

declare var window: any;

export class FirebaseAnalyticsWeb extends WebPlugin
  implements FirebaseAnalyticsPlugin {
  private not_supported_mssg = "This method is not supported";
  private options_missing_mssg = "Firebase options are missing";
  private duplicate_app_mssg = "Firebase app already exists";
  private analytics_missing_mssg =
    "Firebase analytics is not initialized. Make sure initializeFirebase() is called once";

  public readonly ready: Promise<any>;
  private readyResolver: Function;
  private analyticsRef: any;

  private scripts = [
    {
      key: "firebase-app",
      src: "https://www.gstatic.com/firebasejs/7.15.4/firebase-app.js",
    },
    {
      key: "firebase-ac",
      src: "https://www.gstatic.com/firebasejs/7.15.4/firebase-analytics.js",
    },
  ];

  constructor() {
    super({
      name: "FirebaseAnalytics",
      platforms: ["web"],
    });

    this.ready = new Promise((resolve) => (this.readyResolver = resolve));
    this.configure();
  }

  /**
   * Configure and Initialize FirebaseApp if not present
   * @param options - web app's Firebase configuration
   * @returns firebase analytics object reference
   * Platform: Web
   */
  initializeFirebase(options: FirebaseInitOptions): Promise<any> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (this.hasFirebaseInitialized()) {
        reject(this.duplicate_app_mssg);
        return;
      }

      if (!options) {
        reject(this.options_missing_mssg);
        return;
      }

      const app = window.firebase.initializeApp(options);
      this.analyticsRef = app.analytics();
      resolve(this.analyticsRef);
    });
  }

  /**
   * Sets the user ID property.
   * @param options - userId: unique identifier of the user to log
   * Platform: Web/Android/iOS
   */
  setUserId(options: { userId: string }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (!this.analyticsRef) {
        reject(this.analytics_missing_mssg);
        return;
      }

      const { userId } = options || { userId: undefined };

      if (!userId) {
        reject("userId property is missing");
        return;
      }

      this.analyticsRef.setUserId(userId);
      resolve();
    });
  }

  /**
   * Sets a user property to a given value.
   * @param options - name: The name of the user property to set.
   *                  value: The value of the user property.
   * Platform: Web/Android/iOS
   */
  setUserProperty(options: { name: string; value: string }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (!this.analyticsRef) {
        reject(this.analytics_missing_mssg);
        return;
      }

      const { name, value } = options || { name: undefined, value: undefined };

      if (!name) {
        reject("name property is missing");
        return;
      }

      if (!value) {
        reject("value property is missing");
        return;
      }

      let property: any = {};
      property[name] = value;
      this.analyticsRef.setUserProperties(property);
      resolve();
    });
  }

  /**
   * Retrieves the app instance id from the service.
   * @returns - instanceId: current instance if of the app
   * Platform: Web/Android/iOS
   */
  getAppInstanceId(): Promise<{ instanceId: string }> {
    return new Promise((resolve, _reject) => resolve);
  }

  /**
   * Sets the current screen name, which specifies the current visual context in your app.
   * @param options - screenName: the activity to which the screen name and class name apply.
   *                  nameOverride: the name of the current screen. Set to null to clear the current screen name.
   * Platform: Android/iOS
   */
  setScreenName(_options: {
    screenName: string;
    nameOverride: string;
  }): Promise<void> {
    return new Promise((resolve, _reject) => resolve);
  }

  /**
   * Clears all analytics data for this app from the device and resets the app instance id.
   * Platform: Android/iOS
   */
  reset(): Promise<void> {
    return new Promise((resolve, _reject) => resolve);
  }

  /**
   * Logs an app event.
   * @param options - name: unique name of the event
   *                  params: the map of event parameters.
   * Platform: Web/Android/iOS
   */
  logEvent(options: { name: string; params: object }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (!this.analyticsRef) {
        reject(this.analytics_missing_mssg);
        return;
      }

      const { name, params } = options || {
        name: undefined,
        params: undefined,
      };

      if (!name) {
        reject("name property is missing");
        return;
      }

      this.analyticsRef.logEvent(name, params);
      resolve();
    });
  }

  /**
   * Sets whether analytics collection is enabled for this app on this device.
   * @param options - enabled: boolean true/false to enable/disable logging
   * Platform: Web/Android/iOS
   */
  setCollectionEnabled(options: { enabled: boolean }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (!this.analyticsRef) {
        reject(this.analytics_missing_mssg);
        return;
      }

      const { enabled } = options || { enabled: false };
      this.analyticsRef.setAnalyticsCollectionEnabled(enabled);
      resolve();
    });
  }

  /**
   * Sets the duration of inactivity that terminates the current session.
   * @param options - duration: duration of inactivity
   * Platform: Android/iOS
   */
  setSessionTimeoutDuration(_options: { duration: number }): Promise<void> {
    return new Promise((_resolve, reject) => {
      reject(this.not_supported_mssg);
    });
  }

  /**
   * Returns analytics reference object
   */
  remoteConfig() {
    return this.analyticsRef;
  }

  enable(): Promise<void> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (!this.analyticsRef) {
        reject(this.analytics_missing_mssg);
        return;
      }

      this.analyticsRef.setAnalyticsCollectionEnabled(true);
      resolve();
    });
  }

  disable(): Promise<void> {
    return new Promise(async (resolve, reject) => {
      await this.ready;

      if (!this.analyticsRef) {
        reject(this.analytics_missing_mssg);
        return;
      }

      this.analyticsRef.setAnalyticsCollectionEnabled(false);
      resolve();
    });
  }

  /**
   * Ready resolver to check and load firebase analytics
   */
  private async configure() {
    try {
      await this.loadScripts();

      if (
        window.firebase &&
        window.firebase.analytics &&
        this.hasFirebaseInitialized()
      ) {
        this.analyticsRef = window.firebase.analytics();
      }
    } catch (error) {
      throw error;
    }

    const interval = setInterval(() => {
      if (!window.firebase) {
        return;
      }
      clearInterval(interval);
      this.readyResolver();
    }, 50);
  }

  /**
   * Check for existing loaded script and load new scripts
   */
  private loadScripts() {
    const firebaseAppScript = this.scripts[0];
    const firebaseAnalyticsScript = this.scripts[1];

    return new Promise(async (resolve, _reject) => {
      const scripts = this.scripts.map((script) => script.key);
      if (
        document.getElementById(scripts[0]) &&
        document.getElementById(scripts[1])
      ) {
        return resolve();
      }

      await this.loadScript(firebaseAppScript.key, firebaseAppScript.src);
      await this.loadScript(
        firebaseAnalyticsScript.key,
        firebaseAnalyticsScript.src
      );
      resolve();
    });
  }

  /**
   * Loaded single script with provided id and source
   * @param id - unique identifier of the script
   * @param src - source of the script
   */
  private loadScript(id: string, src: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const file = document.createElement("script");
      file.type = "text/javascript";
      file.src = src;
      file.id = id;
      file.onload = resolve;
      file.onerror = reject;
      document.querySelector("head").appendChild(file);
    });
  }

  /**
   * Returns true/false if firebase object reference exists inside window
   */
  private hasFirebaseInitialized() {
    if (!window.firebase) {
      return false;
    }

    const firebaseApps = window.firebase.apps;
    if (firebaseApps && firebaseApps.length === 0) {
      return false;
    }

    return true;
  }
}

const FirebaseAnalytics = new FirebaseAnalyticsWeb();

export { FirebaseAnalytics };

import { registerWebPlugin } from "@capacitor/core";
registerWebPlugin(FirebaseAnalytics);
