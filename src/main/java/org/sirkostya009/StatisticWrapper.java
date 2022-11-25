package org.sirkostya009;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@JacksonXmlRootElement(localName = "database")
public class StatisticWrapper {
    @JacksonXmlElementWrapper(localName = "statistics")
    @JacksonXmlProperty(localName = "statistic")
    List<Statistic> statistics;
}
