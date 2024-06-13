package christopheleblond.todolist.api.configuration;

import christopheleblond.todolist.api.TodoListApi;
import christopheleblond.todolist.data.InMemoryTodoListInventoryAdapter;
import christopheleblond.todolist.data.MongoDbTodoListInventoryAdapter;
import christopheleblond.todolist.domain.TodoListService;
import christopheleblond.todolist.spi.TodoListInventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class TodoListAppConfig {

    @Bean
    public TodoListApi todoService(TodoListInventory inventory) {
        log.info("Create todo Service {}", inventory);
        return new TodoListService(inventory);
    }

    @Bean
    @ConditionalOnProperty(name = "todolist.inventory", havingValue = "MONGO_DB", matchIfMissing = true)
    public TodoListInventory mongoDbTodolistInventory() {
        return new MongoDbTodoListInventoryAdapter();
    }

    @Bean
    @ConditionalOnProperty(name = "todolist.inventory", havingValue = "IN_MEMORY")
    public TodoListInventory inMemoryTodolistInventory() {
        return new InMemoryTodoListInventoryAdapter();
    }
}
