# BD2-Neo4J
#### Load the database
* Open the Neo4J browser, create a new project, and copy the content from `CargaDeDatos.txt`
#### Querys
1. Get the owners of the apparments from a specific building. Copy the following text into the query box `Match(al1:Edificio{name:"Alameda"})-[:Tiene]-(unidad)-[:Propietario]->(duenio:Duenio)
return duenio.name`
2. Get the rented apparments and its tenants. Copy the following text into the query box `Match (unidad:Unidad{estado:"Alquilado"})-[:Alquilado_Por]-(arrendatario:Arrendatario)
return unidad.name,arrendatario.name,arrendatario.apellido`
3. Get the apparment for a specific tenant, Carlos in this example. Copy the following text into the query box `Match(arrendatario:Arrendatario{name:"Carlos"})-[:Alquilado_Por]-(unidad:Unidad)
return unidad.name,arrendatario.name`
4. Get how many tenants a building has. Copy the following text into the query box `match (edificio:Edificio{name:"Posadas"})-[:Tiene]-(unidad:Unidad{estado:"Alquilado"})-[:Alquilado_Por]->(arrendatario:Arrendatario)
return edificio.name, count(unidad)`
5. Get the apparment of each tenant. Copy the following text into the query box `match (arrendatario:Arrendatario)-[:Alquilado_Por]-(unidad:Unidad)
return arrendatario.name,arrendatario.apellido,unidad.name`
6. Get how much should a specific owner pay in expenses for all of his appartments. Copy the following text into the query box `match(duenio:Duenio{name:"Charly"})-[:Propietario]-(unidad:Unidad)
return duenio.name,sum(unidad.expensas) as expensas_a_pagar`




 
