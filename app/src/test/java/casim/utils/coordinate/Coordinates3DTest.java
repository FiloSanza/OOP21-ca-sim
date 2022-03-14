package casim.utils.coordinate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link Coordinates3D}.
 */
class Coordinates3DTest {

    static final int XVALUE = 5;
    static final int YVALUE = 10;
    static final int ZVALUE = 15;
    static final Coordinates3D<Integer>  COORD = CoordinatesUtil.of(XVALUE, YVALUE, ZVALUE);
    /**
     * Test for {@link Coordinates3D#getX()} method.
     */
    @Test
    void testGetX() {
        Assert.assertEquals(XVALUE, (int) COORD.getX());
    }

    /**
     * Test for {@link Coordinates3D#getY()} method.
     */
    @Test
    void testGetY() {
        Assert.assertEquals(YVALUE, (int) COORD.getY());
    }

    /**
     * Test for {@link Coordinates3D#getZ()} method.
     */
    @Test
    void testGetZ() {
        Assert.assertEquals(ZVALUE, (int) COORD.getZ());
    }

    /**
     * Test for {@link Coordinates3D#equals(Object)} method.
     */
    @Test
    void testEquals() {
        Assert.assertTrue(COORD.equals(COORD));
        var coord01 = CoordinatesUtil.of(XVALUE, YVALUE, ZVALUE);
        Assert.assertTrue(COORD.equals(coord01));
        coord01 = CoordinatesUtil.of(YVALUE, XVALUE, ZVALUE);
        Assert.assertFalse(COORD.equals(coord01));
    }
}