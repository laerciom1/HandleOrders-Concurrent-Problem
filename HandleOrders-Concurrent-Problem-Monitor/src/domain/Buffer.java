package domain;

import java.util.concurrent.LinkedBlockingQueue;

public class Buffer{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados

	public Buffer(int size){ // inicia com o tamanho limite do buffer
		this.buffer = new LinkedBlockingQueue<Order>(size);
	}

	public void printBuffer(){ // imprime o buffer
		synchronized(buffer){
			for(Order i : buffer){
				i.printOrder();
			}
		}
	}

	public LinkedBlockingQueue<Order> getBuffer(){
		return buffer; // retorna o buffer
	}
}
