package startspring2.com.example.cookpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.model.RecipeLevel;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findRecipesByLevel(RecipeLevel level);
    List<Recipe> findRecipesByType_Name(String name);
    List<Recipe> findRecipesByTimeIsLessThanEqual(Integer time);
    List<Recipe> findRecipesByTimeIsGreaterThanEqual(Integer time);
    List<Recipe> findRecipesByLevelAndTimeIsLessThanEqual(RecipeLevel level, Integer time);
    List<Recipe> findRecipesByLevelAndTimeIsGreaterThanEqual(RecipeLevel level, Integer time);
    List<Recipe> findRecipesByType_NameAndLevel(String typeName, RecipeLevel level);
    List<Recipe> findRecipesByType_NameAndTimeIsGreaterThanEqual(String typeName, Integer time);
    List<Recipe> findRecipesByType_NameAndTimeIsLessThanEqual(String typeName, Integer time);
    List<Recipe> findRecipesByLevelAndType_NameAndTimeIsLessThanEqual(RecipeLevel level, String typeName, Integer time);
    List<Recipe> findRecipesByLevelAndType_NameAndTimeIsGreaterThanEqual(RecipeLevel level, String typeName, Integer time);
    Recipe findRecipeByName(String name);

}
