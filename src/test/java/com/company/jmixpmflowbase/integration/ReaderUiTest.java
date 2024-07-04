package com.company.jmixpmflowbase.integration;

import com.company.jmixpmflowbase.JmixpmFlowBaseApplication;
import com.company.jmixpmflowbase.test_support.AuthenticatedAsTasksViewer;
import com.company.jmixpmflowbase.view.task.TaskListView;
import com.vaadin.flow.component.Component;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@UiTest(authenticator = AuthenticatedAsTasksViewer.class)
@SpringBootTest(classes = {JmixpmFlowBaseApplication.class, FlowuiTestAssistConfiguration.class})
public class ReaderUiTest {

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void create_button_unavailable(){
        viewNavigators.view(TaskListView.class).navigate();
        TaskListView currentView = UiTestUtils.getCurrentView();

        JmixButton createBtn = findComponent(currentView, "createBtn");

        Assertions.assertFalse(createBtn.isEnabled());

    }

    @Test
    void edit_button_changed(){
        viewNavigators.view(TaskListView.class).navigate();
        TaskListView currentView = UiTestUtils.getCurrentView();

        JmixButton readBtn = findComponent(currentView, "editBtn");
        String expectedCaption = "Read";
        Assertions.assertEquals(readBtn.getText(), expectedCaption);

    }

    @SuppressWarnings("unchecked")
    private <T> T findComponent(View<?> view, String componentId) {
        Optional<Component> component = UiComponentUtils.findComponent(view, componentId);
        Assertions.assertTrue(component.isPresent());
        return (T) component.get();
    }
}
