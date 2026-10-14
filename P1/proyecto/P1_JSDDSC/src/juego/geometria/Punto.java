package juego.geometria;

import java.lang.Math;
import java.util.Random;

/** La clase Punto representa un punto inmutable en el espacio bidimensional.
 *  Una vez creado el objeto no puede cambiar de estado.
 *
 */
public class Punto {
    public final static int defaultValue = 0;

     final int x;
     final int y;


	// Constructor por defecto
	public Punto() {
		this (0, 0);
	
	}

	// Constructor
	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Constructor de copia
	public Punto(Punto otra) {
		this(otra.x, otra.y);
	}

    /** Devuelve el valor de la coordenada x
     * 
     */
    public int getX() {
        return x;
    }

    /** Devuelve el valor de la coordenada x
     * 
     */
    public int getY() {
        return y;
    }

	/** Devuelve un nuevo objeto Posicion que representa el valor de la posición
	 *  adyacente a la instancia actual en la dirección pasada como parámetro.
	 */
	public Punto Adyacente(Direccion direccion) {

		switch (direccion) {
		case DERECHA:
			return new Punto(x + 1, y); 
		case IZQUIERDA:
			return new Punto(x - 1, y); 
		case ARRIBA:
		    return new Punto(x, y + 1); 
		case ABAJO:
		    return new Punto(x, y - 1); 
		}

		return null;
	}

	/**
	 * Informa si la posición pasada como parámetro es adyacente a la instancia actual
	 * en la dirección indicada.
	 * 
	 * @return true si es adyacente y false en caso contrario.
	 */
	public Boolean isAdyacente(Punto otra, Direccion direccion) {

	    return otra.equals(this.Adyacente(direccion));
	}

	/**
	 * Devuelve la situación relativa de la instancia actual respecto a la posición que se pasa como parámetro.
	 * En el caso de no ser posiciones adyacentes el valor de retorno es null. 
	 */
	public Direccion situacion_relativa(Punto otra){
	    int difX = Math.abs(x - otra.x);
	    int difY = Math.abs(y - otra.y);

		if (difY == 1 && difX == 0)
			return Direccion.ARRIBA;
		if (difY == 1 && difX == 0)
			return Direccion.ABAJO;
		if (difY == 0 && difX == 1)
			return Direccion.IZQUIERDA;
		if (difY == 0 && difX == 1)
			return Direccion.DERECHA;
		
		return null;
	}
	
	public Punto desplazar(int incX, int incY){
	    return new Punto(x+incX,y+incY);
	}
	
	public Punto desplazar(Direccion direccion){
	    switch (direccion){
	    case ARRIBA : return desplazar(0,1);
	    case ABAJO: return desplazar(0,-1);
	    case DERECHA: return desplazar(1, 0);
	    case IZQUIERDA: return desplazar(-1,0);
	    default:
	        return null;
	    }
	}

	/** Calcula la distancia entre dos posiciones.
	 *
	 */
	private double distancia (Punto posicion) {
		return Math.sqrt(Math.pow((double) this.x-posicion.x, 2)+Math.pow((double) this.y-posicion.y, 2));
	}

	@Override
	public String toString() {
		return getClass().getName() + " [x=" + x + ", y=" + y + "]";
	}

	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        // Comprobamos nulos y clases para evitar excepciones
        if (obj == null || getClass() != obj.getClass())
            return false;

        Punto other = (Punto) obj; // ¡Este cast ahora SÍ es necesario! (Soluciona S1905)
        return (x == other.x && y == other.y);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

	

}

