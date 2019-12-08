package com.titan.androidunittesting.ui.noteslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.titan.androidunittesting.R;
import com.titan.androidunittesting.repository.NoteRepository;
import com.titan.androidunittesting.ui.note.NoteActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "NotesListActivity";

    //@Inject
    //NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        //Log.d(TAG, "onCreate: " + noteRepository);

        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}
