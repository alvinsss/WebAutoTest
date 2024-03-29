package org.testng.eclipse.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Multimap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.testng.eclipse.TestNGPlugin;
import org.testng.eclipse.TestNGPluginConstants;
import org.testng.eclipse.launch.TestNGLaunchConfigurationConstants;
import org.testng.eclipse.launch.TestNGLaunchConfigurationConstants.LaunchType;
import org.testng.eclipse.launch.components.Filters;
import org.testng.eclipse.ui.RunInfo;
import org.testng.eclipse.util.DependencyInfo;
import org.testng.eclipse.util.JDTUtil;
import org.testng.eclipse.util.LaunchUtil;
import org.testng.eclipse.util.StringUtils;
import org.testng.eclipse.util.SuiteGenerator;
import org.testng.remote.RemoteTestNG;
import org.testng.xml.LaunchSuite;

/**
 * Helper methods to store and retrieve values from a launch configuration.
 * 
 * @author cbeust
 */
public class ConfigurationHelper {

  public static class LaunchInfo {
    private String m_projectName;
    private LaunchType m_launchType;
    private Collection<String> m_classNames;
    private Collection<String> m_packageNames;
    private Map<String, Collection<String>> m_classMethods;
    private String m_suiteName;
    private Map<String, List<String>> m_groupMap = Maps.newHashMap();
    private String m_logLevel;

    public LaunchInfo(String projectName, LaunchType launchType,
        Collection<String> classNames, Collection<String> packageNames,
        Map<String, Collection<String>> classMethodsMap,
        Map<String, List<String>> groupMap, String suiteName,
        String complianceLevel, String logLevel) {
      m_projectName = projectName;
      m_launchType = launchType;
      m_classNames = classNames;
      m_classMethods = classMethodsMap;
      m_groupMap = groupMap;
      m_suiteName = suiteName.trim();
      m_logLevel = logLevel;
      m_packageNames = packageNames;
    }
  }

  public static int getLogLevel(ILaunchConfiguration config) {
    String stringResult = getStringAttribute(config,
        TestNGLaunchConfigurationConstants.LOG_LEVEL);
    if (null == stringResult) {
      return TestNGLaunchConfigurationConstants.DEFAULT_LOG_LEVEL;
    } else {
      return Integer.parseInt(stringResult);
    }
  }

  public static String getSourcePath(ILaunchConfiguration config) {
    return getStringAttribute(config,
        TestNGLaunchConfigurationConstants.DIRECTORY_TEST_LIST);
  }

  public static List<String> getGroups(ILaunchConfiguration config) {
    return getListAttribute(config,
        TestNGLaunchConfigurationConstants.GROUP_LIST);
  }

  public static List<String> getGroupClasses(ILaunchConfiguration config) {
    return getListAttribute(config,
        TestNGLaunchConfigurationConstants.GROUP_CLASS_LIST);
  }

  public static List<String> getClasses(ILaunchConfiguration config) {
    return getListAttribute(config,
        TestNGLaunchConfigurationConstants.CLASS_TEST_LIST);
  }

  public static List<String> getPackages(ILaunchConfiguration config) {
    return getListAttribute(config,
        TestNGLaunchConfigurationConstants.PACKAGE_TEST_LIST);
  }

  public static List<String> getSuites(ILaunchConfiguration config) {
    return getListAttribute(config,
        TestNGLaunchConfigurationConstants.SUITE_TEST_LIST);
  }

  public static List<String> getSources(ILaunchConfiguration config) {
    return getListAttribute(config,
        TestNGLaunchConfigurationConstants.SOURCE_TEST_LIST);
  }

  public static String getProjectName(ILaunchConfiguration config) {
    return getStringAttribute(config,
        IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME);
  }

  public static String getMain(ILaunchConfiguration configuration) {
    return getStringAttribute(configuration,
        IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME);
  }

  public static List<String> getMethods(ILaunchConfiguration configuration) {
    return getListAttribute(configuration,
        TestNGLaunchConfigurationConstants.METHOD_TEST_LIST);
  }

  private static String getProjectJvmArgs() {
    IPreferenceStore store = TestNGPlugin.getDefault().getPreferenceStore();
    String result = store.getString(TestNGPluginConstants.S_JVM_ARGS);
    return result;
  }

