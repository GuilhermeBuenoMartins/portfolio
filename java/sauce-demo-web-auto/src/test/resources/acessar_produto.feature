#language: en
@access_product
Feature: Acessar produto
    Validar o acesso ao produto e a persistência dos seus detalhes

    Background: Login
        Given usuario acessa o site Swag Labs
        When usuario faz login com os seguintes dados
            | username      | password     |
            | standard_user | secret_sauce |
        Then usuario e direcionado a pagina Products

    @access_product_1   @principal
    Scenario: Validar persistência dos detalhes do produto apos acesso em Products
        # Escolhe pelo nome do produto, caso vazio (""), a escolha é aleatória.
        Given usuario escolhe produto "" na pagina Products
        When usuario acessa o produto escolhido na pagina Products
        Then os detalhes do produto persistem ao serem apresentados individualmente
