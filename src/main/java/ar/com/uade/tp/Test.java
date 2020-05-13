package ar.com.uade.tp;

import ar.com.uade.tp.Connection;

public class Test {

	public static void main(String[] args) {
		Connection connect = new Connection();
		String name = null;
		System.out.println("Demostración de base de datos NEO4J desde Java");
		System.out.println("# Consulta 1: todos los duenios #");
		connect.getDuenios();
		System.out.println("# Consulta 2: la unidad con mayor expensas por edificio #");
		System.out.println("Edificio: Alameda");
		String nameString = "Alameda";
		connect.getUnidadConMayorExpensasPorEdificio(nameString);
		System.out.println("--------------------------------------------");
		System.out.println("# Creación de un edificio y sus unidades");
		name = connect.crearEdificio();
		System.out.println("# Demostración de creado de edificio: #");
		connect.getEdificio1(name);
		System.out.println();
		System.out.println("# Eliminación del edificio dado #");
		connect.eliminarEdificio(name);
		connect.getEdificio2(name);
		System.out.println();
		System.out.println("Fin del programa");
		
		System.exit(0);

	}
}
