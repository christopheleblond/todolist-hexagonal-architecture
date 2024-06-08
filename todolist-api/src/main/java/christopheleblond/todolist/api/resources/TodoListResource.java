package christopheleblond.todolist.api.resources;

import christopheleblond.todolist.domain.TodoList;

import java.util.List;

public record TodoListResource(String id, String title, String description, List<TodoResource> tasks) {

    public TodoListResource(TodoList model) {
        this(model.getId(), model.getTitle(), model.getDescription(), model.getTasks().stream().map(TodoResource::new).toList());
    }
}
