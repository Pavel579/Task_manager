package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TaskStatus;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    Subtask subtask1;
    Subtask subtask2;
    Epic epic;
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    @BeforeEach
    public void beforeEach() {
        subtask1 = new Subtask(2, "name", "description", 1);
        subtask2 = new Subtask(3, "name", "description", 1);
        epic = new Epic(1, "name", "description");
        epic.addSubtaskIdInEpic(subtask1.getId());
        epic.addSubtaskIdInEpic(subtask2.getId());
    }

    @Test
    public void shouldReturnNewWhenAllSubtasksNew() {
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void shouldReturnDoneWhenAllSubtasksDone() {
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void shouldReturnInProgressWhenSubtasksNewAndDone() {
        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.DONE);
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldReturnInProgressWhenSubtasksInProgress() {
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        subtaskList.put(subtask1.getId(), subtask1);
        subtaskList.put(subtask2.getId(), subtask2);
        epic.checkEpicStatus(subtaskList);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldReturnNewWhenNoSubtasks() {
        Epic epic2 = new Epic(4, "name", "description");
        assertEquals(TaskStatus.NEW, epic2.getStatus());
    }

}