#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseAnalytics, "FirebaseAnalytics",
           CAP_PLUGIN_METHOD(setUserId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setUserProperty, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getAppInstanceId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setScreenName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(reset, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(logEvent, CAPPluginReturnPromise);
)
