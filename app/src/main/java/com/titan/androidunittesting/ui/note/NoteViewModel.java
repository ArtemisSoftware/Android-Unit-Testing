package com.titan.androidunittesting.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.androidunittesting.models.Note;
import com.titan.androidunittesting.repository.NoteRepository;
import com.titan.androidunittesting.ui.Resource;

import javax.inject.Inject;

public class NoteViewModel extends ViewModel {

    //inject
    private final NoteRepository noteRepository;

    private MutableLiveData<Note> note = new MutableLiveData<>();


    @Inject
    public NoteViewModel(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public LiveData<Note> observeNote(){
        return note;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception{
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.insertNote(note.getValue())
        );
    }



    public void setNote(Note note) throws Exception{

        if(note.getTitle() == null || note.getTitle().equals("")){
            throw new Exception(NoteRepository.NOTE_TITLE_NULL);
        }

        this.note.setValue(note);
    }
}
