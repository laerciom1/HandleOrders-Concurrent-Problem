package actors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import domain.Order;

public class ProducerAgent extends Thread{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados
	private static int actualId = 1; // actualId static usado para se ter um Id unico para cada agente
	@SuppressWarnings("unused")
	private int id; // Id individual de cada agente
	private static Integer produced_orders;
	private static Integer goal;
	private Semaphore semOrders;
	private Semaphore semProducerBuffer;
	private Semaphore semBuffer;
	

	public ProducerAgent(LinkedBlockingQueue<Order> buffer, Semaphore semOrders, Semaphore semProducerBuffer, Semaphore semBuffer){
		this.buffer = buffer;
		this.id = actualId++;
		goal = buffer.remainingCapacity();
		this.semOrders = semOrders;
		this.semProducerBuffer = semProducerBuffer;
		this.semBuffer = semBuffer;
	}
	
	public static void reset(){
		actualId = 1; // reseta o actualId para a constru��o de um novo cen�rio de execu��o
		produced_orders = 0;
	}
	
	public void run(){
		Boolean done = false;//se produziu 5000
		Boolean produced;//se conseguiu adicionar no buffer
		Boolean add = null;//se deve produzir ou n�o
		while(!done){
			produced = false;
			try {
				semOrders.acquire();//entra na região crítica
				add = produced_orders < goal;
				if(add){
					produced_orders++;
					System.out.println("Produced orders: " + produced_orders);
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				semOrders.release();//sai da região crítica
			}
			if(add){
				try{sleep(50);}
				catch(InterruptedException e){e.printStackTrace();}
				
				try {
					semProducerBuffer.acquire();
					Order order = new Order();
					produced = buffer.offer(order);
					if(!produced){
						System.out.println("PA"+id+" - Esperando para inserir pedido.");
						while(!produced){
							//buffer.wait();
							produced = buffer.offer(order);
						}
						System.out.println("PA"+id+" - Produzi o pedido "+order.getId()+".");
						//buffer.notify();
						semProducerBuffer.release();
						semBuffer.release();
					}
					else{
						System.out.println("PA"+id+" - Produzi o pedido "+order.getId()+".");
						semProducerBuffer.release();
						semBuffer.release();
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else{
				done = true;
			}
		}
		System.out.println("PA "+id+" terminou. " + produced_orders);
	}
}
