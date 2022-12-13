import org.junit.jupiter.api.Test;
import org.sirkostya009.task2.PropertyWrapper;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.sirkostya009.task2.Utility.loadFromProperties;

public class PropertyAnnotationTest {
    @Test
    public void testLoadFromProperties() throws Exception {
        var wrapper = loadFromProperties(PropertyWrapper.class, path("some.properties"));

        assertThat(wrapper.getStringProperty()).isEqualTo("value1");
        assertThat(wrapper.getIntProperty()).isEqualTo(10);
        assertThat(wrapper.getTimeProperty())
                .isEqualTo(LocalDateTime.of(2022, 11, 29, 18, 30).toInstant(ZoneOffset.UTC));
    }

    @Test
    public void testFaultyDate() {
        assertThatThrownBy(() -> loadFromProperties(PropertyWrapper.class, path("faulty_date.properties")))
                .hasMessageContaining("01.01.1980")
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    public void testEmpty() throws Exception {
        var wrapper = loadFromProperties(PropertyWrapper.class, path("empty.properties"));

        assertThat(wrapper.getStringProperty()).isNull();
        assertThat(wrapper.getTimeProperty()).isNull();
        assertThat(wrapper.getIntProperty()).isZero();
    }

    @Test
    public void testFaultyNumber() {
        assertThatThrownBy(() -> loadFromProperties(PropertyWrapper.class, path("faulty_number.properties")))
                .hasMessageContaining("va")
                .isInstanceOf(NumberFormatException.class);
    }

    private Path path(String resourceName) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(PropertyAnnotationTest.class.getResource(resourceName)).toURI());
    }
}
