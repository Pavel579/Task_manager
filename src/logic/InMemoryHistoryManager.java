package logic;

import data.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static Node<Task> historyNode;
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        linkLast(task);
        historyMap.put(task.getId(), historyNode);

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
        return getTask();
    }

    //Метод добавляет элемент в конец "списка" (создает ее Node)
    private void linkLast(Task e) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, e, null);
        historyNode = newNode;
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.setNext(newNode);
    }

    //Метод собирает все задачи в ArrayList и возвращает его
    private List<Task> getTask() {
        List<Task> arrayList = new ArrayList<>();
        for (Node<Task> x = first; x != null; x = x.getNext()) {
            arrayList.add(x.getItem());
        }
        return arrayList;
    }

    //Метод вырезает Node
    private void removeNode(Node<Task> node) {
        final Node<Task> next = node.getNext();
        final Node<Task> prev = node.getPrev();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }

        node.setItem(null);
    }

}
