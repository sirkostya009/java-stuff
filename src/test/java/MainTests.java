import org.junit.jupiter.api.Test;
import org.sirkostya009.Main;
import org.sirkostya009.shapes.Cube;
import org.sirkostya009.shapes.Cylinder;
import org.sirkostya009.shapes.Sphere;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTests {

    private final Main main = new Main();

    @Test
    void testNegativesOut() {
        var given = List.of(-1, 2, -3, 4, -5, 6, -7, 8, -9, 10);

        var result = main.filterNegativesOut(given);

        var expected = List.of(2, 4, 6, 8, 10);

        assertEquals(expected, result);
    }

    @Test
    void testTop5Hashtags() {
        var given = List.of(
                "#tag1 ".repeat(5),
                "#tag2",
                "#tag2",
                "#tag2",
                "#tag2",
                "#tag3 #tag1",
                "#tag3 #tag2",
                "#tag3 #tag3",
                "another cool #tag4",
                "#tag4 -- do you see this?",
                "#tag4",
                "#tag5 ".repeat(15)
        );

        var result = main.top5Hashtags(given);

        assertEquals(result.size(), 5);

        assertEquals(result.get(0).getKey(), "#tag5");
        assertEquals(result.get(0).getValue(), 1);

        assertEquals(result.get(1).getKey(), "#tag1");
        assertEquals(result.get(1).getValue(), 2);

        assertEquals(result.get(2).getKey(), "#tag4");
        assertEquals(result.get(2).getValue(), 3);

        assertEquals(result.get(3).getKey(), "#tag3");
        assertEquals(result.get(3).getValue(), 3);

        assertEquals(result.get(4).getKey(), "#tag2");
        assertEquals(result.get(4).getValue(), 5);
    }

    @Test
    void testSortShapes() {
        var given = List.of(
                new Cube(5),
                new Cylinder(4, 10),
                new Sphere(15),
                new Cube(.1),
                new Sphere(5),
                new Cylinder(10, 5)
        );

        var result = main.sortShapes(given);

        assertEquals(((Cube) result.get(0)).getSide(), .1);
        assertEquals(((Cube) result.get(1)).getSide(), .5);
        assertEquals(((Sphere) result.get(2)).getRadius(), 5);
        assertEquals(((Cylinder) result.get(3)).getRadius(), 4);
        assertEquals(((Cylinder) result.get(4)).getRadius(), 10);
        assertEquals(((Sphere) result.get(5)).getRadius(), 15);
    }

}
