package startspring2.com.example.cookpage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import startspring2.com.example.cookpage.model.Unit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateIngredientDto {

    private String name;
    private Unit unit;
    private Integer typeOfIngredientsId;
    private String description;


}
