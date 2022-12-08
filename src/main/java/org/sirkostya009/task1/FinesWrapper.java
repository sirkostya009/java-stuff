package org.sirkostya009.task1;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@JacksonXmlRootElement(localName = "database")
public class FinesWrapper {
    @JacksonXmlElementWrapper(localName = "fines")
    @JacksonXmlProperty(localName = "fine")
    List<Fine> fines;
}