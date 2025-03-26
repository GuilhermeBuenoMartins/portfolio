package br.saucedemowebauto.dto.enums;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Optional;

public enum MenuLateral {

    ALL_ITEMS("All Items"), ABOUT("About"), LOGOUT("Logout"), RESET_APP_STATE("Reset App Store");

    private String text;

    private MenuLateral(String text) {
        this.text = text;
    }

    /**
     * Devolve um objeto do tipo <code>MenuLateral</code> a partir do texto da
     * opção.
     * @param text texto da opção.
     * @return objeto do tipo <code>MenuLateral</code>.
     */
    public static MenuLateral getMenuLateral(String text) {
        MenuLateral[] values = MenuLateral.values();
        final String search = text.toUpperCase().trim();
        Optional<MenuLateral> optional = Arrays.stream(values)
                .filter(value -> value.getText().toUpperCase().equals(search)).findFirst();
        if (optional.isEmpty()) {
            String errorMessage = "Não foi possível encontrar a opção de Menu Lateral de valor '" +
                    text + "'.";
            throw new InputMismatchException(errorMessage);
        }
        return optional.get();
    }

    /**
     * @return retorna valor da opção como <code>String</code>.
     */
    public String getText() {
        return text;
    }

}
