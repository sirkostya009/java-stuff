package org.sirkostya009.task1;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JacksonXmlRootElement(localName = "fine")
public class Fine {
    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;
    @JacksonXmlProperty(localName = "sum", isAttribute = true)
    private Double sum;
}
