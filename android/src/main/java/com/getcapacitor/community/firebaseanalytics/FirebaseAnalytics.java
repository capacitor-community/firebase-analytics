package com.getcapacitor.community.firebaseanalytics;

import android.Manifest;
import android.os.Bundle;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import java.util.Iterator;
import org.json.JSONObject;

@NativePlugin(
  permissions = {
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.INTERNET,
    Manifest.permission.WAKE_LOCK,
  }
)
public class FirebaseAnalytics extends Plugin {
  private com.google.firebase.analytics.FirebaseAnalytics mFirebaseAnalytics;

  private final String MISSING_REF_MSSG =
    "Firebase analytics is not initialized";

  @Override
  public void load() {
    super.load();

    // Obtain the FirebaseAnalytics instance.
    mFirebaseAnalytics =
      com.google.firebase.analytics.FirebaseAnalytics.getInstance(
        this.bridge.getActivity()
      );
  }

  /**
   * Sets the user ID property.
   * @param call - userId: unique identifier of the user to log
   */
  @PluginMethod
  public void setUserId(PluginCall call) {
    try {
      if (mFirebaseAnalytics == null) {
        call.error(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("userId")) {
        call.error("userId property is missing");
        return;
      }

      String userId = call.getString("userId");
      mFirebaseAnalytics.setUserId(userId);
      call.success();
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  /**
   * Sets a user property to a given value.
   * @param call - name: The name of the user property to set.
   *               value: The value of the user property.
   */
  @PluginMethod
  public void setUserProperty(PluginCall call) {
    try {
      if (mFirebaseAnalytics == null) {
        call.error(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("name")) {
        call.error("name property is missing");
        return;
      }

      if (!call.hasOption("value")) {
        call.error("value property is missing");
        return;
      }

      String name = call.getString("name");
      String value = call.getString("value");

      mFirebaseAnalytics.setUserProperty(name, value);
      call.success();
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  /**
   * Retrieves the app instance id from the service.
   * @param call - instanceId: current instance if of the app
   */
  @PluginMethod
  public void getAppInstanceId(PluginCall call) {
    try {
      if (mFirebaseAnalytics == null) {
        call.error(MISSING_REF_MSSG);
        return;
      }

      String instanceId = mFirebaseAnalytics.getAppInstanceId().toString();

      if (instanceId.isEmpty()) {
        call.error("failed to obtain app instance id");
        return;
      }

      JSObject result = new JSObject();
      result.put("instanceId", instanceId);
      call.success(result);
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  /**
   * Sets the current screen name, which specifies the current visual context in your app.
   * @param call - screenName: the activity to which the screen name and class name apply.
   *               nameOverride: the name of the current screen. Set to null to clear the current screen name.
   */
  @PluginMethod
  public void setScreenName(final PluginCall call) {
    try {
      if (mFirebaseAnalytics == null) {
        call.error(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("screenName")) {
        call.error("screenName property is missing");
        return;
      }

      final String screenName = call.getString("screenName");
      final String nameOverride = call.getString("nameOverride", null);

      bridge
        .getActivity()
        .runOnUiThread(
          new Runnable() {

            @Override
            public void run() {
              mFirebaseAnalytics.setCurrentScreen(
                bridge.getActivity(),
                screenName,
                nameOverride
              );
              call.success();
            }
          }
        );
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  /**
   * Clears all analytics data for this app from the device and resets the app instance id.
   * @param call
   */
  @PluginMethod
  public void reset(PluginCall call) {
    try {
      if (mFirebaseAnalytics == null) {
        call.error(MISSING_REF_MSSG);
        return;
      }

      mFirebaseAnalytics.resetAnalyticsData();
      call.success();
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  /**
   * Logs an app event.
   * @param call - name: unique name of the event
   *               params: the map of event parameters.
   */
  @PluginMethod
  public void logEvent(PluginCall call) {
    try {
      if (mFirebaseAnalytics == null) {
        call.error(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("name")) {
        call.error("name property is missing");
        return;
      }

      String name = call.getString("name");
      JSObject data = call.getData();
      JSONObject params = data.getJSObject("params");
      Bundle bundle = new Bundle();

      if (params != null) {
        Iterator<String> keys = params.keys();

        while (keys.hasNext()) {
          String key = keys.next();
          Object value = params.get(key);

          if (value instanceof String) {
            bundle.putString(key, (String) value);
          } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
          } else if (value instanceof Double) {
            bundle.putDouble(key, (Double) value);
          } else if (value instanceof Long) {
            bundle.putLong(key, (Long) value);
          } else {
            call.reject("value for " + key + " is missing");
          }
        }
      }

      mFirebaseAnalytics.logEvent(name, bundle);
      call.success();
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  /**
   * Sets whether analytics collection is enabled for this app on this device.
   * @param call - enabled: boolean true/false to enable/disable logging
   */
  @PluginMethod
  public void setCollectionEnabled(PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.error(MISSING_REF_MSSG);
      return;
    }

    boolean enabled = call.getBoolean("enabled", false);

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(enabled);
    call.success();
  }

  /**
   * Deprecated: use setCollectionEnabled() instead
   * Enable analytics collection for this app on this device.
   * @param call - enabled: boolean true/false to enable/disable logging
   */
  @Deprecated
  @PluginMethod
  public void enable(PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.error(MISSING_REF_MSSG);
      return;
    }

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    call.success();
  }

  /**
   * Deprecated: use setCollectionEnabled() instead
   * Disable analytics collection for this app on this device.
   * @param call
   */
  @Deprecated
  @PluginMethod
  public void disable(PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.error(MISSING_REF_MSSG);
      return;
    }

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(false);
    call.success();
  }

  /**
   * Sets the duration of inactivity that terminates the current session.
   * @param call: options - duration: duration of inactivity
   */
  @PluginMethod
  public void setSessionTimeoutDuration(PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.error(MISSING_REF_MSSG);
      return;
    }

    int duration = call.getInt("duration", 1800);

    mFirebaseAnalytics.setSessionTimeoutDuration(duration);
    call.success();
  }
}
