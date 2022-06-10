package logic;

import java.io.IOException;

public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    T fileTaskManager;
    T httpTaskManager;

    public abstract void createManager() throws IOException, InterruptedException;
}
