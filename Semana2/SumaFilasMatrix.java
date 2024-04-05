import java.util.concurrent.atomic.AtomicInteger;

public class SumaFilasMatrix {
    private static final int N = 20;
    private static final int NUM_THREADS = 3;
    private static volatile int currentRow = 0;
    private static volatile int[][] MTX = new int[N][N];
    private static final AtomicInteger[] STATES = new AtomicInteger[NUM_THREADS]; // Cambiar a AtomicInteger

    public static void LoadMatrix() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                MTX[i][j] = (int) (Math.random() * 1000);
            }
        }
    }

    public static void ShowMatrix() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                System.out.print(MTX[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int SumSerial() {
        int result = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                result += MTX[i][j];
            } 
        }
        return result;
    }

    public static void main(String[] args) {
        LoadMatrix();
        ShowMatrix();
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
                    int row;
                    synchronized (SumaFilasMatrix.class) { // Sincronizar la actualización de currentInterval
                        if (currentRow >= N) {
                            break; // Salir del bucle si no hay más intervalos disponibles
                        }
                        row = currentRow;
                        currentRow++; // Actualizamos el intervalo actual
                    }
                    int temp = 0;
                    for (int j = 0; j < N; ++j) {
                        temp += MTX[row][j];
                    }
                    STATES[indice].addAndGet(temp); // Usamos addAndGet para operaciones atómicas
                    System.out.println("Hilo " + (indice + 1) + " esta procesando la fila " + row + " ,y resultado: " + temp);
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
        System.out.println("Todos los hilos han sido procesados.");
        int temporal = 0;
        for (int i = 0; i < NUM_THREADS; i++) {
            temporal += STATES[i].get();
        }
        System.out.println("Resultado en Paralelo => " + temporal);
    }
}


