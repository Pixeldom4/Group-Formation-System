package interfaceadapter;

import usecase.deleteproject.DeleteProjectOutputBoundary;

public class DeleteProjectPresenter implements DeleteProjectOutputBoundary {
    public DeleteProjectPresenter() {

    }

    @Override
    public void prepareDeleteSuccess() {
        System.out.println("delete success");
    }

    @Override
    public void prepareDeleteFailure(String error) {

    }
}
