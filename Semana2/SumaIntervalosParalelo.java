import java.util.concurrent.atomic.AtomicInteger;

public class SumaIntervalosParalelo {
    private static final int N = 100;
    private static final int NUM_INTERVALS = 10;
    private static final int NUM_THREADS = 3;
    private static volatile int actual = 0;
    private static volatile int[] X = new int[N];
    private static final AtomicInteger[] STATES = new AtomicInteger[NUM_THREADS];

    public static void LoadVector() {
        for (int i = 0; i < N; ++i) {
            X[i] = (int) (Math.random() * 1000);
        }
    }

    public static void ShowVector() {
        for (int i = 0; i < N; ++i) {
            System.out.print(X[i] + " ");
        }
        System.out.println();
    }

    public static int SumSerial() {
        int result = 0;
        for (int i = 0; i < N; ++i) {
            result += X[i];
        }
        return result;
    }

    public static void main(String[] args) {
        LoadVector();
        ShowVector();
        System.out.println();
        System.out.println("Resultado en serial => " + SumSerial());
        Thread[] threads = new Thread[NUM_THREADS];

        // Inicializamos los elementos del arreglo STATES
        for (int i = 0; i < NUM_THREADS; i++) {
            STATES[i] = new AtomicInteger(0);
        }

        // Iteramos sobre cada hilo
        for (int i = 0; i < NUM_THREADS; i++) {
            final int indice = i;
            threads[i] = new Thread(() -> {
                // Mientras haya intervalos disponibles
                while (true) {
                    int start;
                    int end;
                    synchronized (SumaIntervalosParalelo.class) { // Sincronizar la actualización de actual
                        if (actual >= NUM_INTERVALS) {
                            break; // Salir del bucle si no hay más intervalos disponibles
                        }
                        start = actual * 10;
                        endI = Math.min(start + 10, 100) - 1;
                        actual++; // Actualizamos el intervalo actual
                    }
                    int temp = 0;
                    for (int j = start; j <= end; ++j) {
                        temp += X[j];
                    }
                    STATES[indice].addAndGet(temp); // Usamos addAndGet para operaciones atómicas
                    System.out.println("Hilo " + (indice + 1) + " esta procesando el intervalo de " + start + " hacia " + end + 
                            " con resultado: " + temp);
                    try {
                        Thread.sleep(1000); // Simulamos el procesamiento con una espera de 1 segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start(); // Iniciamos el hilo
        }

        // Esperamos a que todos los hilos terminen su trabajo
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Todos los hilos fueron procesados.");
        int temporal = 0;
        for (int i = 0; i < NUM_THREADS; i++) {
            temporal += STATES[i].get();
        }
        System.out.println("Resultado en Paralelo => " + temporal);
    }
}

