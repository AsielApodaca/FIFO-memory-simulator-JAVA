package ejecucion;
import java.util.Scanner;
import administradores.*;
import excepciones.*;

/**
 * @author Equipo FIFO Sistemas Operativos
 * Clase Menu para interacción con usuario.
 */

public class Menu {
    //Scanner teclado
    private Scanner tec = new Scanner(System.in);
    private AdministradorMemoria adminMemoria = new AdministradorMemoria();
    private AdministradorProcesos adminProcesos = new AdministradorProcesos();
    //Crea simulación de memoria
    //private AdministradorMemoria[] adminMemoria = new AdministradorMemoria[0];
    /**
     * Constructor general
     */
    
    public Menu(){}
    /**
     * Seleccionar opción a ejecutar, si la opción ingresada no es válida se repite el ciclo.
     **/
    
    public void ejecutar(){
        System.out.print("""
                         
        - Menú de opciones -
        1. Configurar administrador de memoria.
        2. Configurar administrador de procesos.
        3. Terminar proceso.
        Teclee número de opción:\u00A0""");
        while(true){
            switch(tec.nextInt()){
                case 1:
                    configuracionAdministradorMemoria();
                    break;
                case 2:
                    if(adminMemoria.getTamanhoMemoria() == 0 || adminMemoria.getTipoParticion() == ' '){
                        System.out.println("""
                                           Error: Debe terminar de administrar la memoria
                                           antes de poder administrar los procesos.
                                           """);
                        ejecutar();
                    }else{
                        configuracionAdministradorProcesos();
                    break;
                    }
                case 3:
                    System.out.println("Fin de la operación");
                    System.exit(0);
                default:
                    System.out.println("""
                                       Error: La opción ingresada no es válida.
                                       Ingrese unicamente valores indicados (1, 2 o 3).
                                       """);
            }
        } 
    }
    
    
    private void configuracionAdministradorMemoria(){
        while (true) {
            System.out.print("""
                             
                           - Configuraración de administrador de memoria -
                           1. Ingresar tamaño total de memoria.
                           2. Seleccionar tipo de partición.
                           3. Eliminar memoria (También eliminará los procesos).
                           4. Regresar.
                           Teclee número de opción:\u00A0""");
            int opcion = tec.nextInt();
            if(opcion == 1){ //1. Ingresar tamaño total de memoria.
                if(adminMemoria.getTamanhoMemoria() != 0){
                    System.out.println("""
                                       
                                       La memoria ya tiene un tamaño asignado, Elimine los datos de la
                                       memoria para poder asignar nuevos valores.
                                       """);
                    continue;
                } 

                //1. Ingresar tamaño total de memoria.
                System.out.print("\nIngrese capacidad de MB a asignar a la memoria: ");
                int capacidad = tec.nextInt();
                if(capacidad <1){
                    System.out.println("""
                                       
                                       Debe ingresar un valor mayor o igual a 1.
                                       Se ha cancelado la operación.
                                       """);
                    continue;
                }
                adminMemoria.setTamanhoMemoria(capacidad);
                System.out.println("\nSe ha establecido un espacio de "+adminMemoria.getTamanhoMemoria()+"MB a la memoria.");
            }else if(opcion == 2){ //2. Seleccionar tipo de partición.
                if(adminMemoria.getTipoParticion() != ' '){
                    System.out.println("""
                                       
                                       La memoria ya tiene un tipo de partición asignada, Elimine los datos de la
                                       memoria para poder asignar nuevos valores.
                                       """);
                    continue;
                } 

                // 2. Seleccionar tipo de partición.
                System.out.print("""
                                 
                                 - Selección de tipo de partición -
                                 Seleccione el tipo de partición a asignar: 
                                 1. Fija.
                                 2. Dinámica.
                                 3. Cancelar.
                                 Teclee número de opción:\u00A0""");
                opcion = tec.nextInt();
                
                //2.1. Fija
                if(opcion == 1){
                    if(adminMemoria.getTamanhoMemoria() == 0){
                        System.out.println("""
                                           
                                           Primero asigna un tamaño a la memoria.
                                           Se ha cancelado la operación.
                                           """);
                        continue;
                    }
                    System.out.print("\nIngrese tamaño de partición fija en MB a asignar: ");
                    int tamanhoParticion = tec.nextInt();
                    if(tamanhoParticion<1){
                        System.out.println("""
                                       
                                       Debe ingresar un valor mayor o igual a 1.
                                       Se ha cancelado la operación.
                                       """);
                    continue;
                    }
                    if(adminMemoria.getTamanhoMemoria()%tamanhoParticion != 0){
                        System.out.println("""
                                           
                                           El tamaño de partición ingresada no se divide
                                           equitativamente entre el tamaño de la memoria.
                                           Debe ingresar un valor que no deje residuos
                                           en la memoria.
                                           Se ha cancelado la operación.
                                           """);
                        continue;
                    }
                    adminMemoria.setTamanhoParticion(tamanhoParticion);
                    adminMemoria.setTipoParticion('F');
                    System.out.println("\nSe ha establecido un tamaño de "+adminMemoria.getTamanhoParticion()+
                                        "MB a las particiones fijas.\nSe produjo un total de "+
                                        ((int)(adminMemoria.getTamanhoMemoria()/adminMemoria.getTamanhoParticion()))+
                                        " particion(es) en la memoria.");
                }else 
                    //2.2. Dinámica.
                    if(opcion == 2){
                    System.out.print("""
                                 
                                 - Selección de tipo de ajuste para partición dinámica -
                                 Seleccione el tipo de ajuste a asignar.
                                 1. Primer ajuste.
                                 2. Mejor ajuste.
                                 3. Cancelar.
                                 Teclee número de opción:\u00A0""");
                    opcion = tec.nextInt();
                    
                    if(opcion == 1){ //2.2.1 Primer ajuste.
                        adminMemoria.setTipoAjuste('P');
                        adminMemoria.setTipoParticion('D');
                        System.out.println("Se ha asignado configuración por primer ajuste.");
                    }else if(opcion == 2){ //2.2.2 Mejor ajuste.
                        adminMemoria.setTipoAjuste('M');
                        adminMemoria.setTipoParticion('D');
                        System.out.println("Se ha asignado configuración por mejor ajuste");
                    }else if(opcion == 3){ //2.2.3 Cancelar
                        System.out.println("\n Se ha cancelado la operación.");
                        continue;
                    }else{ //Error
                        System.out.print("""
                                
                                        Error: La opción ingresada no es válida.
                                        Se ha cancelado la operación.
                                        """);
                    }
                    
                }else if(opcion == 3){ //2.3. Cancelar.
                    System.out.println("\n Se ha cancelado la operación.");
                    continue;
                }
                
            }else if(opcion == 3){ //3. Eliminar memoria (También eliminará los procesos).
                adminMemoria = new AdministradorMemoria();
                System.out.println("\nSe han eliminado todos los valores e información de la memoria.");
                
            }else if(opcion == 4){ //4. Regresar.
                break;
            }else{ //Error
               System.out.print("""
                                
                                Error: La opción ingresada no es válida.
                                Por favor ingrese únicamente valores indicados (1, 2 o 3).
                                """);
            }
        }
        ejecutar();
    }
    
