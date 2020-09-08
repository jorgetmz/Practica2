package condicionescompetencias;


import javax.swing.JTextArea;

public class Hilo extends Thread{
    private JTextArea area;
    private RCompartido rc;
    boolean band;
    boolean pausar;
    boolean detener;
    
    public boolean getBand(){
        return band;
    }
    
    Hilo(JTextArea area, RCompartido rc){
        this.area = area;
        this.rc = rc;
    }
    
    public void run(){
        band=true;
        while(true){
            try{
                if(band){
                    rc.setDatoCompartido(this.getName());
                    area.append(rc.getDatoCompartido()+"\n");
                    Thread.sleep(1500); //2 segundos
                    synchronized(this){
                        while(pausar){
                            wait();
                        }
                        if(detener){
                            break;
                        }
                    }
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    synchronized public void pausar()
    {
       pausar=true;
       detener=false;
       notify();
    }
   
    synchronized void reanudar(){
        detener=false;
        pausar=false;
        notify();
    }
   
    synchronized void detener(){
       detener=true;
       pausar=false;
       notify();
    }
}
