package com.titan.androidunittesting.repository;

import androidx.lifecycle.MutableLiveData;

import com.titan.androidunittesting.models.Note;
import com.titan.androidunittesting.persistence.NoteDao;
import com.titan.androidunittesting.ui.Resource;
import com.titan.androidunittesting.util.InstantExecutorExtension;
import com.titan.androidunittesting.util.LiveDataTestUtil;
import com.titan.androidunittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.DELETE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
public class NoteRepositoryTest {

    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

    //system under test
    private NoteRepository noteRepository;

    //another way to instanciate
    //@Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
        //another way to instanciate
        //MockitoAnnotations.initMocks(this);

        noteDao = Mockito.mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }


    /**
     * Insert note
     * Verify the correct method is called
     * Confirm observer is triggered
     * Confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {

        //Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);


        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: " + returnedValue.data);
        assertEquals(Resource.success(1, NoteRepository.INSERT_SUCCESS), returnedValue);


        //or test using RxJava
        /*
        noteRepository.insertNote(NOTE1)
                .test()
                .await()
                .assertValue(Resource.success(1, NoteRepository.INSERT_SUCCESS));
         */
    }


    /**
     * Insert note
     * Failure (return -1)
     */
    @Test
    void insertNote_returnFailure() throws Exception {

        //Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);


        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: " + returnedValue);
        assertEquals(Resource.error(null, NoteRepository.INSERT_FAILURE), returnedValue);
    }


    /**
     * Insert note
     * Null title
     * confirm throw exception
     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NoteRepository.NOTE_TITLE_NULL, exception.getMessage());

    }

    /**
     * Update note
     * verify correct method is called
     * confirm observer is trigger
     * confirm number of rows updated
     */
    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {

        //Arrange
        final int updatedRow = 1;

        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updatedRow));

        //Act

        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(updatedRow, NoteRepository.UPDATE_SUCCESS), returnedValue);

    }

    /**
     * Update note
     * Failure (-1)
     */
    @Test
    void updateNote_returnFailure() throws Exception {

        //Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDao.updateNote(any(Note.class))).thenReturn(returnedData);

        //Act

        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, NoteRepository.UPDATE_FAILURE), returnedValue);

    }

    /**
     * Update note
     * null title
     * throw exception
     */
    @Test
    void updateNote_nullTitle_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        assertEquals(NoteRepository.NOTE_TITLE_NULL, exception.getMessage());
    }


    /**
     * Delete note
     * null id
     * Throw exception
     */
    @Test
    void deleteNote_nullId_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final  Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });

        assertEquals(NoteRepository.INVALID_NOTE_ID, exception.getMessage());
    }

    /**
     * Delete note
     * delete success
     * return Resource.success with delete row
     */
    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {

        //Arrange

        final int deleteRow = 1;
        Resource<Integer> successResponse = Resource.success(deleteRow, NoteRepository.DELETE_SUCCESS);
        LiveDataTestUtil< Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deleteRow));

        //Act

        Resource<Integer> observeResource = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        //Assert

        assertEquals(successResponse, observeResource);

    }


    /**
     * Delete note
     * Delete failure
     * return resource.error
     */
    @Test
    void deleteNote_deleteFailure_returnResourceFailure() throws Exception {

        //Arrange

        final int deletedRow = -1;
        Resource<Integer> errorResponse = Resource.error(null, NoteRepository.DELETE_FAILURE);
        LiveDataTestUtil< Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        //Act

        Resource<Integer> observeResource = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        //Assert

        assertEquals(errorResponse, observeResource);

    }

    /**
     * retrieve notes
     * return list of notes
     */
    @Test
    void getNotes_returnListWithNotes() throws Exception {

        //Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);

        when(noteDao.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(notes, observedData);
    }

    /**
     * Retrieve notes
     * Return empty list
     */
    @Test
    void getNotes_returnEmptyList() throws Exception {

        //Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);

        when(noteDao.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(notes, observedData);
    }
}
