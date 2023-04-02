import java.util.List;

public class SudokuSolver {
    private static final int SIZE = 9;

    public static boolean placeNumberAndCheckError(Tile[][] sudoku, int row, int column, int value) {
        sudoku[row][column].setValue(value);

        return removeValueInSquareAndCheckError(sudoku, row, column, value)
                || removeValueInRowAndCheckError(sudoku, row, column, value)
                || removeValueInColumnAndCheckError(sudoku, row, column, value);
    }

    private static boolean removeValueInSquareAndCheckError(Tile[][] sudoku, int row, int column, int value) {
        int startRow = 3 * (row / 3);
        int endRow = 3 * (row / 3 + 1);

        int startColumn = 3 * (column / 3);
        int endColumn = 3 * (column / 3 + 1);

        for (int i = startRow; i < endRow; i++) {
            for (int j = startColumn; j < endColumn; j++) {
                if (i == row && j == column) {
                    continue;
                }
                if (removeAndCheckError(sudoku[i][j], value)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean removeValueInRowAndCheckError(Tile[][] sudoku, int row, int column, int value) {
        for (int j = 0; j < 9; j++) {
            if (j == column) {
                continue;
            }
            if (removeAndCheckError(sudoku[row][j], value)) {
                return true;
            }
        }
        return false;
    }

    private static boolean removeValueInColumnAndCheckError(Tile[][] sudoku, int row, int column, int value) {
        for (int i = 0; i < 9; i++) {
            if (i == row) {
                continue;
            }
            if (removeAndCheckError(sudoku[i][column], value)) {
                return true;
            }
        }
        return false;
    }

    private static boolean removeAndCheckError(Tile tile, int value) {
        if (tile.getValue() == null) {
            if (hasOnlyThisElement(tile.getPossibleValues(), value)) {
                return true;
            }
            tile.getPossibleValues().remove(Integer.valueOf(value));
            return false;
        } else {
            return tile.getValue() == value;
        }
    }

    private static boolean hasOnlyThisElement(List<Integer> list, int element) {
        return list.size() == 1 && list.get(0) == element;
    }

    public static boolean solve(Tile[][] sudoku) {
        boolean placed = true;
        while (placed) {
            placed = false;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    Tile tile = sudoku[i][j];
                    if (tile.getValue() == null && tile.getPossibleValues().size() == 1) {
                        if (placeNumberAndCheckError(sudoku, i, j, tile.getPossibleValues().get(0))) {
                            return false;
                        }
                        placed = true;
                    }
                }
            }
        }
        return true;
    }

    public static void advanceSolve(Tile[][] sudoku) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sudoku[i][j].getValue() == null) {
                    for (var value : sudoku[i][j].getPossibleValues()) {
                        Tile[][] newSudoku = copy(sudoku);
                        if (placeNumberAndCheckError(newSudoku, i, j, value) || !solve(newSudoku)) {
                            continue;
                        }
                        advanceSolve(newSudoku);
                        if (isDone(newSudoku)) {
                            print(newSudoku);
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }

    private static Tile[][] copy(Tile[][] source) {
        Tile[][] dest = new Tile[9][9];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                dest[i][j] = source[i][j].newTile();
            }
        }
        return dest;
    }

    private static boolean isDone(Tile[][] sudoku) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sudoku[i][j].getValue() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void print(Tile[][] sudoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j].print();
            }
            System.out.println();
        }
        System.out.println();
    }
}
