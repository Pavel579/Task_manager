package logic;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        linkLast(task);
        historyMap.put(task.getId(), last);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    //Метод добавляет элемент в конец "списка" (создает ее Node)
    private void linkLast(Task task) {
        Node newNode = new Node(last, task, null);
        if (isNull(first)) {
            first = newNode;
        } else {
            last.setNext(newNode);
        }
        last = newNode;
    }

    //Метод собирает все задачи в ArrayList и возвращает его
    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> current = first;
        while (Objects.nonNull(current)) {
            tasks.add(current.getItem());
            current = current.getNext();
        }
        return tasks;
    }

    //Метод вырезает Node
    private void removeNode(Node<Task> node) {
        Node<Task> next = node.getNext();
        Node<Task> prev = node.getPrev();

        if (node.hasPrev()) {
            prev.setNext(next);
            node.setPrev(null);
        } else {
            first = next;
        }

        if (node.hasNext()) {
            next.setPrev(prev);
            node.setNext(null);
        } else {
            last = prev;
        }

        node.setItem(null);
    }
}