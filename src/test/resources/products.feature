#language:en
@products
Feature: Products

    Validar acesso ao carrinho de compras e a detalhes dos produtos e, adição
    dos produtos ao carrinho de compras através da pagina `Products`.

    Background: Login
        Given acesso ao site "https://www.saucedemo.com/"
        When usuario faz login com os seguintes dados
            | username      | password     |
            | standard_user | secret_sauce |
        Then usuario e direcionado a pagina Products

    @products_1 @principal
    Scenario: Validar a adicionar de um produto ao carrinho de compras na página `Products`
        # Neste step, use a variável texto vazia ("") para escolher um produto aleatório
        When usuario adicionar produto "" ao carrinho de compras na pagina `Products`
        Then o produto e adicionado ao carrinho de compras que apresenta 1 produto

    @products_2 @alternative
    Scenario: Validar a adicionar de mais de um produto ao carrinho de compras na página `Products`
        # Neste step, use a variável texto vazia ("") para escolher um produto aleatório
        When usuario adicionar produtos ao carrinho de compras na pagina `Products`
            | Sauce Labs Bike Light             |
            | Sauce Labs Onesie                 |
            | Test.allTheThings() T-Shirt (Red) |
        Then os produtos sao adicionados ao carrinho de compras que apresenta 3 produto

    @products_3 @principal
    Scenario: Validar a remoção de um produto adionado ao carrinho de compras na página `Products`
        # Neste step, use a variável texto vazia ("") para escolher um produto aleatório
        Given usuario adiciona produto "" ao carrinho de compras na pagina `Products`
        When usuario remove produto do carrinho de compras na pagina `Products`
        Then o produto e removido do carrinho de compras

    @products_4 @alternative
    Scenario: Validar a remoção de mais de um produto adicionado ao carrinho de compras na página `Products`
        # Neste step, use a variável texto vazia ("") para escolher um produto aleatório
        Given usuario adiciona produtos ao carrinho de compras na pagina `Products`
            | Sauce Labs Backpack      |
            | Sauce Labs Bolt T-Shirt  |
            | Sauce Labs Fleece Jacket |
        When usuario remove produtos do carrinho de compras na pagina `Products`
            | Sauce Labs Bolt T-Shirt  |
            | Sauce Labs Fleece Jacket |
        Then os produtos sao removidos do carrinho de compras que apresenta 1 produto

