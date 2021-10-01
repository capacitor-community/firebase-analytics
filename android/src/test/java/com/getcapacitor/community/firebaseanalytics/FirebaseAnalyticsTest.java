package com.getcapacitor.community.firebaseanalytics;

import android.os.Bundle;

import org.json.JSONObject;

import org.junit.Test;

import static org.junit.Assert.*;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FirebaseAnalyticsTest {
  
  private Bundle toBundle(String json) throws Exception {
    return FirebaseAnalytics.convertJsonToBundle(new JSONObject(json));
  }

  private void check(String json, String bundleStr) throws Exception {
    assertEquals(bundleStr, toBundle(json).toString());
  }

  @Test
  public void convertJsonToBundle() throws Exception {
    check("{}", "Bundle[{}]");
    check("{ \"hello\": \"world\" }", "Bundle[{hello=world}]");
    check("{ \"foo\": 123.456 }", "Bundle[{foo=123.456}]");
    check("{ \"foo\": null }", "Bundle[{}]");
    check("{ \"foo\": { \"bar\": 123 } }", "Bundle[{foo=Bundle[{bar=123}]}]");
    check("{ \"foo\": {} }", "Bundle[{foo=Bundle[{}]}]");
    
    assertArrayEquals(
      new String[]{"Bundle[{bar=123}]", "Bundle[{fizz=456}]"},
      Arrays.stream(toBundle("{ \"foo\": [{ \"bar\": 123 }, { \"fizz\": 456 }] }").getParcelableArray("foo")).map(p -> p.toString()).collect(Collectors.toList()).toArray()
    );

    assertArrayEquals(new float[]{123.0f, 456.0f}, toBundle("{ \"foo\": [123, 456] }").getFloatArray("foo"), 0.0f);
    assertArrayEquals(new float[]{123.0f, 456.789f}, toBundle("{ \"foo\": [123, 456.789] }").getFloatArray("foo"), 0.0f);

    check("{ \"foo\": [{ \"bar\": 123 }, 456.789], \"fizz\": 123 }", "Bundle[{fizz=123}]");
  }
}
