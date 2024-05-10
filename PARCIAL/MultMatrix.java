public class MultMatrix {
    private static final int N = 1000;
    private static final double T = 8;
    private static final double[][] A = new double[N][N];
    private static final double[][] B = new double[N][N];
    private static int currentRow = 0;

    public static void main(String[] args) {
        LoadMatrix(A); LoadMatrix(B);
	//PrintMatrix(A, "Matrix A"); 
	//PrintMatrix(B, "Matrix B");
        System.out.println("Method Parallel");
        double TP = Parallel();
        System.out.println("Method Serial");
        double TS = Serial();
	System.out.println("The Speedup Factor: " + (TS / TP));
    }
    //=============================================================
    public static void LoadMatrix(double[][] M) {
    	for (int i = 0; i < N; ++i) {
	    for (int j = 0; j < N; ++j) {
		M[i][j] = (double) (Math.random() * 10 + 1);
	    }
	}
    }   
    //=============================================================
    public static double Serial() {
        //System.out.println("Multiplication of Matrix in serial mode");
        long startTime = System.currentTimeMillis();
        MultMatrixSerial(A, B);
	long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " ms");
	System.out.println("Tiempo promedio de ejecucion serial " + (totalTime / T) + " ms");
	return (totalTime / T);
    }
    //=============================================================
    public static double Parallel() { 
        //System.out.println("Multiplication of Matrix in parallel mode");
        long startTime = System.currentTimeMillis();
        MultMatrixParallel(A, B);  
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " ms");
    	System.out.println("Tiempo promedio de ejecucion paralelo " + (totalTime / T) + " ms");
    	return (totalTime / T);
    }
    //=============================================================
    public static void LoadZeros2D(double[][] A) {
	for (int i = 0; i < N; ++i) {
	    for (int j = 0; j < N; ++j) {
		A[i][j] = 0;
	    }
	}
    }
    //=============================================================
    public static void MultMatrixSerial(double[][] A, double[][] B) {
	double[][] C = new double[N][N];
	LoadZeros2D(C);
	for (int i = 0; i < N; ++i) {
	    for (int j = 0; j < N; ++j) {
		for (int k = 0; k < N; ++k) {
		    C[i][j] += (A[i][k] * B[k][j]);
		}
	    }
	}
	//PrintMatrix(C, "Matrix result");
    }
    //=================================================================
    public static void MultMatrixParallel(double[][] A, double[][]B) {
	double[][] C = new double[N][N];
	LoadZeros2D(C);
	Thread[] threads = new Thread[8];
	for (int t = 0; t < 8; ++t) {
	    threads[t] = new Thread(() -> {
		while (true) {
		    int row;
		    synchronized (MultMatrix.class) {
			if (currentRow >= N) break;
			row = currentRow;
			currentRow++;
		    }
		    for (int j = 0; j < N; ++j) {
			for (int k = 0; k < N; ++k) {
			    C[row][j] += (A[row][k] * B[k][j]);
			}
		    }
		}
	    });
	    threads[t].start();
	}
	for (Thread thread : threads) {
	    try {
		thread.join();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	//PrintMatrix(C, "Matrix Result");
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
