package com.axc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.axc.GameNinety.generateStripOfSixTickets;

public class GameNinetyTest {

    int[][] RC = new int[18][9]; // store the 6 tickets

    @Before
    public void init() {
        this.RC = generateStripOfSixTickets();
    }

    @Test
    public void fiveNumbersOnARowTest() {

        Set<Integer> setAll = new HashSet<>(91); // a set with all numbers cumulated from 6 tickets

        List<List<Integer>> rows =
                Arrays.stream(RC).
                        map(rowArray ->
                                Arrays.stream(rowArray)
                                        .boxed()
                                        .collect(Collectors.toList())
                        ).collect(Collectors.toList());

        rows.forEach(oneRowList -> {

            System.out.println(" TEST >> oneRowList = " + oneRowList.toString());

            Set<Integer> setRow = new HashSet<>(oneRowList);

            setRow.remove(0);

            Assert.assertEquals(5, setRow.size()); // are 5 distinct numbers in a row of a ticket

            setAll.addAll(setRow);
        });
        setAll.remove(0);
        Assert.assertEquals(90, setAll.size());  // all 90 numbers used in 6 tickets are distinct
    }


}