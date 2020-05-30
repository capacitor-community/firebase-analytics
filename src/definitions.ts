declare module "@capacitor/core" {
  interface PluginRegistry {
    FirebaseAnalytics: FirebaseAnalyticsPlugin;
  }
}

export interface FirebaseAnalyticsPlugin {
  setUserId(options: { userId: string }): Promise<void>;
  setUserProperty(options: { name: string; value: string; }): Promise<void>;
  getAppInstanceId(): Promise<{ instanceId: string; }>;
  setScreenName(options: { screenName: string; nameOverride: string; }): Promise<void>;
  reset(): Promise<void>;
  logEvent(options: { name: string; params: object; }): Promise<void>;
}
