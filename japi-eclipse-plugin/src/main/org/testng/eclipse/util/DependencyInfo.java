package org.testng.eclipse.util;

import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.testng.eclipse.launch.components.Filters;

/**
 * A class that represents all the information about groups in the current
 * project (which types depend on which groups, which types define groups, which
 * methods define on which groups and which methods define groups.
 */
public class DependencyInfo {
  private Multimap<String, IType> typesByGroups;
  private Multimap<IType, String> groupDependenciesByTypes;
  private Multimap<String, IMethod> methodsByGroups;
  private Multimap<IMethod, String> groupDependenciesByMethods;
  private Multimap<IMethod, IMethod> methodsByMethods;

  private DependencyInfo() {
    typesByGroups = ArrayListMultimap.create();
    groupDependenciesByTypes = ArrayListMultimap.create();
    methodsByGroups = ArrayListMultimap.create();
    groupDependenciesByMethods = ArrayListMultimap.create();
    methodsByMethods = ArrayListMultimap.create();
  }

  public Multimap<String, IType> getTypesByGroups() {
    return typesByGroups;
  }

  public Multimap<IType, String> getGroupDependenciesByTypes() {
    return groupDependenciesByTypes;
  }

  public Multimap<String, IMethod> getMethodsByGroups() {
    return methodsByGroups;
  }

  public Multimap<IMethod, String> getGroupDependenciesByMethods() {
    return groupDependenciesByMethods;
  }

  public Multimap<IMethod, IMethod> getMethodsByMethods() {
    return methodsByMethods;
  }

  public static DependencyInfo getDependencyInfo(final IJavaProject javaProject) {
    final DependencyInfo result = new DependencyInfo();
    final Set<IType> allTypes = Sets.newHashSet();
    try {
      TestSearchEngine.collectTypes(javaProject, allTypes, Filters.SINGLE_TEST,
          "Parsing tests");
      for (IType type : allTypes) {
        for (IMethod method : type.getMethods()) {
          for (IAnnotation annotation : method.getAnnotations()) {
            IMemberValuePair[] pairs = annotation.getMemberValuePairs();
            if ("Test".equals(annotation.getElementName()) && pairs.length > 0) {
              for (IMemberValuePair pair : pairs) {
                if ("groups".equals(pair.getMemberName())) {
                  Object groups = pair.getValue();
                  if (groups.getClass().isArray()) {
                    for (Object o : (Object[]) groups) {
                      result.typesByGroups.put(o.toString(), type);
                      result.methodsByGroups.put(o.toString(), method);
                    }
                  } else {
                    result.typesByGroups.put(groups.toString(), type);
                    result.methodsByGroups.put(groups.toString(), method);
                  }
                } else if ("dependsOnGroups".equals(pair.getMemberName())) {
                  Object dependencies = pair.getValue();
                  if (dependencies.getClass().isArray()) {
                    for (Object o : (Object[]) dependencies) {
                      result.groupDependenciesByTypes.put(type, o.toString());
                      result.groupDependenciesByMethods.put(method,
                          o.toString());
                    }
                  } else {
                    result.groupDependenciesByTypes.put(type,
                        dependencies.toString());
                    result.groupDependenciesByMethods.put(method,
                        dependencies.toString());
                  }

                } else if ("dependsOnMethods".equals(pair.getMemberName())) {
                  Object dependencies = pair.getValue();
                  IType methodType = method.getDeclaringType();
                  if (dependencies.getClass().isArray()) {
                    for (Object o : (Object[]) dependencies) {
                      IMethod depMethod = JDTUtil
                          .fuzzyFindMethodInTypeHierarchy(methodType,
                              o.toString(), new String[0]);
                      result.methodsByMethods.put(method, depMethod);
                    }
                  } else {
                    IMethod depMethod = JDTUtil.fuzzyFindMethodInTypeHierarchy(
                        methodType, dependencies.toString(), new String[0]);
                    result.methodsByMethods.put(method, depMethod);
                  }
                }
              }
            }
          }
        }
      }
    } catch (CoreException ex) {
      ex.printStackTrace();
    }
    return result;
  }
}
