package com.titan.androidunittesting.util;

import org.junit.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    private static final String today = "12-2019";

    @Test
    public void testGetCurrentTimestamp_returnedTimestamp(){
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                assertEquals(today, DateUtil.getCurrentTimeStamp());
                System.out.println("Timestamp is generated correctly");
            }
        });
    }


    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10, 11})
    public void getMonthFromNumber_returnSuccess(int monthNumber, TestInfo testInfo, TestReporter testReporter){
        assertEquals(DateUtil.months[monthNumber], DateUtil.getMonthFromNumber(DateUtil.monthNumbers[monthNumber]));
        System.out.println(DateUtil.months[monthNumber] + " : " + DateUtil.getMonthFromNumber(DateUtil.monthNumbers[monthNumber]));
    }


    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11})
    public void testGetMonthFromNumber_returnError(int monthNumber, TestInfo testInfo, TestReporter testReporter){
        int randomInt = new Random().nextInt(90) + 13;
        assertEquals(DateUtil.getMonthFromNumber(String.valueOf(monthNumber * randomInt)), DateUtil.GET_MONTH_ERROR);
        System.out.println("month [" + randomInt + "] : " + DateUtil.GET_MONTH_ERROR);
    }

}
