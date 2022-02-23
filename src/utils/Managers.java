package utils;

import logic.HistoryManager;
import logic.InMemoryHistoryManager;
import logic.InMemoryTaskManager;
import logic.TaskManager;

public class Managers {
    private static final HistoryManager historyManager = new InMemoryHistoryManager();
    private static final TaskManager manager = new InMemoryTaskManager();

    private Managers() {
    }

    public static TaskManager getDefault() {
        return manager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }


}
