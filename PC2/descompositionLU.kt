import kotlin.ExperimentalStdlibApi
import kotlin.random.Random
import kotlin.concurrent.thread

val N = 3000
val M = Array(N) {DoubleArray(N)}

fun main() {
    loadMatrix();
    println("Method Parallel:")
    parallel()
    println("Method Serial:")
    serial()
}
//===================================================
fun loadMatrix() : Unit {
    for (i in 0 until N) {
        for (j in 0 until N) {
            var e = Random.nextInt(1, 11)
            M[i][j] = e.toDouble()
        }
    }
}
//===================================================
fun serial() : Unit {
    //println("Descomposition LU in serial mode")
    val start = System.currentTimeMillis()
    descompositionLUSerial(M)
    val end = System.currentTimeMillis()
    val total = end - start
    println("Execution Time: " + total + " ms")
} 
//===================================================
fun parallel() : Unit {
    //println ("Descomposition LU in parallel mode")
    val start = System.currentTimeMillis()
    descompositionLUParallel(M)
    val end = System.currentTimeMillis()
    val total = end - start
    println("Execution Time: " + total + " ms")
}
//===================================================
fun descompositionLUSerial(M: Array<DoubleArray>) : Unit {
    val L = Array(N) {DoubleArray(N)}
    val U = Array(N) {DoubleArray(N)}
    for (i in 0 until N) {
        L[i][i] = 1.0;
        for (j in i until N) {
            var sum = 0.0
            for (k in 0 until i) {
                sum += L[i][k] * U[k][j]
            }
            U[i][j] = M[i][j] - sum
        }
        for (j in i + 1 until N) {
            var sum = 0.0
            for (k in 0 until i) {
                sum += L[j][k] * U[k][i]
            }
            L[j][i] = (M[j][i] - sum) / U[i][i]
        }
    }
    //printMatrix(L, "L")
    //printMatrix(U, "U")
}
//===================================================
@OptIn(ExperimentalStdlibApi::class)
fun descompositionLUParallel(M: Array<DoubleArray>) : Unit {
    val L = Array(N) {DoubleArray(N)}
    val U = Array(N) {DoubleArray(N)}
    val threads = arrayOfNulls<Thread>(8)
    val current = intArrayOf(0)

    for (i in 0 until 8) {
        threads[i] = thread(start = true) {
            while (true) {
                val currentRow: Int
                synchronized(Any()) {
                    if (current[0] >= N) {
                        return@thread
                    }
                    currentRow = current[0]
                    current[0]++
                }
                L[currentRow][currentRow] = 1.0
                for (j in currentRow until N) {
                    var sum: Double = 0.0
                    for (k in 0 until currentRow) {
                        sum += L[currentRow][k] * U[k][j]
                    }
                    U[currentRow][j] = M[currentRow][j] - sum
                }
                for (j in currentRow + 1 until N) {
                    var sum: Double = 0.0
                    for (k in 0 until currentRow) {
                        sum += L[j][k] * U[k][currentRow]
                    }
                    L[j][currentRow] = (M[j][currentRow] - sum) / U[currentRow][currentRow]
                }
            }

        }
    }
    threads.forEach { it?.join()}
    //printMatrix(L, "L")
    //printMatrix(U, "U")
}
//===================================================
fun printMatrix(M: Array<DoubleArray>, NAME: String) : Unit {
    println("Matrix $NAME")
    for (row in M) {
        for (value in row) {
            print("$value\t")
        }
        println()
    }
    println()
}
//===================================================

//===================================================

