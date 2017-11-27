package ch.fhnw.edu.eaf.rentalmgmt.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonAutoDetect
public class MovieDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("rented")
    private boolean rented;
    @JsonProperty("releaseDate")
    private Date releaseDate;
    @JsonProperty("priceCategory")
    private PriceCategoryDTO priceCategory;

    @JsonAutoDetect
    @Data
    @NoArgsConstructor
    class PriceCategoryDTO {
        @JsonProperty("id")
        private long id;
        @JsonProperty("name")
        private String name;
    }

}
