import { WebPlugin } from "@capacitor/core";

import { FirebaseAnalyticsPlugin, FirebaseInitOptions } from "./definitions";

declare var window: any;

export class FirebaseAnalyticsWeb extends WebPlugin
  implements FirebaseAnalyticsPlugin {
  private not_supported_mssg = "This method is not supported";
  private options_missing_mssg = "Firebase options are missing";
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
    this.loadScripts();
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
  get remoteConfig() {
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
  
  // 
  // Note: The methods below are common to all Firebase capacitor plugins. Best to create `capacitor-community / firebase-common`,
  // move the code there and add it as module to all FB plugins.
  // 

  /**
   * Configure and Initialize FirebaseApp if not present
   * @param options - web app's Firebase configuration
   * @returns firebase analytics object reference
   * Platform: Web
   */
  async initializeFirebase(options: FirebaseInitOptions): Promise<any> {
    if (!options) 
      throw new Error(this.options_missing_mssg);

    await this.firebaseObjectReadyPromise();
    const app = this.isFirebaseInitialized() ? window.firebase : window.firebase.initializeApp(options);
    this.analyticsRef = app.analytics();
    this.readyResolver();
    return this.analyticsRef;
  }

  /**
   * Check for existing loaded script and load new scripts
   */
  private loadScripts(): Promise<Array<any>> {
    return Promise.all( this.scripts.map( s => this.loadScript(s.key, s.src) ) );
  }

  /**
   * Loaded single script with provided id and source
   * @param id - unique identifier of the script
   * @param src - source of the script
   */
  private loadScript(id: string, src: string): Promise<any> {
    return new Promise((resolve, reject) => {
      if (document.getElementById(id)){
        resolve(null);
      } else {
        const file = document.createElement("script");
        file.type = "text/javascript";
        file.src = src;
        file.id = id;
        file.onload = resolve;
        file.onerror = reject;
        document.querySelector("head").appendChild(file);  
      }
    });
  }

  private firebaseObjectReadyPromise(): Promise<void> {
    var tries = 100;
    return new Promise((resolve, reject) => {
      const interval = setInterval(() => {
        if (window.firebase?.analytics) {
          clearInterval(interval);
          resolve( null );
        } else if (tries-- <= 0) {
          reject("Firebase fails to load");
        }
      }, 50);
    } );
  }

  private isFirebaseInitialized() {
    const length = window.firebase?.apps?.length;
    return length && length > 0;
  }
}

const FirebaseAnalytics = new FirebaseAnalyticsWeb();

export { FirebaseAnalytics };

import { registerWebPlugin } from "@capacitor/core";
registerWebPlugin(FirebaseAnalytics);
