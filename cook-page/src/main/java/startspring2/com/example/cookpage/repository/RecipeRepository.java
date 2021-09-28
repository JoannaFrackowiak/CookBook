package startspring2.com.example.cookpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.model.RecipeLevel;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findRecipesByLevel(RecipeLevel level);
    List<Recipe> findRecipesByType_Id(Integer id);
    List<Recipe> findRecipesByTimeLessThan(Integer time);


}
