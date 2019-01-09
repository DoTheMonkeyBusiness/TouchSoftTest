package com.company;

import java.util.*;

public class Main {


    public static class PossibleSetsOfDiscs {
        public ArrayList<ArrayList<Integer>> positions = new ArrayList<>();
        public long discsSum;
        public boolean possibleSolution;


        public PossibleSetsOfDiscs(long discsSum, boolean possibleSolution) {
            this.discsSum = discsSum;
            this.possibleSolution = possibleSolution;
        }
    }

    private static void checkRestrictions(ArrayList<Integer> discs) {
        boolean ok = true;
        int sum = 0;
        if (discs.size() > 1000) {
            ok = false;
        }
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i) <= 20 && discs.get(i) >= 0) {
                sum += discs.get(i);
            } else {
                ok = false;
            }
        }
        if (sum > 10000) {
            ok = false;
        }

        if (!ok) {
            System.out.println("The entered numbers are not subject to restrictions!!!");
            System.exit(0);
        }
    }


    public static ArrayList fillingIn(int[] nums) {

        ArrayList<Integer> discs = new ArrayList<Integer>();

        for (int i = 0; i < nums.length; i++) {
            discs.add(nums[i]);
        }
        Collections.sort(discs);

        checkRestrictions(discs);

        return discs;

    }


    //maximum set size
    public static int midleFunc(ArrayList<Integer> discs) {
        int middle = 0;

        for (int i = 0; i < discs.size(); i++) {
            middle = middle + discs.get(i);
        }

        return middle / 2;
    }


    //all possible subsets
    public static void countingPowerSet(int middle, ArrayList<Integer> discs, ArrayList<PossibleSetsOfDiscs> possibleSetsOfDiscs) {

        ArrayList<Integer> discsPositions = new ArrayList<Integer>();
        ArrayList<Object> discsPositionsArray = new ArrayList<Object>();
        ArrayList<Long> unique = new ArrayList<Long>();
        long pow_set_size = (long) Math.pow(2, discs.size());
        long sum = 0;


        for (long i = 0; i < pow_set_size; i++) {
            discsPositions.clear();
            sum = 0;

            for (int j = 0; j < discs.size(); j++) {
                if ((i & (1 << j)) > 0) {
                    sum += discs.get(j);
                    discsPositions.add(j);

                }
            }
            searchForPossibleSolutions(middle, sum, discsPositionsArray, unique, possibleSetsOfDiscs, discsPositions);

        }
    }

    //check the amount for uniqueness or entry in possibleSetsOfDiscs
    private static void searchForPossibleSolutions(int middle, long sum, ArrayList<Object> discsPositionsArray, ArrayList<Long> unique, ArrayList<PossibleSetsOfDiscs> possibleSetsOfDiscs, ArrayList<Integer> discsPositions) {
        boolean isNotContains = true;

        if (sum <= middle && discsPositions.size() > 0) {
            if (unique.indexOf(sum) != -1) {
                for (int i = 0; i < possibleSetsOfDiscs.size(); i++) {
                    if (possibleSetsOfDiscs.get(i).discsSum == sum) {
                        possibleSetsOfDiscs.get(i).positions.add((ArrayList<Integer>) discsPositions.clone());
                        if (!possibleSetsOfDiscs.get(i).possibleSolution) {
                            matchSearch(possibleSetsOfDiscs, i, possibleSetsOfDiscs.get(i).positions, discsPositions);
                        }
                        isNotContains = false;
                        break;
                    }
                }
                if (isNotContains) {
                    possibleSetsOfDiscs.add(new PossibleSetsOfDiscs(sum, false));
                    possibleSetsOfDiscs.get(possibleSetsOfDiscs.size() - 1).positions.add((ArrayList<Integer>) discsPositions.clone());
                    possibleSetsOfDiscs.get(possibleSetsOfDiscs.size() - 1).positions.add((ArrayList<Integer>) discsPositionsArray.get(unique.indexOf(sum)));
                    matchSearch(possibleSetsOfDiscs, (possibleSetsOfDiscs.size() - 1), possibleSetsOfDiscs.get(possibleSetsOfDiscs.size() - 1).positions, discsPositions);
                }
            } else {
                unique.add(sum);
                discsPositionsArray.add(discsPositions.clone());
            }
        }
    }


    //checking that the item may be the answer
    private static void matchSearch(ArrayList<PossibleSetsOfDiscs> possibleSetsOfDiscs, int index, ArrayList<ArrayList<Integer>> positions, ArrayList<Integer> discsPositions) {
        int counter = 0;
        boolean possibleSolution = false;
        loop:
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                counter = 0;
                for (int m = 0; m < positions.get(j).size(); m++) {
                    if (positions.get(i).contains(positions.get(j).get(m))) {
                        counter++;
                        break;
                    }
                }
                if (counter == 0) {
                    possibleSolution = true;
                    break loop;
                }
            }
        }
        if (possibleSolution) {
            possibleSetsOfDiscs.get(index).possibleSolution = true;
        }
    }



    //answer search
    private static ArrayList<Long> countingSetOfDiscs(int middle, ArrayList<PossibleSetsOfDiscs> possibleSetsOfDiscs) {
        ArrayList<Long> possibleDiscs = new ArrayList<Long>();


        for (int i = 0; i < possibleSetsOfDiscs.size(); i++) {
            if (possibleSetsOfDiscs.get(i).possibleSolution) {
                possibleDiscs.add((possibleSetsOfDiscs.get(i).discsSum));
            }
        }
        return possibleDiscs;
    }


    public static void main(String[] args) {
        // write your code here
        int middle;
        ArrayList<PossibleSetsOfDiscs> possibleSetsOfDiscs = new ArrayList<PossibleSetsOfDiscs>();
        possibleSetsOfDiscs.add(new PossibleSetsOfDiscs(0, true));
        ArrayList<Integer> discs = fillingIn(new int[]{0, 1,2,3,6});
        middle = midleFunc(discs);
        countingPowerSet(middle, discs, possibleSetsOfDiscs);
        System.out.println(Collections.max(countingSetOfDiscs(middle, possibleSetsOfDiscs)) * 2);

    }


}
