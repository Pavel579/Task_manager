package logic;

public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    T fileTaskManager;

    public abstract void createManager();
}
