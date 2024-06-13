package christopheleblond.todolist.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "todolist")
public class AppProperties {

    private InventoryMode inventory = InventoryMode.MONGO_DB;

    public enum InventoryMode {
        MONGO_DB,
        IN_MEMORY
    }
}
