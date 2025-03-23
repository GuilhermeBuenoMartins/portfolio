#language:en
@login
Feature: Login
    Validar autenticação de usuário para acesso a página `Products`.

    Background: Acesso ao site
        Given acesso ao site "https://www.saucedemo.com/"
    
    @login_01   @principal
    Scenario: Validar login com dados válidos
        When usuario faz login com dados validos
            | user          | password     |
            | standard_user | secret_sauce |
        Then usuario e direcionado a pagina Products