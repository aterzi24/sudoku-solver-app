import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tile {
    private Integer value = null;
    private List<Integer> possibleValues = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

    public void print() {
        if (value == null) {
            System.out.print("x ");
        } else {
            System.out.print(value + " ");
        }
    }

    public Tile newTile() {
        return new Tile(value, new ArrayList<>(possibleValues));
    }
}
