package casim.ui.components.menu;

import java.util.List;

import casim.controller.menu.MenuController;
import casim.ui.components.page.PageContainer;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Models the general functionalities of a menu.
 */
public abstract class AbstractMenu extends VBox {
    private final MenuController controller;
    private final PageContainer container;

    /**
     * Build a new {@link AbstractMenu}.
     * 
     * @param container the {@link PageContainer} holding the menu.
     * @param controller the {@link MenuController} controlling the menu.
     */
    public AbstractMenu(final PageContainer container, final MenuController controller) {
        this.controller = controller;
        this.container = container;
        this.setStyle("-fx-font-size: 20");
    }

    /**
     * Add a new node component to the menu.
     * 
     * @param <T> The type of node to add.
     * @param node the node component that has to be added to the menu.
     */
    public <T extends Node> void addNode(final T node) {
        this.getChildren().add(node);
    }

    /**
     * Add new nodes components to the menu.
     * 
     * @param <T> The type of node to add.
     * @param nodes the nodes components that have to be added to the menu.
     */
    public <T extends Node> void addNodes(final List<T> nodes) {
        nodes.forEach(this::addNode);
    }

    /**
     * Get the {@link MenuController}.
     * 
     * @return the {@link MenuController} .
     */
    public MenuController getMenuController() {
        return this.controller;
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
