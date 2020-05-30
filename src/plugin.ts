import { Plugins } from '@capacitor/core';
import { FirebaseAnalyticsPlugin as FBAnalyticsPlugin } from './definitions';

const { FirebaseAnalyticsPlugin } = Plugins;

export class FirebaseAnalytics implements FBAnalyticsPlugin {
    setUserId(options: { userId: string; }): Promise<void> {
        return FirebaseAnalyticsPlugin.setUserId(options);
    }
    setUserProperty(options: { name: string; value: string; }): Promise<void> {
        return FirebaseAnalyticsPlugin.setUserProperty(options);
    }
    getAppInstanceId(): Promise<{ instanceId: string; }> {
        return FirebaseAnalyticsPlugin.getAppInstanceId();
    }
    setScreenName(options: { screenName: string; nameOverride: string; }): Promise<void> {
        return FirebaseAnalyticsPlugin.setScreenName(options);
    }
    reset(): Promise<void> {
        return FirebaseAnalyticsPlugin.reset();
    }
    logEvent(options: { name: string; params: object; }): Promise<void> {
        return FirebaseAnalyticsPlugin.logEvent(options);
    }

}