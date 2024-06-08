package christopheleblond.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TodoList {

    private String id;

    private String title;

    private String description;

    private List<Todo> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    public Todo getTaskById(String id) {
        return tasks.stream().filter(t -> t.id().equals(id)).findFirst().orElse(null);
    }

    public void addTask(String label, String description) {
        addTask(new Todo(UUID.randomUUID().toString(), label, description, TodoState.CREATED));
    }

    public void addTask(Todo task) {
        var exists = getTaskById(task.id());
        if (exists != null) {
            throw new IllegalArgumentException("Task with id " + task.id() + " already exists in list " + id);
        } else {
            tasks.add(task);
        }
    }

    public boolean removeTask(String id) {
        return tasks.removeIf(t -> t.id().equals(id));
    }
}
