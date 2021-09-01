package startspring2.com.example.cookpage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import startspring2.com.example.cookpage.model.Unit;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDto {

    private Integer id;
    private String name;
    private Unit unit;
    private Integer typesOfIngredientsId;
    private String description;
}
