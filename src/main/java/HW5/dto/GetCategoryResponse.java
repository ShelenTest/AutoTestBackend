package HW5.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetCategoryResponse {

    private Integer id;
    private String title;
    private List<Product> products = new ArrayList<>();
}
