package christopheleblond.todolist.api.controller;

import christopheleblond.todolist.api.TodoListApi;
import christopheleblond.todolist.api.resources.TodoListResource;
import christopheleblond.todolist.api.resources.TodoResource;
import christopheleblond.todolist.domain.Todo;
import christopheleblond.todolist.domain.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos")
public class TodoListController {

    @Autowired
    private TodoListApi todoListApi;

    @GetMapping
    public List<TodoListResource> findAllLists() {
        return todoListApi.findAllTodoList().stream().map(TodoListResource::new).toList();
    }

    @GetMapping("/{id}")
    public TodoListResource findById(@PathVariable String id) {
        var list = todoListApi.findTodoListById(id);
        if (list != null) {
            return new TodoListResource(list);
        } else {
            return null;
        }
    }

    @PutMapping
    public ResponseEntity<TodoListResource> createTodoList(TodoListResource todoList) {
        TodoList created = todoListApi.addTodoList(todoList.title(), todoList.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TodoListResource(created));
    }

    @DeleteMapping("/{id}")
    public void deleteTodoList(@PathVariable String id) {
        todoListApi.removeTodoListById(id);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<TodoResource> addTask(@PathVariable String id, @RequestBody TodoResource task) throws IllegalAccessException {
        Todo created = todoListApi.addTask(id, task.label(), task.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TodoResource(created));
    }

    @GetMapping("/{id}/tasks")
    public List<TodoResource> getAllTasks(@PathVariable String id) {
        return todoListApi.findTodoListById(id).getTasks().stream().map(TodoResource::new).toList();
    }

    @GetMapping("/{id}/tasks/{taskId}")
    public TodoResource getTaskById(@PathVariable String id, @PathVariable String taskId) {
        var todo = todoListApi.findTodoListById(id).getTaskById(taskId);
        if (todo != null) {
            return new TodoResource(todo);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}/tasks/{taskId}")
    public void removeTask(@PathVariable String id, @PathVariable String taskId) throws IllegalAccessException {
        todoListApi.removeTask(id, taskId);
    }
}
