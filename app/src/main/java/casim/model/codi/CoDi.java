package casim.model.codi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

import casim.model.abstraction.automaton.AbstractAutomaton;
import casim.model.abstraction.utils.NeighborsFunctions;
import casim.model.codi.cell.CoDiCell;
import casim.model.codi.cell.attributes.CellState;
import casim.model.codi.cell.attributes.Direction;
import casim.model.codi.cell.builder.CoDiCellBuilder;
import casim.model.codi.cell.builder.CoDiCellBuilderImpl;
import casim.model.codi.cell.builder.utils.StateToCellFunction;
import casim.model.codi.rule.GrowthUpdateRule;
import casim.model.codi.rule.SignalingUpdateRule;
import casim.utils.coordinate.Coordinates3D;
import casim.utils.coordinate.CoordinatesUtil;
import casim.utils.grid.Grid2D;
import casim.utils.grid.Grid2DImpl;
import casim.utils.grid.Grid3D;
import casim.utils.grid.Grid3DImpl;
import casim.utils.range.Ranges;

/**
 * Implementation of CoDi {@link casim.model.abstraction.automaton.Automaton}.
 */
public class CoDi extends AbstractAutomaton<CellState, CoDiCell> {

    private boolean changed; 
    private Grid3D<CoDiCell> state;
    private boolean hasSetupSignaling;
    private final GrowthUpdateRule growthUpdateRule;
    private final SignalingUpdateRule signalingUpdateRule;


