package startspring2.com.example.cookpage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    private Integer id;
    private String name;
    private Integer time;
    private RecipeLevel level;
    private TypesOfRecipes type;
    private String details;

    private List<AmountOfIngredients> amountOfIngredients;




}