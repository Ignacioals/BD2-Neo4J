package ar.com.uade.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Statement;
import org.neo4j.driver.StatementResult;
public class Connection {

	
	private Driver driver;
	
	public Connection() {
		driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ));
	}
	
	public Session openSesion() {
		return driver.session();
	}
	
	public void closeSesion(Session sesion) {
		sesion.close();
	}
	
	public void getDuenios() {
		
		Session sesion = this.openSesion();
		Statement query = new Statement("MATCH(duenio:Duenio) Return duenio.name");
		StatementResult record = sesion.run(query);
		List<Record> registros = record.list();
		if (!registros.isEmpty()) {
			for(Record registro : registros) {
		
			/*
			 * registros es una lista de nodos. 
			 * 
			 * get(0) me devuelve el primer valor del nodo
			 * 
			 * El segundo get me permite seguir por las propiedades.
			 * 
			 * */
			System.out.println(registro.get(0));
		
			}
		}
		
		this.closeSesion(sesion);
	}
	
	public void getUnidadConMayorExpensasPorEdificio(String name) { //Consulta 2
		Session sesion = this.openSesion();
		Statement query = new Statement("match (e:Edificio{name:'"+name+"'})-[:Tiene]-(u:Unidad) "
				+ "RETURN e.name,u.name,u.expensas\r\n" + 
				"ORDER BY u.expensas DESC\r\n" + 
				"LIMIT 1");
		StatementResult record = sesion.run(query);
		List<Record> registros = record.list();
		for(Record r : registros) {
			System.out.println("Edificio: "+r.get(0) +" Unidad: "+r.get(1));
			System.out.println("Expensas: "+r.get(2));
		}
		this.closeSesion(sesion);
	}

	public String crearEdificio() {
		String shortname, name, direccion;
		int pisos,unidadesxpiso;
		List<Integer> expensasxtipounidad = new ArrayList<Integer>();
		Scanner entrada= new Scanner(System.in);
		System.out.print("Ingrese nombre del edificio: ");
		name = entrada.nextLine();
		System.out.print("Ingrese un nombre para el edificio (alfanuméricos de  maximo 3 digitos): ");
		shortname = entrada.nextLine();
		System.out.print("Ingrese la dirección del edificio: ");
		direccion= entrada.nextLine();
		System.out.print("Ingrese la cantidad de pisos del edificio (Máximo 15): ");
		pisos= entrada.nextInt();
		int max = 15/pisos;
		System.out.print("Ingrese la cantidad de unidades por piso del edificio (Máximo "+max+"): ");
		unidadesxpiso = entrada.nextInt();
		for(int i=0;i<unidadesxpiso;i++) {
			System.out.print("Ingrese valor de expensas para unidades: $");
			expensasxtipounidad.add(entrada.nextInt());
		}
		System.out.println("todas las unidades se crearán con estado 'No alquilado' de forma predeterminada");
		String query = "CREATE ("+shortname+":Edificio{name:'"+name+"', direccion:'"+direccion+"'}),";
		int count = 0;
		for(int i=1;i<=pisos;i++) {
			for(int j=0;j<unidadesxpiso;j++) {
				String queryuni = "("+(char)(count+'a')+":Unidad{name:'Unidad"+i+" Edificio"+j+1+"', estado: 'Alquilado', expensas:"+expensasxtipounidad.get(j)+"})";
				count++;
				if(i==pisos && j==unidadesxpiso-1) {
					queryuni= queryuni.concat(" ");
				} else {
					queryuni= queryuni.concat(", ");
				}
				query = query.concat(queryuni);
			}
		}
		query = query.concat("WITH "+shortname+", ");
		for (int i=0; i<count; i++) {
			if(i==count-1) {
			query= query.concat((char)(i+'a')+" ");
			}else {
			query= query.concat((char)(i+'a')+", ");
			}
		}
		query = query.concat("CREATE ");
		for (int i=0; i<count; i++) {
			if(i==count-1) {
				query= query.concat("("+shortname+")-[:Tiene]->("+(char)(i+'a')+") ");
				}else {
				query= query.concat("("+shortname+")-[:Tiene]->("+(char)(i+'a')+"), ");
				}
		}
		System.out.println(query);
		Session sesion = this.openSesion();
		Statement stat = new Statement(query);
		sesion.run(stat);
		System.out.println("Edificio '"+name+"' (con "+pisos * unidadesxpiso+" unidades) Creado");
		this.closeSesion(sesion);
		return name;
		
	}
	public void eliminarEdificio(String name) {
		Session sesion = this.openSesion();
		Statement query = new Statement("match (edificio:Edificio{name:'"+name+"'})-[:Tiene]-(unidad:Unidad) "
				+ "detach delete edificio,unidad");
		sesion.run(query);
		System.out.println("El edificio "+name+" ha sido eliminado.");
		this.closeSesion(sesion);
	}
	
	public void getEdificio1(String shortname) {
		Session sesion = this.openSesion();
		Statement query = new Statement("match (edificio:Edificio{name:'"+shortname+"'})-[:Tiene]-(unidad:Unidad) "
				+ "return edificio.name,unidad.name");
		StatementResult record = sesion.run(query);
		List<Record> registros = record.list();
		if (!registros.isEmpty()) {
			for (Record r: registros) {
				System.out.println("Edificio: "+registros.get(0));
				System.out.println("Unidad: "+registros.get(0));
			}
		} else {
			System.out.println("No existe el edificio '"+shortname+"'.");
		}
		this.closeSesion(sesion);
		
	}
	
	public void getEdificio2(String shortname) {
		Session sesion = this.openSesion();
		Statement query = new Statement("match (edificio:Edificio{name:'"+shortname+"'})-[:Tiene]-(unidad:Unidad) "
				+ "return edificio.name,unidad.name");
		StatementResult record = sesion.run(query);
		List<Record> registros = record.list();
		if (!registros.isEmpty()) {
			for (Record r: registros) {
				System.out.println("Edificio: "+registros.get(0));
				System.out.println("Unidad: "+registros.get(0));
			}
		} else {
			System.out.println("No existe el edificio '"+shortname+"'.");
		}
		this.closeSesion(sesion);
		
	}
	
	
	
}
