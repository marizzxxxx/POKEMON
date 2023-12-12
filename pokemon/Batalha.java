import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
public class Batalha extends Controller {
	/**********************************************************************/
	/*	Exercício 1: Simulação de uma batalha entre dois treinadores	  */
	/**********************************************************************/
	public void batalhaComum (Treinador jogador1, Treinador jogador2) throws FileNotFoundException {	
		System.out.println("");
		Scanner leitura = new Scanner(System.in);
		System.out.println(jogador1.getNome() + " x " + jogador2.getNome());
		while (!jogador1.fugiu() && jogador1.temPokemonVivo() && jogador2.temPokemonVivo() && !jogador2.fugiu()){
			Batalha simulacao = new Batalha();
			int opcao;

			//Jogador 1 escolhe o que deseja fazer

			System.out.println("O que o treinador " + jogador1.getNome() + " deseja fazer?");
			System.out.println("1 - Atacar");
			opcao = leitura.nextInt();

			if (opcao == 1) {
				System.out.println("Escolha o ataque: ");
				Pokemon aux = jogador1.getPokemonAtual();
				aux.imprimeAtaques();
				opcao = leitura.nextInt();
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar (tm, jogador1, jogador2, opcao));
			}
			//Jogador 2 escolhe o que deseja fazer
			System.out.println("O que o treinador " + jogador2.getNome() + " deseja fazer?");
			System.out.println("1 - Atacar");
			opcao = leitura.nextInt();

			if (opcao == 1) {
				System.out.println("Escolha o ataque: ");
				Pokemon aux = jogador2.getPokemonAtual();
				aux.imprimeAtaques();
				opcao = leitura.nextInt();
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar (tm, jogador2, jogador1, opcao));
			}
			//executa os eventos da rodada
			simulacao.run();
		}		
	}

	/**********************************************************************/
	/*	Exercício 2: Simulação de uma batalha entre dois treinadores	  */
	/**********************************************************************/
	public void batalhaSelvagem (Treinador jogador) {

		//cria o treinador que tem o pokemon selvagem
		Scanner leitura = new Scanner (System.in);
		Ataque[] listaAtaque = new Ataque[4];
		listaAtaque[0] = new Ataque("Tackle", 50);
		listaAtaque[1] = new Ataque("Gust", 40);
		listaAtaque[2] = new Ataque("Wing Attack", 60);
		listaAtaque[3] = new Ataque("Hurricane", 110);		
		Pokemon[] listaPokemon = { new Pokemon ("Pidgey", "Voador", 300, listaAtaque)};
		Treinador inimigo = new Treinador ("Pokemon selvagem", listaPokemon);
		//Entrando na batalha
		System.out.println("\nUm " + inimigo.getPokemonAtual().getNome() + " selvagem apareceu!");
		while (!jogador.fugiu() && !jogador.perdeu() && !inimigo.perdeu()) {	
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Atacar				2 - Usar item\n");
			int opcao = leitura.nextInt();
			Batalha simulacao = new Batalha();
			
			if (opcao == 1) {
				System.out.println("Escolha o ataque: ");
				Pokemon aux = jogador.getPokemonAtual();
				aux.imprimeAtaques();
				opcao = leitura.nextInt();
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar (tm, jogador, inimigo, opcao));
			}
			else if (opcao == 2) {
				long tm = System.currentTimeMillis();
				int item;
				System.out.println("Escolha o item: \n1 - PokéBola");
				item = leitura.nextInt();

				if(item == 1)
					simulacao.addEvent (new Capturar (tm, jogador, inimigo));
				else
					System.out.println("Opção inválida!");
			}
			
			//Ataque do selvagem
			double sorteio = Math.random();
			if(sorteio < 0.25) {
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar(tm, inimigo, jogador, 0));
			}
			else if(sorteio >= 0.25 && sorteio < 0.5) {
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar(tm, inimigo, jogador, 1));
			}
			else if(sorteio >= 0.5 && sorteio < 0.75) {
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar(tm, inimigo, jogador, 2));
			}
			else if(sorteio >= 0.75) {
				long tm = System.currentTimeMillis();
				simulacao.addEvent (new Atacar(tm, inimigo, jogador, 3));
			}
			simulacao.run();
		}
		
	}
	
	//Evento: Treinador ataca outro
	public class Atacar extends Event {
		private Treinador quemAtaca;
		private Treinador alvo;
		private int ataque;
		private boolean trocou = false;
		public 	Atacar(long eventTime, Treinador quemAtaca, Treinador alvo, int ataque) {
			super(eventTime);
			this.quemAtaca = quemAtaca;
			this.alvo = alvo;
			this.ataque = ataque;
		}
		public void action() {
			Pokemon aux = alvo.getPokemonAtual();
			double k = quemAtaca.getPokemonAtual().vantagem(aux);
			if (aux.estaVivo()) {
				aux.diminuiHP(k * quemAtaca.getDanoAtual(ataque));
				System.out.println(quemAtaca.getNome() + " decidiu atacar:");
				System.out.println(quemAtaca.getPokemonAtual().getNome() + 
						" usou " + quemAtaca.getAtaqueAtual(ataque) + "!");	
				if (k == 0)
					System.out.println("O ataque não surtiu efeito.");
				else if (k == 0.5)
					System.out.println("O ataque não foi muito efetivo.");
				else if (k == 2)
					System.out.println("O ataque foi muito efetivo!");
			}	
			if (aux.estaVivo()){				
				System.out.println(aux.getNome() + " agora tem " + alvo.getPokemonAtual().getHp() + "HP.\n");
			}
				else {
					System.out.println (alvo.getNome() + " foi derrotado!");
					System.out.println("Vencedor: " + quemAtaca.getNome());
					alvo.foiDerrotado();
				}
			}
				
		}

	//Evento: Treinador tenta capturar o pokémon selvagem
		public class Capturar extends Event{
			private Treinador jogador;
			private Treinador alvo;
			public Capturar(long eventTime, Treinador jogador, Treinador alvo){
				super(eventTime);
				this.jogador = jogador;
				this.alvo = alvo;
			}
			public void action(){
				System.out.println(jogador.getNome() + " usou uma PokéBola!");
				if(Math.random() > (jogador.getPokemonAtual().getHp()/jogador.getPokemonAtual().getHpMax()) ) {
					System.out.println("Conseguiu! O " + alvo.getPokemonAtual().getNome() + " selvagem foi capturado!");
					jogador.adicionaPokemon(alvo.getPokemonAtual());
					alvo.foiDerrotado();
				}
				else{
					System.out.println("Ah não! O " + alvo.getPokemonAtual().getNome() + " selvagem escapou!");
				}
			}
			public int prioridade() {
				return 2;
			}
			public boolean treinadorDerrotado() {
				return alvo.perdeu();
			}
			public boolean treinadorFugiu() {
				return false;
			}
			public boolean treinadorTrocou() {
				return false;
			}
		}

}