    private void configuracionAdministradorProcesos(){
        while(true){
            System.out.println("""
                               
                               - Configuración de administrador de procesos -
                               1. Agregar proceso.
                               2. Visualizar memoria disponible.
                               3. Visualizar memoria total (Ocupada y Libre).
                               4. Ejecutar simulador de procesos.
                               5. Regresar.
                               Teclee número de opción:\u00A0""");
            int opcion = tec.nextInt();
            if(opcion == 1){ //1. Agregar proceso
                System.out.print("\n Ingrese Id del proceso: ");
                String pid = tec.next();
                System.out.print("\n Ingrese tiempo dedicado a ejecución: ");
                int tiempo = tec.nextInt();
                if(tiempo<1){
                        System.out.println("""
                                       
                                       Debe ingresar un valor mayor o igual a 1.
                                       Se ha cancelado la operación.
                                       """);
                    continue;
                }
                System.out.print("\n Ingrese tamaño en MB del proceso: ");
                int tamanho = tec.nextInt();
                if(tamanho<1){
                        System.out.println("""
                                       
                                       Debe ingresar un valor mayor o igual a 1.
                                       Se ha cancelado la operación.
                                       """);
                    continue;
                }
                adminMemoria.anhadirProceso(new Pcb(pid, "Bloqueado",tiempo,tamanho,adminMemoria.getProcesos().size()));
                adminProcesos.agregarProceso(new Pcb(pid, "Bloqueado",tiempo,tamanho,adminMemoria.getProcesos().size()));
            }else if(opcion == 2){ //2. Visualizar memoria disponible
                System.out.println("\n Memoria disponible: "+adminMemoria.getMemoriaDisponible()+"MB.");
            }else if(opcion == 3){ //3. Visualizar memoria total (Ocupada y Libre)
                System.out.println("\n Memoria ocupada: "+adminMemoria.getMemoriaOcupada()+"MB."
                                  +"\n Memoria disponible: "+adminMemoria.getMemoriaDisponible()+"MB."
                                  +"\n Memoria total: "+adminMemoria.getTamanhoMemoria()+"MB.");
            }else if(opcion == 4){ //4. Ejecutar simulador de procesos
                System.out.println("""
                               
                               - Ejecutar simulador de procesos -
                               1. Visualizar tabla de procesos en espera o listos.
                               2. Visualizar tabla de procesos en ejecución.
                               3. Visualizar tabla de procesos en estado de bloqueo.
                               4. Visulizar memoria dividida en bloques por PCB.
                               5. Actualizar ejecución.
                               6. Regresar.
                               Teclee número de opción:\u00A0""");
                opcion = tec.nextInt();
                if(opcion == 1){ //1. Visualizar tabla de procesos en espera o listos.
                    adminProcesos.imprimirEspera();
                }else if(opcion == 2){ //2. Visualizar tabla de procesos en ejecución.
                    adminProcesos.imprimirEjecucion();
                }else if(opcion == 3){ //3. Visualizar tabla de procesos en estado de bloqueo.
                    adminProcesos.imprimirBloqueo();
                }else if(opcion == 4){ //4. Visulizar memoria dividida en bloques por PCB.
                    adminProcesos.imprimirMemoria(adminMemoria);
                }else if(opcion == 5){ //5. Actualizar ejecución.
                    adminProcesos.actualizarEjecucion(adminMemoria.getProcesos());
                    adminMemoria.actualizarMemoria();
                }else if(opcion != 6){ //Error si la opción ingresada no fue: 5. Regresar.
                    System.out.print("""
                                
                                     Error: La opción ingresada no es válida.
                                     Se ha cancelado la operación.
                                     """);
                }
            }else if(opcion == 5){ //5. Regresar
                break;
            }else{ //Error
                System.out.print("""
                                
                                 Error: La opción ingresada no es válida.
                                 Por favor ingrese únicamente valores indicados (1, 2, 3, 4 o 5).
                                 """);
            }
        }
        ejecutar();
    }
}
