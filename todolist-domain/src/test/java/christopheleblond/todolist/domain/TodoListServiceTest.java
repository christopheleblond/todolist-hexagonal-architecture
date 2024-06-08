package christopheleblond.todolist.domain;

import christopheleblond.todolist.spi.stubs.TodoListInventoryStub;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class TodoListServiceTest {

    private TodoListService todoListService;

    @BeforeEach
    void setUp() {
        todoListService = new TodoListService(new TodoListInventoryStub());
    }

    @Test
    public void should_return_todolist_when_find_by_id() {
        TodoList todoListById = todoListService.findTodoListById("002");

        assertThat(todoListById).isNotNull();
        assertThat(todoListById).hasFieldOrPropertyWithValue("title", "List 2");
        assertThat(todoListById).hasFieldOrPropertyWithValue("description", "Description list 2");
        assertThat(todoListById.getTasks()).hasSize(3);
    }

    @Test
    public void should_return_all_list_when_find_all() {
        List<TodoList> allTodoList = todoListService.findAllTodoList();

        assertThat(allTodoList)
                .isNotNull()
                .hasSize(3)
                .extracting("id").containsExactly("001", "002", "003");
    }

    @Test
    public void should_contain_new_list_when_add() {
        TodoList todoList = todoListService.addTodoList("List 4", "Description list 4");

        assertThat(todoListService.findAllTodoList()).hasSize(4);
        assertThat(todoListService.findTodoListById(todoList.getId())).isNotNull().hasFieldOrPropertyWithValue("title", "List 4");
    }

    @Test
    public void should_not_contain_list_when_remove() {
        todoListService.removeTodoListById("002");

        assertThat(todoListService.findAllTodoList()).hasSize(2);
        assertThat(todoListService.findTodoListById("002")).isNull();
    }

    @Test
    public void should_contain_new_task_when_add_task() throws IllegalAccessException {
        var newTask = todoListService.addTask("001", "003", "New Task");
        TodoList list = todoListService.findTodoListById("001");

        assertThat(list.getTasks()).hasSize(6);
        assertThat(list.getTaskById(newTask.id())).isNotNull();
    }

    @Test
    public void should_fail_when_add_task_with_unknown_list_id() {
        assertThatThrownBy(
                () -> todoListService.addTask("0099", "003", "New Task"))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessage("No list identified by 0099 found !");
    }

    @Test
    public void should_not_contain_task_when_remove_task() throws IllegalAccessException {
        todoListService.removeTask("002", "t4");

        TodoList list = todoListService.findTodoListById("002");

        assertThat(list.getTasks()).hasSize(2);
        assertThat(list.getTaskById("t4")).isNull();
    }

    @Test
    public void should_fail_when_remove_task_with_unknown_list() {
        assertThatThrownBy(() -> todoListService.removeTask("999", "t1"))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessage("No list identified by 999 found !");
    }

    @Test
    public void should_fail_when_remove_unknown_task_in_list() {
        assertThatThrownBy(() -> todoListService.removeTask("001", "999"))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessage("No task identified by 999 found in list 001 !");
    }
}