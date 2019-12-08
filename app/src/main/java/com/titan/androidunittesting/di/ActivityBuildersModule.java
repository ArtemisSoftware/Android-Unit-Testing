package com.titan.androidunittesting.di;

import com.titan.androidunittesting.ui.noteslist.NotesListActivity;
import com.titan.androidunittesting.ui.note.NoteActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributeNoteActivity();
}
