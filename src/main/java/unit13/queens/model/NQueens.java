package unit13.queens.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import unit13.backtracker.Backtracker;
import unit13.backtracker.Configuration;

public class NQueens implements Configuration<NQueens> {
    private final int n;
    private final Queen[] queens;

    public NQueens(int n, Queen[] queens) {
        this.n = n;
        this.queens = queens;
    }

    public Queen[] getQueens() {
        return this.queens;
    }

    @Override
    public Collection<NQueens> getSuccessors() {
        List<NQueens> successors = new ArrayList<>();

        int row = queens.length;
        for(int col = 0; col < n; col++) {
            // make an array big enough for one more queen
            Queen[] copy = Arrays.copyOf(queens, queens.length+1);
            // make a queen in the next row at the specified column
            Queen queen = new Queen(row, col);
            // store the new queen in the last index in the array
            copy[row] = queen;

            NQueens successor = new NQueens(n, copy);
            successors.add(successor);
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        if(queens.length > 1) {
            Queen newest = queens[queens.length-1];
            for(int i = 0; i < queens.length-1; i++) {
                Queen queen = queens[i];
                if(queen.canAttack(newest)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isGoal() {
        if(queens.length == n && isValid()) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(queens);
    }

    public static void main(String[] args) {
        Queen[] queens = new Queen[0];
        NQueens nQueens = new NQueens(8, queens);
        // System.out.println(nQueens);

        // Collection<NQueens> successors = nQueens.getSuccessors();
        // for(NQueens successor : successors) {
        //     System.out.println(successor);
        // }
        // System.out.println(nQueens.isGoal());
        // System.out.println(nQueens.isValid());

        Backtracker<NQueens> backtracker = new Backtracker<>(false);
        NQueens solution = backtracker.solve(nQueens);
        if(solution != null) {
            System.out.println(solution);
        } else {
            System.out.println("No Solution!");
        }
    }
}
