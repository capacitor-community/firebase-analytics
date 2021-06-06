#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

CAP_PLUGIN(FirebaseAnalytics, "FirebaseAnalytics",
           CAP_PLUGIN_METHOD(setUserId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setUserProperty, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getAppInstanceId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(reset, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(logEvent, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setCollectionEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setSessionTimeoutDuration, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(enable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(disable, CAPPluginReturnPromise);
)
