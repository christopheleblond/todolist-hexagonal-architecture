package christopheleblond.todolist.api;

import christopheleblond.todolist.api.configuration.TodolistTestAppConfig;
import christopheleblond.todolist.api.controller.TodoListController;
import christopheleblond.todolist.domain.Todo;
import christopheleblond.todolist.domain.TodoList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TodoListApplication.class, TodolistTestAppConfig.class},
        properties = {
                "spring.main.allow-bean-definition-overriding=true",
                "todolist.stub=true"
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TodoListApplicationTest {

    @Autowired
    private TodoListController controller;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_context_loads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void should_get_all_todo_list() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].id").value("001"));
    }

    @Test
    void should_get_list_when_find_by_id() throws Exception {
        mockMvc.perform(get("/todos/001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("001"))
                .andExpect(jsonPath("$.title").value("List 1"));
    }

    @Test
    void should_contain_new_list_when_create_list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/todos"))
                .andExpect(status().isCreated())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        TodoList created = mapper.readValue(json, TodoList.class);

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder("001", "002", created.getId(), "003")));
    }

    @Test
    void should_not_contain_deleted_list_when_delete_by_id() throws Exception {
        mockMvc.perform(delete("/todos/002"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder("001", "003")));
        ;
    }

    @Test
    void should_return_all_tasks_when_find_all_tasks() throws Exception {
        mockMvc.perform(get("/todos/002/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    void should_return_task_when_find_task_by_id() throws Exception {
        mockMvc.perform(get("/todos/001/tasks/t1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("t1"))
                .andExpect(jsonPath("$.label").value("label1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void should_contain_new_task_when_add_new_task_to_list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/todos/003/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new Todo(null, "Test", "Description", null))))
                .andExpect(status().isCreated())
                .andReturn();

        Todo todo = mapper.readValue(mvcResult.getResponse().getContentAsString(), Todo.class);

        mockMvc.perform(get("/todos/003/tasks/" + todo.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Test"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void should_not_contain_task_when_delete() throws Exception {
        mockMvc.perform(delete("/todos/003/tasks/t1"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/todos/003/tasks"))
                .andReturn();

        List<Todo> todos = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Todo>>() {
        });

        assertThat(todos).hasSize(2);
    }
}