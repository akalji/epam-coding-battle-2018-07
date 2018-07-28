package com.epam;

import com.epam.client.streamdashboardTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class streamdashboardSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for streamdashboard");
    suite.addTestSuite(streamdashboardTest.class);
    return suite;
  }
}
