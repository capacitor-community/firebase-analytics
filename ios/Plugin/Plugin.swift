import Foundation
import Capacitor
import FirebaseCore
import FirebaseAnalytics

/**
 *
 * Created by Stewan Silva on 07/05/2019.
 *
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/ios
 */
@objc(AnalyticsPlugin)
public class AnalyticsPlugin: CAPPlugin {
  
  public override func load() {
    if (FirebaseApp.app() == nil) {
      FirebaseApp.configure();
    }
    Analytics.setAnalyticsCollectionEnabled(true)
  }
  
  @objc func instance(_ call: CAPPluginCall) {
    DispatchQueue.main.async {
      call.success(["id": Analytics.appInstanceID()])
    }
  }
  
  @objc func reset(_ call: CAPPluginCall) {
    DispatchQueue.main.async {
      Analytics.resetAnalyticsData();
      call.success();
    }
  }
  
  @objc func setScreen(_ call: CAPPluginCall) {
    let screenName = call.getString("name");
    let screenClass = call.getString("class");
    
    if screenName != nil {
      DispatchQueue.main.async {
        Analytics.setScreenName(screenName, screenClass: screenClass);
        call.success();
      }
    } else {
      call.error("missing screen name")
      return
    }
  }
  
  @objc func setUserID(_ call: CAPPluginCall) {
    let value = call.getString("value");
    
    if value != nil {
      DispatchQueue.main.async {
        Analytics.setUserID(value);
        call.success();
      }
    } else {
      call.error("missing user id value")
      return
    }
  }
  
  @objc func setUserProp(_ call: CAPPluginCall) {
    let key = call.getString("key");
    let value = call.getString("value");
    
    if key != nil && value != nil {
      DispatchQueue.main.async {
        Analytics.setUserProperty(value, forName: key!);
        call.success();
      }
    } else {
      call.error("missing key/value")
      return
    }
  }
  
  @objc func logEvent(_ call: CAPPluginCall) {
    let name = call.getString("name");
    let params = call.getObject("params") ?? nil;
    
    if name != nil {
      DispatchQueue.main.async {
        Analytics.logEvent(name!, parameters: params);
        call.success();
      }
    } else {
      call.error("missing event name")
      return
    }
  }
}