    /**
     * Constructor of {@link CoDi}.
     * 
     * @param state the grid of {@link CellState} used to initialize the automaton.
     */
    public CoDi(final Grid3D<CellState> state) {
        this.changed = true;
        this.hasSetupSignaling = false;
        final Function<CellState, CoDiCell> cellFunction = new StateToCellFunction();
        this.state = state.map(s -> cellFunction.apply(s));
        this.growthUpdateRule = new GrowthUpdateRule(NeighborsFunctions::neighbors3DFunction);
        this.signalingUpdateRule = new SignalingUpdateRule(NeighborsFunctions::neighbors3DFunction);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    protected Grid2D<CoDiCell> doStep() {
        if (changed) {
            this.changed = false;
            return growthStep();
        } else {
            if (!hasSetupSignaling) {
                signalingSetup();
            }
            return signalStep();
        }
    }

    private Grid2D<CoDiCell> growthStep() { 
        final var newState = new Grid3DImpl<CoDiCell>(this.state.getHeight(), this.state.getWidth(), this.state.getDepth());
        for (final var coord: this.visitGrid()) {
            CoDiCell cell = this.state.get(coord);
            final CellState oldCellState = cell.getState();
            cell = this.growthUpdateRule.getNextCell(Pair.of(coord, cell), this.state); 
            if (oldCellState != cell.getState()) {
                this.changed = true;
            }
            newState.set(coord, cell);
        }
        this.state = newState;
        this.kicking();
        return this.getGrid();
    }

    private Grid2D<CoDiCell> signalStep() {
        final var newState = new Grid3DImpl<CoDiCell>(this.state.getHeight(), this.state.getWidth(), this.state.getDepth());
        for (final var coord: this.visitGrid()) {
            final CoDiCell cell = this.signalingUpdateRule.getNextCell(Pair.of(coord, this.state.get(coord)), this.state);
            newState.set(coord, cell);
        }
        this.state = newState;
        this.kicking();
        return this.getGrid();
    }

    private void signalingSetup() {
        final Random random = new Random(); 
        this.hasSetupSignaling = true;
        for (final var coord: this.visitGrid()) {
            final CoDiCell cell = this.state.get(coord);
            if (cell.getState() == CellState.NEURON) { 
                cell.setActivationCounter(random.nextInt(32));
            } else {
                cell.setActivationCounter(0);
            }
        }
    }

    private List<Coordinates3D<Integer>> visitGrid() {
        final List<Coordinates3D<Integer>> coordList = new ArrayList<>();
        for (final var z: Ranges.of(0, this.state.getDepth())) {
            for (final var y: Ranges.of(0, this.state.getHeight())) {
                for (final var x: Ranges.of(0, this.state.getWidth())) {
                    final var coord = CoordinatesUtil.of(x, y, z);
                    coordList.add(coord);
                }
            }
        }
        return coordList;
    }

    @Override
    public Grid2D<CoDiCell> getGrid() {
        final int x = 0; //TODO per ora tengo un layer costante
        final Grid2D<CoDiCell> gridLayer = new Grid2DImpl<>(this.state.getWidth(), this.state.getDepth());
        for (final var z: Ranges.of(0, this.state.getDepth())) {
            for (final var y: Ranges.of(0, this.state.getHeight())) {
                final var coord2D = CoordinatesUtil.of(y, z);
                final var coord3D = CoordinatesUtil.of(x, y, z);
                CoDiCell cell = this.state.get(coord3D);
                if ((cell.getState() == CellState.AXON || cell.getState() == CellState.DENDRITE) 
                        && cell.getActivationCounter() != 0) {
                    cell = this.getActiveCell(cell);
                }
                gridLayer.set(coord2D, cell);
            }
        }
        return gridLayer;
    }

    private CoDiCell getActiveCell(final CoDiCell cell) {
        final CoDiCellBuilder builder = new CoDiCellBuilderImpl();
        return builder.gate(cell.getGate())
                      .activationCounter(cell.getActivationCounter())
                      .chromosome(cell.getChromosome())
                      .neighborsPreviousInput(cell.getNeighborsPreviousInput())
                      .state((cell.getState() == CellState.AXON) ? CellState.ACTIVATED_AXON : CellState.ACTIVATED_DENDRITE)
                      .build();
    }

    private void kicking() {
        this.kickPositiveDirections();
        this.kickNegativeDirections();
    }

    private void kickPositiveDirections() {
        for (int z = 0; z < this.state.getDepth(); z++) {
            for (int y = 0; y < this.state.getHeight(); y++) {
                for (int x = 0; x < this.state.getWidth(); x++) {
                    final Coordinates3D<Integer> coord = CoordinatesUtil.of(x, y, z);
                    this.setStateValueWithCheck(coord, CoordinatesUtil.of(x, y, z + 1), Direction.NORTH);
                    this.setStateValueWithCheck(coord, CoordinatesUtil.of(x, y + 1, z), Direction.TOP);
                    this.setStateValueWithCheck(coord, CoordinatesUtil.of(x + 1, y, z), Direction.EAST);
                }
            }
        }
    }

    private void kickNegativeDirections() {
        for (int z = this.state.getDepth() - 1; z >= 0; z--) {
            for (int y = this.state.getHeight() - 1; y >= 0; y--) {
                for (int x = this.state.getWidth() - 1; x >= 0; x--) {
                    final Coordinates3D<Integer> coord = CoordinatesUtil.of(x, y, z);
                    this.setStateValueWithCheck(coord, CoordinatesUtil.of(x, y, z - 1), Direction.SOUTH);
                    this.setStateValueWithCheck(coord, CoordinatesUtil.of(x, y - 1, z), Direction.BOTTOM);
                    this.setStateValueWithCheck(coord, CoordinatesUtil.of(x - 1, y, z), Direction.WEST);
                }
            }
        }
    }

    private void setStateValueWithCheck(final Coordinates3D<Integer> coord, final Coordinates3D<Integer> neighbourCoord,
            final Direction direction) {
        if (this.state.isCoordValid(neighbourCoord) && this.state.get(coord).getNeighborsPreviousInput().containsKey(direction)) {
            this.state.get(coord).setNeighborsPreviousInputDirection(direction,
                    this.state.get(neighbourCoord).getSpecificNeighborsPreviousInput(direction).get());
        } else {
            this.state.get(coord).setNeighborsPreviousInputDirection(direction, 0);
        }
    }

}