package com.company.jmixpmflowbase.security;

import com.company.jmixpmflowbase.entity.Task;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "TasksReader", code = TasksReaderRole.CODE, scope = "UI")
public interface TasksReaderRole {
    String CODE = "tasks-reader";

    @MenuPolicy(menuIds = "Task_.list")
    @ViewPolicy(viewIds = {"Task_.list", "MainView", "Task_.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.READ)
    void task();
}