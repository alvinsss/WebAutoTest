package org.testng.eclipse.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.testng.eclipse.TestNGPlugin;
import org.testng.eclipse.TestNGPluginConstants;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
   * initializeDefaultPreferences()
   */
  public void initializeDefaultPreferences() {
    IPreferenceStore store = TestNGPlugin.getDefault().getPreferenceStore();
    store
        .setDefault(TestNGPluginConstants.S_OUTDIR, "/target/surefire-reports");
    store
        .setDefault(
            TestNGPluginConstants.S_EXCLUDED_STACK_TRACES,
            "org.testng.internal org.testng.TestRunner org.testng.SuiteRunner "
                + "org.testng.remote.RemoteTestNG org.testng.TestNG sun.reflect java.lang");
  }

}
