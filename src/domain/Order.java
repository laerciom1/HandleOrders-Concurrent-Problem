package domain;

import java.util.Random;

public class Order{
	private static int actualId = 1;
	private String id;
	private String text;

	public Order(){
		Random rand = new Random();
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
		
		// converte o id atual para String para poder usar o .length() da classe
		String numberOfIdDigits = Integer.toString(actualId);
		// pega o numero de digitos que estão faltando para se ter 20 digitos no id
		int numberOfMissingIdDigits = 20 - numberOfIdDigits.length();
		this.id = new String();
		// adicionando os digitos que faltam para se ter 20 digitos na String final
		for(int i = 0; i < numberOfMissingIdDigits; i++){
			this.id += "0";
		}
		// adicionando o id atual para finalizar
		this.id += Integer.toString(actualId);
		// atualiza o id atual para o próximo pedido criado
		actualId++;
		// >> Deixando o texto no formato requerido
		// pega o numero de digito que estão faltando para se ter 1000 digitos no texto
		int numberOfMissingTextDigits = 1000 - finalText.length();
		this.text = new String(); // String que receberá o texto já formatado
		this.text += finalText; // adicionando o texto desejado
		// adicionando os digitos que faltam para se ter 1000 digitos na String final
		for(int i = 0; i < numberOfMissingTextDigits; i++){
			this.text += " ";
		}
	}

	public void printOrder(){ // imprime o pedido
		System.out.println("ID: " + this.id + ", Texto: " + this.text);
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
	}

	public String getId(){
		return id;
	}

	public String getTexto(){
		return text;
	}

	public static void reset(){
		actualId = 1; // reseta o actualId para a construção de um novo cenário de execução
	}
}
