package startspring2.com.example.cookpage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypesOfRecipesDto {

    private Integer id;
    private String name;
}
