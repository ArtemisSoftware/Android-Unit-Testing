package com.titan.androidunittesting.repository;

import com.titan.androidunittesting.models.Note;
import com.titan.androidunittesting.persistence.NoteDao;
import com.titan.androidunittesting.ui.Resource;
import com.titan.androidunittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import io.reactivex.Single;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
}
