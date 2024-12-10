/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ureticituketicisimulasyonu;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UreticiTuketiciSimulasyonu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int depoKapasitesi = 10;
        int uretimMiktari = 5; //Her üreticinin ve tüketicinin kaç kez işlem yapacağını belirler.İhtiyacımıza göre değiştirebiliriz.
        ArrayBlockingQueue<Integer> depo = new ArrayBlockingQueue<>(depoKapasitesi);
        Semaphore ureticiSemafor = new Semaphore(depoKapasitesi);
        Semaphore tuketiciSemafor = new Semaphore(0);

        int ureticiSayisi = 3;
        int tuketiciSayisi = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(ureticiSayisi + tuketiciSayisi);
        
        for (int i = 1; i <= ureticiSayisi; i++) {
            final int ureticiId = i;
            executorService.submit(() -> {
                for (int j = 0; j < uretimMiktari; j++) {
                    try {
                        ureticiSemafor.acquire();
                        depo.put(ureticiId);
                        System.out.println("Uretici " + ureticiId + ": Urun uretildi.");
                        tuketiciSemafor.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        
        for (int i = 1; i <= tuketiciSayisi; i++) {
            final int tuketiciId = i;
            executorService.submit(() -> {
                for (int j = 0; j < uretimMiktari; j++) {
                    try {
                        tuketiciSemafor.acquire();
                        int urun = depo.take();
                        System.out.println("Tuketici " + tuketiciId + ": Urun " + urun + " tuketildi.");
                        ureticiSemafor.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        
        executorService.shutdown();
        //Depo.performansAnalizi();
    }
    
}
