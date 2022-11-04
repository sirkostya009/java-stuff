import org.junit.jupiter.api.Test;
import org.sirkostya009.Main;
import org.sirkostya009.shapes.Cube;
import org.sirkostya009.shapes.Cylinder;
import org.sirkostya009.shapes.Sphere;

import java.util.List;

public class MainTests {

    private final Main main = new Main();

    @Test
    void testNegativesOut() {
        var given = List.of(-1, 2, -3, 4, -5, 6, -7, 8, -9, 10);

        var result = main.filterNegativesOut(given);

        var expected = List.of(2, 4, 6, 8, 10);

        assert result.equals(expected);
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

        assert result.size() == 5;

        assert result.get(0).getKey().equals("#tag5");
        assert result.get(0).getValue().equals(1);

        assert result.get(1).getKey().equals("#tag1");
        assert result.get(1).getValue().equals(2);

        assert result.get(2).getKey().equals("#tag4");
        assert result.get(2).getValue().equals(3);

        assert result.get(3).getKey().equals("#tag3");
        assert result.get(3).getValue().equals(3);

        assert result.get(4).getKey().equals("#tag2");
        assert result.get(4).getValue().equals(5);
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

        assert ((Cube) result.get(0)).side() == .1;
        assert ((Cube) result.get(1)).side() == 5;
        assert ((Sphere) result.get(2)).radius() == 5;
        assert ((Cylinder) result.get(3)).radius() == 4;
        assert ((Cylinder) result.get(4)).radius() == 10;
        assert ((Sphere) result.get(5)).radius() == 15;
    }

}
