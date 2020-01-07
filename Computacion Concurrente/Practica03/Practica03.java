import java.util.*;



public class Practica03{

	public static class Hilo extends Thread{

		public boolean wait; //variable que nos sirve para simular la condicion de espera
		public Semaforo semaforo; //Semaforo que controlorá los threads

		//Constructor de la clase Hilo
		public Hilo(Semaforo semaforo){
			this.semaforo = semaforo;
			this.wait = false; 
		}


		//Metodo que hace el wait de un semaforo
		public synchronized void acquire(Semaforo semaforo)throws InterruptedException{	
			if(semaforo.contador == 0){ //verificamos si el contador es cero
				this.wait = true;  //Cambiamos la condicion de espera a True para que los otros hilos sepan que deben esperar
				semaforo.lista.add(this); //Añadimos el thread que quiere entrar a la seccion especial a la lista de espera
				while(wait){
					this.sleep(100);	//dormimos el thread 
				}		
			}else{
				semaforo.contador--; //si el contador no es cero entonces disminuimos el contador en uno
			}

		} 

		//Metodo que hace el signal de un semaforo
		public synchronized void signal(Semaforo semaforo){
			if(!semaforo.lista.isEmpty()){ //verificamos si la cola de threads esta vacia
				Hilo siguiente = semaforo.lista.poll(); //sacamos de la lista al thread
				siguiente.wait = false;	//cambiamos la condicion de espera a false

			}else{
				semaforo.contador++; //en otro caso aumentamos el contador en uno
			}
		}

		public void run(){
			for(int i=0; i<10 ; i++){
				try{
					this.acquire(semaforo);
				}catch(InterruptedException e){
					System.out.println(e);
				}
				//seccion critica
				System.out.println("El hilo ["+Thread.currentThread().getName()+"] acaba de entrar a la sección crítica.");
				System.out.println("El contador del semaforo es:"+semaforo.contador);
				try{
					Hilo.sleep(100); //hacemos la espera de un milisegundo
				}catch(InterruptedException e){
					System.out.println(e);
				}
				this.signal(semaforo);
				System.out.println("El hilo ["+Thread.currentThread().getName()+"] ha salido de la sección crítica.");
				//fin de la seccion critica			

			}


		}

	}
	public static class Semaforo{
		public int contador; //Contador del semaforo
	    public Queue<Hilo> lista = new LinkedList<>(); //lista que guardara los hilos que estan durmiendo
	    
	    //Constructor de la clase Semaforo
	    public Semaforo(int contador){
	        this.contador = contador;
	    }
}
	public static void main(String[]args){
    	List<Hilo> threads = new ArrayList<>();
    	Scanner ls = new Scanner(System.in);
    	System.out.println("Ingresa el # de hilos a ejecutar:");
    	int x = ls.nextInt();
    	Semaforo semaforo = new Semaforo(1);
    	try{
    	for(int i=0; i<x; i++){
    		Hilo hilos = new Hilo(semaforo);
    		threads.add(hilos);
    		hilos.start();
    	}
    	for(Hilo hilos : threads){
    		hilos.join();
    	}
    }catch(Exception e){
    	System.out.println(e);
    }

    }



}    