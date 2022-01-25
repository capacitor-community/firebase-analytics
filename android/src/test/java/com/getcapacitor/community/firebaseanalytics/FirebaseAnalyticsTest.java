package com.getcapacitor.community.firebaseanalytics;

import android.os.Bundle;

import org.json.JSONObject;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

public class FirebaseAnalyticsTest {
  
  private Bundle toBundle(String json) throws Exception {
    return FirebaseAnalytics.convertJsonToBundle(new JSONObject(json));
  }

  private void assertBundle(String json, String bundleStr) throws Exception {
    assertEquals(bundleStr, toBundle(json).toString());
  }

  @Test
  public void convertJsonToBundle() throws Exception {
    // basic fields
    assertBundle("{}", "Bundle[{}]");
    assertBundle("{ \"hello\": \"world\" }", "Bundle[{hello=world}]");
    assertBundle("{ \"foo\": 123.456 }", "Bundle[{foo=123.456}]");
    assertBundle("{ \"foo\": null }", "Bundle[{}]");

    // nested objects
    assertBundle("{ \"foo\": { \"bar\": 123 } }", "Bundle[{foo=Bundle[{bar=123}]}]");
    assertBundle("{ \"foo\": {} }", "Bundle[{foo=Bundle[{}]}]");

    // don't allow mismatched array elements
    assertBundle("{ \"foo\": [{ \"bar\": 123 }, 456.789], \"fizz\": 123 }", "Bundle[{fizz=123}]");

    // array of bundles
    assertArrayEquals(
      new String[]{"Bundle[{bar=123}]", "Bundle[{fizz=456}]"},
      Arrays.stream(toBundle("{ \"foo\": [{ \"bar\": 123 }, { \"fizz\": 456 }] }").getParcelableArray("foo")).map(p -> p.toString()).toArray()
    );

    // any combination of number types in array should be fine
    assertArrayEquals(
      new float[]{123, 456},
      toBundle("{ \"foo\": [123, 456] }").getFloatArray("foo"),
      0
    );
    assertArrayEquals(
      new float[]{123, 456.789f},
      toBundle("{ \"foo\": [123, 456.789] }").getFloatArray("foo"),
      0
    );
  }
}
