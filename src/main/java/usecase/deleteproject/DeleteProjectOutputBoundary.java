package usecase.deleteproject;

public interface DeleteProjectOutputBoundary {
    void prepareDeleteSuccess();
    void prepareDeleteFailure(String error);
}
