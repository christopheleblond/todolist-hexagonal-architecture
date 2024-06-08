package christopheleblond.todolist.api.configuration;

import christopheleblond.todolist.spi.TodoListInventory;
import christopheleblond.todolist.spi.stubs.TodoListInventoryStub;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodolistTestAppConfig {

    @Bean
    @ConditionalOnProperty(value = "todolist.stub", havingValue = "true")
    public TodoListInventory todoListInventory() {
        return new TodoListInventoryStub();
    }
}
