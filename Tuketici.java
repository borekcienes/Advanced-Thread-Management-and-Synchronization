/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ureticituketicisimulasyonu;

/**
 *
 * @author borek
 */
public class Tuketici implements Runnable{
    private final Depo depo;
    private final int id;

    public Tuketici(Depo depo, int id) {
        this.depo = depo;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                depo.tuket();
                Thread.sleep((int) (Math.random() * 1500));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
