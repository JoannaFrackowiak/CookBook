package startspring2.com.example.cookpage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer time;
    private RecipeLevel level;
    @ManyToOne
    private TypesOfRecipes type;
    private String details;
    @OneToMany(mappedBy = "recipe")
    private List<AmountOfIngredients> amountOfIngredients;




}