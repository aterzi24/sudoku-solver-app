import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String FILE_NAME = "input.txt";

    public static boolean isAcceptableNumber(char c) {
        return '1' <= c && c <= '9';
    }

    public static void main(String[] args) throws IOException {
        Tile[][] sudoku = new Tile[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = new Tile();
            }
        }

        var lines = Files.readAllLines(Paths.get(FILE_NAME));

        for (int i = 0; i < 9; i++) {
            var line = lines.get(i);

            for (int j = 0; j < 9; j++) {
                char c = line.charAt(j);
                if (isAcceptableNumber(c)) {
                    var value = c - '0';
                    sudoku[i][j].getPossibleValues().clear();
                    sudoku[i][j].getPossibleValues().add(value);
                    SudokuSolver.placeNumberAndCheckError(sudoku, i, j, value);
                }
            }
        }

        SudokuSolver.solve(sudoku);
        SudokuSolver.advanceSolve(sudoku);
    }
}
