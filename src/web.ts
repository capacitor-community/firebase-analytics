import { WebPlugin } from '@capacitor/core';
import { AnalyticsProtocol } from './definitions';

export class AnalyticsPluginWeb extends WebPlugin /*implements AnalyticsProtocol*/ {
  constructor() {
    super({
      name: 'AnalyticsPlugin',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const AnalyticsPlugin = new AnalyticsPluginWeb();

export { AnalyticsPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(AnalyticsPlugin);
