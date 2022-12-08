package org.sirkostya009.task2;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Utility {
    private static Object parseObject(Class<?> fieldClass, String value, Property propertyAnnotation) {
        if (Instant.class.equals(fieldClass))
            return LocalDateTime.parse(
                    value,
                    propertyAnnotation == null || propertyAnnotation.format().isBlank() ?
                            DateTimeFormatter.ISO_INSTANT :
                            DateTimeFormatter.ofPattern(propertyAnnotation.format())
            ).toInstant(ZoneOffset.UTC);

        if (int.class.equals(fieldClass) || Integer.class.equals(fieldClass))
            return Integer.parseInt(value);

        if (String.class.equals(fieldClass))
            return value;

        return null;
    }

    public static <T> T loadFromProperties(Class<T> clazz, Path propertiesPath) throws Exception {
        var properties = new Properties();
        properties.load(new FileInputStream(propertiesPath.toFile()));

        var instance = clazz.getDeclaredConstructor().newInstance();

        for (var field : clazz.getDeclaredFields()) {
            var annotation = field.getAnnotation(Property.class);
            var property = properties.getProperty( // should I crash when property is null, or should I just go on?
                    annotation == null || annotation.name().isBlank() ? field.getName() : annotation.name()
            );

            field.setAccessible(true);
            field.set(instance, parseObject(field.getType(), property, annotation));
        }

        return instance;
    }
}
