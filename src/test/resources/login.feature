#language:en
@login
Feature: Login
    
    Validar autenticação de usuário para acesso a página Products.

    Background: Acesso ao site
        Given acesso ao site "https://www.saucedemo.com/"

    @login_1   @principal
    Scenario Outline: Validar login com dados válidos
        When usuario faz login com os seguintes dados
            | username   | password   |
            | <username> | <password> |
        Then usuario e direcionado a pagina Products
        Examples:
            | username      | password     |
            | standard_user | secret_sauce |
            | visual_user   | secret_sauce |

    @login_2   @excecao
    Scenario Outline: Validar tentativa de login sem preenchimento de campo
        When usuario faz login com os seguintes dados
            | username   | password   |
            | <username> | <password> |
        Then a mensagem "<messageErrorExpected>" e apresentada na pagina Login
        Examples:
            | username    | password     | messageErrorExpected               |
            |             |              | Epic sadface: Username is required |
            |             | secret_sauce | Epic sadface: Username is required |
            | visual_user |              | Epic sadface: Password is required |

    @login_3    @excecao
    Scenario: Validar tentativa de login de usuario bloqueado
        When usuario faz login com os seguintes dados
            | username        | password     |
            | locked_out_user | secret_sauce |
        Then a mensagem "Epic sadface: Sorry, this user has been locked out." e apresentada na pagina Login

    Scenario: Validar tentativa de login de usuario inexistente
        When usuario faz login com os seguintes dados
            | username     | password     |
            | unknown_user | secret_sauce |
        Then a mensagem "Epic sadface: Username and password do not match any user in this service" e apresentada na pagina Login