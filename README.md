
###### Canditato Jaison Pereira ######

###### Estoque - Bebidas - ######

Stack:

-Java 8:
-Spring Boot
-Spring - Data
-JUNIT4
-MongoDB
-Maven

#-- Ambiente De Desenv --#

- Spring Tool Suite -- Eclipse Based
- Debian 8


# Data Base Technology ####
- tenho experiência com JPA/Hibernate/SpringData enfim bancos relacionais , porém para esse desafio optei por usar NOSQL - Mongo DB,
para que esse desafio  agregue no meu investimento em BIG DATA e sistemas distribuidos em Java
- a base esta hospedada em mlab em um perfil gratuito (Server Location Amazon Web Service)

# Estrategia de Consulta ##
AgregationFramework (Spring data) utilizado para gerar algumas consultas na base, não optei por utilizar spring query para deixar facil a manuntenção com criteria


#-- considerações realizadas#

# Cadastro e consulta das bebidas armazenadas em cada seção com suas respectivas queries
R: path parameter define qual seção é consultada:
ex: /estoque/secoes/1  (1 representa o numero da seção)


# Consulta do volume total no estoque por cada tipo de bebida
R:
Path parameter define o Enum Type do tipo de bebida
/estoque/{tipoBebida}


# Consulta dos locais disponíveis de armazenamento de um determinado volume e tipo
de bebida. (calcular via algoritmo).
R:
Path parameter define o Enum Type do tipo de bebida retornando as secoes disponives para armazenamento
Path: /estoque/disponivel/{tipoBebida}/?volume=45

# Consulta das seções disponíveis para venda de determinado tipo de bebida
Path: estoque/disponivel/venda/{tipoBebida}



# Cadastro de histórico de entrada e saída de bebidas em caso de venda e
recebimento.
atributo contendo o codigo de operacao de estoque devera ser enviado indicando a operacao realizada no caso Entrada ou saida Enum:OperacaoEstoqueType 
/estoque/secoes/{numeroseção}
Existem testem que garantem o funcionamento dessa operacao
porém é necessario informar o idDo ItemEstoque para efetuar a saida


# Consulta do histórico de entradas e saídas por tipo de bebida e seção.
- O endpoint de consulta de histórico de entrada e saída de estoque, deve retornar
os resultados ordenados por data e seção, podendo alterar a ordenação via
parâmetros.
R: O EndPoint realiza consulta ordenada (ascendente e descendente) que são manipuladas via parametro booleano

PATH: /historico?desc=true



## Comentarios no codigo ####
Prezo por clean code.Porem os comentarios no codigo é apenas para facilitar a dinamica do code review do recrutador

##### TESTES UNITáRIOS - JUNIT #####

- RestTemplate 
- utilizando Restfull validamos o "Http status code " para identificar a semantica dos retornos esperados
- Validação na camada de regra de negócio

# Requisito-Para situações de erro, é necessário que a resposta da requisição seja coerente em exibir uma mensagem condizente com o erro.
R: - Exceptions São tratadas por um @ControllerAdvice dos spring classe - ResourceExceptionHandler


#### Testes Junits Implementados######
#- Valida inserção  de operação de estoque com seção inexistente, retorna http status code not found 404(seção não encontrada)
#- Valida inserção  em numero de seção existente porém sem campos obrigatorios, horário, tipo, volume, seção e responsável pela entrada, erro esperado com http status na faixa  422(Unprocesable entity)
#-Valida Consulta  de seção inexistente na camada de negócio, esperando Exception NotFoundsecaoException
#-Valida tentativa de inserção de dois tipos de bebidas na mesma seção , esperando a Exception TwoDrinkTypeException
#-Valida tentativa de inserção de bebidas que excedem a capacidade de armazenamento da seção alcoolica(Obs volume é calculo por ml)
#-Valida tentativa de inserção de bebidas que excedem a capacidade de armazenamento da seção nao alcoolica(Obs volume é calculo por ml)
#-Valida se as operacoes de entrada realizadas via WebService sao registradas  historico
#-Valida inserção  de bebida nao permitida, com http status code not found 404(Tipo de bebida não encontrada)
#-Valida Consulta  de Tipo de bebida inexistente na camada de negócio, esperando Exception NotFoundTypeBebidaException
#-Valida se a quantidade inserida é a quantidade  retornada de volume, independentemente da seção armazenada,foi validado uma inserção de um tipo de bebida diferente nesse teste
#-Valida metodo de consulta de seções disponiveis para armazenar um determinado volume de um determinado tipo de bebida
#-Valida consulta de secoes disponiveis para venda a partir de um determinado tipo de bebida
#-Valida tentativa de saida de item inexistente no estoque
#-Validacao de tentativa de inserção de bebidas alcoolicas e nao alcoolicas no mesmo dia e na mesma secao.





