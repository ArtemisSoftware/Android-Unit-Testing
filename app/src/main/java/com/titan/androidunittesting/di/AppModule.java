package com.titan.androidunittesting.di;

import android.app.Application;

import androidx.room.Room;

import com.titan.androidunittesting.persistence.NoteDao;
import com.titan.androidunittesting.persistence.NoteDataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static NoteDataBase provideNoteDatabase(Application application){
        return Room.databaseBuilder(application, NoteDataBase.class, NoteDataBase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDataBase noteDataBase){
        return noteDataBase.getNoteDao();
    }
}
