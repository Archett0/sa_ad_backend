package sg.edu.nus.ad_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineLearningDTO {
    @JsonProperty("Author")
    private String Author;
    @JsonProperty("ISBN")
    private String ISBN;
    @JsonProperty("Title")
    private String Title;
}
