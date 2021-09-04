# Turing-Machine

Esse programa foi desenvolvido com o aprendizado em mente. Ele explora a funcionalidade 
de uma máquina de Turing com fita ilimitada e três movimentos.

Para simular a fita infinita é criado um vetor com 1024 caracteres em cada lado do input,
por conta disso, há um offset de também 1024 células na condução da cabeça de leitura e 
escrita.

Cada máquina precisa de conjunto de comandos, enviados via documento .csv (mais sobre sua 
formatação depois), o estado inicial da máquina e um vetor de estados finais.

Métodos para imprimir o caminho de execução, configurar a fita inicial, coletar informações
da tabela de comandos e a própria execução da máquina, que recebe a entrada para análise
também estão presentes.

# Como configurar sua própria máquina

Primeiro, crie um arquivo .csv e salve na pasta de recursos ```res```. Depois preencha o cabeçalho
do documento com a seguinte linha para se guiar na edição do arquiv (o cabeçalho também é importante
porque o algoritmo de leitura dos comandos ignora a primeira linha do documento, destinada para a
identificação das colunas na tabela):

```Estado ! Leitura ! Escrita ! Movimentacao ! ParaEstado```

Note os "!" estre as colunas, ele é o delimitador para leitura das colunas, ele é necessário para separar
as informações de cada parâmetro (Se a exclamação não te interessar, esse argumento pode ser alterado
em [UniversalMachine.java: linha 38]).

```lineScanner.useDelimiter("!");```

Vale notar também que as colunas de 'Estado' e 'Para Estado' adimitem strings, diferente das outras colunas,
que aceitam apenas characteres. Com caso especial para coluna de 'Movimentcao' que aceita apenas as 
entradas {R, L, S} referentes aos movimentos que a máquina pode realizar:
  - R: Direita;
  - L: Esquerda;
  - S: Estacionário.

A condição de parada é alcançada quando o comando analizado aponta para um próximo estado com um dos seguintes
valores: ```{'#', '%', ' '}```. Quando um comando apontar para um desses caracteres na coluna 'ParaEstado',
a máquina verifica se o estado atual pertence ao conjunto de estados finais enviados na sua criação para
mais uma checagem.

# Exemplo de Tabela de Comandos

Esse exemplo de Tabela de Comandos pertence a uma máquina já presente na aplicação responsável por avaliar se
o número de a e b são iguais na fita de entrada enviada:

```
Estado ! Leitura ! Escrita ! Movimentacao ! ParaEstado
 A     ! a       ! A       ! R            ! B
 A     ! b       ! B       ! R            ! D
 A     ! A       ! A       ! R            ! A
 A     ! B       ! B       ! R            ! A
 A     !         !         ! L            ! #
 B     ! a       ! a       ! R            ! B
 B     ! B       ! B       ! R            ! B
 B     ! A       ! A       ! R            ! B
 B     ! b       ! B       ! S            ! C
 C     ! a       ! a       ! L            ! C
 C     ! A       ! A       ! L            ! C
 C     ! b       ! b       ! L            ! C
 C     ! B       ! B       ! L            ! C
 C     !         !         ! R            ! A
 D     ! b       ! b       ! R            ! D
 D     ! B       ! B       ! R            ! D
 D     ! A       ! A       ! R            ! D
 D     ! a       ! A       ! S            ! C
```

Nessa máquina temos que o estado A é o estado inicial e a tabela apresenta todos comandos de cada um dos estados
para elaboração da máquina. As informações para leitura e escrita estão nas colunas 2 e 3, respectivamente, e
a coluna de movimentção está restrita aos caracteres R, L e S. Com análise da tabela sabemos também que o estado A
leva à uma condição de parada, mas só a operação só será dada como bem sucedida se A também pertencer ao grupo
de estados finais da máquina enviados na criação da mesma.



Obrigado por visitar o repositório! :D
