#language: en
@add_to_cart @reset_app_state
Feature: Adicionar produtos ao carrinho

    Validar a adição de produtos ao carrinho a partir da tela "Products"

    Background: Login
        Given usuario acessa o site Swag Labs
        When usuario faz login com os seguintes dados
            | username      | password     |
            | standard_user | secret_sauce |
        Then usuario e direcionado a pagina Products

    @add_to_cart_1  @principal
    Scenario: Validar botão "Remove" após a adição de produto ao carrinho
        # Escolhe pelo nome do produto, caso vazio (""), a escolha é aleatória.
        Given usuario escolhe produto "" na pagina Products
        When usuario adicionar produto na pagina Products ao carrinho
        Then o botao Remove substitui o botao Add to Cart

    @add_to_cart_2   @altenative
    Scenario: Validar persistência dos detalhes do produto apos acesso em Products
        # Escolhe pelo nome do produto, caso vazio (""), a escolha é aleatória.
        Given usuario escolhe produto "" na pagina Products
        When usuario acessa o produto escolhido na pagina Products
        And usuario adicionar produto escolhido
        Then o botao Remove substitui o botao Add to Cart

    @add_to_cart_3  @principal
    Scenario: Validar quantidade de produtos adicionados ao carrinho
        Given usuario escolhe produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
            | Test.allTheThings() T-Shirt (Red) |
        When usuario adicionar produtos na pagina Products ao carrinho
        Then o icone de carrinho apresenta a quantidade produtos adicionados

    @add_to_cart_4  @principal
    Scenario: Validar quantidade de produtos adicionados ao carrinho
        Given usuario escolhe produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
            | Test.allTheThings() T-Shirt (Red) |
        And usuario adicionar produtos na pagina Products ao carrinho
        When usuario acessar a pagina Your Cart no Menu Superior
        Then os produtos adicionados aparecem listados na pagina Your Cart 