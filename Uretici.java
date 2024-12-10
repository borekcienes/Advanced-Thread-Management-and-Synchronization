/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ureticituketicisimulasyonu;

/**
 *
 * @author borek
 */
public class Uretici implements Runnable {
    private final Depo depo;
    private final int id;

    public Uretici(Depo depo, int id) {
        this.depo = depo;
        this.id = id;
    }

    @Override
    public void run() {
        int urunSayisi = 0;
        try {
            while (true) {
                Urun urun = new Urun(urunSayisi++);
                depo.uret(urun);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
