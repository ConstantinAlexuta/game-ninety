package com.axc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

            Set<Integer> setRow = new HashSet<>(oneRowList);

            setRow.remove(0);

            Assert.assertEquals(5, setRow.size()); // are 5 distinct numbers in a row of a ticket

            setAll.addAll(setRow);
        });
        setAll.remove(0);
        Assert.assertEquals(90, setAll.size());  // all 90 numbers used in 6 tickets are distinct
    }


    @Test
    public void checkEachTicketColumnHasAtLeastOneNumber() {

        for (int r = 1; r < 18; r = r + 3) {

            for (int c = 0; c < 9; c++) {

                int oneTicketColumnSum = RC[r - 1][c] + RC[r][c] + RC[r + 1][c];

                Assert.assertTrue(
                        oneTicketColumnSum > 0
                );
            }
        }
    }

    @Test
    public void checkFirstColumnContainsNumbersToNine() {

        Set<Integer> setNumber = new HashSet<>(9);

        for (int r = 0; r < 18; r++) {

            setNumber.add(RC[r][0]);
        }
        setNumber.remove(0);

        Assert.assertEquals(9, setNumber.size());

        Assert.assertEquals(
                IntStream.rangeClosed(1, 9).boxed().sorted().collect(Collectors.toList()),
                setNumber.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void checkColumnTwoToEightContainsNumbersToTen() {

        for (int c = 1; c < 8; c++) {

            Set<Integer> setNumber = new HashSet<>(10);

            for (int r = 0; r < 18; r++) {

                setNumber.add(RC[r][c]);
            }

            setNumber.remove(Integer.valueOf(0));

            Assert.assertEquals(10, setNumber.size());

            Assert.assertEquals(
                    IntStream.rangeClosed(c * 10, c * 10 + 9).boxed().sorted().collect(Collectors.toList()),
                    setNumber.stream().sorted().collect(Collectors.toList()));
        }
    }

    @Test
    public void checkLastColumnContainsNumbersToEleven() {

        Set<Integer> setNumber = new HashSet<>(11);

        for (int r = 0; r < 18; r++) {

            setNumber.add(RC[r][8]);
        }
        setNumber.remove(0);

        Assert.assertEquals(11, setNumber.size());

        Assert.assertEquals(
                IntStream.rangeClosed(80, 90).boxed().sorted().collect(Collectors.toList()),
                setNumber.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void checkArrangedInAscendingOrder() {

        List<Integer> columnTicketList = new ArrayList<>();

        List<List<Integer>> rows =
                Arrays.stream(RC).
                        map(rowArray ->
                                Arrays.stream(rowArray)
                                        .boxed()
                                        .collect(Collectors.toList())
                        ).collect(Collectors.toList());

        for (int c = 0; c < 9; c++) {

            for (int r = 0; r < 18; r = r + 3) {

                columnTicketList.add(rows.get(r).get(c));
                columnTicketList.add(rows.get(r + 1).get(c));
                columnTicketList.add(rows.get(r + 2).get(c));

                Assert.assertEquals(columnTicketList,
                        columnTicketList.stream().sorted().collect(Collectors.toList()));
            }
        }
    }

    @Test
    public void noDuplicateNumbersToNinetyInStripTest() {

        Set<Integer> setAll = new HashSet<>(91); // a set with all numbers cumulated from 6 tickets

        List<List<Integer>> rows =
                Arrays.stream(RC).
                        map(rowArray ->
                                Arrays.stream(rowArray)
                                        .boxed()
                                        .collect(Collectors.toList())
                        ).collect(Collectors.toList());

        rows.forEach(setAll::addAll);

        setAll.remove(0);

        Assert.assertEquals(90, setAll.size());  // all 90 numbers used in 6 tickets are distinct
    }

}