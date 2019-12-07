package com.titan.androidunittesting.ui.note;

import com.titan.androidunittesting.repository.NoteRepository;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteViewModelTest {

    //system under test
    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }



    /**
     * can't observe a note that hasn't been set
     * @throws Exception
     */
    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception {
        // Arrange

        // Act

        // Assert
    }


    /**
     * Observe a note has been set and onChanged will trigger in activity
     * @throws Exception
     */
    @Test
    void observeNote_whenSet() throws Exception {
        // Arrange

        // Act

        // Assert
    }



    /**
     * Insert a new note and observe row returned
     * @throws Exception
     */
    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange

        // Act

        // Assert
    }



    /**
     * insert: dont return a new row without observer
     * @throws Exception
     */
    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {
        // Arrange

        // Act

        // Assert
    }


    /**
     * set note, null title, throw exception
     * @throws Exception
     */
    @Test
    void setNote_nullTitle_throwException() throws Exception {
        // Arrange

        // Act

        // Assert
    }

}
