package com.titan.androidunittesting.repository;

import com.titan.androidunittesting.persistence.NoteDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NoteRepositoryTest {

    //system under test

    private NoteRepository noteRepository;

    //another way to instanciate
    //@Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
        //another way to instanciate
        //MockitoAnnotations.initMocks(this);

        noteDao = Mockito.mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }


    /**
     * Insert note
     * Verify the correct method is called
     * Confirm observer is triggered
     * Confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {

        //Arrange

        //Act

        //Assert

    }


    /**
     * Insert note
     * Failure (return -1)
     */
    @Test
    void insertNote_returnFailure() throws Exception {

        //Arrange

        //Act

        //Assert

    }


    /**
     * Insert note
     * Null title
     * confirm throw exception
     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception {

        //Arrange

        //Act

        //Assert

    }
}
