package usecase.deleteproject;

import viewmodel.MyProjectsPanelViewModel;

public class DeleteProjectUseCaseFactory {

    private DeleteProjectUseCaseFactory() {}

    public static DeleteProjectController createDeleteProjectController(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        DeleteProjectOutputBoundary deleteProjectPresenter = new DeleteProjectPresenter(myProjectsPanelViewModel);
        DeleteProjectInputBoundary deleteProjectInteractor = new DeleteProjectInteractor(deleteProjectPresenter);
        return new DeleteProjectController(deleteProjectInteractor);
    }
}
