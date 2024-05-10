import java.util.concurrent.atomic.AtomicInteger;
import java.io.FileWriter;
import java.io.IOException;

public class QR {
    private static final int N = 1000; 
    private static final double[][] M = new double[N][N];
    private static final Object lock = new Object();
    private static final AtomicInteger currentCol = new AtomicInteger(0);
 //===========================================================================
    public static void main(String[] args) {
        System.out.println("Metodo QR");
        LoadMatrix();
        //PrintMatrix(M, "M");
        System.out.println("Method Parallel");
        Parallel();
        System.out.println("Method Serial");
        Serial();
    }
 //===========================================================================
    public static void LoadMatrix() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                M[i][j] = (int) ((Math.random() * 10) + 1);
            }
        }
    } 
 //===========================================================================
    public static void Serial() {
        long startTime = System.currentTimeMillis();
        double[][][] QR_Serial = DescompositionQRSerial(M);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " ms");
    	writeMatrixToFile(QR_Serial[0], "Q_Serial.txt");
	writeMatrixToFile(QR_Serial[1], "R_Serial.txt");
    }
 //===========================================================================
    public static void Parallel() { 
        long startTime = System.currentTimeMillis();
        double[][][] QR_Parallel = DescompositionQRParallel(M);  
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " ms");
	writeMatrixToFile(QR_Parallel[0], "Q_Parallel.txt");
	writeMatrixToFile(QR_Parallel[1], "R_Parallel.txt");
    }
 //===========================================================================
    public static void LoadZeros2D(double[][] M) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                M[i][j] = 0;
            }
        }
    }
 //===========================================================================
    public static void LoadZeros1D(double[] A) {
        for (int i = 0; i < N; ++i) {
            A[i] = 0;
        }
    }
 //===========================================================================
    public static double[][][] DescompositionQRSerial(double[][] M) {
        int n = N; // dimension of square matrix M[N][N]
        double[][][] QR = new double[2][N][N];
	double[][] Q = new double[N][N];
        double[][] R = new double[N][N];
        LoadZeros2D(Q); LoadZeros2D(R);
        for (int j = 0; j < N; ++j) {
            double[] tmp = new double[N];

            for (int i = 0; i < N; ++i) {
                tmp[i] = M[i][j];
            }
            for (int i = 0; i < j; ++i) {
                R[i][j] = 0;
                for (int k = 0; k < N; ++k) {
                    R[i][j] += Q[k][i] * M[k][j];
                }
                for (int k = 0; k < N; ++k) {
                    tmp[k] = tmp[k] - (R[i][j] * Q[k][i]);
                }
            }

            double norma = Math.sqrt(dot(tmp, tmp));
            R[j][j] = norma;
            for (int i = 0; i < N; ++i) {
                Q[i][j] = tmp[i] / norma;
            }
        }
	QR[0] = Q;
	QR[1] = R;
	return QR;
        //PrintMatrix(Q, "Matrix Q");
        //PrintMatrix(R, "Matrix R");
    }
 //===========================================================================
    public static double dot(double[] A, double[] B) {
        double sum = 0;
        for (int i = 0; i < N; ++i) {
            sum += (A[i] * B[i]);
        }
        return sum;
    }
 //===========================================================================
    public static double[][][] DescompositionQRParallel(double[][] M) {
        double[][][] QR = new double[2][N][N];
	double[][] Q = new double[N][N];
        double[][] R = new double[N][N];

        Thread[] threads = new Thread[8];
        for (int t = 0; t < 8; ++t) {
            threads[t] = new Thread(() -> {
                int col;
                while (true) {
                    col = currentCol.getAndIncrement();
                    if (col >= N) break;

                    double[] tmp = new double[N];

                    for (int i = 0; i < N; ++i) {
                        tmp[i] = M[i][col];
                    }

                    for (int i = 0; i < col; ++i) {
                        double sum = 0;
                        for (int k = 0; k < N; ++k) {
                            sum = sum + (Q[k][i] * M[k][col]);
                        }
                        for (int k = 0; k < N; ++k) {
                            tmp[k] = tmp[k] -  (sum * Q[k][i]);
                        }
			R[i][col] = sum;
                    }
                    double norma;
                    norma = Math.sqrt(dot(tmp, tmp));
                    R[col][col] = norma;
                    for (int i = 0; i < N; ++i) {
                        Q[i][col] = tmp[i] / norma;
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
	//PrintMatrix(Q, "Q");
	//PrintMatrix(R, "R");
	QR[0] = Q;
	QR[1] = R;
	return QR;
    }
    //===========================================================================
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
    //===========================================================================
    public static void writeMatrixToFile(double[][] matrix, String filename) {
	try {
	    FileWriter writer = new FileWriter(filename);
	    for (int i = 0; i < N; ++i) {
		for (int j = 0; j < N; ++j) {
		    writer.write(matrix[i][j] + "\t");
		}
		writer.write("\n");
	    }
	    writer.close();
	    System.out.println("Se creo el archivo "+ filename +", y envio el output a este correctamente");
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}

