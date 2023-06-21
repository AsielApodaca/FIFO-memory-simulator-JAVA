package ejecucion;
import java.util.Objects;
/**
 * @author Equipo FIFO Sistemas Operativos
 * Clase Pcb.
 */
public class Pcb {
    private String pid,estado;
    private int tiempoProceso,tamanhoProceso,tiempoLlegada;
    /**
     * Constructor general.
     */
    public Pcb(){}
    /**
     * Constructor para crear objeto proceso.
     * @param pid Valor tipo String para identificar id del proceso.
     * @param tiempoProceso Valor tipo int para evaluar tiempo a procesar.
     */
    public Pcb(String pid, String estado, int tiempoProceso, int tamanhoProceso, int tiempoLlegada){
        this.pid = pid;
        this.estado = estado;
        this.tiempoProceso = tiempoProceso;
        this.tamanhoProceso = tamanhoProceso;
        this.tiempoLlegada = tiempoLlegada;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTiempoProceso() {
        return tiempoProceso;
    }
    public void setTamanhoProceso(int tamanho){
        this.tamanhoProceso = tamanho;
    }
    
    public int getTamanhoProceso(){
        return tamanhoProceso;
    }
    
    public void setTiempoProceso(int tiempoProceso) {
        this.tiempoProceso = tiempoProceso;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.pid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pcb other = (Pcb) obj;
        return Objects.equals(this.pid, other.pid);
    }

    @Override
    public String toString() {
        return "Pcb{" + "pid=" + pid + ", estado=" + estado + ", tiempoProceso=" + tiempoProceso + ", tiempoLlegada=" + tiempoLlegada + '}';
    }
    
}
