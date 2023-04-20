# API SpringBoot para Gestión de Turnos Rotativos

Esta API permite gestionar el registro de empleados y sus jornadas laborales dependiendo de su concepto mediante distintos endpoints detallados a continuación:

## Crear un nuevo empleado

Crea un nuevo empleado. Utilizando el verbo http POST se envía un request a [http://localhost:8080/empleado](http://localhost:8080/empleado). El body del request debe ser de la siguiente manera y todos los campos son requeridos:

```
{
  "nroDocumentro": 30415654,
  "nombre": "German",
  "apellido": "Zotella",
  "email": "gzotella@gmail.com",
  "fechaNacimiento": "1998-08-06",
  "fechaIngreso": "2019-06-04"
}

```

El body del response será de la siguiente manera:

```
{
  "id": 1,
  "nroDocumentro": 30415654,
  "nombre": "German",
  "apellido": "Zotella",
  "email": "gzotella@gmail.com",
  "fechaNacimiento": "1998-08-06",
  "fechaIngreso": "2019-01-01",
  "fechaCreacion": "2023-02-22"
}

```

Cuando se invoca al servicio de crear empleado correctamente, se retorna el status code 201 y el response definido anteriormente.

Cuando se invoca al servicio de crear empleado:

- Si la edad del empleado es menor a 18 años, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: “La edad del empleado no puede ser menor a 18 años.”
- Si existe un empleado registrado con el mismo número de documento brindado, el sistema muestra:
    - Status Code: 409 (Conflict)
    - Mensaje: “Ya existe un empleado con el documento ingresado.”
- Si existe un empleado registrado con el mismo email brindado, el sistema muestra:
    - Status Code: 409 (Conflict)
    - Mensaje: “Ya existe un empleado con el email ingresado.”
- Si la fecha de ingreso es posterior al día de la fecha, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: “La fecha de ingreso no puede ser posterior al día de la fecha.”
- Si la fecha de nacimiento es posterior al día de la fecha, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: “La fecha de nacimiento no puede ser posterior al día de la fecha.”
- Si el email no cumple con el formato estándar de mail:
    - Status Code: 400 (Bad Request)
    - Mensaje: “El email ingresado no es correcto.”
- Si el nombre y/o apellido ingresado poseen números o caracteres especiales, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: “Solo se permiten letras en el campo ‘nombre_campo’ ”. Ejemplo: Solo se permiten letras en el campo ‘nombre’.
- Si algún campo obligatorio no es ingresado, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: “ ‘nombre_campo’ es obligatorio. ”. Ejemplo: ‘nroDocumento’ es obligatorio.

## Obtener la lista de todos los empleados

Obtiene la lista de empleados. Utilizando el verbo http GET se envía un request a [http://localhost:8080/empleado](http://localhost:8080/empleado). No se envía body en el request. El body del response será de la siguiente manera:

```
[
  {
    "id": 1,
    "nroDocumentro": 30415654,
    "nombre": "German",
    "apellido": "Zotella",
    "email": "gzotella@gmail.com",
    "fechaNacimiento": "1998-08-06",
    "fechaIngreso": "2019-06-04",
    "fechaCreacion": "2023-02-22"
  },
  {
    "id": 2,
    "nroDocumentro": 3565454321,
    "nombre": "Jorge",
    "apellido": "Rolon",
    "email": "jrolon@gmail.com",
    "fechaNacimiento": "1998-08-06",
    "fechaIngreso": "2019-06-04",
    "fechaCreacion": "2023-02-22"
  }
]

```

Cuando se invoca la operación de obtener empleados correctamente, se retorna el status code 200 y el response definido anteriormente. Si la cantidad de empleados registrados es cero, se retorna status code 200 y la lista vacía.

## Obtener información del empleado por ID

Recupera las información de un empleado proporcionando un ID. Utilizando el verbo http GET se envía un request a [http://localhost:8080/empleado/{empleadoId}](http://localhost:8080/empleado/%7BempleadoId%7D). No se envía body en el request. El body del response será de la siguiente manera:

```
{
  "id": 1,
  "nroDocumentro": 30415654,
  "nombre": "German",
  "apellido": "Zotella",
  "email": "gzotella@gmail.com",
  "fechaNacimiento": "1998-08-06",
  "fechaIngreso": "2019-06-04",
  "fechaCreacion": "2023-02-22"
}

```

Cuando se invoca la operación de obtener empleados correctamente, se retorna el Status code 200 y el response definido anteriormente. En caso de que el id provisto en la ruta variable, no coincide con el de un empleado existente, el sistema muestra:

- Status Code: 404 (Not Found)
- Mensaje: “No se encontró el empleado con Id: {idEmpleado}”. Ejemplo: “No se encontró el empleado con Id: 5”.

## Actualizar empleado por ID

Actualiza la información existente de un empleado. Utilizando el verbo http PUT se envía un request a [http://localhost:8080/empleado/{empleadoId}](http://localhost:8080/empleado/%7BempleadoId%7D). El body del request y el response tienen la misma estructura que el método para crear un nuevo Empleado.

Cuando se invoca al servicio de crear empleado correctamente, se retorna el status code 200 y el response definido anteriormente. Cuando se invoca al servicio de actualizar empleado se respetan los mismos mensajes de error y status que al crear un empleado ya además:

- Si el id provisto en la ruta variable, no coincide con el de un empleado existente, el sistema muestra:
    - Status Code: 404 (Not Found)
    - Mensaje: “No se encontró el empleado con Id: {idEmpleado}”. Ejemplo: “No se encontró el empleado con Id: 5”.

## Eliminar un empleado por ID

Elimina un empleado proporcionando un ID. Utilizando el verbo http DELETE se envía un request a [http://localhost:8080/empleado/{empleadoId}](http://localhost:8080/empleado/%7BempleadoId%7D). No se envía body en el requisito y tampoco se recibe un body en el response. Cuando se invoca la operación de eliminar empleados correctamente, el sistema muestra:

- Status Code: 204 (NO CONTENT)

Cuando se invoca la operación de eliminar empleado, en caso de que el id provisto en la ruta variable, no coincide con el de un empleado existente, el sistema muestra:

- Status Code: 404 (Not Found)
- Mensaje: “No se encontró el empleado con Id: {idEmpleado}”. Ejemplo: “No se encontró el empleado con Id: 5”.

## Obtener conceptos

Permite obtener la lista de todos los conceptos laborales. Utilizando el verbo http GET se envía un request a [http://localhost:8080/concepto](http://localhost:8080/concepto). No se envía body en el request y la respuesta tiene la siguiente estructura:

```
[
  {
    "id": 1,
    "nombre": "Turno Normal",
    "hsMinimo": 6,
    "hsMaximo": 8,
    "laborable": true
  },
  {
    "id": 2,
    "nombre": "Turno Extra",
    "hsMinimo": 2,
    "hsMaximo": 6,
    "laborable": true
  },
  {
    "id": 3,
    "nombre": "Día Libre",
    "hsMinimo": null,
    "hsMaximo": null,
    "laborable": true
  }
]

```

Cuando el valor de ‘hsTrabajadas’ es null, no se incluyen en el response.

## Obtener jornadas laborales

Permite obtener la lista de todos las jornadas laborales o de aquellas que coincidan con los parámetros brindados.

- /jornada -> retornará la lista de todas las jornadas.
- /jornada?nroDocumento=32135640 -> retorna las jornadas del empleado que coincide con el documento.
- /jornada?fecha=January 1, 2023 -> retorna las jornadas registradas en esa fecha.
- /jornada?nroDocumento=32135640&fecha=January 1, 2023 -> retorna las jornadas del empleado que coincide con el documento y la fecha ingresada.

En caso de que uno o ambos de los Query Params no coincidan con una o más jornadas laborales existentes se retornará una lista vacía.

Utilizando el verbo http GET se envía un request a:

- [http://localhost:8080/jornada](http://localhost:8080/jornada)
- [http://localhost:8080/jornada?nroDocumento=5435324532&fecha=2023-01-01](http://localhost:8080/jornada?nroDocumento=5435324532&fecha=2023-01-01)
- [http://localhost:8080/jornada?nroDocumento=5435324532](http://localhost:8080/jornada?nroDocumento=5435324532)
- [http://localhost:8080/jornada?fecha=2023-01-01](http://localhost:8080/jornada?fecha=2023-01-01)

Las respuestas respetan la siguiente estructura:

```
[
  {
    "nroDocumento": 32135640,
    "nombreCompleto": "German Gonzalez",
    "fecha": "2023-01-01",
    "concepto": "Turno Normal",
    "hsTrabajadas": 6
  },
  {
    "nroDocumento": 32135640,
    "nombreCompleto": "German Gonzalez",
    "fecha": "2023-01-01",
    "concepto": "Turno Extra",
    "hsTrabajadas": 4
  },
  {
    "nroDocumento": 32135640,
    "nombreCompleto": "German Gonzalez",
    "fecha": "2023-01-02",
    "concepto": "Turno Normal",
    "hsTrabajadas": 8
  },
  {
    "nroDocumento": 30155634,
    "nombreCompleto": "Gonzalo Rodriguez",
    "fecha": "2023-01-01",
    "concepto": "Día Libre",
    "hsTrabajadas": null
  },
  {
    "nroDocumento": 28648915,
    "nombreCompleto": "Juan Perez",
    "fecha": "2023-01-02",
    "concepto": "Turno Normal",
    "hsTrabajadas": 8
  },
  {
    "nroDocumento": 22654781,
    "nombreCompleto": "Melina Perez",
    "fecha": "2023-01-05",
    "concepto": "Turno Extra",
    "hsTrabajadas": 5
  }
]

```

Cuando el valor de ‘hsTrabajadas’ es null, no se incluyen en el response.

## Creación de Jornada Laboral

Permite crear una nueva jornada laboral. Utilizando el verbo http POST se envía un request a [http://localhost:8080/jornada](http://localhost:8080/jornada). El body debe cumplir con esta estructura donde hsTrabajadas solamente no es requerido para el concepto "Día Libre":

```
{
  "idEmpleado": 1,
  "idConcepto": 1,
  "fecha": "2023-01-01",
  "hsTrabajadas": 8
}

```

La respuesta cumple con la siguiente estructura:

```
{
  "id": 1,
  "nroDocumento": 32135640,
  "nombreCompleto": "German Gonzalez",
  "fecha": "2023-01-01",
  "concepto": "Turno Normal",
  "hsTrabajadas": 6
}

```

Cuando se invoca al servicio de crear empleado correctamente, se retorna el status code 201 y el response definido anteriormente.

Cuando se invoca al servicio de crear empleado:

- Si algún campo obligatorio no es ingresado, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: “ ‘nombre_campo’ es obligatorio. ”. Ejemplo: ‘idEmpleado’ es obligatirio.
- Si NO existe el empleado ingresado, el sistema muestra:
    - Status Code: 404 (Not Found)
    - Mensaje: “No existe el empleado ingresado.”
- Si NO existe el concepto ingresado, el sistema muestra:
    - Status Code: 404 (Not Found)
    - Mensaje: “No existe el concepto ingresado.”
- Si el concepto ingresado corresponde a ‘Turno Normal’ o ‘Turno Extra’ y no se ingresa el campo ‘hsTrabajadas’, el sistema muestra:
    - Status Code: 400 (Bad Request)
    - Mensaje: ‘hsTrabajadas’ es obligatorio para el concepto ingresado.
- Si el concepto ingresado corresponde a ‘Día Libre’ y se ingresa el campo ‘hsTrabajadas’, el sistema muestra:
- Status Code: 400 (Bad Request)
- Mensaje: El concepto ingresado no requiere el ingreso de ‘hsTrabajadas’.

Un empleado puede cargar una jornada de turno normal o extra, siempre y cuando las horas trabajadas estén dentro del rango de horas mínimas y máximas del concepto elegido, en caso de no cumplirse:
• Mensaje: “El rango de horas que se puede cargar para este concepto es de X - Y”
Donde X = horas mínimas e Y = horas máximas.

Un empleado con “Día libre” en una fecha no puede cargar otra jornada en esa fecha.
• Mensaje: “El empleado ingresado cuenta con un día libre en esa fecha.”

No es posible cargar para un empleado en una misma fecha, dos jornadas con el mismo concepto.
• Mensaje: “El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.”

Un empleado puede cargar una jornada de turno normal y una jornada de turno extra en un mismo día, siempre y cuando la combinación no supere las 12 horas. En caso de no cumplirse:
• Mensaje: “El empleado no puede cargar más de 12 horas trabajadas en un día.”

Un empleado no puede cargar un día libre en un fecha si tiene otros turnos cargados.
• Mensaje: “El empleado no puede cargar Dia Libre si cargo un turno previamente para la fecha ingresada.”

Un empleado no puede cargar más de 48 horas semanales.
• Mensaje: “El empleado ingresado supera las 48 horas semanales.”

Un empleado no puede cargar más de 3 turnos extra en una semana.
• Mensaje: “El empleado ingresado ya cuenta con 3 turnos extra esta semana.”

Un empleado no puede cargar más de 5 turnos normales en una semana.
• Mensaje: “El empleado ingresado ya cuenta con 5 turnos normales esta semana.”

Un empleado no puede tener más de 2 días libres por semana.
• Mensaje: “El empleado no cuenta con más días libres esta semana.”
