package christopheleblond.todolist.domain;

import christopheleblond.todolist.api.TodoListApi;
import christopheleblond.todolist.spi.TodoListInventory;

import java.util.List;
import java.util.UUID;

public class TodoListService implements TodoListApi {

    private TodoListInventory todoListInventory;

    public TodoListService(TodoListInventory inventory) {
        this.todoListInventory = inventory;
    }

    @Override
    public TodoList findTodoListById(String id) {
        return todoListInventory.findById(id);
    }

    @Override
    public TodoList addTodoList(String title, String description) {
        return todoListInventory.add(title, description);
    }

    @Override
    public List<TodoList> findAllTodoList() {
        return todoListInventory.findAll();
    }

    @Override
    public void removeTodoListById(String id) {
        todoListInventory.remove(id);
    }

    @Override
    public Todo addTask(String listId, String label, String description) throws IllegalAccessException {
        TodoList list = todoListInventory.findById(listId);
        Todo task = new Todo(UUID.randomUUID().toString(), label, description, TodoState.CREATED);
        if (list != null) {
            list.getTasks().add(task);
            todoListInventory.update(list);
            return task;
        } else {
            throw new IllegalAccessException("No list identified by " + listId + " found !");
        }
    }

    @Override
    public void removeTask(String listId, String todoId) throws IllegalAccessException {
        TodoList list = todoListInventory.findById(listId);

        if (list != null) {
            boolean removed = list.getTasks().removeIf(t -> t.id().equals(todoId));
            if (!removed) {
                throw new IllegalAccessException("No task identified by " + todoId + " found in list " + listId + " !");
            }
        } else {
            throw new IllegalAccessException("No list identified by " + listId + " found !");
        }
    }
}
