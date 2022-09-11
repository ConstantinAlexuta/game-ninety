package com.axc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameNinety {

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        generateStripOfSixTickets();
        Long endTime = System.currentTimeMillis();

        System.out.println(" startTime = "+ startTime);
        System.out.println(" endTime = "+ endTime);
        System.out.println(" Duration = "+ (endTime-startTime) +" Millis");
        System.out.println(" Duration = "+ (endTime-startTime)/1000 +" Seconds");
    }

    static int[][] RC;

    static int[] C; // count numbers on a row  , should be from 0 to 5 numbers on a row

    static Map<Integer, List<Integer>> mapN;   // N 1...9  10...19  89...90

    static Map<Integer, List<Integer>> mapR;

    static Random R = new Random();

    public static int[][] generateStripOfSixTickets() {

        cleanInput();

        fillTheListWithAvailableNumbersOnAColumn();

        fillTheListWithAvailableCellsOnAColumn();

        for (int c = 1; c <= 9; c++) { // loop to columns

            if (c >= 5) {
                checkIfFiveNumberRuleForceToPolulateSomeRows(c);
            }

//            System.out.println(" >>DEBUG>> mapN"+mapN.get(c).size());
//            System.out.println(" >>DEBUG>> mapR"+mapR.get(c).size());

            populateEachTicketWithOneNumberPerColumn(c);


            populateRemainingNumbersOnThisColumn(c);

            arrangeInAscendingOrder();


//            // in this column, in each ticket, put at least one number in one row of three
//            System.out.println("");  System.out.println("");  System.out.println("");
//            int t=1,  tr=1,  iLv=0,  cV=0,  Row=0; // t tichet, tr ticket row 1,2,3   cV cell value    iLv random index of list to take value
//            for(t=1; t<=6; t++){    // In a single tichet, on o column, put at least one number in one random row
//
//
//                System.out.println("");  System.out.println("");  System.out.println("");
//            }


            printTickets();


        }

        if (!mapN.get(9).isEmpty()) {
            for (int f = 0; f < 18; f++) {
                if (C[f] < 5) {
                    RC[f][8] = mapN.get(9).remove(0);
                }
            }
        }

        printTickets();
        mapN.forEach((k, l) -> {
            if (!l.isEmpty()) System.out.println(" >>>>>>> mapN = " + l.toString());

            if (l.isEmpty()) System.out.println(" >>>>>>> list is empty = ");

        });

        return RC;
    }


    private static void cleanInput() {
        RC = new int[18][9];
        C = new int[18];
        mapN = new HashMap<>(9);   // N 1...9  10...19  89...90
        mapR = new HashMap<>(9);
    }


    private static void fillTheListWithAvailableCellsOnAColumn() {
        int c = 1, i = 0, n = 9, s = 0, e = 9, a = 0; // c=column  s=start  e=end  a=add
        c = 1;
        i = 0;
        n = 9;
        s = 0;
        e = 9;
        a = 0;  //  row number 0...17, sR start Row  eR end Row
        int r = 0, rS = 0, rE = 17, rM = 18;  // r row, rS row Start, rE rowEnd rM rowMaxim
        int v = 0, vS = 0, vE = 17, vM = 18;  // r val, rS val Start, rE valEnd rM valMaxim
        for (; c <= 9; c++) {
            a = (c - 1) * 10;
            s = a;
            e = a + 9;
            if (c == 1) {
                s = 1;
            }
            if (c == 9) {
                e = 90;
            }
            List<Integer> listVal = new ArrayList<>(18);

            mapR.put(c, IntStream.rangeClosed(vS, vE).boxed().collect(Collectors.toList()));
            Collections.shuffle(mapR.get(c));
            System.out.println(mapR.get(c));
            i = 0;
        }
    }


    public static void printTickets() {
        int[] aR = new int[18];
        int val = 0;
        System.out.println();
        System.out.println("===================================================================================");
        System.out.println();

        for (int row = 0; row < 18; row++) {
            aR[row] = 5;
            for (int col = 0; col < 9; col++) {
                val = RC[row][col];
                if (col == 0 && val != 0) {
                    System.out.print(" ");
                }
                if (val != 0) {
                    aR[row]--;
                }
                System.out.print("       " +
                        (val == 0 ? "--" : val) + " "
                );
            }
            System.out.print("     aR[" + row + "]=" + aR[row]);
            System.out.println();
            if ((row + 1) % 3 == 0) System.out.println("\n");
        }
    }


    private static void fillTheListWithAvailableNumbersOnAColumn() {
        int c = 1, i = 0, n = 9, s = 0, e = 9, a = 0; // c=column  s=start  e=end  a=add

        for (; c <= 9; c++) {
            a = (c - 1) * 10;
            s = a;
            e = a + 9;
            if (c == 1) {
                s = 1;
            }
            if (c == 9) {
                e = 90;
            }

            mapN.put(c, IntStream.rangeClosed(s, e).boxed().collect(Collectors.toList()));
            Collections.shuffle(mapN.get(c));
            System.out.println(mapN.get(c));
            i = 0;
        }
        System.out.println("\n\n");
    }


    private static void populateEachTicketWithOneNumberPerColumn(int c) {

        System.out.println();
        System.out.print("Fills of rows:  ");
        for (int roW = 0; roW < 18; roW++) {
            System.out.print("   " + C[roW]);
        }

        // in this column, in each ticket, put at least one number in one row of three
        System.out.println("");
        System.out.println("");
        System.out.println("");
        int t = 1, tr = 0, iLv = 0, cV = 0, Row = 0; // t tichet, tr ticket row 1,2,3   cV cell value    iLv random index of list to take value
        for (t = 1; t <= 6; t++) {    // In a single tichet, on o column, put at least one number in one random row

            System.out.print("  c=" + c + "  t=" + t);

            if (c < 5) {
                tr = R.nextInt(3);
                System.out.print(" tr=" + tr); // choose a random row in one ticket
                Row = ((t - 1) * 3) + (tr);
                System.out.print(" Row=" + Row);  // row in the matrix store of the row ticket

                if (C[Row] < 5) {// the row  have less than 5 numbers

                    mapR.get(c).remove(Integer.valueOf(Row));  // remove the row from the shuffle list that store the unpopulated rows in a column


                    iLv = R.nextInt(mapN.get(c).size());  // choose a random index from a list with shuffled numbers in range (1 - 9/10) * column number
                    cV = mapN.get(c).remove(iLv);         // take the random value and remove to not be available anymore

                    RC[Row][c - 1] = cV;                          // assign the randomized value in the strip tickets store
//                    if(debug) { RC[Row][c-1] = 91;}                          // assign the randomized value in the strip tickets store


                    ++C[Row];                                      // keep in mind that on the current row was added an number to not be more than 5 on a row
                    System.out.println("");
                }
            }

            if (c >= 5 && RC[(t - 1) * 3][c - 1] == 0 && RC[(t - 1) * 3 + 1][c - 1] == 0 && RC[(t - 1) * 3 + 2][c - 1] == 0) {

                tr = R.nextInt(3);
                System.out.print(" tr=" + tr); // choose a random row in one ticket
                Row = ((t - 1) * 3) + (tr);
                System.out.print(" Row=" + Row);  // row in the matrix store of the row ticket

                if (C[Row] < 5) {// the row  have less than 5 numbers
                    mapR.get(c).remove(Integer.valueOf(Row));  // remove the row from the shuffle list that store the unpopulated rows in a column


                    iLv = R.nextInt(mapN.get(c).size());  // choose a random index from a list with shuffled numbers in range (1 - 9/10) * column number
                    cV = mapN.get(c).remove(iLv);         // take the random value and remove to not be available anymore

                    RC[Row][c - 1] = cV;                          // assign the randomized value in the strip tickets store
//                    if(debug) { RC[Row][c-1] = 93;  }                        // assign the randomized value in the strip tickets store


                    ++C[Row];                                      // keep in mind that on the current row was added an number to not be more than 5 on a row
                    System.out.println("");
                    printTickets();
                }
            }
            System.out.println("");
            printTickets();
            System.out.println("");
        }
    }


    private static void populateRemainingNumbersOnThisColumn(int c) {
        int n = 0, iL = 0, mL = 18, r = 0, iN = 0, v = 0;
        int nM = 10;
        if (c == 1) {
            nM = 9;
        }
        if (c == 9) {
            nM = 11;
        }
        for (n = nM; n > 0; --n, --mL, --nM) {         // loop to random 9 cells to populate them with numbers
            System.out.print("c=" + c + " nM=" + nM + " n=" + n);
            if (c == 9) {
                nM = Math.min(nM, mapR.get(c).size());
            }
            System.out.print("  nM=" + nM);
            if (nM > 0) {
                nM = mapR.get(c).size();
                iL = R.nextInt(nM); // choose a random index position on a list with random values from (1 to 9/10/11) * col

                System.out.print("  iL=" + iL);
                System.out.print("  mapR.get(c),size()=" + mapR.get(c).size());
                r = mapR.get(c).remove(iL); // get a random row (1...18) on column c
                System.out.print("  r=" + r);
                if (C[r] < 5) {
                    nM = mapN.get(c).size();
                    System.out.print("   size nM=" + nM);
                    iN = nM == 0 ? 0 : R.nextInt(nM);   // get a random index in the available storing number list for this column
                    System.out.print("  iN=" + iN);
                    if (mapN.get(c).isEmpty()) continue;
                    v = mapN.get(c).remove(iN); // get a random number value from a random/shuffle numbers list
                    System.out.print("  v=" + v);
                    RC[r][c - 1] = v;
                    ++C[r];
                }
                final Integer removeRowValue = r;
                if (C[r] == 5) {
                    mapR.forEach((row, list) -> list.remove(removeRowValue));
                }  // are 5 numbers populated on a row, so remove to not be used further
            }

            System.out.println();
        }
    }


    private static void checkIfFiveNumberRuleForceToPolulateSomeRows(int c) {
        int n = 0, iL = 0, mL = 18, r = 0, iN = 0, v = 0;
        int nM = 10;
        if (c == 1) {
            nM = 9;
        }
        if (c == 9) {
            nM = 11;
        }
        int Row = 0;
        // we are at column 5, and we have 4 empty cells, so the other cells must have numbers on this row
        for (int roW = 0; roW < 18; roW++) {
//                    if(C[roW] == 5) continue;
            if (C[roW] < 5 && C[roW] + 5 == c) {
                for (int x = c + 0; x <= 9; x++) {                   // for column cX 5, 6, 7, 8, 9

                    if (!mapN.get(x).isEmpty()) {
                        nM = mapN.get(x).size();      // the number of numbers available in storing list
                        iN = nM == 0 ? 0 : R.nextInt(nM);         // get a random index in the available storing number list for this column
                        v = mapN.get(x).remove(iN); // get a random number value from a random/shuffle numbers list
                        RC[roW][x - 1] = v;
//                        if(debug) { RC[roW][x-1] = 92; }
                        mapR.get(c).remove(Integer.valueOf(Row));  // remove the row from the shuffle list that store the unpopulated rows in a column

                        ++C[roW];
                        if (C[roW] == 5) break;
                    }
                }
                C[roW] = 5; // number capacity filled on this row
                final Integer removeRowValue = r;
                mapR.forEach((row, list) -> list.remove(removeRowValue));   // are 5 numbers populated on a row, so remove to not be used further
            }
            System.out.println("  c=" + c + "  in LAST LOOP >>>>>>  roW=" + roW);
            printTickets();
        }
    }

    /**
     * Arrange ascending similar to Bubble sort but skipping sorting if the value is zero, to keep the random effort.
     */
    private static void arrangeInAscendingOrder() {
        for (int c = 0; c < 9; c++) {

            for (int r = 0; r < 18; r = r + 3) {
                int temp;
                if (RC[r][c] != 0 && RC[r + 1][c] != 0 && RC[r + 2][c] != 0) {
                    if (RC[r][c] > RC[r + 1][c]) {
                        temp = RC[r + 1][c];
                        RC[r + 1][c] = RC[r][c];
                        RC[r][c] = temp;
                    }
                    if (RC[r + 1][c] > RC[r + 2][c]) {
                        temp = RC[r + 2][c];
                        RC[r + 2][c] = RC[r + 1][c];
                        RC[r + 1][c] = temp;
                    }
                    if (RC[r][c] > RC[r + 1][c]) {
                        temp = RC[r + 1][c];
                        RC[r + 1][c] = RC[r][c];
                        RC[r][c] = temp;
                    }
                }
                if (RC[r][c] != 0 && RC[r + 1][c] != 0 && RC[r + 2][c] == 0) {
                    if (RC[r][c] > RC[r + 1][c]) {
                        temp = RC[r + 1][c];
                        RC[r + 1][c] = RC[r][c];
                        RC[r][c] = temp;
                    }
                }
                if (RC[r][c] != 0 && RC[r + 1][c] == 0 && RC[r + 2][c] != 0) {
                    if (RC[r][c] > RC[r + 2][c]) {
                        temp = RC[r + 2][c];
                        RC[r + 2][c] = RC[r][c];
                        RC[r][c] = temp;
                    }
                }
                if (RC[r][c] == 0 && RC[r + 1][c] != 0 && RC[r + 2][c] != 0) {
                    if (RC[r + 1][c] > RC[r + 2][c]) {
                        temp = RC[r + 2][c];
                        RC[r + 2][c] = RC[r + 1][c];
                        RC[r + 1][c] = temp;
                    }
                }
            }

        }
    }

    static boolean debug = false;

}
