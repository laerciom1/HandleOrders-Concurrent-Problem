package domain;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer{
	private LinkedBlockingQueue<Order> buffer; // fila dos pedidos a serem processados

	public Buffer(int size){ // inicia com o tamanho limite do buffer
		this.buffer = new LinkedBlockingQueue<Order>(size);
	}

	public void feedBuffer(){ // alimenta o buffer com pedidos que terão textos aletórios
		Random rand = new Random();
		Boolean added = true;
		synchronized(buffer){
			while(added != false){ // enquanto ainda for possível adicionar
				// tamanho do texto aleatório (qtd de digitos) que sera gerado
				int[] digitos = new int[rand.nextInt(1000) + 1];
				for(int i = 0; i < digitos.length; i++){
					// pegando entre 48 e 122 na tabela ASCII, o que corresponde a
					// todas as letras (aA-zZ) e alguns simbolos
					digitos[i] = rand.nextInt(74) + 48 + 1;
				}
				String finalText = new String();
				for(int i : digitos){
					// inserindo os inteiros na string com cast para char, para que se tornem os digitos do texto
					finalText += (char) i;
				}
				Order order = new Order(finalText); // cria um novo order com o texto gerado
				// adiciona no buffer. caso o buffer esteja cheio, o metodo offer retornará false, fazendo com que
				// a execução saia do laço, ja que added sera false
				added = buffer.offer(order);
			}
		}
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
