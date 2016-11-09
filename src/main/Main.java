package main;

import java.util.concurrent.Semaphore;

import actors.ConsumerAgent;
import actors.ProducerAgent;
import domain.Buffer;
import domain.Order;

public class Main extends Thread{
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	   }  
	
	public static void main(String args[]){
		
//		long[][] processingTimes = new long[49][10+2];
		
		/**
		 * Cada linha dessa matriz ter� como primeiro elemento o numero de agents consumidores utilizados no
		 * experimento, como segundo elemento o numero de agentes produtores utilizados no experimento, e os
		 * elementos seguintes ser�o o tempo que levou para a conclus�o de cada experimento. Exemplo.:
		 * +----+----+------+------+------+
		 * |[01]|[01]|[9000]|[9025]|[9020]| Essa matriz resultado indica que foram feitos 3
		 * +----+----+------+------+------+ experimentos (5 - 2 colunas, pois as primeiras servem
		 * |[05]|[05]|[5000]|[5025]|[5020]| apenas para representar a quantidade de agentes usados
		 * +----+----+------+------+------+ no experimento) com 3 quantidades de agentes distintas,
		 * |[10]|[10]|[2000]|[2025]|[2020]| sendo, respectivamente, 1 consumidor e 1 produtor, 5 consumidores.
		 * +----+----+------+------+------+ e 5 produtores, 10 consumidores e 10 produtores. Para os experimentos
		 * com 1 consumidor e 1 produtor, por exemplo, obtivemos os resultados 9000, 9025 e 9020.
		 */
		
//		int consumers_int_aux = 7;
//		int producers_int_aux = 7;
//		Boolean consumers_boolean_aux = true;
//		Boolean producers_boolean_aux;
//		int numberOfConsumers = 1;
//		int numberOfProducers = 1;
//		
//		Semaphore semConsumerOrders = new Semaphore(1);
//		Semaphore semConsumerBuffer = new Semaphore(1);
//		
//		while(consumers_int_aux > 0){
//			producers_boolean_aux = true;
//			while(producers_int_aux > 0){
//				processingTimes[((7-consumers_int_aux)*7)+(7-producers_int_aux)][0] = numberOfConsumers;
//				processingTimes[((7-consumers_int_aux)*7)+(7-producers_int_aux)][1] = numberOfProducers;
//				
//				if(producers_boolean_aux){
//					numberOfProducers *= 5;
//				}
//				else{
//					numberOfProducers *= 2;
//				}
//				
//				producers_boolean_aux = !producers_boolean_aux;
//				producers_int_aux--;
//			}
//			if(consumers_boolean_aux){
//				numberOfConsumers *= 5;
//			}
//			else{
//				numberOfConsumers *= 2;
//			}
//			
//			consumers_boolean_aux = !consumers_boolean_aux;
//			consumers_int_aux--;
//			producers_int_aux = 7;
//			numberOfProducers = 1;
//		}
		
//		for(long[] i : processingTimes){
//			for(long j : i){
//				System.out.print(j + " ");
//			}
//			System.out.println("");
//		}
		
		
		Semaphore semConsumerOrders = new Semaphore(1);
		Semaphore semProducerOrders = new Semaphore(1);
		Semaphore semConsumerBuffer = new Semaphore(1);
		Semaphore semProducerBuffer = new Semaphore(1);
		Semaphore semBuffer = new Semaphore(0);
		long[] processingTimes = new long[10];
		int consumers_n = 10;
		int producers_n = 10;
			for(int j = 0; j < 1; j++){
				Buffer buffer = new Buffer(5000);
				Order.reset();
				ConsumerAgent.reset();
				ProducerAgent.reset();
				
				ConsumerAgent[] consumers = new ConsumerAgent[consumers_n];
				for(int c = 0; c < consumers.length; c++){
					consumers[c] = new ConsumerAgent(buffer.getBuffer(), semConsumerOrders, semConsumerBuffer, semBuffer);
				}
				ProducerAgent[] producers = new ProducerAgent[producers_n];
				for(int p = 0; p < producers.length; p++){
					producers[p] = new ProducerAgent(buffer.getBuffer(), semProducerOrders, semProducerBuffer, semBuffer);
				}
				
				processingTimes[j] = System.currentTimeMillis();
				for(int c = 0; c < consumers.length; c++){
					consumers[c].start();
				}
				for(int p = 0; p < producers.length; p++){
					producers[p].start();
				}
				for(int c = 0; c < (int) consumers.length; c++){
					// espera at� que todos os agentes terminem (o que ocorre quando conseguem esvariar o buffer)
					try{
						consumers[c].join();
					}
					catch(InterruptedException e){e.printStackTrace();}
				}
				for(int p = 0; p < producers.length; p++){
					try{
						producers[p].join();
					}
					catch(InterruptedException e){e.printStackTrace();}
				}
				processingTimes[j] = System.currentTimeMillis() - processingTimes[j];
				
				System.out.println(consumers_n + " CAs e " + producers_n + " PAs - terminado.");
			}
		
		
			for(long j : processingTimes){
				System.out.print(j + "	");
			}
			
	}
}
