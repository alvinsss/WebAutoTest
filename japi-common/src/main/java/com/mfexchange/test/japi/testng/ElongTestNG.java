package com.elong.test.japi.testng;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.CommandLineArgs;
import org.testng.TestNG;
import org.testng.TestNGException;
import org.testng.TestRunner;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class ElongTestNG extends TestNG {
    private static final Logger LOGGER = Logger.getLogger(ElongTestNG.class);
    private static final Pattern pattern = Pattern.compile(
            "^\nDependencyMap::Method \"[^\"]+\" depends on nonexistent group \"(.*)\"$");

    static String join(Set<String> c) {
        StringBuilder sb = new StringBuilder();
        for (String s : c) {
            if (0 != sb.length()) {
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    static void exitWithError(String msg) {
        System.err.println(msg);
        new JCommander(new CommandLineArgs()).usage();
        System.exit(1);
    }

    public static void main(String[] args) {
        Set<String> groups = new HashSet<String>();
        groups.addAll(Arrays.asList(args[1].split(",")));

        while (true) {
            String[] argv = {args[0], " -groups ", join(groups)};
            ElongTestNG result = new ElongTestNG();
            int status = 0;

            try {
                CommandLineArgs cla = new CommandLineArgs();
                new JCommander(cla, argv);
                validateCommandLineParameters(cla);
                result.configure(cla);
            }
            catch(ParameterException ex) {
                exitWithError(ex.getMessage());
            }

            try {
                result.run();
            }
            catch(TestNGException ex) {
                String message = ex.getMessage();
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    String group = matcher.group(1);
                    if (!groups.contains(group)) {
                        groups.add(group);
                        Utils.log("ElongTestNG", 0, "Resolving:\n  " + 
                                String.format("It depends on group %s, will run again\n", group));
                        continue;
                    }
                }

                if (TestRunner.getVerbose() > 1) {
                    ex.printStackTrace(System.out);
                }
                else {
                    LOGGER.error(ex.getMessage());
                }
                status = HAS_FAILURE;
            }
            System.exit(result.getStatus() | status);
        }
    }
}
