#language: en
@remove_from_cart    @reset_app_state
Feature: Remover produtos do carrinho

    Validar a remoção de um ou mais produtos do carrinho de compras a partir das telas "Products" ou "Your Cart".

    Background: Login
        Given usuario acessa o site Swag Labs
        When usuario faz login com os seguintes dados
            | username      | password     |
            | standard_user | secret_sauce |
        Then usuario e direcionado a pagina Products
    
    @remove_from_cart_1 @principal
    Scenario: Validar botão "Add to Cart" após remoção de produto do carrinho
        Given usuario escolhe produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
            | Test.allTheThings() T-Shirt (Red) |
        And usuario adicionar produtos na pagina Products ao carrinho
        # Escolhe pelo nome do produto, caso vazio (""), a escolha é aleatória.
        When usuario remove o produto "" na pagina Products
        Then o produto removido tem o botao Remove substituido pelo botao Add to Cart
        And os demais produtos mantem o botao Remove

        
    @remove_from_cart_2 @alternative
    Scenario: Validar botão "Add to Cart" após remoção de produto do carrinho em acesso ao Produto
        Given usuario escolhe produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
            | Test.allTheThings() T-Shirt (Red) |
        And usuario adicionar produtos na pagina Products ao carrinho
        # Escolhe pelo nome do produto, caso vazio (""), a escolha é aleatória.
        When usuario remove o produto "" na pagina Products apos acessar Produto
        Then o botao Add to Cart substitui o botao Remove

    @remove_from_cart_3 @principal
    Scenario: Validar quantidade de produtos adicionados ao carrinho
        Given usuario escolhe produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
            | Test.allTheThings() T-Shirt (Red) |
        And usuario adicionar produtos na pagina Products ao carrinho
        When usuario remove o produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
        Then o icone de carrinho apresenta a quantidade produtos adicionados

    @remove_from_cart_4 @principal
    Scenario: Validar quantidade de produtos adicionados ao carrinho
        Given usuario escolhe produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
            | Test.allTheThings() T-Shirt (Red) |
        And usuario adicionar produtos na pagina Products ao carrinho
        When usuario remove o produtos na pagina Products
            | Sauce Labs Bike Light             |
            | Sauce Labs Fleece Jacket          |
        And usuario acessar a pagina Your Cart no Menu Superior
        Then os produtos adicionados aparecem listados na pagina Your Cart
