package ua.sirkostya009.javastuff.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Declaration {
    @JsonProperty("position_en")
    private String positionEn;
    @JsonProperty("url")
    private String url;
    @JsonProperty("income")
    private long income;
    @JsonProperty("region_uk")
    private String regionUk;
    @JsonProperty("office_en")
    private String officeEn;
    @JsonProperty("position_uk")
    private String positionUk;
    @JsonProperty("year")
    private String year;
    @JsonProperty("office_uk")
    private String officeUk;
    @JsonProperty("region_en")
    private String regionEn;
}
