

import java.time.Duration;
import java.util.concurrent.Semaphore;

public class ColaCarniceria implements Runnable {

    private boolean esAtendido = false;
    public static Semaphore semaphore = new Semaphore(4);


    public void carniceria() {
        try {
            int random = (int) (Math.random() * 10 + 10);
            semaphore.acquire();  // Cerramos el semaforo
            System.out.println("El " + Thread.currentThread().getName() + " está  pidiendo en la carniceria");
            Thread.sleep(Duration.ofSeconds(random)); //Se tardan entre 10 y 20 segundos en ser atendidos
            System.out.println("El " + Thread.currentThread().getName() + " ha terminado en la carniceria");
            semaphore.release(); // Abrimos el semaforo
        } catch (InterruptedException e) {
            System.out.println("Ocurrio un error que hizo que el hilo se interrumpiera." + e);
        }
    }

    @Override
    public void run() {
        while (esAtendido == false) {
            if (semaphore.availablePermits() > 0 && esAtendido == false) {
                carniceria();
                esAtendido = true;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thread hilo = new Thread(new ColaCarniceria());
            hilo.setName("Cliente número " + i );
            hilo.start();
        }
    }
}