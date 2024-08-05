package usecase.createverification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class CreateVerificationInteractorTest {

    private ImageFinderInterface imageFinder;
    private CreateVerificationOutputBoundary createVerificationPresenter;
    private CreateVerificationInteractor interactor;

    @BeforeEach
    public void setUp() {
        imageFinder = mock(ImageFinder.class);
        createVerificationPresenter = mock(CreateVerificationPresenter.class);
        interactor = new CreateVerificationInteractor(createVerificationPresenter, imageFinder);
    }


    @Test
    public void createVerification_withValidImageFinder_callsVerificationCreated() {
        when(imageFinder.findImage("verifyimages/")).thenReturn("image");
        interactor.createVerification();
        ArgumentCaptor<CreateVerificationOutputData> captor = ArgumentCaptor.forClass(CreateVerificationOutputData.class);
        verify(createVerificationPresenter).verificationCreated(captor.capture());
        assertEquals("image", captor.getValue().getVerificationImageLocation());
    }

    @Test
    public void createVerification_withNoImageFound_callsVerificationCreatedWithNull() {
        when(imageFinder.findImage("verifyimages/")).thenReturn(null);
        interactor.createVerification();
        ArgumentCaptor<CreateVerificationOutputData> captor = ArgumentCaptor.forClass(CreateVerificationOutputData.class);
        verify(createVerificationPresenter).verificationCreated(captor.capture());
        assertNull(captor.getValue().getVerificationImageLocation());
    }

    @Test
    public void createVerification_withEmptyImageLocation_callsVerificationCreatedWithEmptyString() {
        when(imageFinder.findImage("verifyimages/")).thenReturn("");
        interactor.createVerification();
        ArgumentCaptor<CreateVerificationOutputData> captor = ArgumentCaptor.forClass(CreateVerificationOutputData.class);
        verify(createVerificationPresenter).verificationCreated(captor.capture());
        assertEquals("", captor.getValue().getVerificationImageLocation());
    }
}