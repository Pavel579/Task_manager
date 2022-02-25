package logic;

class Node<Task> {
    private Task item;
    private Node<Task> next;
    private Node<Task> prev;

    Node(Node<Task> prev, Task element, Node<Task> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }

    Task getItem() {
        return item;
    }

    void setItem(Task item) {
        this.item = item;
    }

    Node<Task> getNext() {
        return next;
    }

    void setNext(Node<Task> next) {
        this.next = next;
    }

    Node<Task> getPrev() {
        return prev;
    }

    void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}