package com.titan.androidunittesting;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.titan.androidunittesting.models.Note;
import com.titan.androidunittesting.util.LiveDataTestUtil;
import com.titan.androidunittesting.util.TestUtil;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class NoteDaoTest extends NoteDatabaseTest{


    public static final  String TEST_TITLE = "This is a test title";
    public static final  String TEST_CONTENT = "This is some test content";
    public static final  String TEST_TIMESTAMP = "08-2018";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    /**
     * Insert, read, delete
     * @throws Exception
     */
    @Test
    public void insertReadDelete() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        //insert
        getNoteDao().insertNote(note).blockingGet(); //wait until inserted

        // read

        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        Assert.assertNotNull(insertedNotes);

        Assert.assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        Assert.assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        Assert.assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        Assert.assertEquals(note, insertedNotes.get(0));

        // delete

        getNoteDao().deleteNote(note).blockingGet();

        //confirm the database is empty

        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        Assert.assertEquals(0, insertedNotes.size());
    }



    /**
     * Insert, Read, update, read, delete
     * @throws Exception
     */
    @Test
    public void insertReadUpdateReadDelete() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        //insert
        getNoteDao().insertNote(note).blockingGet(); //wait until inserted

        // read

        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        Assert.assertNotNull(insertedNotes);

        Assert.assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        Assert.assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        Assert.assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        Assert.assertEquals(note, insertedNotes.get(0));

        //update

        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        //read

        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        Assert.assertEquals(TEST_TITLE, insertedNotes.get(0).getTitle());
        Assert.assertEquals(TEST_CONTENT, insertedNotes.get(0).getContent());
        Assert.assertEquals(TEST_TIMESTAMP, insertedNotes.get(0).getTimestamp());

        note.setId(insertedNotes.get(0).getId());
        Assert.assertEquals(note, insertedNotes.get(0));

        // delete

        getNoteDao().deleteNote(note).blockingGet();

        //confirm the database is empty

        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        Assert.assertEquals(0, insertedNotes.size());
    }


    /**
     * Insert note with null title, throw exception
     * @throws Exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception{

        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        //insert
        getNoteDao().insertNote(note).blockingGet();
    }



    /**
     * Insert, Update with null title, throw exception
     * @throws Exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void updateNote_nullTitle_throwSQLiteConstraintException() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);


        //insert
        getNoteDao().insertNote(note).blockingGet();

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        Assert.assertNotNull(insertedNotes);

        //update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();

    }


}
