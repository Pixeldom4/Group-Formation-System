package usecase.deleteproject;

public class DeleteProjectInputData {
    private final int userId;
    public DeleteProjectInputData(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
