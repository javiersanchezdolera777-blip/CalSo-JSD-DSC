## 1. Miembros del grupo

| Miembro    | Nombre y apellidos    |
| ---------- | --------------------- |
| Alumno/a 1 | Javier Sánchez Dólera |
| Alumno/a 2 | Diego Sánchez Cano    |

**Nombre del proyecto Eclipse:** `P1_JSDDSC`

---

## 2. Análisis inicial

Antes de realizar ninguna modificación sobre el código proporcionado se ha ejecutado el análisis estático del proyecto utilizando **SonarQube for Eclipse** con su configuración por defecto.

### Captura inicial


![[sonar_inicial.png]]
---

## 3. Disconformidades detectadas

En el análisis inicial se han identificado las siguientes disconformidades:

|  Nº | Regla Sonar  | Archivo          | Línea | Disconformidad                                                                                                   |
| --: | ------------ | ---------------- | ----: | ---------------------------------------------------------------------------------------------------------------- |
|   1 | `java:S1598` | `Direccion.java` |     1 | File path "src\geometria" should match package name "juego.geometria". Move the file or change the package name. |
|   2 | `java:S1197` | `Direccion.java` |    19 | Move the array designators [] to the type.                                                                       |
|   3 | `java:S2119` | `Direccion.java` |    21 | Save and re-use this "Random".                                                                                   |
|   4 | `java:1598`  | `Programa.java`  |     1 | File path "src\pruebas" should match package name "juego.pruebas". Move the file or change the package name.     |
|   5 | `java:S1197` | `Programa.java`  |     7 | Move the array designators [] to the type.                                                                       |
|   6 | `java:S1197` | `Programa.java`  |    10 | Move the array designators [] to the type.                                                                       |
|   7 | `java:S106`  | `Programa.java`  |    20 | Replace this use of System.out by a logger.                                                                      |
|   8 | `java:S4973` | `Programa.java`  |    18 | Strings and Boxed types should be compared using "equals()".                                                     |
|   9 | `java:S2184` | `Punto.java`     |   117 | Cast one of the operands of this subtraction operation to a "double".                                            |
|  10 | `java:S2184` | `Punto.java`     |   117 | Cast one of the operands of this subtraction operation to a "double".                                            |
|  11 | `java:S1201` | `Punto.java`     |   126 | Either override Object.equals(Object), or rename the method to prevent any confusion.                            |
|  12 | `java:S1598` | `Punto.java`     |     1 | File path "src\geometria" should match package name "juego.geometria". Move the file or change the package name. |
|  13 | `java:S2975` | `Punto.java`     |   136 | Remove this "clone" implementation; use a copy constructor or copy factory instead.                              |
|  14 | `java:S108`  | `Punto.java`     |   143 | Remove this block of code, fill it in, or add a comment explaining why it is empty.                              |
|  15 | `java:S1905` | `Punto.java`     |   130 | Remove this unnecessary cast to "Punto".                                                                         |
|  16 | `java:S1128` | `Punto.java`     |     3 | Remove this unnecessary import: java.lang classes are always implicitly imported.                                |
|  17 | `java:S1144` | `Punto.java`     |   116 | Remove this unused private "distancia" method.                                                                   |
|  18 | `java:S115`  | `Punto.java`     |    11 | Rename this constant name to match the regular expression '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.                       |
|  19 | `java:S100`  | `Punto.java`     |    51 | Rename this method name to match the regular expression '^[a-z][a-zA-Z0-9]*$'.                                   |
|  20 | `java:S100`  | `Punto.java`     |    82 | Rename this method name to match the regular expression '^[a-z][a-zA-Z0-9]*$'.                                   |
|  21 | `java:S1124` | `Punto.java`     |    11 | Reorder the modifiers to comply with the Java Language Specification.                                            |
|  22 | `java:S2225` | `Punto.java`     |   145 | Return a non null object.                                                                                        |
|  23 | `java:S1598` | `Circulo.java`   |     1 | File path "src\geometria" should match package name "juego.geometria". Move the file or change the package name. |
|  24 | `java:S1172` | `Circulo.java`   |    10 | Remove this unused method parameter "centroIni".                                                                 |
|  25 | `java:S101`  | `Circulo.java`   |     3 | Rename this class name to match the regular expression '^[A-Z][a-zA-Z0-9]*$'.                                    |
|  26 | java:S2201   | Programa.java    |    16 | Resource	Date	Description<br>Programa.java	9 days ago	The return value of "concat" must be used.<br>             |
|  27 | java:S1128   | Punto.java       |     4 | Resource	Date	Description<br>Punto.java	9 days ago	Remove this unused import 'java.util.Random'.<br>             |

