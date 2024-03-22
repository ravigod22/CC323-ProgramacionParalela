package com.mycompany.multipliint;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;  
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Arrays;

public class MultipliInt {

private static JFrame WDW1 = new JFrame("Program 1");
private static JFrame WDW2 = new JFrame("Program 2");
private static JFrame WDW3 = new JFrame("Program 3");
private static JFrame WDW4 = new JFrame("Program 4");
private static JFrame WDW5 = new JFrame("Program 5");

private static JScrollPane SP1;
private static JScrollPane SP2;
private static JScrollPane SP3;
private static JScrollPane SP4;
private static JScrollPane SP5;

private static JTextArea   TA1  = new JTextArea("");  
private static JTextArea   TA2  = new JTextArea("");  
private static JTextArea   TA3  = new JTextArea("");  
private static JTextArea   TA4  = new JTextArea("");  
private static JTextArea   TA5  = new JTextArea("");  

private static Font fntLABEL = new Font("Arial",Font.BOLD,24);
private static Font fntTEXT  = new Font("Lucida Console",Font.BOLD,18);

private static JLabel LBL1Start  = new javax.swing.JLabel();
private static JLabel LBL2Start  = new javax.swing.JLabel();
private static JLabel LBL3Start  = new javax.swing.JLabel();
private static JLabel LBL4Start  = new javax.swing.JLabel();
private static JLabel LBL5Start  = new javax.swing.JLabel();

private static JLabel LBL1Finish = new javax.swing.JLabel();
private static JLabel LBL2Finish = new javax.swing.JLabel();
private static JLabel LBL3Finish = new javax.swing.JLabel();
private static JLabel LBL4Finish = new javax.swing.JLabel();
private static JLabel LBL5Finish = new javax.swing.JLabel();



private static final int N = 25;
private static int[] V1 = new int[N];
private static int[] V2 = new int[N];
private static int[] V3 = new int[N];
private static int[] V4 = new int[N];
private static int[] V5 = new int[N];
    //==============================================================================
    public static void ConfigurarControles(JFrame WDW, 
                                           int WW,
                                           int HH,
                                           int LEFT,
                                           int TOP,
                                           JScrollPane SP,
                                           JTextArea   TA,
                                           JLabel      LBLStart,
                                           JLabel      LBLFinish
                                           )  {

        WDW.setSize(WW, HH);
        WDW.setLocation(LEFT,TOP);
        WDW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WDW.setLayout(null);
        WDW.setVisible(true);

        LBLStart.setBounds(25, 20, 300, 40);
        LBLFinish.setBounds(25, 400, 300, 400);

        LBLStart.setFont(fntLABEL);
        LBLFinish.setFont(fntLABEL);

      //LBLStart.setBounds(spLeft,10,lblWidth,lblHeight);
      //LBLFinish.setBounds(spLeft,10+spHeight+10,lblWidth,lblHeight);

        TA.setEditable(false);
        TA.setBounds(35,60,300,500);
        TA.setBackground(Color.WHITE);
        TA.setFont(fntTEXT);
        TA.setForeground(Color.GREEN);
        TA.setBackground(Color.BLACK);

        SP = new JScrollPane(TA);
        SP.setBounds(25,50,300,500);

        WDW.add(LBLStart);
        WDW.add(SP);
        WDW.add(LBLFinish);
        WDW.setVisible(true);

    }
    //==============================================================================
    private static void LoadVector() {
        Random r = new Random();   
        for(int i = 0; i<N;i++){
            V1[i] = r.nextInt(5) + 1;
            V2[i] = r.nextInt(5) + 1;
            V3[i] = r.nextInt(5) + 1;
            V4[i] = r.nextInt(5) + 1;
            V5[i] = r.nextInt(5) + 1;
        }
    }
    //==============================================================================
    public static void main(String[] args) throws InterruptedException {
        AtomicLong AI1 = new AtomicLong(1);
        AtomicLong AI2 = new AtomicLong(1);
        AtomicLong AI3 = new AtomicLong(1);
        AtomicLong AI4 = new AtomicLong(1);
        AtomicLong AI5 = new AtomicLong(1);

        LoadVector();

        ConfigurarControles(WDW1, 375, 800, 20, 10, SP1, TA1, LBL1Start, LBL1Finish);
        ConfigurarControles(WDW2, 375, 800, 395, 10, SP2, TA2, LBL2Start, LBL2Finish);
        ConfigurarControles(WDW3, 375, 800, 770, 10, SP3, TA3, LBL3Start, LBL3Finish);
        ConfigurarControles(WDW4, 375, 800, 1145,10, SP4, TA4, LBL4Start, LBL4Finish);
        ConfigurarControles(WDW5,375,800,1520,10,SP5,TA5,LBL5Start,LBL5Finish);

        //-------------------------------------------------
        new Thread(new Runnable() {  //Thread.sleep(1000);
            public void run() {
                long inicio = System.currentTimeMillis();
                LBL1Start.setText("T.E: " + String.format("%.4f", inicio / 1000.0) + " segundos");
                TA1.setText("");
                //suma de elementos
                AI1.set(mul(V1, TA1));
                
                long fin = System.currentTimeMillis() - inicio;
                LBL1Finish.setText("T.E: " + String.format("%.4f", fin / 1000.0) + " segundos");
                TA1.append("La suma es: " + AI1);
                System.out.println("\n");
            }
        }).start();
        //-------------------------------------------------
        new Thread(new Runnable() {
            public void run() {
                long inicio = System.currentTimeMillis();
                LBL2Start.setText("T.E: " + String.format("%.4f", inicio / 1000.0) + " segundos");
                TA2.setText("");
                //suma de elementos
                AI2.set(mul(V2, TA2));
                
                long fin = System.currentTimeMillis() - inicio;
                LBL2Finish.setText("T.E: " + String.format("%.4f", fin / 1000.0) + " segundos");
                TA2.append("La suma es: " + AI2);
                System.out.println("\n");
            }
        }).start();
        //-------------------------------------------------
        new Thread(new Runnable() {
            public void run() {
                long inicio = System.currentTimeMillis();
                LBL3Start.setText("T.E: " + String.format("%.4f", inicio / 1000.0) + " segundos");
                TA3.setText("");
                
                AI3.set(mul(V3, TA3));
                
                long fin = System.currentTimeMillis() - inicio;
                LBL3Finish.setText("T.E: " + String.format("%.4f", fin / 1000.0) + " segundos");
                TA3.append("La suma es: " + AI3);
                System.out.println("\n");
            }
        }).start();
        //-------------------------------------------------
        new Thread(new Runnable() {
            public void run() {
                long inicio = System.currentTimeMillis();
                LBL4Start.setText("T.E: " + String.format("%.4f", inicio / 1000.0) + " segundos");
                TA4.setText("");
                
                AI4.set(mul(V4, TA4));
                
                long fin = System.currentTimeMillis() - inicio;
                LBL4Finish.setText("T.E: " + String.format("%.4f", fin / 1000.0) + " segundos");
                TA4.append("La suma es: " + AI4);
                System.out.println("\n");
            }
        }).start();
        //-------------------------------------------------
        new Thread(new Runnable() {
            public void run() {
                long inicio = System.currentTimeMillis();
                LBL5Start.setText("T.E: " + String.format("%.4f", inicio / 1000.0) + " segundos");
                TA5.setText("");
                
                AI5.set(mul(V5, TA5));
                
                long fin = System.currentTimeMillis() - inicio;
                LBL5Finish.setText("T.E: " + String.format("%.4f", fin / 1000.0) + " segundos");
                TA5.append("La suma es: " + AI5);
                System.out.println("\n");
            }
        }).start();
        //-------------------------------------------------
    }
    //==============================================================================
    public static int Partition(int V[],int A,int B)    {
    int E,P,TMP;
        P = A;
        E = V[B];
        for(int i = A;i<B;i++) {
            if(V[i]<=E) {
               TMP = V[i];
               V[i] = V[P];
               V[P] = TMP;
               P++;
            }
        }
        TMP = V[B];
        V[B] = V[P];
        V[P] = TMP;
        return P;
    }
    //==============================================================================
    public static void QuickSort(int V[],int A,int B, JTextArea TA) {
    int E;
        if(A<B) {
           TA.append("Partition [" + A + "," +  B + "]\n");
           TA.append("Hola\n");
           E = Partition(V,A,B);
           QuickSort(V,A,E-1,TA);
           QuickSort(V,E+1,B,TA);
        }
    }
    //==============================================================================
    public static void BubbleSort(int V[], JTextArea TA) {
    int n,tmp;
        n = V.length;
        TA.setText("");
        for (int k=1;k<=n-1;k++) {
            TA.append("Cycle " + k + "\n");
            for(int i=0;i<=n-k-1;i++) {
                if (V[i]>V[i+1]) {
                    tmp = V[i];
                    V[i] = V[i+1];
                    V[i+1] = tmp;
                }
            }
        }  
    }
    //==============================================================================
    public static void BubbleSort2(int V[], JTextArea TA) {
    boolean Sw;
    int k,n,tmp;
        n = V.length;
        TA.setText("");
        Sw = true;
        k=1;
        while((k<=n-1)&&(Sw==true)) {
            TA.append("Cycle " + k + "\n");
            Sw = false;
            for(int i=0;i<=n-k-1;i++) {
                if (V[i]>V[i+1]) {
                    Sw = true;
                    tmp = V[i];
                    V[i] = V[i+1];
                    V[i+1] = tmp;
                }
            }
            k++;
        }  
    }
    //==============================================================================
    public long FibR(int n) {
        if(n == 1) {
          return 0;
        }
       else if(n==2) {
        return 1;
       }
        else {
          return FibR(n-2) + FibR(n-1);
        }
     }
     public void ImprimirSecuenciaFibonacci(int N) {
        for(int i=1;i<=N;i++) {
            System.out.println(i + "\n"+ FibR(i));
        }
     }
     //=============================================================================
     public static long mul(int V[], JTextArea TA) {
         long result = 1;
         for (int i = 0; i < V.length; i++) {
             result = result * (1L * V[i]);
             TA.append(i + " ---> " + V[i] + " --> " + result + "\n");
         }
         return result;
     }
}
