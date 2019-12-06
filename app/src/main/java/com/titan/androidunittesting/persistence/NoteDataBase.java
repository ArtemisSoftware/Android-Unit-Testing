package com.titan.androidunittesting.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.titan.androidunittesting.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db";

    public abstract NoteDao getNoteDao();
}
