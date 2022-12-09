import org.junit.jupiter.api.Test;
import org.sirkostya009.task2.PropertyWrapper;
import org.sirkostya009.task2.Utility;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        assertThatThrownBy(() -> Utility.loadFromProperties(PropertyWrapper.class, resourcePath))
                .hasMessageContaining("01.01.1980")
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    public void testEmpty() throws Exception {
        var resourcePath = Path.of(PropertyAnnotationTest.class.getResource("empty.properties").toURI());

        var wrapper = Utility.loadFromProperties(PropertyWrapper.class, resourcePath);

        assertThat(wrapper.getStringProperty()).isNull();
        assertThat(wrapper.getTimeProperty()).isNull();
        assertThat(wrapper.getIntProperty()).isZero();
    }

    @Test
    public void testFaultyNumber() throws Exception {
        var resourcePath = Path.of(PropertyAnnotationTest.class.getResource("faulty_number.properties").toURI());

        assertThatThrownBy(() -> Utility.loadFromProperties(PropertyWrapper.class, resourcePath))
                .hasMessageContaining("va")
                .isInstanceOf(NumberFormatException.class);
    }
}
