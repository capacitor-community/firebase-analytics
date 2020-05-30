import { Plugins } from '@capacitor/core';
import { FirebaseAnalyticsPlugin } from './definitions';

const { FirebaseAnalytics: FBAnalytics } = Plugins;

export class FirebaseAnalytics implements FirebaseAnalyticsPlugin {
    setUserId(options: { userId: string; }): Promise<void> {
        return FBAnalytics.setUserId(options);
    }
    setUserProperty(options: { name: string; value: string; }): Promise<void> {
        return FBAnalytics.setUserProperty(options);
    }
    getAppInstanceId(): Promise<{ instanceId: string; }> {
        return FBAnalytics.getAppInstanceId();
    }
    setScreenName(options: { screenName: string; nameOverride: string; }): Promise<void> {
        return FBAnalytics.setScreenName(options);
    }
    reset(): Promise<void> {
        return FBAnalytics.reset();
    }
    logEvent(options: { name: string; params: object; }): Promise<void> {
        return FBAnalytics.logEvent(options);
    }

}