import Foundation
import Capacitor
import FirebaseCore
import FirebaseAnalytics

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/ios
 */
@objc(FirebaseAnalytics)
public class FirebaseAnalytics: CAPPlugin {
    
    var firebaseAnalytics: FirebaseAnalytics?
    
    public override func load() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }
    
    @objc func setUserId(_ call: CAPPluginCall) {
        if call.hasOption("userId") {
            let userId = call.getString("userId")
            
            Analytics.setUserID(userId)
            call.success()
        } else {
            call.reject("userId is missing")
        }
    }
    
    @objc func setUserProperty(_ call: CAPPluginCall) {
        if call.hasOption("name") && call.hasOption("value") {
            let name = call.getString("name")
            let value = call.getString("value")
            
            Analytics.setUserProperty(value, forName: name!)
            call.success()
        } else {
            call.reject("name or value is missing")
        }
    }
    
    @objc func getAppInstanceId(_ call: CAPPluginCall) {
        let instanceId = Analytics.appInstanceID()
        call.success([
            "instanceId": instanceId
        ])
    }
    
    @objc func setScreenName(_ call: CAPPluginCall) {
        if call.hasOption("screenName") {
            let screenName = call.getString("screenName")
            let nameOverride = call.getString("nameOverride") ?? nil
            
            Analytics.setScreenName(screenName, screenClass: nameOverride)
            call.success()
        } else {
            call.reject("screenName is missing")
        }
    }
    
    @objc func reset(_ call: CAPPluginCall) {
        Analytics.resetAnalyticsData()
        call.success()
    }
    
    @objc func logEvent(_ call: CAPPluginCall) {
        if call.hasOption("name") {
            let name = call.getString("name")
            let params = call.getObject("params") ?? nil
            
            Analytics.logEvent(name!, parameters: params)
            call.success()
        } else {
            call.reject("name is missing")
        }
    }

    @objc func enable(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            Analytics.setAnalyticsCollectionEnabled(true)
            call.success()
        }
    }
  
    @objc func disable(_ call: CAPPluginCall) {
            DispatchQueue.main.async {
            Analytics.setAnalyticsCollectionEnabled(false)
            call.success()
        }
    }
}
