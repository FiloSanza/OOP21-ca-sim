package casim.ui.components.menu;

import java.util.List;

import casim.ui.components.page.PageContainer;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

public abstract class AbstractMenu extends VBox {
    //TODO: Add controller & controller getter
    private final PageContainer container;

    public AbstractMenu(final PageContainer container) {
        this.container = container;
    }

    /**
     * Add a new control component to the menu.
     * 
     * @param control the control component that has to be added to the menu.
     */
    public void addControl(final Control control) {
        this.getChildren().add(control);
    }

    /**
     * Add a new control component to the menu.
     * 
     * @param control the control component that has to be added to the menu.
     */
    public void addControls(final List<Control> controls) {
        controls.forEach(this::addControl);
    }

    /**
     * Get the {@link PageContainer} where the menu is.
     * 
     * @return the {@link PageContainer} holding the menu.
     */
    public PageContainer getContainer() {
        return this.container;
    }
}