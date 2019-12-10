package com.titan.androidunittesting.ui.note;

import androidx.lifecycle.MutableLiveData;

import com.titan.androidunittesting.models.Note;
import com.titan.androidunittesting.repository.NoteRepository;
import com.titan.androidunittesting.ui.Resource;
import com.titan.androidunittesting.ui.noteslist.NotesListViewModel;
import com.titan.androidunittesting.util.InstantExecutorExtension;
import com.titan.androidunittesting.util.LiveDataTestUtil;
import com.titan.androidunittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.DELETE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
public class NotesListViewModelTest {

    //system under test
    private NotesListViewModel viewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        viewModel = new NotesListViewModel(noteRepository);
    }

    /**
     * Retrieve list of notes
     * observe lisst
     * return list
     */
    @Test
    void retriveNotes_returnNotesList() throws Exception {

        //Arrange
        List<Note> returnedData = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);

        when(noteRepository.getNotes()).thenReturn(returnedValue);


        //Act

        viewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(viewModel.observeNotes());


        //Assert
        assertEquals(returnedData, observedData);

    }

    /**
     * retrireve list pf notes
     * observe the list
     * return emty list
     */
    @Test
    void retriveNotes_returnEmptyNotesList() throws Exception {

        //Arrange
        List<Note> returnedData = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);

        when(noteRepository.getNotes()).thenReturn(returnedValue);

        //Act

        viewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(viewModel.observeNotes());


        //Assert
        assertEquals(returnedData, observedData);

    }


    /**
     * delete note
     * observe Resource.success
     * return Resource.error
     */
    @Test
    void deleteNote_observeResourceSuccess() throws Exception {

        //Arrange
        Note deleteNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(1, NoteRepository.DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        //Act

        Resource<Integer> observedValue = liveDataTestUtil.getValue(viewModel.deleteNote(deleteNote));

        //Assert

        assertEquals(returnedData, observedValue);

    }



    /**
     * delete note
     * observe Resource.error
     * return Resource.error
     * @throws Exception
     */
    @Test
    void deleteNote_observeResourceError() throws Exception {
        // Arrange
        Note deletedNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.error(null, NoteRepository.DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        // Act
        Resource<Integer> observedValue = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));


        // Assert
        assertEquals(returnedData, observedValue);
    }
}
