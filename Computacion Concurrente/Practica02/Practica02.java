import java.lang.Thread;

public class Practica02{


public static volatile boolean[] flags = {false,false};
public static volatile int[] local = {0,1};
public static volatile int[] turn = {0,1};
public static volatile int turno = 0;

//clase auxiliar que realiza el algoritmo de peterson
	private class Peterson implements Runnable{
		private int id;			//el indice del hilo

/**
*Constructor que recibe un id para iniciar el algoritmo
*@param id el indice del hilo
*/
		public Peterson(int id){
			this.id = id; //indice del hilo
			
		}

/**
*Resuelve el problema de exclusion mutua
*/
		public void run(){
			for(int i=0; i<20;i++){
			int otro = (id+1)%2;
			flags[id] = true;
			turno = otro;
			int x = 0;
			while(flags[otro] && turno == otro);
				//seccion critica
				System.out.println("El hilo: "+id+ " acaba de entrar a la sección crítica.");
				for(int k=0; k<1000; k++){
					x+=k;
				}
				System.out.println("El hilo: "+id+ " ha salido de la sección crítica.");
				//fin de la seccion critica
			flags[id] = false;
		}
	}
}
//clase auxiliar que realiza el algoritmo de dekker
	private class Dekker implements Runnable{
		private int id;			//el indice del hilo

/**
*Constructor que recibe un id para iniciar el algoritmo
*@param id el indice del hilo
*/
		public Dekker(int id){
			this.id = id;  //el indice del hilo
		}

/**
*Resuelve el problema de exclusion mutua
*/
		public void run(){
			for(int i=0;i<20;i++){
			int otro = (id+1) % 2;
			flags[id] = true;
			int x = 0;
			while(flags[otro]){
				if(turno == otro){
					flags[id] = false;
					while(turno != id);
					flags[id] = true;
				}
			}
			//seccion critica
			System.out.println("El hilo: "+ id+" acaba de entrar a la sección crítica.");
			for(int k=0; k<1000; k++){
				x+=k;
			}
			System.out.println("El hilo: "+id+" ha salido de la sección crítica.");	
			//fin de la seccion critica
		
			turno = otro;
			flags[id] = false;
			}
		}
	}
//clase auxiliar que realiza el algoritmo de Kessels
	private class Kessels implements Runnable{
		private int id;			//el indice del hilo


/**
*Constructor que recibe un id para iniciar el algoritmo
*@param id el indice del hilo
*/
		public Kessels(int id){
			this.id = id;  	//el indice del hilo
			
		}

/**
*Resuelve el problema de exclusion mutua
*/
		public void run(){
		for(int i=0; i<20;i++){	
		int x = 0;
		if(this.id == 0){	
			flags[0] = true;
			local[0] = turn[1];
			turn[0] = local[0];
			
			while(!(flags[1] == false || local[0] != turn[1]));
					//seccion critica
			System.out.println("El hilo "+id+" acaba de entrar a la sección crítica.");
			for(int j=0 ; j<1000 ; j++){
				x+=j;
			}
			System.out.println("El hilo "+id+ " ha salido de la sección crítica.");
			//fin de la seccion critica
			flags[0] = false;
		}else{
			flags[1] = true;
			local[1] = 1 - turn[0];
			turn[1] = local[1];
			while(!(flags[0] == false || local[1] == turn[0]));
					//seccion critica
			System.out.println("El hilo "+id+" acaba de entrar a la sección crítica.");
			for(int j=0 ; j<1000 ; j++){
				x+=j;
			}
			System.out.println("El hilo "+id+ " ha salido de la sección crítica.");
			//fin de la seccion critica
			flags[1] = false;

		}

			}
		}
	}	
//Constructor de la clase en donde inicializamos los hilos para cada algoritmo
	public Practica02(){
		try {
			Thread hilo1 = new Thread(new Dekker(0));
			Thread hilo2 = new Thread(new Dekker(1));
			Thread hilo3 = new Thread(new Peterson(0));
			Thread hilo4 = new Thread(new Peterson(1));
			Thread hilo5 = new Thread(new Kessels(0));
			Thread hilo6 = new Thread(new Kessels(1));
			System.out.println("\n\t\tAlgoritmo de Dekker."); //inicia el algoritmo de dekker con los hilos 1 y 2
			hilo1.start();
			hilo2.start();
			hilo1.join();
			hilo2.join();
			System.out.println("\n\t\tAlgoritmo de Peterson."); //inicia el algoritmo de peterson con los hilos 3 y 4
			hilo3.start();
			hilo4.start();
			hilo3.join();
			hilo4.join();
			System.out.println("\n\t\tAlgoritmo de Kessels.");//inicia el algoritmo de kessels con los hilos 5 y 6
			hilo5.start();
			hilo6.start();
			hilo5.join();
			hilo6.join();
		}catch(Exception e){
			System.out.println(e);
		}
	}
public static void main(String[] args){
	new Practica02();
}	








}