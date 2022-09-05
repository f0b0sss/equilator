package com.equilator.services;

public class Calc2 {
    /*

    private volatile int c;

    public synchronized void value(){
        System.out.println("Counter = " + c);
    }

    public Calc2() {
        c = 0;
    }

    public synchronized void add(){
        c++;
    }




    public static void main(String[] args) {
        Calc2 calc2 = new Calc2();

        Thread incThread = new Thread(() -> {
            System.out.println("start 1");
            for (int i = 0; i < 1000000; i++) {
                calc2.add();
            }
            System.out.println("end 1");
        });

        Thread decThread = new Thread(() -> {
            while (incThread.isAlive()){
                System.out.println("start 2");
                calc2.value();
                System.out.println("end 2");
            }
        });

        try {
            incThread.start();
            decThread.start();
            incThread.join();
            decThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

     */




}
