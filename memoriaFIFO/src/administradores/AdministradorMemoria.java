package administradores;
import ejecucion.Pcb;
import excepciones.AdministaradorException;
import java.util.ArrayList;

/**
 * @author Equipo FIFO Sistemas Operativos
 * Clase AdministradorMemoria.
 */

public class AdministradorMemoria {
    private Pcb[] memoria;
    private ArrayList<Pcb> procesos = new ArrayList<Pcb>();
    private int tamanhoParticion = 0,turno = -1;
    private char tipoParticion = ' ',tipoAjuste = ' ';
    
    /**
     * Constructor general
     */
    public AdministradorMemoria(){}
    
    public void actualizarMemoria(){
        Boolean ejecutando = false;
        Pcb pro;
        for(int i = 0;i<memoria.length;i++){
            if(memoria[i] != null && memoria[i].getEstado().equals("Ejecutando")){
                memoria[i].setTiempoProceso(memoria[i].getTiempoProceso()-1);
                ejecutando = true;
                if(memoria[i].getTiempoProceso() == 0){
                    pro = memoria[i];
                    pro.setEstado("Finalizado");
                    ejecutando = false;
                    procesos.set(procesos.indexOf(memoria[i]), pro);
                    int tamanho = memoria[i].getTamanhoProceso();
                    for(int k = 0;k<tamanho;k++){
                        memoria[i+k] = null;
                    }
                }
                break;
            }
        }
        if(tipoParticion == 'F'){
            particionFija();
        }else{
            if(tipoAjuste == 'P'){
                primerAjuste();
            }else{
                mejorAjuste();
            }
        }
        if(ejecutando == false){
            turno++;
            for(int i = 0;i<memoria.length;i++){
                if(memoria[i] != null && memoria[i].getTiempoLlegada() == turno){
                    pro = memoria[i];
                    pro.setEstado("Ejecutando");
                    procesos.set(procesos.indexOf(memoria[i]), pro);
                    for(int k = 0;k<memoria[i].getTamanhoProceso();k++){
                        memoria[i+k].setEstado("Ejecutando");
                    }
                    break;
                }
            }
        }
    }
    /**
     * Si hay una partición fija desocupada, se le asigna el proceso siguente a la lista.
     */
    public void particionFija(){
        for(int i = 0;i<memoria.length/tamanhoParticion;i++){
            if(memoria[tamanhoParticion*i] == null){
                for(int j = 0;j<procesos.size();j++){
                    if(procesos.get(j).getEstado().equals("Bloqueado") && procesos.get(j).getTamanhoProceso()<=tamanhoParticion){
                        Pcb pro = procesos.get(j);
                        pro.setEstado("Espera");
                        procesos.set(j, pro);
                        for(int k = 0;k<procesos.get(j).getTamanhoProceso();k++){
                            memoria[tamanhoParticion*i+k] = procesos.get(j);
                        }
                        break;
                    }
                }
            }  
        }
    }
    /**
     * Si hay espacio suficiente en la memoria, asigna a ese espacio el proceso siguiente a la lista,
     * si no hay espacio suficiente no realiza nada.
     */
    public void primerAjuste(){
        boolean disponible = false;
        for(int i = 0;i<procesos.size();i++){
            if(procesos.get(i).getEstado().equals("Bloqueado") && procesos.get(i).getTamanhoProceso()<=memoria.length){
                Pcb proceso = procesos.get(i);
                int tamanhoProceso = proceso.getTamanhoProceso();
                int espacio = 0;
                
                int ubicacion = memoria.length;
                for(int k = 0;k<memoria.length;k++){
                    if(memoria[k] == null){
                        ubicacion = k;
                        break;
                    }
                }
                for(int k = ubicacion;k<memoria.length;k++){
                    if(memoria[k] == null){
                        espacio++;
                        if(espacio>=tamanhoProceso){
                            disponible = true;
                            for(int j = 0;j<espacio;j++){
                                Pcb procesoEspera = proceso;
                                procesoEspera.setEstado("Espera");
                                procesos.set(procesos.indexOf(proceso), procesoEspera);
                                memoria[j+ubicacion] = procesoEspera;
                            }
                            break;
                        }
                    }else{
                        disponible = false;
                        espacio = 0;
                    }
                }
                if(disponible == false)break;
            }
        }
    }
    
