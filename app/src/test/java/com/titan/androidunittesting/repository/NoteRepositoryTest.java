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

    @Test
    void dummyTest() throws Exception {

        Assertions.assertNotNull(noteDao);
        Assertions.assertNotNull(noteRepository);

        //Arrange

        //Act

        //Assert

    }
}
