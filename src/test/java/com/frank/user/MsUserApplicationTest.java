package com.frank.user;

import org.junit.jupiter.api.Test;

public class MsUserApplicationTest {

    @Test
    public void logBeanDefinitionNames_runsWithoutError() {
        String[] names = new String[]{"bean1", "bean2"};
        // call private helper via reflection
        try {
            java.lang.reflect.Method m = MsUserApplication.class.getDeclaredMethod("logBeanDefinitionNames", String[].class);
            m.setAccessible(true);
            m.invoke(null, (Object) names);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
