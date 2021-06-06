declare module "@capacitor/core" {
  interface PluginRegistry {
    FirebaseAnalytics: FirebaseAnalyticsPlugin;
  }
}

export interface FirebaseAnalyticsPlugin {
  initializeFirebase(options: FirebaseInitOptions): Promise<any>;
  setUserId(options: { userId: string }): Promise<void>;
  setUserProperty(options: { name: string; value: string }): Promise<void>;
  getAppInstanceId(): Promise<{ instanceId: string }>;
  reset(): Promise<void>;
  logEvent(options: { name: string; params: object }): Promise<void>;
  setCollectionEnabled(options: { enabled: boolean }): Promise<void>;
  setSessionTimeoutDuration(options: { duration: number }): Promise<void>;
  enable(): Promise<void>;
  disable(): Promise<void>;
}

export interface FirebaseInitOptions {
  apiKey: string;
  authDomain: string;
  databaseURL: string;
  projectId: string;
  storageBucket: string;
  messagingSenderId: string;
  appId: string;
  measurementId: string;
}