  /**
   * @return the JVM args from the configuration or, if not found, from the
   *         preferences.
   */
  public static String getJvmArgs(ILaunchConfiguration configuration) {
    String result = getProjectJvmArgs();

    // JVM args from the previous configuration take precedence over the
    // preference
    if (configuration != null) {
      try {
        result = configuration.getAttribute(
            IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, result);
        result = VariablesPlugin.getDefault().getStringVariableManager()
            .performStringSubstitution(result);
      } catch (CoreException e) {
        e.printStackTrace();
      }
    }

    return result;
  }

  public static ILaunchConfigurationWorkingCopy setJvmArgs(
      ILaunchConfigurationWorkingCopy configuration, String args) {
    configuration.setAttribute(
        IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, args);

    return configuration;
  }

  public static LaunchType getType(ILaunchConfiguration configuration) {
    int result = getIntAttribute(configuration,
        TestNGLaunchConfigurationConstants.TYPE);
    return LaunchType.fromInt(result);
  }

  public static String getProjectName(ILaunch launch) {
    return launch
        .getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME);
  }

  public static int getPort(ILaunch launch) {
    try {
      return Integer.parseInt(launch
          .getAttribute(TestNGLaunchConfigurationConstants.PORT));
    } catch (Throwable thr) {
      return 0;
    }
  }

  public static String getSubName(ILaunch launch) {
    return launch
        .getAttribute(TestNGLaunchConfigurationConstants.TESTNG_RUN_NAME_ATTR);
  }

  // ///////////////////
  private static Map<String, String> getMapAttribute(
      ILaunchConfiguration config, String attr) {
    Map<String, String> result = null;

    try {
      result = config.getAttribute(attr, result);
    } catch (CoreException cex) {
      TestNGPlugin.log(cex);
    }

    return result;
  }

  private static List<String> getListAttribute(ILaunchConfiguration config,
      String attr) {
    List<String> result = null;

    try {
      result = config.getAttribute(attr, result);
    } catch (CoreException e) {
      TestNGPlugin.log(e);
    }

    return result;

  }

  private static String getStringAttribute(ILaunchConfiguration config,
      String attr) {
    String result = null;

    try {
      result = config.getAttribute(attr, result);
    } catch (CoreException e) {
      TestNGPlugin.log(e);
    }

    return result;

  }

  private static int getIntAttribute(ILaunchConfiguration config, String attr) {
    int result = 0;

    try {
      result = config.getAttribute(attr, result);
    } catch (CoreException e) {
      TestNGPlugin.log(e);
    }

    return result;
  }

  public static ILaunchConfigurationWorkingCopy createBasicConfiguration(
      final ILaunchManager launchManager, final IProject project,
      final String confName) {
    ILaunchConfigurationWorkingCopy wConf = null;

    try {
      ILaunchConfigurationType configurationType = launchManager
          .getLaunchConfigurationType(TestNGLaunchConfigurationConstants.ID_TESTNG_APPLICATION);
      wConf = configurationType.newInstance(null /* project */, confName); // launchManager.generateUniqueLaunchConfigurationNameFrom(confName));
      wConf.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
          RemoteTestNG.class.getName());
      wConf.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
          project.getName());
    } catch (CoreException ce) {
      TestNGPlugin.log(ce);
    }

    return wConf;
  }

  /**
   * @param selectedProject
   */
  public static void createBasicConfiguration(IJavaProject javaProject,
      ILaunchConfigurationWorkingCopy config) {
    final String projectName = (javaProject == null) ? "" : javaProject
        .getElementName();

    config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
        projectName);
    config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
        RemoteTestNG.class.getName());
    config.setAttribute(TestNGLaunchConfigurationConstants.TYPE,
        LaunchType.CLASS.ordinal());
    config.setAttribute(TestNGLaunchConfigurationConstants.LOG_LEVEL, "2");
  }

  private static Set<IMethod> getClosureMethods(Set<IMethod> methods,
      DependencyInfo groupInfo) {
    Set<IMethod> closureMethods = Sets.newHashSet(methods);
    for (IMethod iMethod : methods) {
      try {
        if (LaunchUtil.methodHasDependencies(iMethod)) {
          closureMethods.addAll(LaunchUtil.findMethodTransitiveClosure(iMethod,
              groupInfo));
        }
      } catch (JavaModelException e) {
        TestNGPlugin.log(e);
      }
    }
    return closureMethods;
  }

  private static Map<String, Collection<String>> getClassMethods(
      Set<IMethod> methods) {
    Multimap<String, String> classMethodsMultimap = ArrayListMultimap.create();
    for (IMethod iMethod : methods) {
      String methodName = iMethod.getElementName();
      String className = iMethod.getDeclaringType().getFullyQualifiedName();
      classMethodsMultimap.put(className, methodName);
    }
    return classMethodsMultimap.asMap();
  }

  public static boolean isTestNGMethod(IMethod method)
      throws JavaModelException {
    IAnnotation beforeClassAnnotation = method.getAnnotation("BeforeClass");
    IAnnotation beforeMethodAnnotation = method.getAnnotation("BeforeMethod");
    IAnnotation testAnnotation = method.getAnnotation("Test");
    IAnnotation dataProviderAnnotation = method.getAnnotation("DataProvider");
    IAnnotation afterMethodAnnotation = method.getAnnotation("AfterMethod");
    IAnnotation afterClassAnnotation = method.getAnnotation("AfterClass");
    return testAnnotation != null/*
                                  * && beforeClassAnnotation == null &&
                                  * beforeMethodAnnotation == null &&
                                  * dataProviderAnnotation == null &&
                                  * afterMethodAnnotation == null &&
                                  * afterClassAnnotation == null
                                  */;
  }

  /**
   * @return List<LaunchSuite>
   */
  public static List<LaunchSuite> getLaunchSuites(IJavaProject ijp,
      ILaunchConfiguration configuration) {
    LaunchType type = ConfigurationHelper.getType(configuration);
    Map<String, String> parameters = getMapAttribute(configuration,
        TestNGLaunchConfigurationConstants.PARAMS);
    Map<String, Collection<String>> classMethods = null;

    if (type == LaunchType.SUITE) {
      return createLaunchSuites(ijp.getProject(), getSuites(configuration));
    }

    DependencyInfo groupInfo = DependencyInfo.getDependencyInfo(ijp);
    Set<IMethod> methodSet = new HashSet<IMethod>();
    if (type == LaunchType.GROUP) {
      List<String> groups = getGroups(configuration);
      Multimap<String, IMethod> methodsByGroups = groupInfo
          .getMethodsByGroups();
      for (String group : groups) {
        methodSet.addAll(methodsByGroups.get(group));
      }
    } else if (type == LaunchType.CLASS) {
      List<String> testClasses = getClasses(configuration);
      Set<IType> typeSet = new HashSet<IType>();
      for (String testClass : testClasses) {
        try {
          IType iType = ijp.findType(testClass);
          typeSet.add(iType);
        } catch (JavaModelException ex) {
          ex.printStackTrace();
        }
      }

      try {
        for (IType iType : typeSet) {
          IMethod[] iMethods = iType.getMethods();
          for (IMethod imethod : iMethods) {
            if (isTestNGMethod(imethod)) {
              methodSet.add(imethod);
            }
          }
        }
      } catch (JavaModelException ex) {
        ex.printStackTrace();
      }
    } else if (type == LaunchType.METHOD) {
      classMethods = getClassMethods(configuration);
      for (String className : classMethods.keySet()) {
        Collection<String> methods = classMethods.get(className);
        for (String methodName : methods) {
          try {
            IMethod method = (IMethod) JDTUtil.findElement(ijp, className,
                methodName, null);
            methodSet.add(method);
          } catch (JavaModelException e) {
            e.printStackTrace();
          }
        }
      }
    } else if (type == LaunchType.PACKAGE) {
      List<String> packages = getListAttribute(configuration,
          TestNGLaunchConfigurationConstants.PACKAGE_TEST_LIST);
      try {
        IPackageFragment[] iPackageFragments = ijp.getPackageFragments();
        Set<IPackageFragment> iPackageFragmentSet = new HashSet<IPackageFragment>();
        for (String packageName : packages) {
          for (IPackageFragment iPackageFragment : iPackageFragments) {
            if (packageName.equals(iPackageFragment.getElementName())) {
              iPackageFragmentSet.add(iPackageFragment);
              break;
            }
          }
        }

        Set<IType> typeSet = new HashSet<IType>();
        for (IPackageFragment iPackageFragment : iPackageFragmentSet) {
          findPackages(iPackageFragment, typeSet);
        }

        for (IType iType : typeSet) {
          IMethod[] iMethods = iType.getMethods();
          for (IMethod imethod : iMethods) {
            if (isTestNGMethod(imethod)) {
              methodSet.add(imethod);
            }
          }
        }
      } catch (JavaModelException e) {
        TestNGPlugin.log(e);
      }
    }

    Set<IMethod> closureMethods = getClosureMethods(methodSet, groupInfo);
    classMethods = getClassMethods(closureMethods);

    /**
     * Custom Eclipse plugin suite generator. Instead of using TestNG core suite
     * generator, we are using a set of custom generators that allow more
     * customization.
     */
    return Arrays.asList(new LaunchSuite[] {
        SuiteGenerator.createCustomizedSuite(ijp.getProject().getName(), classMethods,
            parameters, getLogLevel(configuration)) });
  }

  public static void findPackages(IJavaElement ije, Set result) {
    if (IJavaElement.PACKAGE_FRAGMENT == ije.getElementType()) {
      try {
        ICompilationUnit[] compilationUnits = ((IPackageFragment) ije)
            .getCompilationUnits();

        for (int i = 0; i < compilationUnits.length; i++) {
          findPackages(compilationUnits[i], result);
        }
      } catch (JavaModelException jme) {
        TestNGPlugin.log(jme);
      }

    }

    if (IJavaElement.COMPILATION_UNIT == ije.getElementType()) {
      try {
        IType[] types = ((ICompilationUnit) ije).getAllTypes();

        for (int i = 0; i < types.length; i++) {
          if (Filters.SINGLE_TEST.accept(types[i])) {
            result.add(types[i]);
          }
        }
      } catch (JavaModelException jme) {
        TestNGPlugin.log(jme);
      }
    }
  }

  public static Map<String, Collection<String>> getClassMethods(
      ILaunchConfiguration configuration) {
    Map<String, String> confResult = getMapAttribute(configuration,
        TestNGLaunchConfigurationConstants.ALL_METHODS_LIST);
    if (null == confResult)
      return null;

    Map<String, Collection<String>> results = new HashMap<String, Collection<String>>();
    for (Map.Entry<String, String> entry : confResult.entrySet()) {
      String className = entry.getKey();
      String methodNames = entry.getValue();
      results.put(className, Arrays.asList(methodNames.split(";")));
    }

    return results;
  }

  /**
   * @return List<LaunchSuite>
   */
  private static List<LaunchSuite> createLaunchSuites(final IProject project,
      List<String> suites) {
    List<LaunchSuite> result = Lists.newArrayList();

    for (String suitePath : suites) {
      File suiteFile = new File(suitePath);
      if (suiteFile.exists() && suiteFile.isFile()) {
      } else {
        suiteFile = project.getFile(suitePath).getLocation().toFile();
      }

      result.add(SuiteGenerator.createProxiedXmlSuite(suiteFile));
    }

    return result;
  }

  /**
   * Looks for an available configuration that matches the project and confName
   * parameters. If the defaultConfiguration is not null, it is used. The
   * defaultConfiguration may be null, which may cause null to be returned if
   * there is not an exact match for the project and confName parameters. This
   * method was added to allow the FailureTab to pass along the previous
   * configuration for re-use, so that any jvm args defined there will be used.
   * 
   * @param launchManager
   * @param project
   * @param confName
   * @param defaultConfiguration
   * @return
   */
  public static ILaunchConfiguration findConfiguration(
      ILaunchManager launchManager, IProject project, String confName,
      RunInfo runInfo) {

    ILaunchConfiguration resultConf = null;
    try {
      ILaunchConfigurationType confType = launchManager
          .getLaunchConfigurationType(TestNGLaunchConfigurationConstants.ID_TESTNG_APPLICATION);
      ;

      ILaunchConfiguration[] availConfs = launchManager
          .getLaunchConfigurations(confType);

      final String projectName = project.getName();
      final String mainRunner = TestNGPluginConstants.MAIN_RUNNER;

      for (ILaunchConfiguration availConf : availConfs) {
        String confProjectName = ConfigurationHelper.getProjectName(availConf);
        String confMainName = ConfigurationHelper.getMain(availConf);

        if (projectName.equals(confProjectName)
            && mainRunner.equals(confMainName)) {
          if (confName != null && confName.equals(availConf.getName())) {
            resultConf = availConf;
            break;
          } else if (runInfo != null) {
            Map<String, Collection<String>> availableClassMethods = getClassMethods(availConf);
            String method = runInfo.getMethodName();
            if (method != null && availableClassMethods != null) {
              String className = runInfo.getClassName();
              Object o = availableClassMethods.get(className);
              if (o != null && o instanceof List) {
                List methods = (List) o;
                if (methods.size() == 1) {
                  String available = (String) methods.get(0);
                  if (method.equalsIgnoreCase(available)) {
                    resultConf = availConf;
                    break;
                  }
                }
              }
            }
          }// else if
           // TODO: else complain about no reference parameters
        }// if
      } // for
    } catch (CoreException ce) {
      ; // IGNORE
    }

    return resultConf;
  }

  public static Map<String, String> toClassMethodsMap(
      Map<String, Collection<String>> classMethods) {
    Map<String, String> result = new HashMap<String, String>();
    for (Map.Entry<String, Collection<String>> entry : classMethods.entrySet()) {
      String clsName = entry.getKey();
      Collection<String> methods = entry.getValue();
      StringBuffer strMethods = new StringBuffer();
      int i = 0;
      for (String method : methods) {
        if (i++ > 0)
          strMethods.append(";");
        strMethods.append(method);
      }

      result.put(clsName, strMethods.toString());
    }

    return result;
  }

  /**
   * @param configuration
   */
  public static void updateLaunchConfiguration(
      ILaunchConfigurationWorkingCopy configuration, LaunchInfo launchInfo) {
    Map<String, Collection<String>> classMethods = Maps.newHashMap();
    if (launchInfo.m_groupMap != null) {
      Collection<List<String>> classes = launchInfo.m_groupMap.values();
      if (null != classes) {
        for (List<String> classList : classes) {
          for (String c : classList) {
            classMethods.put(c, Collections.<String> emptyList());
          }
        }
      }
    }
    Collection<String> classNames = launchInfo.m_classNames;
    List<Object> classNamesList = new ArrayList<Object>();
    if (null != classNames && !classNames.isEmpty()) {
      for (String cls : classNames) {
        classMethods.put(cls, Collections.<String> emptyList());
        classNamesList.add(cls);
      }
    }
    List<Object> packageList = new ArrayList<Object>();
    if (launchInfo.m_packageNames != null) {
      packageList.addAll(launchInfo.m_packageNames);
    }
    if (null != launchInfo.m_classMethods) {
      classMethods.putAll(launchInfo.m_classMethods);
    }

    configuration.setAttribute(TestNGLaunchConfigurationConstants.TYPE,
        launchInfo.m_launchType.ordinal());
    configuration.setAttribute(
        IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
        launchInfo.m_projectName);
    configuration.setAttribute(
        IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
        RemoteTestNG.class.getName());
    configuration.setAttribute(
        TestNGLaunchConfigurationConstants.CLASS_TEST_LIST, classNamesList);
    configuration.setAttribute(
        TestNGLaunchConfigurationConstants.PACKAGE_TEST_LIST, packageList);
    configuration.setAttribute(TestNGLaunchConfigurationConstants.GROUP_LIST,
        new ArrayList<Object>(launchInfo.m_groupMap.keySet()));
    configuration.setAttribute(
        TestNGLaunchConfigurationConstants.GROUP_CLASS_LIST,
        Utils.uniqueMergeList(launchInfo.m_groupMap.values()));
    configuration.setAttribute(
        TestNGLaunchConfigurationConstants.SUITE_TEST_LIST,
        StringUtils.stringToNullList(launchInfo.m_suiteName));
    configuration.setAttribute(
        TestNGLaunchConfigurationConstants.ALL_METHODS_LIST,
        toClassMethodsMap(classMethods));
    configuration.setAttribute(TestNGLaunchConfigurationConstants.LOG_LEVEL,
        launchInfo.m_logLevel);
  }
}
