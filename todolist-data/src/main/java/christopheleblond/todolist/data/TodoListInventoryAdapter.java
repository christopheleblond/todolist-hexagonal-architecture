package christopheleblond.todolist.data;

import christopheleblond.todolist.domain.TodoList;
import christopheleblond.todolist.spi.TodoListInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

public class TodoListInventoryAdapter implements TodoListInventory {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TodoList findById(String id) {
        return mongoTemplate.findById(id, TodoList.class);
    }

    @Override
    public TodoList add(String title, String description) {
        return mongoTemplate.insert(new TodoList(UUID.randomUUID().toString(), title, description, List.of()));
    }

    @Override
    public void remove(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), TodoList.class);
    }

    @Override
    public List<TodoList> findAll() {
        return mongoTemplate.findAll(TodoList.class);
    }

    @Override
    public void update(TodoList list) {
        mongoTemplate.save(list);
    }
}
