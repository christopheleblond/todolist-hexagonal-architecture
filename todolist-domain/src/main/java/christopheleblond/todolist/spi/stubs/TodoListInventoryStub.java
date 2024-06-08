package christopheleblond.todolist.spi.stubs;

import christopheleblond.todolist.domain.Todo;
import christopheleblond.todolist.domain.TodoList;
import christopheleblond.todolist.domain.TodoState;
import christopheleblond.todolist.spi.TodoListInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoListInventoryStub implements TodoListInventory {

    private List<TodoList> todoLists = new ArrayList<>(List.of(new TodoList[]{
            new TodoList("001", "List 1", "Description list 1", new ArrayList<>(List.of(new Todo[]{
                    new Todo("t1", "label1", "Description 1", TodoState.DONE),
                    new Todo("t2", "label2", "Description 2", TodoState.ACTIVE),
                    new Todo("t3", "label3", "Description 3", TodoState.ACTIVE),
                    new Todo("t4", "label4", "Description 4", TodoState.CREATED),
                    new Todo("t5", "label5", "Description 5", TodoState.DONE),
            }))),
            new TodoList("002", "List 2", "Description list 2", new ArrayList<>(List.of(new Todo[]{
                    new Todo("t1", "label1", "Description 1", TodoState.DONE),
                    new Todo("t4", "label4", "Description 4", TodoState.CREATED),
                    new Todo("t5", "label5", "Description 5", TodoState.DONE),
            }))),
            new TodoList("003", "List 3", "Description list 3", new ArrayList<>(List.of(new Todo[]{
                    new Todo("t1", "label1", "Description 1", TodoState.DONE),
                    new Todo("t4", "label4", "Description 4", TodoState.CREATED),
                    new Todo("t5", "label5", "Description 5", TodoState.CREATED),
            })))
    }));

    @Override
    public TodoList findById(String id) {
        return todoLists.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public TodoList add(String title, String description) {
        TodoList list = new TodoList();
        list.setId(UUID.randomUUID().toString());
        list.setTitle(title);
        list.setDescription(description);

        todoLists.add(list);
        return list;
    }

    @Override
    public void remove(String id) {
        todoLists.removeIf(todoList -> todoList.getId().equals(id));
    }

    @Override
    public List<TodoList> findAll() {
        return todoLists;
    }

    @Override
    public void update(TodoList list) {
        remove(list.getId());
        todoLists.add(list);
    }
}
