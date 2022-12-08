import org.junit.jupiter.api.Test;
import org.sirkostya009.task2.PropertyWrapper;
import org.sirkostya009.task2.Utility;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyAnnotationTest {
    @Test
    public void testLoadFromProperties() throws Exception {
        var resourcePath = Path.of(PropertyAnnotationTest.class.getResource("some.properties").toURI());
        var wrapper = Utility.loadFromProperties(PropertyWrapper.class, resourcePath);

        assertThat(wrapper.getStringProperty()).isEqualTo("value1");
        assertThat(wrapper.getIntProperty()).isEqualTo(10);
        assertThat(wrapper.getTimeProperty())
                .isEqualTo(LocalDateTime.of(2022, 11, 29, 18, 30).toInstant(ZoneOffset.UTC));
    }

    @Test
    public void testFaultyDate() throws Exception {
        var resourcePath = Path.of(PropertyAnnotationTest.class.getResource("faulty_date.properties").toURI());
        var wrapper = Utility.loadFromProperties(PropertyWrapper.class, resourcePath);

    }
}
