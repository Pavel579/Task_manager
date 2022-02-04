package Data;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        setStatus("NEW");
        setName(name);
        setDescription(description);
        this.epicId = epicId;

    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                " Status=" + getStatus() +
                '}';
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return this.epicId;
    }


    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

}
