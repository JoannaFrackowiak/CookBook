package startspring2.com.example.cookpage.model;

import lombok.Getter;

public enum Unit {
    PIECES(""),
    MILLILITRES("ml"),
    GRAMS("g"),
    TEASPOON("teaspoon"),
    TABLESPOON("tablespoon"),
    CUP("cup");

    @Getter
    private String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }
}
