package com.titan.androidunittesting.ui.note;

import com.titan.androidunittesting.models.Note;
import com.titan.androidunittesting.persistence.NoteDao;
import com.titan.androidunittesting.repository.NoteRepository;
import com.titan.androidunittesting.ui.Resource;
import com.titan.androidunittesting.util.InstantExecutorExtension;
import com.titan.androidunittesting.util.LiveDataTestUtil;
import com.titan.androidunittesting.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {

    //system under test
    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void initEach(){

        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);

    }


    /**
     * Insert note
     * Verify the correct method is called
     * Confirm observer is triggered
     * Confirm new rows inserted
     */
    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception {

        // Arrange

        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        // Act

        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert

        assertNull(note);
    }

    /**
     * Observe a note has been set and onChanged will trigger in activity
     * @throws Exception
     */
    @Test
    void observeNote_whenSet() throws Exception {

        // Arrange
        Note note = new Note (TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        // Act

        noteViewModel.setNote(note);
        Note observedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert

        assertEquals(note, observedNote);
    }


    /**
     * Insert a new note and observe row returned
     * @throws Exception
     */
    @Test
    void insertNote_returnRow() throws Exception {

        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;

        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, NoteRepository.INSERT_SUCCESS));
        Mockito.when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act

        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.insertNote());

        // Assert

        assertEquals(Resource.success(insertedRow, NoteRepository.INSERT_SUCCESS), returnedValue);
    }



    /**
     * insert: dont return a new row without observer
     * @throws Exception
     */
    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {

        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);


        // Act
        noteViewModel.setNote(note);

        // Assert
        verify(noteRepository, never()).insertNote(any(Note.class));
    }


    /**
     * set note, null title, throw exception
     * @throws Exception
     */
    @Test
    void setNote_nullTitle_throwException() throws Exception {

        // Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);


        //asserts
        assertThrows(Exception.class, new Executable(){

            @Override
            public void execute() throws Throwable {

                // Act
                noteViewModel.setNote(note);
            }
        });

    }



    /**
     * Update a new note and observe row returned
     * @throws Exception
     */
    @Test
    void updateNote_returnRow() throws Exception {

        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int updatedRow = 1;

        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(updatedRow, NoteRepository.UPDATE_SUCCESS));
        Mockito.when(noteRepository.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act

        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.updateNote());

        // Assert

        assertEquals(Resource.success(updatedRow, NoteRepository.UPDATE_SUCCESS), returnedValue);
    }

    /**
     * Update: dont return a new row without observer
     */
    @Test
    void dontReturnUpdateRowNumWithoutObserver() throws Exception {

        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);


        // Act
        noteViewModel.setNote(note);

        // Assert
        verify(noteRepository, never()).updateNote(any(Note.class));
    }





    /**
     * Insert a new note and observe row returned
     * @throws Exception
     */
    @Test
    void insertNote_returnRow_v2() throws Exception {

        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;

        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, NoteRepository.INSERT_SUCCESS));
        Mockito.when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act

        noteViewModel.setNote(note);
        noteViewModel.setIsNewNote(true);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.saveNote());

        // Assert

        assertEquals(Resource.success(insertedRow, NoteRepository.INSERT_SUCCESS), returnedValue);
    }



    /**
     * Update a new note and observe row returned
     * @throws Exception
     */
    @Test
    void updateNote_returnRow_v2() throws Exception {

        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int updatedRow = 1;

        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(updatedRow, NoteRepository.UPDATE_SUCCESS));
        Mockito.when(noteRepository.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act

        noteViewModel.setNote(note);
        noteViewModel.setIsNewNote(false);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.saveNote());

        // Assert

        assertEquals(Resource.success(updatedRow, NoteRepository.UPDATE_SUCCESS), returnedValue);
    }


    @Test
    void saveNote_shouldAllowSave_returnFalse() throws Exception {

        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setContent(null);

        //Act

        noteViewModel.setNote(note);
        noteViewModel.setIsNewNote(true);

        //Assert

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                noteViewModel.saveNote();
            }
        });

        assertEquals(NoteViewModel.NO_CONTENT_ERROR, exception.getMessage());

    }
}
