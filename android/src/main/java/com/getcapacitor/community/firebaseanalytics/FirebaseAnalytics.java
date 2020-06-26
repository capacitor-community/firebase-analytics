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

  @Override
  public void load() {
    super.load();

    // Obtain the FirebaseAnalytics instance.
    mFirebaseAnalytics =
      com.google.firebase.analytics.FirebaseAnalytics.getInstance(
        this.bridge.getActivity()
      );
  }

  @PluginMethod
  public void setUserId(PluginCall call) {
    try {
      if (call.hasOption("userId")) {
        String userId = call.getString("userId");

        mFirebaseAnalytics.setUserId(userId);
      } else {
        call.reject("userId is missing");
      }
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  @PluginMethod
  public void setUserProperty(PluginCall call) {
    try {
      if (call.hasOption("name") && call.hasOption("value")) {
        String name = call.getString("name");
        String value = call.getString("value");

        mFirebaseAnalytics.setUserProperty(name, value);
        call.success();
      } else {
        call.reject("name or value is missing");
      }
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  @PluginMethod
  public void getAppInstanceId(PluginCall call) {
    try {
      String instanceId = mFirebaseAnalytics.getAppInstanceId().toString();

      if (instanceId.isEmpty()) {
        call.error("instanceId is missing");
      } else {
        JSObject result = new JSObject();
        result.put("instanceId", instanceId);
        call.success(result);
      }
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  @PluginMethod
  public void setScreenName(PluginCall call) {
    try {
      if (call.hasOption("screenName")) {
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
              }
            }
          );
        call.success();
      } else {
        call.reject("userId is missing");
      }
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  @PluginMethod
  public void reset(PluginCall call) {
    try {
      mFirebaseAnalytics.resetAnalyticsData();
      call.success();
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  @PluginMethod
  public void logEvent(PluginCall call) {
    try {
      if (call.hasOption("name")) {
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
        } else {
          call.reject("key `params` is missing");
        }

        mFirebaseAnalytics.logEvent(name, bundle);
        call.success();
      } else {
        call.reject("name is missing");
      }
    } catch (Exception ex) {
      call.error(ex.getLocalizedMessage());
    }
  }

  @PluginMethod()
  public void enable(PluginCall call) {
      try {
          analytics.setAnalyticsCollectionEnabled(true);
          call.success();
      } catch (Exception e) {
          call.reject(e.getLocalizedMessage(), e);
      }
  }
  
  @PluginMethod()
  public void disable(PluginCall call) {
      try {
          analytics.setAnalyticsCollectionEnabled(false);
          call.success();
      } catch (Exception e) {
          call.reject(e.getLocalizedMessage(), e);
      }
  }
}
