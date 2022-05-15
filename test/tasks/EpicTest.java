package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    private final HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private Subtask subtask1;
    private Subtask subtask2;
    private Epic epic;

    @BeforeEach
    void beforeEach() {
        subtask1 = new Subtask(2, "name", "description", 1);
        subtask2 = new Subtask(3, "name", "description", 1);
        epic = new Epic(1, "name", "description");
        epic.addSubtaskIdInEpic(subtask1.getId());
        epic.addSubtaskIdInEpic(subtask2.getId());
    }

    @Test
    void shouldReturnNewWhenAllSubtasksNew() {
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    void shouldReturnDoneWhenAllSubtasksDone() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void shouldReturnInProgressWhenSubtasksNewAndDone() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void shouldReturnInProgressWhenSubtasksInProgress() {
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void shouldReturnNewWhenNoSubtasks() {
        Epic epic2 = new Epic(4, "name", "description");
        assertEquals(Status.NEW, epic2.getStatus());
    }

}