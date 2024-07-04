package com.company.jmixpmflowbase.unit;

import com.company.jmixpmflowbase.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserUnitTest {

    @Test
    @DisplayName("Checks the computation of instance name")
    void testInstanceName() {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("ivanov_i");

        Assertions.assertEquals("Ivan Ivanov [ivanov_i]", user.getDisplayName());

    }
}
