package juego;

import java.awt.Color;
import entorno.Entorno;

public class Proyectil {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private int direccion; // 1 = Derecha, -1 = Izquierda
    private boolean disparado;

    // Constructor para cuando la Princesa agarra el ítem (se equipa)
    public Proyectil(double x, double y) {
        this.ancho = 30; // Tamaño del cohete/rectángulo
        this.alto = 15;
        this.velocidad = 10; // Velocidad del misil
        this.disparado = false;
        this.direccion = 1; // Por defecto mira a la derecha
        this.x = x;
        this.y = y;
    }

    // Actualiza la lógica del proyectil
    public void actualizar(Princesa princesa, Entorno entorno) {
        if (!disparado) {
            // SI NO SE DISPARÓ: Sigue a la princesa (un poco más arriba de su centro)
            this.x = princesa.getX();
            this.y = princesa.getY() - (princesa.getAlto() / 2);
        } else {
            // SI YA SE DISPARÓ: Se mueve en línea recta en la dirección guardada
            this.x += (this.velocidad * this.direccion);
        }
    }
    public void disparar(int direccionPrincesa) {
        if (!this.disparado) {
            this.disparado = true;
            this.direccion = direccionPrincesa; 
        }
    }
    /**
     * Controla todo el ciclo de vida del proyectil adaptado a la Princesa original.
     * @return true si el proyectil sigue vivo, false si debe destruirse.
     */
    public boolean disparo(Princesa princesa, Entorno entorno) {
        // 1. Dibujarse en la pantalla
        this.dibujar(entorno);

        // 2. Manejar el movimiento (si está en el suelo/equipada o si ya vuela sola)
        if (!this.disparado) {
            // Colisión matemática exacta usando los getters reales de tu Princesa
            boolean tocando = (princesa.getX() - princesa.getAncho()/2 < this.x + this.ancho/2 &&
                               princesa.getX() + princesa.getAncho()/2 > this.x - this.ancho/2 &&
                               princesa.getY() - princesa.getAlto()/2 < this.y + this.alto/2 &&
                               princesa.getY() + princesa.getAlto()/2 > this.y - this.alto/2);
            if (tocando) {
                this.actualizar(princesa, entorno);
            }
        } else {
            this.actualizar(princesa, entorno);
        }

        // 3. DETECTAR EL DISPARO (Mirando las teclas del entorno directamente)
        if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && !this.disparado) {
            // Si el jugador se mueve a la izquierda al disparar, va hacia la izquierda (-1)
            if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
                this.disparar(-1);
            } else { 
                // Por defecto (parada o caminando a la derecha), dispara a la derecha (1)
                this.disparar(1);
            }
        }

        // 4. Verificar si se salió del mapa para avisarle al Main que lo borre
        if (this.seSalioDelMapa(entorno)) {
            return false; 
        }
        return true; 
    }
    // Dibuja el proyectil en la pantalla
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
    }
     // Controla toda la lógica del proyectil: el agarre en el suelo,
     // el seguimiento a la princesa y el avance cuando ya fue lanzado.
    
    
    
    // Verifica si el proyectil se salió de los límites horizontales del mapa
    public boolean seSalioDelMapa(Entorno entorno) {
        if (this.x > entorno.ancho() + 50 || this.x < -50) {
            return true;
        }
        return false;
    }

    // --- GETTERS Y SETTERS ---

    public boolean isDisparado() { 
        return disparado; 
    }

    /**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the alto
	 */
	public double getAlto() {
		return alto;
	}

	/**
	 * @param alto the alto to set
	 */
	public void setAlto(double alto) {
		this.alto = alto;
	}

	/**
	 * @return the ancho
	 */
	public double getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
}