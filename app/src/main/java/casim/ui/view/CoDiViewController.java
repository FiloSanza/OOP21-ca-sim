package casim.ui.view;

import casim.controller.automaton.AutomatonController;
import casim.controller.automaton.CoDiControllerImpl;
import casim.model.codi.cell.attributes.CoDiCellState;
import casim.ui.components.grid.CanvasGridImpl;
import casim.ui.components.page.PageContainer;
import casim.ui.utils.AlertBuilderImpl;
import casim.ui.utils.StateColorMapper;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Implementation of CoDi's {@link AutomatonViewController}.
 */
public class CoDiViewController extends AutomatonViewController<CoDiCellState> {

    private static final String LAYER_INFO = "Remember you can change layer! (Default 0)"
            + "\nA -> go left (-1)"
            + "\nD -> go right (+1)";


    /**
     * Construct a new {@link CodiViewController}.
     * 
     * @param container the {@link PageContainer} holding the view.
     * @param controller the {@link AutomatonController} controlling the view.
     * @param grid the {@link CanvasGridImpl} to be drawn.
     * @param colorMapper the {@link StateColorMapper} that translates cell states to colors.
     */
    public CoDiViewController(final PageContainer container, final AutomatonController<CoDiCellState> controller,
            final CanvasGridImpl grid, final StateColorMapper<CoDiCellState> colorMapper) {
        super(container, controller, grid, colorMapper);
        new AlertBuilderImpl()
        .buildDefaultInfo(LAYER_INFO, container.getOwner())
        .showAndWait();
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.getView().addEventFilter(KeyEvent.KEY_PRESSED, this.changeLayerHandler());
    }

    private EventHandler<KeyEvent> changeLayerHandler() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                if (event.getCode() == KeyCode.A) {
                    final var state = ((CoDiControllerImpl) CoDiViewController.this.getController()).outputLayerLeftShift();
                    CoDiViewController.this.setCellsDrawUpdateStats(state);
                } else if (event.getCode() == KeyCode.D) {
                    final var state = ((CoDiControllerImpl) CoDiViewController.this.getController()).outputLayerRightShift();
                    CoDiViewController.this.setCellsDrawUpdateStats(state);
               }
            }
        };
    }
}
