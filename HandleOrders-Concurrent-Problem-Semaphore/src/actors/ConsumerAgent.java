package actors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import domain.Order;

public class ConsumerAgent extends Thread{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados
	private static int actualId = 1; // actualId static usado para se ter um Id unico para cada agente
	@SuppressWarnings("unused")
	private int id; // Id individual de cada agente
	private static Integer consumed_orders;
	private static int goal;
	private Semaphore semOrders;
	private Semaphore semConsumerBuffer;
	private Semaphore semBuffer;

	public ConsumerAgent(LinkedBlockingQueue<Order> buffer, Semaphore semOrders, Semaphore semConsumerBuffer, Semaphore semBuffer){
		this.buffer = buffer;
		this.id = actualId++;
		goal = buffer.remainingCapacity();
		this.semOrders = semOrders;
		this.semConsumerBuffer = semConsumerBuffer;
		this.semBuffer = semBuffer;
	}
	
	public static void reset(){
		actualId = 1; // reseta o actualId para a construção de um novo cenário de execução
		consumed_orders = 0;
	}

	public void run(){
		Boolean done = false;//sair do laço se terminou de produzir tudo
		Boolean remove = null;//para saber se já consumiu o total definido (5000)
		Order order;
		while(!done){
			try {
				semOrders.acquire();//entra na região crítica
				remove = consumed_orders < goal;
				if(remove){
					consumed_orders++;
					System.out.println("Consumed orders: " + consumed_orders);
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally{
				semOrders.release();//sai da região crítica
			}
			if(remove){
				try {
					semConsumerBuffer.acquire();//entra na região crítica
					order = buffer.poll();
					if(order == null){
						System.out.println("CA" + id + " - Esperando pedido.");
						semBuffer.acquire();
						order = buffer.poll();//Consome o produzido
						
						System.out.println("CA" + this.id + " - Consumi o pedido " + order.getId() + ".");
						semConsumerBuffer.release();
					}
					else{
						System.out.println("CA" + id + " - Consumi o pedido " + order.getId() + ".");
						//buffer.notify();
						semConsumerBuffer.release();
						semBuffer.acquire();
					}
					try{sleep(50);}
					catch(InterruptedException e){e.printStackTrace();}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				done = true;
			}
		}
		System.out.println("CA " + id + " terminou. " + consumed_orders);
	}
}
