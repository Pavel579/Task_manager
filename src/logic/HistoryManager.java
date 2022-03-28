package logic;

import data.Task;

import java.util.List;

public interface HistoryManager {
    //Метод добавляет задачу в список истории просмотров
    void add(Task task);

    //Метод удаляет задачу из истории просмотров
    void remove(int id);

    //Метод возвращает список истории просмотров задач
    List<Task> getHistory();
}
