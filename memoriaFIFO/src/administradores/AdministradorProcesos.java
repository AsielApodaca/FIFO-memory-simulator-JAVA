package administradores;
import ejecucion.Pcb;
import java.util.ArrayList;

/**
 * @author Equipo FIFO Sistemas Operativos
 * Clase AdministrarProcesos
 */

public class AdministradorProcesos {
    private ArrayList<Pcb> colaEjecucion = new ArrayList<Pcb>();
    private ArrayList<Pcb> colaEspera = new ArrayList<Pcb>();
    private ArrayList<Pcb> colaBloqueo = new ArrayList<Pcb>();
    /**
     * Constructor general
     */
    
    public AdministradorProcesos(){}
    
    public void imprimirEjecucion(){
        System.out.println();
        System.out.println("----------------------------------------------------------");
        System.out.println("|                   Procesos en ejecución                |");
        System.out.println("----------------------------------------------------------"); 
        System.out.println("| PID             | Estado    | Turno | Tiempo de ejecución |");
        System.out.println("----------------------------------------------------------"); 
        for(Pcb proceso:colaEjecucion){
            System.out.printf("| %-15s | %-9s | %-5d | %-19d |\n",proceso.getPid(),proceso.getEstado(),proceso.getTiempoLlegada(),proceso.getTiempoProceso());
            System.out.println("-----------------------------------------------------"); 
        }
    }
    public void imprimirEspera(){
        System.out.println();
        System.out.println("-------------------------------------------------------------");
        System.out.println("|                     Procesos en espera                    |");
        System.out.println("-------------------------------------------------------------"); 
        System.out.println("| PID             | Estado    | Turno | Tiempo de ejecución |");
        System.out.println("-------------------------------------------------------------"); 
        for(Pcb proceso:colaEspera){
            System.out.printf("| %-15s | %-9s | %-5d | %-19d |\n",proceso.getPid(),proceso.getEstado(),proceso.getTiempoLlegada(),proceso.getTiempoProceso());
            System.out.println("------------------------------------------------------"); 
        }
    }
    public void imprimirBloqueo(){
        System.out.println();
        System.out.println("-------------------------------------------------------------");
        System.out.println("|                     Procesos en bloqueo                   |");
        System.out.println("-------------------------------------------------------------"); 
        System.out.println("| PID             | Estado    | Turno | Tiempo de ejecución |");
        System.out.println("-------------------------------------------------------------"); 
        for(Pcb proceso:colaBloqueo){
            System.out.printf("| %-15s | %-9s | %-5d | %-19d |\n",proceso.getPid(),proceso.getEstado(),proceso.getTiempoLlegada(),proceso.getTiempoProceso());
            System.out.println("---------------------------------------------------------"); 
        }
    }
    public void actualizarEjecucion(ArrayList<Pcb> procesos){
        colaEjecucion = new ArrayList<Pcb>();
        colaEspera = new ArrayList<Pcb>();
        colaBloqueo = new ArrayList<Pcb>();
        for(Pcb p:procesos){
            switch(p.getEstado()){
                case "Ejecutando":
                    colaEjecucion.add(p);
                    break;
                case "Espera":
                    colaEspera.add(p);
                    break;
                case "Bloqueado":
                    colaBloqueo.add(p);
                    break;
                default:
                    break;
            }
        }
    }
    public void imprimirMemoria(AdministradorMemoria adminMemoria){
        for(int i = 0;i<adminMemoria.getMemoria().length;i++){
            if(adminMemoria.getMemoria()[i] == null)System.out.println(i+": Vacio.");
            else System.out.println(i+": Lleno, PID:"+adminMemoria.getMemoria()[i].getPid());
        }
        System.out.println("\nTipo de partición: "+adminMemoria.getTipoParticion()+".");
        if(adminMemoria.getTipoParticion() == 'F'){
            System.out.println("Tamaño de partición: "+adminMemoria.getTamanhoParticion()+"MB.");
        }else{
            System.out.println("Tipo de ajuste: "+adminMemoria.getTipoAjuste()+".");
        }
        System.out.println("Capacidad de memoria: "+adminMemoria.getTamanhoMemoria()+"MB.");
        System.out.println("Memoria ocupada: "+adminMemoria.getMemoriaOcupada()+"MB.");
        System.out.println("Memoria Disponible: "+adminMemoria.getMemoriaDisponible()+"MB.");
        System.out.println("\n--------------------");
        if(adminMemoria.getTipoParticion() == 'F'){
            for(int i = 0;i<adminMemoria.getTamanhoMemoria();i++){
                if(adminMemoria.getMemoria()[i] != null){
                    System.out.println("|                  |");
                    System.out.printf("| %-16s |\n",adminMemoria.getMemoria()[i].getPid());
                    System.out.printf("| %-16s |\n",adminMemoria.getMemoria()[i].getEstado());
                    System.out.printf("| %-16s |\n","("+adminMemoria.getMemoria()[i].getTamanhoProceso()+" MB)");
                    System.out.printf("| %-16s |\n","Tiempo: "+adminMemoria.getMemoria()[i].getTiempoProceso());
                    System.out.println("|--              --|");
                }else{
                    System.out.println("|                  |");
                    System.out.println("|      Espacio     |");
                    System.out.println("|     Desocupado   |");
                    System.out.printf("| %-16s |\n","("+adminMemoria.getTamanhoParticion()+" MB)");
                    System.out.println("|                  |");
                    System.out.println("|--              --|"); 
                }
                for(int j = 1;j<adminMemoria.getTamanhoParticion();j++){
                    System.out.println("|                  |");
                    System.out.println("|                  |");
                    System.out.println("|                  |");
                    System.out.println("|                  |");
                    System.out.println("|                  |");
                    System.out.println("|--              --|");
                    i++;
                }
                System.out.println("--------------------");
            } 
        }else{
            for(int i = 0;i<adminMemoria.getTamanhoMemoria();i++){
                int espacio = 0;
                if(adminMemoria.getMemoria()[i] == null){
                    for(int k=i;k<adminMemoria.getTamanhoMemoria();k++){
                        if(adminMemoria.getMemoria()[k] == null)espacio++;
                        else break;   
                    }
                    System.out.println("|                  |");
                    System.out.println("|      Espacio     |");
                    System.out.println("|     Desocupado   |");
                    System.out.printf("| %-16s |\n","("+espacio+" MB)");
                    System.out.println("|                  |");
                    System.out.println("|--              --|"); 
                    for(int k = 1;k<espacio;k++){
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|--              --|");
                        i++;
                    }
                }else{
                    espacio++;
                    for(int k=i+1;k<adminMemoria.getTamanhoMemoria();k++){
                        if(adminMemoria.getMemoria()[k] != null && adminMemoria.getMemoria()[k].getPid().equals(adminMemoria.getMemoria()[k-1].getPid()))espacio++;
                        else break;   
                    }
                    System.out.println("|                  |");
                    System.out.printf("| %-16s |\n",adminMemoria.getMemoria()[i].getPid());
                    System.out.printf("| %-16s |\n",adminMemoria.getMemoria()[i].getEstado());
                    System.out.printf("| %-16s |\n","("+adminMemoria.getMemoria()[i].getTamanhoProceso()+" MB)");
                    System.out.printf("| %-16s |\n","Tiempo: "+adminMemoria.getMemoria()[i].getTiempoProceso());
                    System.out.println("|--              --|");
                    for(int k = 1;k<espacio;k++){
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|                  |");
                        System.out.println("|--              --|");
                        i++;
                    }
                }
                System.out.println("--------------------");
            }
        }
    }
    public void agregarProceso(Pcb proceso){
        colaBloqueo.add(proceso);
    }
    public ArrayList<Pcb> getColaEjecucion(){
        return colaEjecucion;
    }
    public ArrayList<Pcb> getColaEspera(){
        return colaEspera;
    }
    public ArrayList<Pcb> getColaBloqueo(){
        return colaBloqueo;
    }
}
