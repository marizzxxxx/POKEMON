import java.util.Scanner;
import java.lang.Math;
import java.io.FileNotFoundException;
import java.io.FileReader;

//Esta classe tem como objetivo interagir com o usuário 
//no intuito de comprovar a funcionalidade dos eventos e dos métodos.
public class InterfacePokemon {
	
	public static void main(String[] args) throws FileNotFoundException {			
		
		Scanner leitura = new Scanner (System.in);
		
		System.out.println("Simulação do jogo Pokemon\n");
		System.out.println("Bem vindo! Escolha o modo de jogo:");
		System.out.println("1 - Batalha entre treinadores");
		System.out.println("2 - Sair");
		
		int tipoJogo = leitura.nextInt(); 
		
		if(tipoJogo == 1){
			//Criando os treinadores (para poupar tempo, nessa parte, a entrada vai ser lida de um arquivo .txt
			Scanner treinadores = new Scanner(new FileReader("criaTreinadores.txt")).useDelimiter("\\||\\n");

			//Criando o primeiro jogador			
			String nome1 = treinadores.next();
			int quantidade = treinadores.nextInt();				
			Pokemon[] listaPokemon1 = new Pokemon[quantidade];
			for (int i = 0; i < quantidade; i++) {
				String nomePok = treinadores.next();
				String tipo = treinadores.next();
				int hp = treinadores.nextInt();
				Ataque[] listaAtk = new Ataque[4];
				for (int j = 0; j < 4; j++) {
					String nomeAtk = treinadores.next();
					int dano = treinadores.nextInt();
					listaAtk[j] = new Ataque(nomeAtk, dano);
				}	
				listaPokemon1[i] = new Pokemon(nomePok, tipo, hp, listaAtk);
			}
			Treinador jogador1 = new Treinador (nome1, listaPokemon1);
			//Criando o segundo jogador
			
			String nome2 = treinadores.next();
			quantidade = treinadores.nextInt();				
			Pokemon[] listaPokemon2 = new Pokemon[quantidade];
			for (int i = 0; i < quantidade; i++) {
				String nomePok = treinadores.next();
				String tipo = treinadores.next();
				int hp = treinadores.nextInt();
				Ataque[] listaAtk = new Ataque[4];
				for (int j = 0; j < 4; j++) {
					String nomeAtk = treinadores.next();
					int dano = treinadores.nextInt();
					listaAtk[j] = new Ataque(nomeAtk, dano);
				}	
				listaPokemon2[i] = new Pokemon(nomePok, tipo, hp, listaAtk);
			}
			Treinador jogador2 = new Treinador (nome2, listaPokemon2);
			//Batalha entre os dois treinadores
			Batalha comum = new Batalha();
			comum.batalhaComum(jogador1, jogador2);			
		}
		else if(tipoJogo == 2){
			System.out.println("Obrigado por jogar!");
		}
		leitura.close();
	}
}