package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.KVTaskClient;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {
    private final KVTaskClient kvTaskClient;
    private final Gson gson = new Gson();

    public HTTPTaskManager(String URL, boolean load) throws IOException, InterruptedException {
        super(new File("resources/test.csv"));
        kvTaskClient = new KVTaskClient(URL);
        if (load) {
            load();
        }
    }

    private void load() {
        try {
            this.taskList.putAll(gson.fromJson(kvTaskClient.load("tasks"), new TypeToken<HashMap<Integer, Task>>() {
            }.getType()));
            this.subtaskList.putAll(gson.fromJson(kvTaskClient.load("subtasks"), new TypeToken<HashMap<Integer, Subtask>>() {
            }.getType()));
            this.epicList.putAll(gson.fromJson(kvTaskClient.load("epics"), new TypeToken<HashMap<Integer, Epic>>() {
            }.getType()));
            List<Integer> historyList = gson.fromJson(kvTaskClient.load("history"), new TypeToken<List<Integer>>() {
            }.getType());
            for (Integer id : historyList) {
                if (this.taskList.containsKey(id)) {
                    this.historyInMemory.add(this.taskList.get(id));
                } else if (this.subtaskList.containsKey(id)) {
                    this.historyInMemory.add(this.subtaskList.get(id));
                } else if (this.epicList.containsKey(id)) {
                    this.historyInMemory.add(this.epicList.get(id));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void save() {
        try {
            kvTaskClient.put("tasks", gson.toJson(taskList));
            kvTaskClient.put("subtasks", gson.toJson(subtaskList));
            kvTaskClient.put("epics", gson.toJson(epicList));
            List<Integer> historyId = new ArrayList<>();
            for (Task task : historyInMemory.getHistory()) {
                historyId.add(task.getId());
            }
            kvTaskClient.put("history", gson.toJson(historyId));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
