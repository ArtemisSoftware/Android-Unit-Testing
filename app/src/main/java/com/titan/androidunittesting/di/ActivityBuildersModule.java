package com.titan.androidunittesting.di;

import com.titan.androidunittesting.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();
}
