package use_case.delete_case;

public interface DeleteProjectBoundary {
    void prepareDeleteSuccess();
    void prepareDeleteFailure(String error);
}
