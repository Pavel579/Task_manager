package utils;

import logic.HistoryManager;
import logic.InMemoryHistoryManager;
import logic.TaskManager;


public class Managers extends InMemoryHistoryManager {
    TaskManager manager;

    public Managers(TaskManager manager) {
        this.manager = manager;
    }

    public TaskManager getDefault(){
        return manager;
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}
