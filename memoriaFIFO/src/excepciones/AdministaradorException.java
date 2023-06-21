/*
 * PersistenciaException.java
 */

package excepciones;

/**
 * Esta clase representa a las excepciones lanzadas por la fachada
 * de la Capa de Persistencia y envuelve a las excepciones lanzadas
 * por las clases que encapsulan la persistencia.
 *
 * @author Equipo FIFO Sistemas Operativos
 */

public class AdministaradorException extends RuntimeException {
  /**
   * Construye una excepción con un mensaje de error nulo.
   */
    
  public AdministaradorException() {
  }
  /**
   * Construye una excepción con el mensaje de error del parámetro.
   * @param msj Mensaje de error.
   */
  
  public AdministaradorException (String msj) {
    super(msj);
  }
  /**
   * Construye una excepción con el mensaje de error del parámetro y la causa
   * original del error.
   * @param msj Mensaje de error.
   * @param causa Causa original del error.
   */
  
  public AdministaradorException(String msj, Throwable causa) {
    super(msj, causa);
  }
  /**
   * Construye una excepción la causa original del error.
   * @param causa Causa original del error.
   */
  
  public AdministaradorException(Throwable causa) {
    super(causa);
  }
}