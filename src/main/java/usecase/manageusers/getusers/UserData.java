package usecase.manageusers.getusers;

import java.util.HashSet;

/**
 * Data transfer object for user data.
 *
 * @param userId              the ID of the user.
 * @param firstName           the first name of the user.
 * @param lastName            the last name of the user.
 * @param userEmail           the email address of the user.
 * @param tags                a set of tags associated with the user.
 * @param desiredCompensation the desired compensation of the user.
 * @param isOwner             flag indicating if the user is the owner.
 */
public record UserData(int userId, String firstName, String lastName, String userEmail, HashSet<String> tags,
                       double desiredCompensation, boolean isOwner) {
}
