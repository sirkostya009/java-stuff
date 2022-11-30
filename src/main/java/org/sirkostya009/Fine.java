package org.sirkostya009;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@JacksonXmlRootElement(localName = "fine")
public class Fine {
    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;
    @JacksonXmlProperty(localName = "sum", isAttribute = true)
    private Double sum;

    public Fine(Map.Entry<String, Double> entry) {
        this.type = entry.getKey();
        this.sum = entry.getValue();
    }
}
