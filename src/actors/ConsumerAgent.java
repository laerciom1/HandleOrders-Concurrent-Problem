package actors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import domain.Order;

public class ConsumerAgent extends Thread{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados
	private static int actualId = 1; // actualId static usado para se ter um Id unico para cada agente
	@SuppressWarnings("unused")
	private int id; // Id individual de cada agente

	public ConsumerAgent(LinkedBlockingQueue<Order> buffer){
		this.buffer = buffer;
		this.id = actualId++;
	}

	@SuppressWarnings("unused")
	@Override
	public void run(){
		Order order;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date horaInicio;
		Date horaFim;
		Boolean done = false;
		while( !done){
			order = buffer.poll(); // remove um pedido da fila para processar
			if(order != null){ // se o pedido nao for nulo, significa que o buffer ainda não está vazio
				horaInicio = Calendar.getInstance().getTime(); // pega a hora que irá iniciar o processamento
				try{
					// dorme por 3 segundos representando o processamento do pedido.
					// utilize valores menores para testes (experimentos)
					Thread.sleep(50);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				horaFim = Calendar.getInstance().getTime(); // pega a hora que termina de processar
				// Exibe no console o agente responsavel, o pedido processado, hora de inicio e de fim do processamento
				
				// System.out.println("++>> ConsumerAgent [" + id + "] "
				// + "Processou o pedido " + order.getId() + ".\n"
				// + "Horário de início: " + format.format(horaInicio) + " - "
				// + "Horario de termino: " + format.format(horaFim));
			}
			else{ // caso o order seja nulo, significa que o buffer está vazio
				done = !done; // sinaliza que o trabalho terminou, saindo do laço e terminando o método
			}
		}
	}
}
