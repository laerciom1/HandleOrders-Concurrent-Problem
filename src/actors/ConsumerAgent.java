package actors;

import java.util.concurrent.LinkedBlockingQueue;
import domain.Order;

public class ConsumerAgent extends Thread{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados
	private static int actualId = 1; // actualId static usado para se ter um Id unico para cada agente
	@SuppressWarnings("unused")
	private int id; // Id individual de cada agente
	private static Integer consumed_orders;
	private static int goal;

	public ConsumerAgent(LinkedBlockingQueue<Order> buffer){
		this.buffer = buffer;
		this.id = actualId++;
		goal = buffer.remainingCapacity();
	}
	
	public static void reset(){
		actualId = 1; // reseta o actualId para a construção de um novo cenário de execução
		consumed_orders = 0;
	}

	public void run(){
		Boolean done = false;//sair do laço se terminou de produzir tudo
		Boolean remove;//para saber se já consumiu o total definido (5000)
		Order order;
		while(!done){
			synchronized(consumed_orders){
				remove = consumed_orders < goal;
				if(remove){
					consumed_orders++;
//					System.out.println("Consumed orders: " + consumed_orders);
				}
				
			}
			if(remove){
				synchronized(buffer){
					order = buffer.poll();
					if(order == null){
						try{
//							System.out.println("CA" + id + " - Esperando pedido.");
							while(order == null){
								buffer.wait();//Espera produzir
								order = buffer.poll();//Consome o produzido
							}
//							System.out.println("CA" + this.id + " - Consumi o pedido " + order.getId() + ".");
							buffer.notify();
						}
						catch(InterruptedException e){e.printStackTrace();}
					}
					else{
//						System.out.println("CA" + id + " - Consumi o pedido " + order.getId() + ".");
						buffer.notify();
					}
					try{sleep(50);}
					catch(InterruptedException e){e.printStackTrace();}
				}
			}
			else{
				done = true;
			}
		}
//		System.out.println("CA " + id + " terminou. " + consumed_orders);
	}
}
