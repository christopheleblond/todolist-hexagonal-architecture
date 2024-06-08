package christopheleblond.todolist.api.resources;

import christopheleblond.todolist.domain.Todo;

public record TodoResource(String id, String label, String description, String state) {

    public TodoResource(Todo model) {
        this(model.id(), model.label(), model.description(), model.state().toString());
    }
}
