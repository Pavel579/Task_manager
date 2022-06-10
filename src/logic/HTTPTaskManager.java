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
import java.util.Set;

public class HTTPTaskManager extends FileBackedTasksManager {
    static KVTaskClient kvTaskClient;
    static Gson gson = new Gson();
    final String tasksKey = "tasks";
    final String subtasksKey = "subtasks";
    final String epicsKey = "epics";
    final String historyKey = "history";

    public HTTPTaskManager(String URL) throws IOException, InterruptedException {
        super(new File("resources/test.csv"));
        kvTaskClient = new KVTaskClient(URL);
    }

    public static HTTPTaskManager load() throws IOException, InterruptedException {
        HTTPTaskManager httpTaskManager = null;
        try {
            httpTaskManager = new HTTPTaskManager("http://localhost:8078");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        httpTaskManager.taskList.putAll(gson.fromJson(kvTaskClient.load("tasks"), new TypeToken<HashMap<Integer, Task>>() {
        }.getType()));
        httpTaskManager.subtaskList.putAll(gson.fromJson(kvTaskClient.load("subtasks"), new TypeToken<HashMap<Integer, Subtask>>() {
        }.getType()));
        httpTaskManager.epicList.putAll(gson.fromJson(kvTaskClient.load("epics"), new TypeToken<HashMap<Integer, Epic>>() {
        }.getType()));
        List<Task> historyList = gson.fromJson(kvTaskClient.load("history"), new TypeToken<List<Task>>() {
        }.getType());
        for (Task task : historyList) {
            httpTaskManager.getHistoryInMemory().add(task);
        }
        return httpTaskManager;
    }

    private void save(String key) {
        try {
            switch (key) {
                case "tasks":
                    kvTaskClient.put(key, gson.toJson(taskList));
                    break;
                case "subtasks":
                    kvTaskClient.put(key, gson.toJson(subtaskList));
                    break;
                case "epics":
                    kvTaskClient.put(key, gson.toJson(epicList));
                    break;
                case "history":
                    kvTaskClient.put(key, gson.toJson(historyInMemory.getHistory()));
                    System.out.println(historyInMemory.getHistory());
                    break;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save(tasksKey);
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save(tasksKey);
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save(tasksKey);
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save(tasksKey);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save(historyKey);
        return task;
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save(subtasksKey);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save(subtasksKey);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask task = super.getSubtaskById(id);
        save(historyKey);
        return task;
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        super.updateSubtask(updatedSubtask);
        save(subtasksKey);
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save(subtasksKey);
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save(epicsKey);
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save(epicsKey);
    }

    @Override
    public Task getEpicById(int id) {
        Task task = super.getEpicById(id);
        save(historyKey);
        return task;
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        super.updateEpic(updatedEpic);
        save(epicsKey);
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save(epicsKey);
    }

    @Override
    public HistoryManager getHistoryInMemory() {
        return super.getHistoryInMemory();
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return super.getTaskList();
    }

    @Override
    public ArrayList<Subtask> getSubtaskList() {
        return super.getSubtaskList();
    }

    @Override
    public ArrayList<Epic> getEpicList() {
        return super.getEpicList();
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }

    @Override
    public List<Task> getEpicSubtasks(int id) {
        return super.getEpicSubtasks(id);
    }
}
