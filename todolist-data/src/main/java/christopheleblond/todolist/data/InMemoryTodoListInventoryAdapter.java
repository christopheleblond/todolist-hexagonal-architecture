package christopheleblond.todolist.data;

import christopheleblond.todolist.domain.TodoList;
import christopheleblond.todolist.spi.TodoListInventory;

import java.util.List;

public class InMemoryTodoListInventoryAdapter implements TodoListInventory {



    @Override
    public TodoList findById(String id) {
        return null;
    }

    @Override
    public TodoList add(String title, String description) {
        return null;
    }

    @Override
    public void remove(String id) {

    }

    @Override
    public List<TodoList> findAll() {
        return List.of();
    }

    @Override
    public void update(TodoList list) {

    }
}
