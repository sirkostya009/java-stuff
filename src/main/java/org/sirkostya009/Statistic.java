package org.sirkostya009;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Statistic {
    @JacksonXmlProperty(localName = "type")
    private String type;
    @JacksonXmlElementWrapper(localName = "fines")
    @JacksonXmlProperty(localName = "fine")
    private List<Double> allFines;
}
