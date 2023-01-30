package com.elong.test.japi.verify;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

public class IVerify {
    public IVerify(String verifyClassName, Object... initArgs) throws Exception {
        Class<?> verifyClass = Class.forName(verifyClassName);
        Class<?>[] initArgsTypes = new Class<?>[initArgs.length];
        for (int i = 0; i < initArgs.length; ++i) {
            initArgsTypes[i] = initArgs[i].getClass();
        }
        Constructor<?> verifyConstructor = verifyClass.getConstructor(Map.class, String.class);
        verifyObject = verifyConstructor.newInstance(initArgs);
    }

    public void verify(String verifyMethod, Object[] args) throws Exception {
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int j = 0; j < args.length; ++j) {
            parameterTypes[j] = args[j].getClass();
        }
        Method method = verifyObject.getClass().getMethod(verifyMethod, parameterTypes);
        method.setAccessible(true);
        method.invoke(verifyObject, args);
        method.setAccessible(false);
    }

    private Object verifyObject;
}
