public class descompositionLU {
    private static final int N = 2000; 
    private static final double[][] M = new double[N][N];
    
    public static void main(String[] args) {
        LoadMatrix();
        //PrintMatrix(M, "M");
        System.out.println("Method Parallel");
        Parallel();
        System.out.println("Method Serial");
        Serial();
    }
    //=============================================================
    public static void LoadMatrix() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                M[i][j] = (int)((Math.random() * 10 + 1)); 
            }
        }
    }
    //=============================================================
    public static void Serial() {
        //System.out.println("Descomposition LU in serial mode");
        long startTime = System.currentTimeMillis();
        DescompositionLUSerial(M);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " ms");
    }
    //=============================================================
    public static void Parallel() { 
        //System.out.println("Descomposition LU in parallel mode");
        long startTime = System.currentTimeMillis();
        DescompositionLUParallel(M);  
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " ms");
    }
    //=============================================================
    public static void DescompositionLUSerial(double[][] M) {
        double[][] L = new double[N][N];
        double[][] U = new double[N][N];
        for (int i = 0; i < N; ++i) {
            L[i][i] = 1;
            for (int j = i; j < N; ++j) {
                double sum = 0;
                for (int k = 0; k < i; ++k) {
                    sum += L[i][k] * U[k][j];
                }
                U[i][j] = M[i][j] - sum; 
            }
            for (int j = i + 1; j < N; ++j) {
                double sum = 0;
                for (int k = 0; k < i; ++k) {
                    sum += L[j][k] * U[k][i];
                }
                L[j][i] = (M[j][i] - sum) / U[i][i];
            }
        }
        //PrintMatrix(L, "L");
        //PrintMatrix(U, "U");
    }
    //=================================================================
    public static void DescompositionLUParallel(double[][] M) {
        double[][] L = new double[N][N];
        double[][] U = new double[N][N];
        Thread[] threads = new Thread[8];
        final int[] current = {0};
        for (int i = 0; i < 8; ++i) {
            threads[i] = new Thread(() -> {
                while (true) {
                    int currentRow; 
                    synchronized (descompositionLU.class) {
                        if (current[0] >= N) {
                            break;
                        }
                        currentRow = current[0];
                        current[0]++;
                    }
                    L[currentRow][currentRow] = 1;
                    for (int j = currentRow; j < N; ++j) {
                        double sum = 0;
                        for (int k = 0; k < currentRow; ++k) {
                            sum += L[currentRow][k] * U[k][j];
                        }
                        U[currentRow][j] = M[currentRow][j] - sum;
                    }
                    for (int j = currentRow + 1; j < N; ++j) {
                        double sum = 0;
                        for (int k = 0; k < currentRow; ++k) {
                            sum += L[j][k] * U[k][currentRow];
                        }
                        L[j][currentRow] = (M[j][currentRow] - sum) / U[currentRow][currentRow];
                    }
                }
                
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //PrintMatrix(L, "L");
        //PrintMatrix(U, "U");
    }
    //=================================================================
    public static void PrintMatrix(double[][] X, String NAME) {
        System.out.println("Matrix " + NAME + ":");
        for (double[] row : X) {
            for (double value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}