> Deben incluirse **todas las disconformidades observadas en el análisis inicial**.

---

## 4. Soluciones adoptadas

### Disconformidad 1,12 y 23 — `java:S1598`

**Localización:** `src/geometria/Direccion.java`, `Punto.java`, `Circulo.java` línea 1
**Responsable:** Diego Sánchez Cano  

**Problema detectado**

Package declaration should match source file directory
SonarQube advierte que la ruta física de varios archivos en el proyecto no coincide con el paquete declarado en su código (`juego.geometria`)


**Solución adoptada**

Se ha renombrado el paquete geometria a juego.geometria.
Han aparecido 2 nuevos errores, el 26 y 27 definidos en la tabla de disconformidades.

---

### Disconformidad 4 — `java:S1598

**Localización:** `src/pruebas/Programa.java´ linea 1  
**Responsable:** Diego Sánchez Cano 

**Problema detectado**

Package declaration should match source file directory
SonarQube advierte que la ruta física de varios archivos en el proyecto no coincide con el paquete declarado en su código (`juego.geometria`)

**Solución adoptada**

Se ha renombrado el paquete pruebas por juego.pruebas.

---

### Disconformidad 3 — `java:SXXXX`

**Localización:** `src/.../Clase.java`, línea XX  
**Responsable:** NOMBRE Y APELLIDOS  

**Problema detectado**

Descripción breve del problema indicado por SonarQube for Eclipse.

**Solución adoptada**

Descripción de la modificación realizada para resolver la disconformidad.

---

## 5. Resumen de las correcciones

|  Nº | Regla Sonar  | Responsable        | Commit    | Resultado |
| --: | ------------ | ------------------ | --------- | --------- |
|   1 | `java:S1598` | Diego Sánchez Cano | `7d0a70d` | Resuelta  |
|   2 | `java:S1598` | Diego Sánchez Cano | `abcdef2` | Resuelta  |
|   3 | `java:SXXXX` | Nombre y apellidos | `abcdef3` | Resuelta  |

---

## 6. Análisis final

Una vez realizadas todas las modificaciones se ha vuelto a ejecutar el análisis del proyecto completo con **SonarQube for Eclipse**.

### Captura final

![Análisis final de SonarQube for Eclipse](imagenes/sonar_final.png)

La captura final permite comprobar que se está analizando el mismo proyecto utilizado en la captura inicial y que ya no quedan disconformidades pendientes.

---

## 7. Proyecto final

La versión final del proyecto Eclipse se encuentra en:

```text
P1/proyecto/P1_JSDDSC/
```

El proyecto incluido en esta carpeta contiene las modificaciones correspondientes a las soluciones documentadas anteriormente y coincide con la versión sobre la que se ha realizado la captura final.

---

## 8. Comprobación de la entrega

- [ ] El nombre del proyecto sigue el formato establecido: `P1_JSDDSC`.
- [ ] Se identifican los dos miembros del grupo.
- [ ] Se incluye la captura del análisis inicial.
- [ ] Se han documentado todas las disconformidades inicialmente detectadas.
- [ ] Cada solución está asociada a un commit identificable en `main`.
- [ ] Se identifica qué miembro del grupo realizó cada corrección.
- [ ] Los dos miembros han participado mediante commits propios.
- [ ] Se incluye la captura del análisis final.
- [ ] La captura final permite comprobar que no quedan disconformidades.
- [ ] Se ha incorporado el proyecto Eclipse final dentro de `P1/proyecto/`.
- [ ] El proyecto final corresponde al código analizado en la captura final.