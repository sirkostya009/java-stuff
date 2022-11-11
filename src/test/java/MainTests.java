import org.junit.jupiter.api.Test;
import org.sirkostya009.Main;
import org.sirkostya009.shapes.Cube;
import org.sirkostya009.shapes.Cylinder;
import org.sirkostya009.shapes.Sphere;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTests {

    @Test
    void testNegativesOut() {
        var given = List.of(-1, 2, -3, 4, -5, 6, -7, 8, -9, 10);

        var result = Main.filterNegativesOut(given);

        assertEquals(List.of(2, 4, 6, 8, 10), result);
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

        var result = Main.top5Hashtags(given);

        assertEquals(5, result.size());

        assertEquals("#tag5", result.get(0).getKey());
        assertEquals(1, result.get(0).getValue());

        assertEquals("#tag1", result.get(1).getKey());
        assertEquals(2, result.get(1).getValue());

        assertEquals("#tag4", result.get(2).getKey());
        assertEquals(3, result.get(2).getValue());

        assertEquals("#tag3", result.get(3).getKey());
        assertEquals(3, result.get(3).getValue());

        assertEquals("#tag2", result.get(4).getKey());
        assertEquals(5, result.get(4).getValue());
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

        var result = Main.sortShapes(given);

        assertEquals(.1, ((Cube) result.get(0)).getSide());
        assertEquals(.5, ((Cube) result.get(1)).getSide());
        assertEquals(5, ((Sphere) result.get(2)).getRadius());
        assertEquals(4, ((Cylinder) result.get(3)).getRadius());
        assertEquals(10, ((Cylinder) result.get(4)).getRadius());
        assertEquals(15, ((Sphere) result.get(5)).getRadius());
    }

}
