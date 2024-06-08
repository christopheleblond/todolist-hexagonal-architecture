package christopheleblond.todolist.domain;

import lombok.Builder;

@Builder
public record Todo(String id, String label, String description, TodoState state) {
}
