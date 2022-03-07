package casim.model.rule.function;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import casim.model.cell.Cell;
import casim.utils.coordinate.Coordinates;
import casim.utils.coordinate.Coordinates2D;
import casim.utils.coordinate.CoordinatesUtil;
import casim.utils.grid.Grid;

/**
 * General function used to get the neighbors of a {@link Cell} in a {@link casim.utils.grid.Grid2D}.
 * 
 *  @param <T> the type of the finite states of the {@link casim.model.Automaton}'s {@link Cell}.
 */
public class Neighbors2DFunction<T> implements BiFunction<Coordinates<Integer>, Grid<Coordinates<Integer>, Cell<T>>, Iterable<Pair<Coordinates<Integer>, Cell<T>>>> {

    /**
     * @param cellCoord the coordinates of the cell of which calculate the neighbors.
     * @param grid the grid where search for the neighbors.
     * @return an iterable containing all the neighbors of the cell.
     */
    @Override
    public Iterable<Pair<Coordinates<Integer>, Cell<T>>> apply(final Coordinates<Integer> cellCoord, final Grid<Coordinates<Integer>, Cell<T>> grid) {
        return Stream.of(CoordinatesUtil.of(1, 0), CoordinatesUtil.of(0, 1), CoordinatesUtil.of(0, -1), CoordinatesUtil.of(-1, 0))
            .map(coord -> CoordinatesUtil.sumInt(coord, (Coordinates2D<Integer>) cellCoord))
            .filter(grid::isCoordValid)
            .map(coord -> Pair.of((Coordinates<Integer>) coord, grid.get(coord))) 
            .collect(Collectors.toList());
    }

}
