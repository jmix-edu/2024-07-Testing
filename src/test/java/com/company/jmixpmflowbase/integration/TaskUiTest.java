package com.company.jmixpmflowbase.integration;

import com.company.jmixpmflowbase.JmixpmFlowBaseApplication;
import com.company.jmixpmflowbase.entity.Project;
import com.company.jmixpmflowbase.entity.Task;
import com.company.jmixpmflowbase.entity.User;
import com.company.jmixpmflowbase.view.main.MainView;
import com.company.jmixpmflowbase.view.task.TaskDetailView;
import com.company.jmixpmflowbase.view.task.TaskListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import io.jmix.core.DataManager;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.EntityPickerComponent;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@UiTest
@SpringBootTest(classes = {JmixpmFlowBaseApplication.class, FlowuiTestAssistConfiguration.class})
public class TaskUiTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void test_toList_Navigation(){
        viewNavigators.view(TaskListView.class).navigate();
        TaskListView currentView = UiTestUtils.getCurrentView();

        DataGrid<Task> tasksGrid = findComponent(currentView, "tasksDataGrid");

        Assertions.assertNotNull(tasksGrid);
    }

    @Test
    void givenNoData_whenSaveClicked_thenNoBackToList(){
        viewNavigators.detailView(Task.class).newEntity().navigate();

        View currentView;

        currentView = UiTestUtils.getCurrentView();

        JmixButton saveBtn =  findComponent(currentView, "saveAndCloseBtn");
        saveBtn.click();

        currentView = UiTestUtils.getCurrentView();

        //after not valid inputs we stay at the same screen
        Assertions.assertInstanceOf(TaskDetailView.class, currentView);
    }

    @Test
    void givenAllNeeded_whenSaveClicked_thenBackToListNavigated(){
        //expected that we go to new task creation from main screen
        viewNavigators.detailView(Task.class).newEntity().navigate();

        View currentView;

        currentView = UiTestUtils.getCurrentView();

        User userToAssign = dataManager.load(User.class).all().one();
        Project project = dataManager.load(Project.class).all().one();
        String name = "test-task";

        EntityPickerComponent<User> userPicker = findComponent(currentView, "assigneeField");
        userPicker.setValueFromClient(userToAssign);

        EntityPickerComponent<Project> projectPicker = findComponent(currentView, "projectField");
        projectPicker.setValueFromClient(project);

        TextField nameField = findComponent(currentView, "nameField");
        nameField.setValue(name);

        JmixButton saveBtn =  findComponent(currentView, "saveAndCloseBtn");
        saveBtn.click();

        currentView = UiTestUtils.getCurrentView();

        //after valid inputs we navigated to main view, in this case
        Assertions.assertInstanceOf(MainView.class, currentView);
    }

    @AfterEach
    void tearDown() {
        dataManager.load(Task.class)
                .query("e.name like ?1", "test-task")
                .list()
                .forEach(tsk -> dataManager.remove(tsk));
    }

    @SuppressWarnings("unchecked")
    private <T> T findComponent(View<?> view, String componentId) {
        Optional<Component> component = UiComponentUtils.findComponent(view, componentId);
        Assertions.assertTrue(component.isPresent());
        return (T) component.get();
    }
}
