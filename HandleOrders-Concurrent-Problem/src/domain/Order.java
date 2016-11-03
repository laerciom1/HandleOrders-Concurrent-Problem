package domain;

public class Order{
	private static int actualId = 1;
	private String id;
	private String text;

	public Order(String text){
		// converte o id atual para String para poder usar o .length() da classe
		String numberOfIdDigits = Integer.toString(actualId);
		// pega o numero de digitos que estão faltando para se ter 20 digitos no id
		int numberOfMissingIdDigits = 20 - numberOfIdDigits.length();
		id = new String();
		// adicionando os digitos que faltam para se ter 20 digitos na String final
		for(int i = 0; i < numberOfMissingIdDigits; i++){
			id += "0";
		}
		// adicionando o id atual para finalizar
		id += Integer.toString(actualId);
		// atualiza o id atual para o próximo pedido criado
		actualId++;
		// >> Deixando o texto no formato requerido
		// pega o numero de digito que estão faltando para se ter 1000 digitos no texto
		int numberOfMissingTextDigits = 1000 - text.length();
		this.text = new String(); // String que receberá o texto já formatado
		this.text += text; // adicionando o texto desejado
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
