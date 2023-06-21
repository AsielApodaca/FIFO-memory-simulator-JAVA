/*
 * DAOException.java
 *
 */

package excepciones;

/**
 * Esta clase representa a las excepciones lanzadas por las clases
 * que se encargan de acceder a los datos en el mecanismo de persistencia.
 *
 * @author Equipo FIFO Sistemas Operativos
 */

public class MenuException extends RuntimeException {
  /**
   * Constructor por omisión. Construye una excepción con un mensaje de error
   * nulo.
   */
    
  public MenuException() {
  }
  /**
   * Construye una excepción con el mensaje de error del parámetro.
   * @param msj Mensaje de error.
   */
  
  public MenuException(String msj) {
    super(msj);
  }
  /**
   * Construye una excepción con el mensaje de error del parámetro y la causa
   * original del error.
   * @param msj Mensaje de error.
   * @param causa Causa original del error.
   */
  
  public MenuException(String msj, Throwable causa) {
    super(msj, causa);
  }
  /**
   * Construye una excepción la causa original del error.
   * @param causa Causa original del error.
   */
  
  public MenuException(Throwable causa) {
    super(causa);
  }
}