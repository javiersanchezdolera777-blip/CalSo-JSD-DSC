package juego.pruebas;

import juego.geometria.Punto;
import java.util.logging.Logger;

public class Programa {
	
	private static final Logger LOGGER = Logger.getLogger(Programa.class.getName());

    public static void main(String[] args) {
      Punto punto1 = new Punto();

      Punto[] puntos = new Punto[2]; 
      puntos[0] = punto1;
      
      String info = ""; 

      for (Punto punto : puntos)
          info.concat(punto.toString());

     String mensaje = (info == "") ? "no hay puntos" : info; 

     LOGGER.info(mensaje);
    }
}
