package logic;

import data.Task;

import java.util.List;

public interface HistoryManager {
    //Метод добавляет задачу в список истории просмотров
    void add(Task task);

    //Метод возвращает список истории просмотров задач
    List<Task> getHistory();

}
