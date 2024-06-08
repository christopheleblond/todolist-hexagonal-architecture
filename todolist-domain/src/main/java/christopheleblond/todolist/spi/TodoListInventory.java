package christopheleblond.todolist.spi;

import christopheleblond.todolist.domain.TodoList;

import java.util.List;

public interface TodoListInventory {

    TodoList findById(String id);

    TodoList add(String title, String description);

    void remove(String id);

    List<TodoList> findAll();

    void update(TodoList list);
}
