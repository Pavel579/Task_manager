package logic;

import data.Epic;
import data.Subtask;
import data.Task;
import exceptions.ManagerSaveException;
import utils.TaskStatus;
import utils.TaskType;

import java.io.*;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private final Set<Task> tasks = new HashSet<>();
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        System.out.println(fb.epicList);
    }

    static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fbm = new FileBackedTasksManager(file);
        try {
            FileReader readFile = new FileReader(file.getName());
            BufferedReader br = new BufferedReader(readFile);
            List<String> line = new ArrayList<>();
            while (br.ready()) {
                line.add(br.readLine());
            }
            for (int i = 1; i < line.size(); i++) {
                if (line.get(i).equals("")) {
                    List<Integer> list = fromHistoryString(line.get(i + 1));
                    for (Integer id : list) {
                        if (fbm.taskList.containsKey(id)) {
                            fbm.historyInMemory.add(fbm.taskList.get(id));
                        } else if (fbm.subtaskList.containsKey(id)) {
                            fbm.historyInMemory.add(fbm.subtaskList.get(id));
                        } else if (fbm.epicList.containsKey(id)) {
                            fbm.historyInMemory.add(fbm.epicList.get(id));
                        }
                    }
                    break;
                } else {
                    fbm.fromString(line.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException();
        }
        return fbm;
    }

    static List<Integer> fromHistoryString(String value) {
        List<Integer> historyIdList;
        String[] historyStringIdArray = value.split(",");
        Integer[] historyIdArray = new Integer[historyStringIdArray.length];
        for (int i = 0; i < historyStringIdArray.length; i++) {
            historyIdArray[i] = Integer.parseInt(historyStringIdArray[i]);
        }
        historyIdList = Arrays.asList(historyIdArray);
        return historyIdList;
    }

    Task fromString(String value) {
        String[] taskArray = value.split(",");
        Task task = null;
        id = Integer.parseInt(taskArray[0]) - 1;
        switch (taskArray[1]) {
            case "TASK":
                task = new Task(taskArray[2], taskArray[4]);
                task.setStatus(TaskStatus.valueOf(taskArray[3]));
                super.createTask(task);
                break;
            case "EPIC":
                task = new Epic(taskArray[2], taskArray[4]);
                super.createEpic((Epic) task);
                break;
            case "SUBTASK":
                task = new Subtask(taskArray[2], taskArray[4], Integer.parseInt(taskArray[5]));
                task.setStatus(TaskStatus.valueOf(taskArray[3]));
                super.createSubtask((Subtask) task);
                break;
        }
        return task;
    }

    void save() {
        tasks.addAll(taskList.values());
        tasks.addAll(subtaskList.values());
        tasks.addAll(epicList.values());

        try (Writer fileWriter = new FileWriter(file.getName())) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task task : tasks) {
                fileWriter.write(toString(task));
            }
            fileWriter.write("\n");
            for (int i = 0; i < historyInMemory.getHistory().size(); i++) {
                String historyIds = historyInMemory.getHistory().get(i).getId() + "";
                if (i != historyInMemory.getHistory().size() - 1) {
                    historyIds = historyIds + ",";
                }
                fileWriter.write(historyIds);
            }

        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    String toString(Task task) {
        TaskType type = TaskType.TASK;
        String epicId = "";
        if (task == null) {
            return null;
        } else if (task.getClassType().equals("subtask")) {
            Subtask subtask = (Subtask) task;
            epicId = subtask.getEpicId() + "";
            type = TaskType.SUBTASK;
        } else if (task.getClassType().equals("epic")) {
            type = TaskType.EPIC;
        }
        return task.getId() + "," + type + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "," + epicId + "\n";
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;

    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask task = super.getSubtaskById(id);
        save();
        return task;
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        super.updateSubtask(updatedSubtask);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public Task getEpicById(int id) {
        Task task = super.getEpicById(id);
        save();
        return task;
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        super.updateEpic(updatedEpic);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }
}
