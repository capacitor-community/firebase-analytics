import { Plugins } from '@capacitor/core';
import {
  AnalyticsProtocol,
  ScreenParams,
  UserIDParams,
  UserPropParams,
  LogEventParams
} from './definitions';

const { AnalyticsPlugin } = Plugins;

export class Analytics implements AnalyticsProtocol {
  enable(): Promise<void> {
    return AnalyticsPlugin.enable();
  }

  disable(): Promise<void> {
    return AnalyticsPlugin.disable();
  }

  instance(): Promise<{ id: string }> {
    return AnalyticsPlugin.instance();
  }

  reset(): Promise<void> {
    return AnalyticsPlugin.reset();
  }

  setScreen(options: ScreenParams): Promise<void> {
    return AnalyticsPlugin.setScreen(options);
  }

  setUserID(options: UserIDParams): Promise<void> {
    return AnalyticsPlugin.setUserID(options);
  }

  setUserProp(options: UserPropParams): Promise<void> {
    return AnalyticsPlugin.setUserProp(options);
  }

  logEvent(options: LogEventParams): Promise<void> {
    return AnalyticsPlugin.logEvent(options);
  }
}
