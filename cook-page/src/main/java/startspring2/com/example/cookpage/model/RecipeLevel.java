package startspring2.com.example.cookpage.model;

import lombok.Getter;

public enum RecipeLevel {
    EASE("ease"),
    MEDIUM("medium"),
    HARD("hard"),
    VERY_HARD("very hard");

    @Getter
    private String displayName;

    RecipeLevel(String displayName) {
        this.displayName = displayName;
    }

}
