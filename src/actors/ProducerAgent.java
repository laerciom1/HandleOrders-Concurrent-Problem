package actors;

import java.util.concurrent.LinkedBlockingQueue;
import domain.Order;

public class ProducerAgent extends Thread{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados
	private static int actualId = 1; // actualId static usado para se ter um Id unico para cada agente
	@SuppressWarnings("unused")
	private int id; // Id individual de cada agente
	private static Integer produced_orders;
	private static Integer goal;

	public ProducerAgent(LinkedBlockingQueue<Order> buffer){
		this.buffer = buffer;
		this.id = actualId++;
		goal = buffer.remainingCapacity();
	}
	
	public static void reset(){
		actualId = 1; // reseta o actualId para a construção de um novo cenário de execução
		produced_orders = 0;
	}
	
	public void run(){
		Boolean done = false;//se produziu 5000
		Boolean produced;//se conseguiu adicionar no buffer
		Boolean add;//se deve produzir ou não
		while(!done){
			produced = false;
			synchronized(produced_orders){
				add = produced_orders < goal;
				if(add){
					produced_orders++;
//					System.out.println("Produced orders: " + produced_orders);
				}
			}
			if(add){
				try{sleep(50);}
				catch(InterruptedException e){e.printStackTrace();}
				
				synchronized(buffer){
					Order order = new Order();
					produced = buffer.offer(order);
					if(!produced){
						try{
//							System.out.println("PA"+id+" - Esperando para inserir pedido.");
							while(!produced){
								buffer.wait();
								produced = buffer.offer(order);
							}
//							System.out.println("PA"+id+" - Produzi o pedido "+order.getId()+".");
							buffer.notify();
						}
						catch(InterruptedException e){e.printStackTrace();}
					}
					else{
//						System.out.println("PA"+id+" - Produzi o pedido "+order.getId()+".");
						buffer.notify();
					}
				}
			}
			else{
				done = true;
			}
		}
//		System.out.println("PA "+id+" terminou. " + produced_orders);
	}
}
