package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Princesa princesa;
	private Enemigo[] enemigos;
	private GestionadorPlataformas plataformas;
	private Proyectil proyectil;
	private Castillo castillo;
	private double camaraY = 0;
	private double maxCamara = 4;
	private Niveles gestorNiveles;
	
	// CORRECCIÓN: Faltaba declarar la variable nivel que usás en el tick
	private int nivel = 1; 
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		princesa = new Princesa(entorno.ancho()/2,200,20,20, entorno);
		plataformas = new GestionadorPlataformas();
		plataformas.crearPiso(300, entorno);
		enemigos = new Enemigo[10];
		this.proyectil = new Proyectil(600, entorno.alto() - 15);
		castillo = new Castillo(200, 550, "castillo.jpg", this.entorno);
		
		// Inicializamos el gestor de niveles
		this.gestorNiveles = new Niveles(this, this.entorno);

		// Inicia el juego!
		this.entorno.iniciar();
	}

	// CORRECCIÓN: 'public' para que Niveles.java lo pueda ejecutar
	public void actualizarCamara(Princesa princesa) {
		if (princesa.getX() + 50 > 600 && entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			camaraY += 1;
		} else {
			camaraY = 0;
		}
		if (camaraY > maxCamara) {
			camaraY = maxCamara;
		}
	}
	
	// ESTO CUENTA LOS ENEMIGOS VIVOS
	private int contarEnemigos() {
	    int cantidad = 0;
	    for(int i = 0; i < enemigos.length; i++) {
	        if(enemigos[i] != null) {
	            cantidad++;
	        }
	    }
	    return cantidad;
	}

	// ESTO ES PARA CREAR UN ENEMIGO
	private void crearEnemigo() {
	    for(int i = 0; i < enemigos.length; i++) {
	        if(enemigos[i] == null) {
	            boolean izquierda = Math.random() < 0.5;
	            double x;
	            if(izquierda) {
	                x = -30;
	            }
	            else {
	                x = entorno.ancho() + 30;
	            }
	            double y = 100 + Math.random() * 300;
	            enemigos[i] = new Enemigo(x, y, izquierda, entorno);
	            break;
	        }
	    }
	}

	// CORRECCIÓN: 'public' para que Niveles.java lo pueda ejecutar
	public void mantenerEnemigos() {
	    while(contarEnemigos() < 3) {
	        crearEnemigo();
	    }
	}

	// CORRECCIÓN: 'public' para que Niveles.java lo pueda ejecutar
	public void actualizarEnemigos() {
	    for(int i = 0; i < enemigos.length; i++) {
	        if(enemigos[i] != null) {
	            enemigos[i].mover();
	            enemigos[i].dibujar();
	            if(enemigos[i].fueraDePantalla()) {
	                enemigos[i] = null;
	            }
	        }
	    }
	}

	// CORRECCIÓN: Método necesario para borrar los enemigos al cambiar de nivel
	public void limpiarEnemigos() {
		for (int i = 0; i < enemigos.length; i++) {
			enemigos[i] = null;
		}
	}

	/**
	 * El tick() ahora SOLO decide qué nivel se debe renderizar y procesar.
	 * Se eliminó todo el código duplicado que estaba acá adentro.
	 */
	public void tick()
	{
		if (this.getNivel() == 1) {
			gestorNiveles.ejecutarNivel1();
		} else if (this.getNivel() == 2) {
			gestorNiveles.ejecutarNivel2();
		}
	}
	
	// --- GETTERS Y SETTERS ---
	public Princesa getPrincesa() { return princesa; }
	public Castillo getCastillo() { return castillo; }
	public GestionadorPlataformas getPlataformas() { return plataformas; }
	public Proyectil getProyectil() { return proyectil; }
	public void setProyectil(Proyectil p) { this.proyectil = p; }
	public double getCamaraY() { return camaraY; }
	public void setNivel(int nivel) { this.nivel = nivel; }
	public int getNivel() { return nivel; }

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}