    public void mejorAjuste(){
        for(int i = 0;i<procesos.size();i++){
            if(procesos.get(i).getEstado().equals("Bloqueado") && procesos.get(i).getTamanhoProceso()<=memoria.length){
                int ubicacion = memoria.length;
                int espacio = 0;
                ArrayList<int[]> espaciosLibres = new ArrayList<int[]>();
                for(int k = 0;k<memoria.length;k++){//Obtiene todas las ubicaciones libres y su espacio.
                    if(memoria[k] == null){
                        ubicacion = k;
                        for(int n = ubicacion;n<memoria.length;n++){
                            if(memoria[n] == null){
                                espacio++;
                            }if(memoria[n] != null || n+1 == memoria.length){
                                int[] libre = {ubicacion,espacio};
                                espacio = 0;
                                espaciosLibres.add(libre);
                                k=n;
                                break;
                            }
                        }
                    }
                }
                int[] mejorAjuste = {-1,memoria.length};
                for(int k = 0;k<espaciosLibres.size();k++){ //obtiene mejor ajuste para proceso
                    if(espaciosLibres.get(k)[1]<=mejorAjuste[1] && espaciosLibres.get(k)[1] >= procesos.get(i).getTamanhoProceso()){
                        mejorAjuste[0] = espaciosLibres.get(k)[0];
                        mejorAjuste[1] = espaciosLibres.get(k)[1];
                    }
                }
                if(mejorAjuste[0] != -1){
                    for(int k = 0;k<procesos.get(i).getTamanhoProceso();k++){
                        Pcb proceso = procesos.get(i);
                        proceso.setEstado("Espera");
                        procesos.set(procesos.indexOf(procesos.get(i)), proceso);
                        memoria[k+mejorAjuste[0]] = proceso;
                    }
                }else{
                    break;
                }
            }
        }
    }
    
    public void anhadirProceso(Pcb proceso){
        procesos.add(proceso);
    }
    
    /**
     * 
     * @return Retorna cantidad de espacios vacios en la memoria.
     */
    public int getMemoriaDisponible(){
        int disponible = 0;
        for(Pcb espacio:memoria)if(espacio == null)disponible++;
        return disponible;
    }
    /**
     * 
     * @return Retorna cantidad de espacios con un proceso en la memoria.
     */
    public int getMemoriaOcupada(){
        int ocupado = 0;
        for(Pcb espacio:memoria)if(espacio != null)ocupado++;
        return ocupado;
    }
    
    /**
     * 
     * @return Retorna tamaño total de la memoria.
     */
    public int getTamanhoMemoria(){
        if(memoria == null){
            return 0;
        }
        else{
            return memoria.length;
        } 
    }
    /**
     * 
     * @return Retorna tipo de partición Fija o Dinámica ('F' / 'D').
     */
    public char getTipoParticion(){
        return tipoParticion;
    }
    /**
     * 
     * @return Retorna tamaño de partición fija.
     */
    public int getTamanhoParticion(){
        return tamanhoParticion;
    }
    /**
     * 
     * @return Retorna tipo de ajuste de memoria.
     */
    public char getTipoAjuste(){
        return tipoAjuste;
    }
    /**
     * 
     * @return Retorna arreglo memoria.
     */
    public Pcb[] getMemoria(){
        return memoria;
    }
    /**
     * 
     * @return Retorna lista de procesos.
     */
    public ArrayList<Pcb> getProcesos(){
        return procesos;
    }
    /**
     * Establece tamaño en MB de la memoria.
     * @param tamanho tamaño a asignar a la memoria.
     */
    public void setTamanhoMemoria(int tamanho){
        memoria = new Pcb[tamanho];
    }
    /**
     * Establece tamaño en MB de partición fija.
     * @param tamanho tamaño a asignar a la partición fija.
     */
    public void setTamanhoParticion(int tamanho){
        this.tamanhoParticion = tamanho;
    }
    /**
     * Establece tipo de particion Fija o Dinámica ('F' / 'D').
     * @param tipo Tipo a establecer ('F' / 'D').
     */
    public void setTipoParticion(char tipo){
        tipoParticion = tipo;
    }
    public void setTipoAjuste(char tipo){
        tipoAjuste = tipo;
    }

    /*
    //No sé si lo que hice está bien así que aquí dejo esto
    @Override
    public String toString() {
        return "AdministradorMemoria{" + "memoria=" + memoria + ", tamanhoParticion=" + tamanhoParticion + ", tipoParticion=" + tipoParticion + ", tipoAjuste=" + tipoAjuste + '}';
    } */
    
    public void imprimirMemoria() {
        System.out.println("AdministradorMemoria{" + "memoria=" + memoria + ", tamanhoParticion=" + tamanhoParticion + ", tipoParticion=" + tipoParticion + ", tipoAjuste=" + tipoAjuste + '}');
    }
}
