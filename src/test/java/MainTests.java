import org.junit.jupiter.api.Test;
import org.sirkostya009.Main;
import org.sirkostya009.shapes.Cube;
import org.sirkostya009.shapes.Cylinder;
import org.sirkostya009.shapes.Sphere;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTests {

    @Test
    void testNegativesOut() {
        var given = new Integer[]{-1, 2, -3, 4, -5, 6, -7, 8, -9, 10};

        var result = Main.filterNegativesOut(given);

        assertThat(result).isEqualTo(List.of(2, 4, 6, 8, 10));
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

        assertThat(result).isEqualTo(Map.of(
                "#tag2", 5,
                "#tag3", 3,
                "#tag4", 3,
                "#tag1", 2,
                "#tag5", 1
                )
        );
    }

    @Test
    void testSortShapes() {
        var cube1 = new Cube(5);
        var cylinder1 = new Cylinder(4, 10);
        var sphere1 = new Sphere(15);
        var cube2 = new Cube(.1);
        var sphere2 = new Sphere(5);
        var cylinder2 = new Cylinder(10, 5);

        var shapes = List.of(cube1, cylinder1, sphere1, cube2, sphere2, cylinder2);

        var result = Main.sortShapes(shapes);

        assertThat(result).isEqualTo(List.of(cube2, cube1, cylinder1, sphere2, cylinder2, sphere1));
    }

}
