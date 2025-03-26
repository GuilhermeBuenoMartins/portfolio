#language: en
@menu_lateral
Feature: Menu Lateral

    Validar o funcionalidade das opções do Menu Lateral.

    Background: Login
        Given acesso ao site "https://www.saucedemo.com/"
        When usuario faz login com os seguintes dados
            | username      | password      |
            | standard_user | secret_sauce  |
        Then usuario e direcionado a pagina Products

    @menu_lateral_1 @products   @principal
    Scenario: Validar acesso a opção All Items na pagina Products
        When usuario acessa a opcao "All Items" na pagina Products
        Then usuario e direcionado a pagina Products

    @menu_lateral_2 @products   @principal
    Scenario: Validar acesso a opção About na pagina Products
        When usuario acessa a opcao "About" na pagina Products
        Then usuario e direcionado a pagina SauceLabs

    @menu_lateral_3 @products   @principal
    Scenario: Validar acesso a opção Logout na pagina Products
        When usuario acessa a opcao "Logout" na pagina Products
        Then usuario e direcionado a pagina Login

    