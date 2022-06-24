package HW5.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
@Setter

public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;
}
