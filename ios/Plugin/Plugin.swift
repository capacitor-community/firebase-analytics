import Foundation
import Capacitor
import FirebaseCore
import FirebaseAnalytics


@objc(FirebaseAnalytics)
public class FirebaseAnalytics: CAPPlugin {

    public override func load() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.success([
            "value": value
        ])
    }

    /// Sets the user ID property.
    /// - Parameter call: userId - unique identifier of the user to log
    @objc func setUserId(_ call: CAPPluginCall) {
        if !call.hasOption("userId") {
            call.error("userId property is missing")
            return
        }

        let userId = call.getString("userId")
        Analytics.setUserID(userId)
        call.success()
    }


    /// Sets a user property to a given value.
    /// - Parameter call: name - The name of the user property to set.
    ///                   value - The value of the user property.
    @objc func setUserProperty(_ call: CAPPluginCall) {
        if !call.hasOption("name") {
            call.error("name property is missing")
            return
        }

        if !call.hasOption("value") {
            call.error("value property is missing")
            return
        }

        let name = call.getString("name")
        let value = call.getString("value")

        Analytics.setUserProperty(value, forName: name!)
        call.success()
    }


    /// Retrieves the app instance id from the service.
    /// - Parameter call: instanceId - current instance if of the app
    @objc func getAppInstanceId(_ call: CAPPluginCall) {
        let instanceId = Analytics.appInstanceID()
        call.success([
            "instanceId": instanceId
        ])
    }


    /// Sets the current screen name, which specifies the current visual context in your app.
    /// - Parameter call: screenName - the activity to which the screen name and class name apply.
    ///                   nameOverride - the name of the current screen. Set to null to clear the current screen name.
    @objc func setScreenName(_ call: CAPPluginCall) {
        if !call.hasOption("screenName") {
            call.error("screenName property is missing")
            return
        }

        let screenName = call.getString("screenName")
        let nameOverride = call.getString("nameOverride") ?? nil

        DispatchQueue.main.async {
            Analytics.logEvent(AnalyticsEventScreenView,
                parameters: [AnalyticsParameterScreenName: screenName,
                             AnalyticsParameterScreenClass: nameOverride])
        }
        call.success()
    }


    /// Clears all analytics data for this app from the device and resets the app instance id.
    @objc func reset(_ call: CAPPluginCall) {
        Analytics.resetAnalyticsData()
        call.success()
    }


    /// Logs an app event.
    /// - Parameter call: name - unique name of the event
    ///                   params - the map of event parameters.
    @objc func logEvent(_ call: CAPPluginCall) {

        /// Name is a required argument to logEvent()
        guard let name = call.getString("name"), !name.isEmpty else {
            call.error("Event name is required and can't be empty")
            return
        }

        /// logEvent() expects `nil` when there are no parameters
        guard var params = call.getObject("params"), !params.isEmpty else {
            Analytics.logEvent(name, parameters: nil)
            call.success()
            return
        }

        /// FirebaseAnalytics silently converts any item quantity that is not an
        /// integer to zero, this includes any NSNumber or string value passed
        /// as an option to CAPPluginCall.
        if var items = params["items"] as? NSArray as? [[String:Any]] {
            for (idx, item) in items.enumerated() {
                if let quantity = item["quantity"] {
                    guard let intVal = quantity as? Int else {
                        call.error("Item quantity must be specified as an integer value")
                        return
                    }
                    items[idx]["quantity"] = intVal
                }
            }
            params["items"] = items
        }

        Analytics.logEvent(name, parameters: params)
        call.success()
    }


    /// Sets whether analytics collection is enabled for this app on this device.
    /// - Parameter call: enabled - boolean true/false to enable/disable logging
    @objc func setCollectionEnabled(_ call: CAPPluginCall) {
        let enabled: Bool = (call.hasOption("enabled") ? call.getBool("enabled") : false)!

        Analytics.setAnalyticsCollectionEnabled(enabled)
        call.success()
    }


    /// Sets the duration of inactivity that terminates the current session.
    /// - Parameter call: duration - duration of inactivity
    @objc func setSessionTimeoutDuration(_ call: CAPPluginCall) {
        let duration = call.getInt("duration") ?? 1800

        Analytics.setSessionTimeoutInterval(TimeInterval(duration))
        call.success()
    }

    /// Deprecated - use setCollectionEnabled instead
    /// Enable analytics collection for this app on this device.
    /// - Parameter call
    @available(*, deprecated, renamed: "setCollectionEnabled")
    @objc func enable(_ call: CAPPluginCall) {
        Analytics.setAnalyticsCollectionEnabled(true)
        call.success()
    }

    /// Deprecated - use setCollectionEnabled instead
    /// Disable analytics collection for this app on this device.
    /// - Parameter call
    @available(*, deprecated, renamed: "setCollectionEnabled")
    @objc func disable(_ call: CAPPluginCall) {
        Analytics.setAnalyticsCollectionEnabled(false)
        call.success()
    }
}
