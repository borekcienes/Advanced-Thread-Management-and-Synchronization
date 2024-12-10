/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ureticituketicisimulasyonu;

/**
 *
 * @author borek
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.atomic.AtomicInteger;

public class Depo {
    private final Queue<Urun> urunler = new LinkedList<>();
    private final int kapasite;
    private final Lock kilit = new ReentrantLock();
    private final Condition doluDegil = kilit.newCondition();
    private final Condition bosDegil = kilit.newCondition();
    
        // Performans analizine yardımcı olmak için sayaçlar
    private static AtomicInteger toplamUretilen = new AtomicInteger(0);
    private static AtomicInteger toplamTuketilen = new AtomicInteger(0);

    public Depo(int kapasite) {
        this.kapasite = kapasite;
    }

    public void uret(Urun urun) throws InterruptedException {
        kilit.lock();
        try {
            while (urunler.size() >= kapasite) {
                doluDegil.await();
            }
            urunler.add(urun);
            toplamUretilen.incrementAndGet();
            System.out.println("Uretildi: " + urun.getId());
            bosDegil.signalAll();
        } finally {
            kilit.unlock();
        }
    }

    public Urun tuket() throws InterruptedException {
        kilit.lock();
        try {
            while (urunler.isEmpty()) {
                bosDegil.await();
            }
            Urun urun = urunler.poll();
            toplamTuketilen.incrementAndGet();
            System.out.println("Tuketildi: " + urun.getId());
            doluDegil.signalAll();
            return urun;
        } finally {
            kilit.unlock();
        }
    }
    public static void performansAnalizi() {
        System.out.println("Toplam Uretilen Urun Sayisi: " + toplamUretilen.get());
        System.out.println("Toplam Tuketilen Urun Sayisi: " + toplamTuketilen.get());
    }
}
