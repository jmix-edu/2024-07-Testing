package com.company.jmixpmflowbase.unit;


import com.company.jmixpmflowbase.JmixpmFlowBaseApplication;
import com.company.jmixpmflowbase.app.TaskService;
import com.company.jmixpmflowbase.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.KeyValueEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = JmixpmFlowBaseApplication.class)
public class TaskUnitTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private Metadata metadata;

    @MockBean
    private DataManager dataManagerMock;

    @Test
    @DisplayName("Tests the computation of the least busy user")
    void checkLeastBusyUser () {

        User user1 = metadata.create(User.class);
        user1.setUsername("user1");
        KeyValueEntity entity1 = metadata.create(KeyValueEntity.class);
        entity1.setValue("user", user1);
        entity1.setValue("efforts", 10L);

        // test data user2
        User user2 = metadata.create(User.class);
        user2.setUsername("user2");
        KeyValueEntity entity2 = metadata.create(KeyValueEntity.class);
        entity1.setValue("user", user2);
        entity1.setValue("efforts", 2L);


        List<KeyValueEntity> entities = List.of(entity1, entity2);

        Mockito.when(dataManagerMock.loadValues(any(ValueLoadContext.class))).thenReturn(entities);

        Assertions.assertEquals(user2, taskService.findLeastBusyUser());

    }
}
