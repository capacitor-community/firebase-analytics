package io.stewan.capacitor.analytics;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.cordova.MockCordovaWebViewImpl;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * Created by Stewan Silva on 07/05/2019.
 * 
 * Please read the Capacitor Android Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/android
 */

@NativePlugin(
        permissions = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WAKE_LOCK
        }
)
public class AnalyticsPlugin extends Plugin {

    private FirebaseAnalytics analytics;

    public void load() {
        analytics = FirebaseAnalytics.getInstance(getContext());
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

    @PluginMethod()
    public void instance(PluginCall call) {
        try {
            String id = analytics.getAppInstanceId().toString();
            JSObject response = new JSObject();
            response.put("id", id);
            call.success(response);
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage(), e);
        }
    }

    @PluginMethod()
    public void reset(PluginCall call) {
        try {
            analytics.resetAnalyticsData();
            call.success();
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage(), e);
        }
    }

    @PluginMethod()
    public void setUserID(PluginCall call) {
        try {
            final String value = call.getString("value");
            if (value != null) {
                analytics.setUserId(value);
                call.success();
            } else {
                call.reject("missing value for UserID");
            }
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage(), e);
        }
    }

    @PluginMethod()
    public void setScreen(PluginCall call) {
        try {
            final String screenName = call.getString("name");
            final String className = call.getString("class", null);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    analytics.setCurrentScreen(getActivity(), screenName, className);
                }
            });
            call.success();
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage(), e);
        }
    }

    @PluginMethod()
    public void setUserProp(PluginCall call) throws JSONException {
        try {
            final String key = call.getString("key");
            final String value = call.getString("value");
            if (key != null && value != null) {
                analytics.setUserProperty(key, value);
                call.success();
            } else {
                call.reject("missing key:value");
            }
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage(), e);
        }
    }


    @PluginMethod()
    public void logEvent(PluginCall call) {
        try {
            final String name = call.getString("name", null);
            JSObject data = call.getData();
            final JSONObject params = data.optJSONObject("params");
            if (name != null) {
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
                            call.reject("Value for key " + key + " is not one of String, Integer, Double or Long");
                        }
                    }
                } else {
                    call.reject("missing params");
                }
                analytics.logEvent(name, bundle);
                call.success();
            } else {
                call.reject("missing name");
            }
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage(), e);
        }
    }

}
