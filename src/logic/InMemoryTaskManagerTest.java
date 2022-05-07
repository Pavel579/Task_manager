package logic;

import java.io.File;

public class InMemoryTaskManagerTest extends TaskManagerTest {

    @Override
    public void createManager() {
        taskManager = new InMemoryTaskManager();
        File file = new File("test.csv");
        fileTaskManager = new FileBackedTasksManager(file);
    }
}
