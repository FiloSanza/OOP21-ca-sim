package casim.model.langtonsant;

import casim.utils.coordinate.Coordinates2D;
import casim.utils.coordinate.CoordinatesUtil;

/**
 * An ant contained in {@link casim.model.langtonsant.LangtonsAnt}.
 */
public class Ant {

    private Coordinates2D<Integer> position;
    private Direction direction;

    public Ant(final Direction direction, final Coordinates2D<Integer> position) {
        this.direction = direction;
        this.position = position;
    }

    /**
     * Returns the current direction of the {@link Ant}.
     * @return the current {@link Direction} of the {@link Ant}
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Sets the current {@link Direction} of the {@link Ant}.
     * @param direction the new {@link Direction} to be set.
     */
    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    /**
     * Returns the current position of the {@link Ant}.
     * @return a {@link Coordinates2D} representing the current position of the {@link Ant}
     */
    public Coordinates2D<Integer> getPosition() {
        return this.position;
    }

    /**
     * Sets the current position of the {@link Ant}.
     * @param position the new {@link Coordinates2D} to be set as the current postiono of the {@link Ant}
     */
    public void setPosition(final Coordinates2D<Integer> position) {
        this.position = position;
    }

    /**
     * Moves the {@link Ant} by changing the {@link Coordinates2D} according to the current {@link Direction} of
     * the {@link Ant}.
     */
    public void move() {
        final var moveInfo = this.direction.getMoveInfo();
        this.setPosition(CoordinatesUtil.of(this.position.getX() + moveInfo.get(0),
                this.position.getY() + moveInfo.get(1)));
    }
}
