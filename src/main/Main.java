package main;

import actors.ConsumerAgent;
import domain.Buffer;
import domain.Order;

public class Main{
	public static void main(String args[]){
		int BUFFER_POSITIONS = 5000; // numero de posicoes no buffer
		int NUMBER_OF_EXECUTIONS = 10; // numero de execuções para cada experimento (com 1, 5, ..., 1000 agents)
		int VARIETY_OF_AGENTS_NUMBER = 7; // Padrão da sequencia: 1, 5, 10, 50, 100, 500, 1000, ...
		long[][] processingTimes = new long[VARIETY_OF_AGENTS_NUMBER][NUMBER_OF_EXECUTIONS + 1];
		
		/**
		 * Cada linha dessa matriz terá como primeiro elemento o numero de agents utilizados
		 * no experimento, e os elementos seguintes serão o tempo que levou para a conclusão
		 * de cada experimento. exemplo.:
		 * +----+------+------+------+
		 * |[01]|[9000]|[9025]|[9020]| Essa matriz resultado indica que foram feitos 3
		 * +----+------+------+------+ experimentos (4 - 1 colunas, pois a primeira serve
		 * |[05]|[5000]|[5025]|[5020]| apenas para representar a quantidade de agentes usado
		 * +----+------+------+------+ no experimento) com 3 quantidades de agentes distintas
		 * |[10]|[2000]|[2025]|[2020]| (1, 5 e 10). Para os experimentos com a quantidade 1,
		 * +----+------+------+------+ por exemplo, obtivemos os resultados 9000, 9025 e 9020.
		 */
		
		/**
		 * Essa parte do código serve para iniciar a primeira coluna
		 * da matriz "processingTimes" descrita anderiormente de acordo
		 * o valor definido para a variável "VARIETY_OF_AGENTS_NUMBER",
		 * seguindo o padão [1, 5, 10, 50, 100, 500, 1000, ...].
		 * Se preferir, comente-a e inicialize a matriz manualmente,
		 * deixando no inicio de cada linha o numero de agentes
		 * que serão usados em cada experimento.
		 * Ex.:
		 * processingTimes[0][0] = 1;
		 * processingTimes[1][0] = 5;
		 * ...
		 * processingTimes[n][0] = 1000;
		 * Onde o valor da variável "VARIETY_OF_AGENTS_NUMBER" deverá ser
		 * setado com o valor de 'n'
		 */
		
		int int_aux = VARIETY_OF_AGENTS_NUMBER; // variável para iterar na linha da matriz
		
		// boolean_aux começa como true, para que dentro do while ele comece multiplicando o numberOfAgents por 5
		Boolean boolean_aux = true;
		
		int numberOfAgents = 1; // valor inicial do padrão
		
		while(int_aux > 0){
			// seta o numero de agentes da linha VARIETY_OF_AGENTS_NUMBER-int_aux
			processingTimes[VARIETY_OF_AGENTS_NUMBER - int_aux][0] = numberOfAgents;
						
			// boolean_aux começa com true, fazendo com que entre no if na primeira iteração
			if(boolean_aux){
				numberOfAgents *= 5;
			}
			// na segunda execução do laço, boolean_aux é false, fazendo com que entre no else
			else{
				numberOfAgents *= 2;
			}
			
			// alternando o valor de boolean_aux o processo se repete, sempre alternando
			// entre multiplicar por 5 ou por 2, e o processo termina quando int_aux chega a 0
			boolean_aux = !boolean_aux;
			int_aux--;
		}
		
		/**
		 *  Descomentar esse trecho para ver o estado inicial da matriz
		 *  "processingTimes" antes das execuções dos experimentos
		 */
		
//		 for(long[] i : processingTimes){
//			 for(long j : i){
//				 	System.out.print(j + " ");
//			 }
//			 System.out.print("\n");
//		 }
		
		/* Essa parte do código é responsável por executar os experimentos */
		
		// para cada numero distindo de agentes que devem estar envolvidos na execução
		for(int agentsNumberPosInMatrix = 0; agentsNumberPosInMatrix < VARIETY_OF_AGENTS_NUMBER; agentsNumberPosInMatrix++){
			// executara NUMBER_OF_EXECUTIONS vezes
			for(int execution = 1; execution < NUMBER_OF_EXECUTIONS + 1; execution++){
				
				// >> Montando o cenário
				Buffer buffer = new Buffer(BUFFER_POSITIONS); // cria o buffer
				ConsumerAgent[] consumerAgents = new ConsumerAgent[(int) processingTimes[agentsNumberPosInMatrix][0]];
				// cria um vetor de agentes do tamanho especificado
				// pelo primeiro elemento da linha correspondente na matriz
				for(int i = 0; i < (int) processingTimes[agentsNumberPosInMatrix][0]; i++){
					// instanciando os elementos do vetor passando como parametro a queue do buffer
					consumerAgents[i] = new ConsumerAgent(buffer.getBuffer());
				}
				Order.reset(); // reseta o actualId do order a cada execução
				buffer.feedBuffer(); // alimenta o buffer com pedidos aleatórios até que ele seja preenchido
				/**
				 * Descomente a linha abaixo caso queira ver como ficou o buffer após a inicialização
				 * ele será impresso no console, então nao eh recomendado para tamanhos de buffer muito altos
				 */
				
				// buffer.printBuffer();
				// >> Terminou de montar o cenário, irá começar o experimento
				
				// pega o tempo inicial do processamento
				processingTimes[agentsNumberPosInMatrix][execution] = System.currentTimeMillis();
				for(int i = 0; i < (int) processingTimes[agentsNumberPosInMatrix][0]; i++){ // inicia o processamento
					consumerAgents[i].start();
				}
				for(int i = 0; i < (int) processingTimes[agentsNumberPosInMatrix][0]; i++){
					// espera até que todos os agentes terminem (o que ocorre quando conseguem esvariar o buffer)
					try{
						consumerAgents[i].join();
					}
					catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				// pega o tempo final do processamento e calcula o tempo de execução dele,
				// guardando na sua respectiva posição da matriz resultado
				processingTimes[agentsNumberPosInMatrix][execution] = System.currentTimeMillis()
						- processingTimes[agentsNumberPosInMatrix][execution];
			}
		}
		
		/**
		 * Imprimindo a matriz resultado que conterá, como explicado anteriormente, os tempos de execução
		 * de cada experimento de acordo com a quantidade de agentes que foram utilizados em cada um deles
		 */		
		for(long[] i : processingTimes){
			System.out.print("N. de ConsumerAgents: " + i[0] + " - ");
			for(int j = 1; j < i.length; j++){
				System.out.print(i[j] + "ms ");
			}
			System.out.print("\n");
		}
	}
}
