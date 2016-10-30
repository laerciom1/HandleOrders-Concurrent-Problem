# Handle Orders Concurrent Problem
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Flag_of_Brazil.svg/2000px-Flag_of_Brazil.svg.png" align="right" width="100">

Pequeno projeto desenvolvido como trabalho prático sobre sincronização em programação concorrente.

## Objetivo
  
  O objetivo deste trabalho é estimular o projeto, implementação e avaliação de soluções para problemas por meio de programação concorrente, em particular colocando em prática os conceitos e mecanismos de sincronização de processos/threads.
  
## O problema

  Um dos maiores desafios no mercado altamente competitivo de hoje é o que se denomina Desafio do Fornecimento. As empresas competem de maneira inovadora e agressiva para que seus produtos e serviços sejam entregues aos clientes no menor tempo e com o menor custo possível. Nesse cenário, o uso de soluções e ferramentas computacionais tem se mostrado um diferencial estratégico significativo. Mais e mais empresas estão investindo na modernização de seus sistemas de apoio para que se destaquem com relação aos seus concorrentes de mercado.

  Uma das empresas diretamente envolvidas nessa disputa é a ATL Transportes e Logística. Essa empresa de transportes é responsável pela entrega dos produtos de uma série de grandes fornecedores e é historicamente muito conceituada em seu segmento de mercado. Contudo, com o advento das vendas pela Internet, a empresa tem percebido que sua capacidade de processamento de pedidos está gradativamente se mostrando aquém do necessário. Se continuar nesse ritmo de queda, em pouco tempo a ATL perderá o destaque no mercado alcançado ao longo dos anos. Ao perceber a necessidade de mudar suas operações, a ATL decidiu que era hora de rever o processo de atendimento a pedidos. Atualmente a recepção de pedidos para entrega de produtos é feita por telefone e/ou pela sua página na Internet. Apesar de permitir que vários pedidos sejam feitos ao mesmo tempo em uma capacidade de 50 atendentes por telefone e 300 solicitações simultâneas pela Internet, o processamento interno dos pedidos é feito por um sistema de software que os analisa um de cada vez. Foi essa a restrição no fluxo de operações identificada pela equipe interna de TI da ATL.

  Como todos os recursos humanos de TI da empresa estão alocados em outros projetos e dispõem de pouco ou nenhum tempo para a realização dessa modernização, o diretor de TI da ATL decidiu abrir uma concorrência no mercado para que diversas empresas apresentem uma solução para melhorar a capacidade de processamento de pedidos. A ideia geral é que seja implementada uma solução concorrente assíncrona para processamento dos pedidos seguindo os princípios gerais do Problema dos Produtores e Consumidores, clássico em sistemas concorrentes. Para essa concorrência aberta, a ATL não exige que seja necessário implementar um sistema de software completo, mas apenas um protótipo que torne possível evidenciar os resultados de uma futura implementação formal. Os pedidos serão enviados em um formato de dados que consiste de um identificador numérico com 20 dígitos e um pacote de dados em formato de texto de 1000 caracteres. Os clientes farão uso da nova ferramenta para alimentar um buffer interno com capacidade para 5000 pedidos. Um processamento interno assíncrono consumirá os pedidos e os processará individualmente, de modo que os clientes não esperarão on-line pela confirmação, mas receberão uma resposta posterior.
  
## Tarefas
  
  A tarefa central a ser realizada neste trabalho é conceber e implementar o protótipo requerido pela ATL para processamento dos pedidos utilizando conceitos e técnicas de programação concorrente, incluindo a criação, execução e sincronização de fluxos de execução (processos/threads) independentes e concorrentes. A solução deverá ser desenvolvida em quatro etapas, cada uma resultando em uma versão da implementação da solução, que deverá ser conservada para fins de avaliação comparativa.
  
  A solução poderá ser implementada utilizando facilidades providas pelas linguagens de programação C/C++ ou Java. A implementação deverá garantir corretude do programa com relação a concorrência e aplicar de forma adequada os conceitos e mecanismos de sincronização de processos/threads.
  
  Além disso, deve-se visar sempre pela busca pelo desenvolvimento de software de qualidade, isto é, funcionando correta e eficientemente, exaustivamente testado e bem documentado. Além da implementação, deverá ser elaborado um relatório escrito descrevendo, pelo menos:
  * como a solução foi projetada;
  * a lógica de sincronização utilizada, em termos dos mecanismos empregados e como ela é feita entre
os fluxos de execução do programa;
  * como é garantida a corretude da solução com relação a concorrência;
  * os resultados obtidos em experimentos computacionais realizados ao final da consecução de cada
