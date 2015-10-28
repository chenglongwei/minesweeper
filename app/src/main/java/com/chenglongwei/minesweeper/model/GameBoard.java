package com.chenglongwei.minesweeper.model;

import java.util.Random;

/**
 * Created by chenglongwei on 10/27/15.
 */
public class GameBoard {
    public static final int DEFAULT_BOARD_WIDTH = 8;
    public static final int DEFAULT_BOARD_HEIGHT = 8;
    public static final int DEFAULT_MINES_NUMBER = 10;
    public static final int MINE = -1;

    private int boardHeight = DEFAULT_BOARD_HEIGHT;
    private int boardWidth = DEFAULT_BOARD_WIDTH;
    private int minesNumber = DEFAULT_MINES_NUMBER;

    //Game board, indicates mine numbers around, MINE means mine.
    private int[][] board;
    //8 directions of a cell.
    public static int[][] directions = new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public GameBoard() {
        initGameBoard();
    }

    public GameBoard(int boardHeight, int boardWidth, int minesNumber) {
        //Do some check of the parameter.
        if (boardHeight > 0 && boardWidth > 0 && minesNumber > 0 &&
                minesNumber < boardHeight * boardWidth) {
            this.boardHeight = boardHeight;
            this.boardWidth = boardWidth;
            this.minesNumber = minesNumber;
        }

        initGameBoard();
    }

    public int getMineNumberAt(int row, int column) {
        return board[row][column];
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getDefaultBoardHeight() {
        return boardHeight;
    }

    private void initGameBoard() {
        board = new int[boardHeight][boardWidth];
        randomAddMine();
    }

    private void randomAddMine() {
        Random random = new Random(System.currentTimeMillis());
        int count = 0;
        int row = 0;
        int column = 0;
        while (count < minesNumber) {
            row = random.nextInt(boardHeight);
            column = random.nextInt(boardWidth);
            if (board[row][column] != MINE) {
                board[row][column] = MINE;
                //update mine numbers of neighbors
                updateNeighborMineNumber(row, column);
                count++;
            }
        }
    }

    private void updateNeighborMineNumber(int row, int column) {
        for (int i = 0; i < 8; i++) {
            int r = row + directions[i][0];
            int c = column + directions[i][1];
            if (r >= 0 && r < boardHeight && c >= 0 && c < boardWidth && board[r][c] != MINE) {
                board[r][c]++;
            }
        }
    }
}
