package com.mfexchange.test.japi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * @goal generateTestProject
 */
public class ETFMojo extends AbstractMojo {
    /**
     * @parameter property="project.build.outputDirectory"
     */
    private File testProjectOutputDirectory;

    /**
     * @parameter property="project.groupId"
     */
    private String groupId;

    /**
     * @parameter property="project.artifactId"
     */
    private String artifactId;

    /**
     * @parameter property="project.version"
     */
    private String version;

    /**
     * @parameter property="project.basedir"
     */
    private File testCaseDir;

    /**
     * @parameter property="generateTestProject.globalSettingsExcel"
     */
    private File globalSettingsExcel;

    /**
     * @parameter property="generateTestProject.orderCaseExcel"
     */
    private File orderCaseExcel;

    /**
     * @parameter property="generateTestProject.extJarsDir"
     */
    private File extJarsDir;

    /**
     * @parameter property="generateTestProject.messageClient"
     */
    private String messageClient;

    /**
     * @parameter property="generateTestProject.timeout"
     */
    private String timeout;

    /**
     * @throws MojoExecutionException
     * @throws MojoFailureException 
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log logger = getLog();
        List<String> LocalJarPaths = null;
        if (null != extJarsDir && extJarsDir.isDirectory()) {
            String relativePath = JAPIUtils.getRelativePath(testProjectOutputDirectory.getAbsolutePath(),
                    extJarsDir.getAbsolutePath(), testCaseDir.getAbsolutePath());
            LocalJarPaths = getLocalJarPaths(extJarsDir, relativePath);
        } else {
            LocalJarPaths = new ArrayList<String>();
        }

        TestProjectGen testProjectGen = new TestProjectGen();
        try {
            testProjectGen.genTestProject(groupId, artifactId, version,
                    testProjectOutputDirectory, LocalJarPaths, testCaseDir,
                    orderCaseExcel, globalSettingsExcel, messageClient, timeout, logger);
        } catch (Exception ex) {
            throw new MojoFailureException("Exception: ", ex);
        }
    }

    private List<String> getLocalJarPaths(File root, String relativePath) {
        List<String> jarPaths = new ArrayList<String>();
        for (File f : root.listFiles()) {
            if (!f.isHidden()) {
                if (f.isDirectory()) {
                    jarPaths.addAll(getLocalJarPaths(f, relativePath));
                } else if (f.getName().endsWith(".jar")) {
                    String path = f.getAbsolutePath().substring(extJarsDir.getAbsolutePath().length() + 1);
                    if (!"".equals(relativePath)) {
                       path = relativePath + File.separator + path;
                    }
                    jarPaths.add(path);
                }
            }
        }
        return jarPaths;
    }
}