etapa;
  * uma análise comparativa entre as diferentes versões da solução produzidas em cada etapa
  * eventuais dificuldades encontradas.
  
## Etapa 1
> Crie agentes baseados em threads que consumam um buffer de 5000 posições previamente preenchido com os pedidos (ou seja, apenas os agentes consumidores deverão ser implementados nesta etapa). O consumidor eliminará continuamente os pedidos do buffer e seu tempo de processamento deverá ser simulado por uma pausa de três segundos. Ao final de cada processamento, deverá ser exibido um log com a identificação do agente consumidor, a identificação do pedido, o horário de início e o horário de término do processamento. Quando o buffer estiver vazio, os agentes consumidores serão bloquedos. Por ora, não há a necessidade de se preocupar com a exclusão mútua necessária entre os diversos agentes consumidores. Uma vez concluída a implementação, faça um experimento no qual sejam iniciadas quantidades distintas de agentes consumidores simultâneos variando entre 1, 5, 10, 50, 100, 500 e 1000. Visando obter maior significância estatística, realize a execução de pelo menos dez execuções para cada uma dessas quantidades e armazene o tempo de execução de cada uma delas. Para exibição dos resultados, crie uma tabela na qual conste os tempos médio, mínimo e máximo, além do desvio padrão dentre as dez execuções para cada quantidade de agentes consumidores. Elabore também um gráfico mostrando os tempos médios de execução para cada quantidade de agentes consumidores.

## Etapa 2
> Altere a implementação realizada na etapa anterior de modo que o buffer seja agora preenchido por um agente (também baseado em threads) produtor. O buffer de pedidos é um objeto compartilhado e, portanto, deve-se garantir que a ação dos agentes produtores e consumidores seja feita de maneira exclusiva, ou seja, deverá haver exclusão mútua entre eles para evitar colisões. Os agentes produtores deverão criar um pedido e seu tempo de processamento deverá ser simulado por uma pausa de três segundos. Cada agente produtor alimentará o buffer continuamente enquanto houver espaço nele e deverá ser bloqueado quando estiver cheio. Quando houver um novo espaço disponível no buffer resultante do eventual consumo de um pedido por um agente consumidor, o agente produtor deverá ser reativado. Ao final de cada processamento, deverá ser exibido um log com a identificação do agente produtor ou consumidor, a identificação do pedido, o horário de início e o horário de término do processamento. Uma vez concluída a implementação, faça um novo experimento no qual sejam iniciadas quantidades distintas tanto de agentes produtores quanto consumidores simultâneos variando entre 1, 5, 10, 50, 100, 500 e 1000. Visando obter maior significância estatística, realize a execução de pelo menos dez execuções para cada uma dessas quantidades e armazene o tempo de execução de cada uma delas. Para exibição dos resultados, crie uma tabela na qual conste os tempos médio, mínimo e máximo, além do desvio padrão dentre as dez execuções para cada quantidade de agentes produtores e consumidores. Elabore também um gráfico mostrando os tempos médios de execução para cada quantidade de agentes.

## Etapa 3
> Altere a implementação realizada na etapa anterior de modo que todo o processo de exclusão mútua seja feito utilizando pelo menos dois dos mecanismos de sincronização de processos/threads concorrentes vistos. Você deverá certificar-se de que os conceitos do mecanismo adotado estejam sendo empregados corretamente e de que o programa está correto com relação a concorrência. Após a atividade de implementação, faça novamente o experimento realizado na etapa anterior, agora com os mecanismos de sincronização. Para discussão, compare os resultados obtidos agora com os obtidos anteriormente e analise o impacto (se houver) apresentado pela utilização de cada técnica, não só em termos de desempenho, mas também em termos de simplificação do código e facilidade de manutenção futura.

## Etapa 4
> Altere a implementação realizada na etapa anterior para atender a um novo requisito colocado pela diretoria de TI da ATL: agora deseja-se garantir que cada um dos pedidos seja processado na ordem em que foi solicitado, numa política FIFO (first-in, first-out). Você deverá aprimorar sua implementação do agente consumidor de modo a garantir essa especificação. Após a atividade de implementação, faça novamente o experimento realizado na etapa anterior, agora com o requisito de garantia de ordem do processamento dos pedidos. Para discussão, compare os resultados obtidos agora com os obtidos anteriormente e analise o impacto (se houver) apresentado pela inclusão desse novo, não só em termos de desempenho, mas também em termos de simplificação do código e facilidade de manutenção futura.
