# inventory-control
IF669 2013.2 UFPE

* Henrique Vicente (hvop)
* Victor Farias (vpf)

## Descrição do Projeto
Controle de estoque (entrada / saída de produtos).

## Entidades
* ProductAbstract: id, price (suggested), amount, name, vendor
* ProductPrototype: estende ProductPrototype
* Product: estende ProductAbstract + armazena propriedade de referência p/ id de ProductPrototype no atributo prototype
* Order: id, Product[], date, state (aberto, fechado), seller (id do Actor), buyer (id da Actor)
* Actor (id, name, company (opcional), email, phone, address)

## Relações entre entidades
ProductPrototype é um modelo de Product. Product é um objeto único que pode ser vendido. Observar que pode-se alterar ou remover um dado ProductPrototype sem alterar um Product criado que o tenha como referência.

## Algumas regras
* Pode-se adicionar em um pedido a quantidade máxima do produto disponível no estoque (repositório de produto).
* A cada produto adicionado é calculado o total esperado e o Product é adicionado no Order.
* Ao fechamento de um produto 
* Ao fechamento é verificado se todo produto anteriormente disponível no estoque continua disponível. Se não, não é possível fechar o pedido. Caso contrário, os produtos são removidos do estoque e o estado do pedido é mudado para finalizado
* Há dois repositórios para Actor (geradores de uma ação): um para vendedores, outro para compradores. Um Actor pode estar em mais de um repositório.
* IDs são do tipo String para todas as entidades e são únicas globalmente, porém possuem padrões diferentes.


## Relatórios possíveis
Vendas por ProductPrototype, filtros possíveis: vendedor, comprador, data.