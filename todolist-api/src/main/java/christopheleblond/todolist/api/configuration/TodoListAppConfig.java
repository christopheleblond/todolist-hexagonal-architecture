package christopheleblond.todolist.api.configuration;

import christopheleblond.todolist.api.TodoListApi;
import christopheleblond.todolist.data.TodoListInventoryAdapter;
import christopheleblond.todolist.domain.TodoListService;
import christopheleblond.todolist.spi.TodoListInventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TodoListAppConfig {

    @Bean
    public TodoListApi todoService(TodoListInventory inventory) {
        log.info("Create todo Service {}", inventory);
        return new TodoListService(inventory);
    }

    @Bean
    public TodoListInventory todoListInventory() {
        return new TodoListInventoryAdapter();
    }
}
