package usecase.manageusers.getusers;

import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import dataaccess.IProjectRepository;
import entities.User;

import java.util.HashSet;

/**
 * Interactor class for retrieving users.
 * Implements the input boundary to handle user retrieval logic.
 */
public class GetUsersInteractor implements GetUsersInputBoundary {
    private final IUserProjectsRepository userProjectsRepository;
    private final IUserRepository userRepository;
    private final IProjectRepository projectRepository;
    private final GetUsersOutputBoundary getUsersPresenter;

    /**
     * Constructs a GetUsersInteractor with the specified repositories and presenter.
     *
     * @param userProjectsRepository the repository to handle user-project associations.
     * @param userRepository the repository to handle user data.
     * @param projectRepository the repository to handle project data.
     * @param getUsersPresenter the presenter to handle output.
     */
    public GetUsersInteractor(IUserProjectsRepository userProjectsRepository, IUserRepository userRepository, IProjectRepository projectRepository, GetUsersOutputBoundary getUsersPresenter) {
        this.userProjectsRepository = userProjectsRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.getUsersPresenter = getUsersPresenter;
    }

    /**
     * Retrieves users based on the provided input data.
     *
     * @param inputData the input data for retrieving users.
     */
    @Override
    public void getUsers(GetUsersInputData inputData) {
        int projectId = inputData.getProjectId();
        if (projectId == 0) {
            getUsersPresenter.prepareFailView("Please select a project.");
            return;
        }

        HashSet<Integer> userIds = userProjectsRepository.getUserIdsForProject(projectId);
        HashSet<UserData> userData = new HashSet<>();

        for (int userId : userIds) {
            User user = userRepository.getUserById(userId);
            boolean isOwner = projectRepository.getOwnerId(projectId) == userId;

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getUserEmail();
            HashSet<String> tags = new HashSet<>(user.getTags());
            double desiredCompensation = user.getDesiredCompensation();

            userData.add(new UserData(userId, firstName, lastName, email, tags, desiredCompensation, isOwner));
        }

        getUsersPresenter.prepareSuccessView(new GetUsersOutputData(userData));
    }
}