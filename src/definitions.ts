declare module '@capacitor/core' {
  interface PluginRegistry {
    AnalyticsPlugin: AnalyticsProtocol;
  }
}

export interface ScreenParams {
  name: string;
  class?: string;
}
export interface UserIDParams {
  value: string;
}
export interface UserPropParams {
  key: string;
  value: string;
}
export interface LogEventParams {
  name: string;
  params?: object;
}

export interface AnalyticsProtocol {
  instance(): Promise<{ id: string }>;
  reset(): Promise<void>;
  setScreen(options: ScreenParams): Promise<void>;
  setUserID(options: UserIDParams): Promise<void>;
  setUserProp(options: UserPropParams): Promise<void>;
  logEvent(options: LogEventParams): Promise<void>;
}
