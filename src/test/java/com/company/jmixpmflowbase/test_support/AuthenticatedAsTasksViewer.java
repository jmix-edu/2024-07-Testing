package com.company.jmixpmflowbase.test_support;

import com.company.jmixpmflowbase.entity.User;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.flowui.testassist.UiTestAuthenticator;
import org.springframework.context.ApplicationContext;

public class AuthenticatedAsTasksViewer implements UiTestAuthenticator {
    @Override
    public void setupAuthentication(ApplicationContext context) {
        User dev1 = context.getBean(UnconstrainedDataManager.class).loadValue("select u from User u where u.username = :username", User.class)
                .parameter("username", "dev1").one();
        context.getBean(SystemAuthenticator.class).begin(dev1.getUsername());
    }

    @Override
    public void removeAuthentication(ApplicationContext context) {
        context.getBean(SystemAuthenticator.class).end();
    }
}
