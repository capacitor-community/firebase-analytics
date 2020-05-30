import { WebPlugin } from '@capacitor/core';
import { FirebaseAnalyticsPlugin } from './definitions';

export class FirebaseAnalyticsWeb extends WebPlugin implements FirebaseAnalyticsPlugin {
  constructor() {
    super({
      name: 'FirebaseAnalytics',
      platforms: ['web']
    });
  }


  setUserId(options: { userId: string; }): Promise<void> {
    console.warn(options);
    throw new Error("Method not implemented.");
  }
  setUserProperty(options: { name: string; value: string; }): Promise<void> {
    console.warn(options);
    throw new Error("Method not implemented.");
  }

  getAppInstanceId(): Promise<{ instanceId: string; }> {
    throw new Error("Method not implemented.");
  }

  setScreenName(options: { screenName: string; nameOverride: string; }): Promise<void> {
    console.warn(options);
    throw new Error("Method not implemented.");
  }

  reset(): Promise<void> {
    throw new Error("Method not implemented.");
  }

  logEvent(options: { name: string; params: object; }): Promise<void> {
    console.warn(options);
    throw new Error("Method not implemented.");
  }

  
}

const FirebaseAnalytics = new FirebaseAnalyticsWeb();

export { FirebaseAnalytics };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(FirebaseAnalytics);
