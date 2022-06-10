package utils;

import logic.HTTPTaskManager;
import logic.HistoryManager;
import logic.InMemoryHistoryManager;
import logic.InMemoryTaskManager;
import logic.TaskManager;

import java.io.IOException;

public class Managers {
    private static final HistoryManager historyManager = new InMemoryHistoryManager();
    ;
    private static final TaskManager manager = new InMemoryTaskManager();
    private static TaskManager httpManager = null;

    static {
        try {
            httpManager = new HTTPTaskManager("http://localhost:8078");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager getDefault() {
        return manager;
    }

    public static TaskManager getHttpManager() {
        return httpManager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
