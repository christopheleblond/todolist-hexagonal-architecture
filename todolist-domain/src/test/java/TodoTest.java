import christopheleblond.todolist.domain.Todo;
import christopheleblond.todolist.domain.TodoState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TodoTest {

    @Test
    public void todoCreationTest() {
        Todo todo = new Todo(UUID.randomUUID().toString(), "Todo", "Description", TodoState.CREATED);

        Assertions.assertThat(todo.label()).isEqualTo("Todo");
    }
}
