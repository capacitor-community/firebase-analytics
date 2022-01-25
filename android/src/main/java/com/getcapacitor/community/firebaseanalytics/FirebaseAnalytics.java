package com.getcapacitor.community.firebaseanalytics;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(
  name = "FirebaseAnalytics",
  permissions = {
    @Permission(
      strings = { Manifest.permission.ACCESS_NETWORK_STATE },
      alias = "network"
    ),
    @Permission(strings = { Manifest.permission.INTERNET }, alias = "internet"),
    @Permission(
      strings = { Manifest.permission.WAKE_LOCK },
      alias = "wakelock"
    ),
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
        bridge.getActivity()
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
        call.reject(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("userId")) {
        call.reject("userId property is missing");
        return;
      }

      String userId = call.getString("userId");
      mFirebaseAnalytics.setUserId(userId);
      call.resolve();
    } catch (Exception ex) {
      call.reject(ex.getLocalizedMessage());
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
        call.reject(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("name")) {
        call.reject("name property is missing");
        return;
      }

      if (!call.hasOption("value")) {
        call.reject("value property is missing");
        return;
      }

      String name = call.getString("name");
      String value = call.getString("value");

      mFirebaseAnalytics.setUserProperty(name, value);
      call.resolve();
    } catch (Exception ex) {
      call.reject(ex.getLocalizedMessage());
    }
  }

  /**
   * Retrieves the app instance id from the service.
   * @param call - instanceId: current instance if of the app
   */
  @PluginMethod
  public void getAppInstanceId(final PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.reject(MISSING_REF_MSSG);
      return;
    }
    Task<String> task = mFirebaseAnalytics.getAppInstanceId();
    task.addOnCompleteListener(new OnCompleteListener<String>() {
        @Override
        public void onComplete(@NonNull Task<String> task) {
            if (task.isSuccessful()) {
              String instanceId = task.getResult();
              if (instanceId != null && instanceId.isEmpty()) {
                call.reject("failed to obtain app instance id");
              } else {
                JSObject result = new JSObject();
                result.put("instanceId", instanceId);
                call.resolve(result);
              }
            } else {
                Exception exception = task.getException();
                call.reject(exception.getLocalizedMessage());
            }
        }
    });
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
        call.reject(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("screenName")) {
        call.reject("screenName property is missing");
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
              Bundle bundle = new Bundle();
              bundle.putString(
                com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME,
                screenName
              );
              bundle.putString(
                com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS,
                nameOverride
              );
              mFirebaseAnalytics.logEvent(
                com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW,
                bundle
              );
              call.resolve();
            }
          }
        );
    } catch (Exception ex) {
      call.reject(ex.getLocalizedMessage());
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
        call.reject(MISSING_REF_MSSG);
        return;
      }

      mFirebaseAnalytics.resetAnalyticsData();
      call.resolve();
    } catch (Exception ex) {
      call.reject(ex.getLocalizedMessage());
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
        call.reject(MISSING_REF_MSSG);
        return;
      }

      if (!call.hasOption("name")) {
        call.reject("name property is missing");
        return;
      }

      String name = call.getString("name");
      JSONObject params = call.getData().getJSObject("params");
      mFirebaseAnalytics.logEvent(name, params != null ? FirebaseAnalytics.convertJsonToBundle(params) : null);
      call.resolve();
    } catch (Exception ex) {
      call.reject(ex.getLocalizedMessage());
    }
  }

  /**
   * Sets whether analytics collection is enabled for this app on this device.
   * @param call - enabled: boolean true/false to enable/disable logging
   */
  @PluginMethod
  public void setCollectionEnabled(PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.reject(MISSING_REF_MSSG);
      return;
    }

    boolean enabled = call.getBoolean("enabled", false);

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(enabled);
    call.resolve();
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
      call.reject(MISSING_REF_MSSG);
      return;
    }

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    call.resolve();
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
      call.reject(MISSING_REF_MSSG);
      return;
    }

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(false);
    call.resolve();
  }

  /**
   * Sets the duration of inactivity that terminates the current session.
   * @param call: options - duration: duration of inactivity
   */
  @PluginMethod
  public void setSessionTimeoutDuration(PluginCall call) {
    if (mFirebaseAnalytics == null) {
      call.reject(MISSING_REF_MSSG);
      return;
    }

    int duration = call.getInt("duration", 1800);

    mFirebaseAnalytics.setSessionTimeoutDuration(duration);
    call.resolve();
  }

  public static Bundle convertJsonToBundle(JSONObject json) {
    Bundle bundle = new Bundle();
    if (json == null || json.length() == 0) return bundle;

    Iterator<String> iterator = json.keys();
    while (iterator.hasNext()) {
      String key = (String) iterator.next();
      try {
        Object value = json.get(key);
        if (value == null);
        else if (value instanceof String) bundle.putString(key, (String) value);
        else if (value instanceof Boolean) bundle.putBoolean(key, (Boolean) value);
        else if (value instanceof Integer) bundle.putInt(key, (Integer) value);
        else if (value instanceof Long) bundle.putLong(key, (Long) value);
        else if (value instanceof Float) bundle.putFloat(key, (Float) value);
        else if (value instanceof Double) bundle.putDouble(key, (Double) value);
        else if (value instanceof JSONObject) bundle.putBundle(key, FirebaseAnalytics.convertJsonToBundle((JSONObject) value));
        else if (value instanceof JSONArray) {
          JSONArray array = (JSONArray) value; 
          Object first = array.length() == 0 ? null : (Object) array.get(0);
          if (first == null);
          else if (first instanceof JSONObject) {
            Bundle[] items = new Bundle[array.length()];
            for (int i = 0; i < array.length(); i++) items[i] = FirebaseAnalytics.convertJsonToBundle(array.getJSONObject(i));
            bundle.putParcelableArray(key, items);
          } else if (first instanceof String) {
            String[] items = new String[array.length()];
            for (int i = 0; i < array.length(); i++) items[i] = array.getString(i);
            bundle.putStringArray(key, items);
          } else if (first instanceof Integer || first instanceof Float || first instanceof Double) {
            float[] items = new float[array.length()];
            for (int i = 0; i < array.length(); i++) {
              items[i] = ((Number) array.get(i)).floatValue();
            }
            bundle.putFloatArray(key, items);
          }
        }
      } catch (ClassCastException | JSONException e) {
        e.printStackTrace();
      }
    }

    return bundle;
  }
}
