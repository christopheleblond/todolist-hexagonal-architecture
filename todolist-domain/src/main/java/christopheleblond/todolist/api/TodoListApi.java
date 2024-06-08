package christopheleblond.todolist.api;

import christopheleblond.todolist.domain.Todo;
import christopheleblond.todolist.domain.TodoList;

import java.util.List;

public interface TodoListApi {

    TodoList findTodoListById(String id);

    TodoList addTodoList(String title, String description);

    List<TodoList> findAllTodoList();

    void removeTodoListById(String id);

    Todo addTask(String listId, String label, String description) throws IllegalAccessException;

    void removeTask(String listId, String todoId) throws IllegalAccessException;
}
