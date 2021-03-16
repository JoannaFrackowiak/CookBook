package startspring2.com.example.cookpage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private int id;
    private String name;
    private String type;
    private String description;
}
