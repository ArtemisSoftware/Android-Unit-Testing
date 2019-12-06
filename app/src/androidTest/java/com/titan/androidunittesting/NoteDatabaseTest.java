package com.titan.androidunittesting;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.titan.androidunittesting.persistence.NoteDao;
import com.titan.androidunittesting.persistence.NoteDataBase;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {

    //system under test
    private NoteDataBase noteDatabase;

    public NoteDao getNoteDao(){
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init(){
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDataBase.class
        ).build();
    }

    @After
    public void finish(){
        noteDatabase.close();
    }
}
