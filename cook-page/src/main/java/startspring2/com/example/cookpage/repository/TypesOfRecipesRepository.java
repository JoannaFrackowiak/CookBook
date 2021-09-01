package startspring2.com.example.cookpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import startspring2.com.example.cookpage.model.TypesOfRecipes;

@Repository
public interface TypesOfRecipesRepository extends JpaRepository<TypesOfRecipes, Integer> {
}
