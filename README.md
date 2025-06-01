# Trafico X: Simulador de Tráfico

## 1. Propósito y Enfoque Pedagógico-Aplicativo

### ¿Por qué simular tráfico urbano con agentes?

La simulación de tráfico urbano con agentes permite una **optimización real de la movilidad**. Permite experimentar con estrategias de control de semáforos, detección de atascos y asignación de rutas en un entorno virtual antes de desplegar cambios en el mundo real. Grandes empresas (como Google con Waze y Microsoft en Azure Digital Twins) utilizan simulaciones de "digital twin" para planificar infraestructuras, reducir emisiones y mejorar los flujos.

**Agentes = Entidades autónomas**
Cada vehículo (o peatón, bicicleta, transporte público) se modela como un "agente" con su propia lógica de decisión: velocidad, destino, reacción a señales. Esto permite ilustrar conceptos de **programación concurrente** (cada agente puede ejecutarse en un hilo) y **paralelismo masivo en GPU** (detección de colisiones, congestión, cálculo de rutas en paralelo).

### Enfoque Pedagógico

Este proyecto te ayudará a resolver un problema urbano real (congestión, emisiones) y te enseñará a integrar tecnologías clave:

* **CPU (Java):** Gestión de agentes, compactación de datos (`pack`), construcción de índices de rutas (`index`) y lógica adaptativa de semáforos.
* **GPU (C++ + OpenCL/SYCL):** Aceleración de tareas masivas como la detección de colisiones, cálculos de distancia y simulación de redes semafóricas a gran escala.

Aprenderás a integrar **JNI (Java Native Interface)**, **ExecutorService** y **GPU computing**.

---

## 2. Compatibilidad con las "Ideas para Simplificar el Proyecto"

Aquí se detalla la compatibilidad de las ideas de simplificación propuestas con la arquitectura del proyecto:

| Idea de Simplificación         | ¿Compatible? / Ajuste Sugerido                                    |
| :----------------------------- | :---------------------------------------------------------------- |
| **Grid-based broad-phase** | ✅ Compatible: Usar cuadrícula para prefiltrar agentes antes de colisiones. |
| **Kernel GPU único y enfocado**| ✅ Compatible: Empieza con un solo kernel `detectCollisionsBroadPhase`. |
| **Arrays primitivos vía JNI** | ✅ Compatible: Interfaz JNI simple que reciba `float[] x, float[] y`. |
| **Prototipo JNI aislado primero** | ✅ Imprescindible: Desarrolla un mini-proyecto “sumar 1” en GPU antes. |
| **Datos mínimos para colisión**| ✅ Compatible: Sólo `x, y, radio` al GPU (el resto queda en Java). |
| **Paralelismo clave en CPU** | ✅ Compatible: Define claramente `PACK()` y `INDEX()` en Java con `ForkJoin` o `ExecutorService`. |
| **Separación de áreas (1–5)** | ✅ Compatible: Cada área puede empezar en modo serial y luego paralelizar. |

---

## 3. Esqueleto de Código (Java + C++/OpenCL)

A continuación, se presenta un bosquejo de la estructura de paquetes, clases y métodos del proyecto:

```
src/
├── java/
│   └── com/tuempresa/simulation/
│       ├── data/
│       │   └── MapLoader.java
│       ├── model/
│       │   ├── Agent.java
│       │   ├── VehicleAgent.java
│       │   └── TrafficLight.java
│       ├── processing/
│       │   ├── IndexBuilder.java         // INDEX(): paralelizable
│       │   ├── AgentManager.java         // PACK(): paralelizable
│       │   └── TrafficController.java    // semáforos adaptativos
│       ├── gpu/
│       │   └── CollisionDetectorJNI.java // interfaz JNI
│       └── MainSimulator.java
└── cpp/ (Falta implementar/hacer Compatible con NetBeans)
    └── CollisionDetector/
        ├── include/
        │   └── CollisionDetector.h
        ├── src/
        │   ├── CollisionDetector.cpp     // OpenCL/SYCL setup
        │   └── kernels.cl                // kernel OpenCL
        └── CMakeLists.txt
```


## **Guía para el grupo: Estructura y Estado Actual del Proyecto `TraficoX`**

### **Visión General**

El proyecto `TraficoX` es una simulación modular de tráfico urbano que tiene como objetivo modelar agentes móviles (por ejemplo, vehículos) e infraestructuras (por ejemplo, semáforos) sobre mapas reales o sintéticos. El sistema está preparado para integrar lógica de colisiones (potencialmente acelerada por GPU), paralelización de procesos y una estructura extensible que permita añadir fácilmente nuevas funcionalidades y tipos de agentes.

---

### **Estructura General de Paquetes y Clases**

#### **1. Paquete `com.mycompany.simulation`**

* **MainSimulator**
  Clase principal que orquesta la simulación. Carga los agentes desde un archivo OSM, inicializa los módulos principales (gestión de agentes, indexado espacial y detección de colisiones) y ejecuta el bucle principal de simulación.

  * **Advertencia:** Actualmente, el bucle se detiene tras una iteración; es necesario definir una condición real para la finalización de la simulación.
  * **Sugerencia:** Revisar cómo se procesan las colisiones y cómo debe evolucionar el estado de los agentes.

---

#### **2. Paquete `com.mycompany.simulation.model`**

* **Agent (abstracta)**
  Clase base para todos los agentes móviles. Define posición, velocidad y métodos básicos.

  * **Advertencia:** El método `getRadius()` es abstracto o puede lanzar excepción si no está implementado en la subclase. **Asegurarse de implementarlo siempre** en cualquier subclase.
* **VehicleAgent**
  Ejemplo de agente móvil (vehículo). Implementa `getRadius()` y `update()`, pero requiere completar la lógica de movimiento y atributos adicionales.
* **TrafficLight**
  Clase vacía por ahora; pensada para modelar semáforos u otros elementos fijos.

  * **Sugerencia:** Agregar atributos y lógica relevante (estado del semáforo, temporización, etc.).

---

#### **3. Paquete `com.mycompany.simulation.data`**

* **MapLoader**
  Encargada de cargar los agentes iniciales desde un archivo OSM (OpenStreetMap).

  * **Advertencia:** Actualmente, solo retorna una lista vacía.
  * **Pendiente:** Implementar el parseo real del archivo OSM y la generación de objetos `Agent`.

---

#### **4. Paquete `com.mycompany.simulation.processing`**

* **AgentManager**
  Maneja la actualización y gestión paralela de los agentes.

  * **Pendiente:** Implementar el uso real de `ExecutorService` para actualizar y filtrar agentes en paralelo.
* **IndexBuilder**
  Encargada de construir índices espaciales para optimizar búsquedas y detección de colisiones.

  * **Pendiente:** Implementar el algoritmo de indexado (cuadrícula, kd-tree, etc.) y considerar paralelización.
* **TrafficController**
  Clase vacía por ahora, prevista para gestionar semáforos o la lógica central de control de tráfico.

---

#### **5. Paquete `com.mycompany.simulation.gpu`**

* **ICollisionDetector (interfaz)**
  Define el método `detectCollisions()` que toma arrays planos de posición y radios, y retorna un array con los pares de colisiones detectados.
* **DummyCollisionDetector**
  Implementación de prueba (mock) que siempre retorna un arreglo vacío, útil para el desarrollo sin la necesidad de GPU.
* **CollisionDetectorJNI**
  Implementación pensada para usar una librería nativa (C++/OpenCL/CUDA).

  * **Advertencia:** Solo funcionará si la librería nativa está correctamente instalada y accesible en el sistema.

---

### **Aspectos Pendientes e Importantes**

* **MapLoader debe implementarse:**
  Por ahora no se cargan agentes reales desde el archivo OSM.

  * *Pendiente:* Implementar parser OSM y crear instancias de `Agent` según la información leída.
* **Métodos vacíos:**
  Muchas clases (`AgentManager`, `IndexBuilder`, `TrafficController`) solo contienen el esqueleto.

  * *Pendiente:* Completar la lógica de actualización de agentes, gestión paralela e indexado espacial.
* **Implementar subclases de Agent:**
  Si se agregan otros tipos de agentes, es obligatorio implementar el método `getRadius()` en cada uno para evitar errores en tiempo de ejecución.
* **Procesamiento de colisiones:**
  Actualmente solo se llama a un mock de detección de colisiones. Es necesario definir cómo se procesan e integran los resultados en el ciclo de simulación.
* **Estructura de archivos y paquetes:**
  Mantener la convención de paquetes y organizar nuevas clases correctamente.
* **Condición de fin del ciclo:**
  Definir claramente cuándo debe terminar la simulación (criterio de parada).
* **Paralelismo:**
  Revisar el uso del `ExecutorService` para asegurarse de que no haya fugas de recursos y que la paralelización se realice correctamente.

---

### **Sugerencias Generales y Buenas Prácticas**

1. **No dejar métodos sin implementar**: Usar siempre excepciones claras (`UnsupportedOperationException`) o comentarios `// TODO` bien visibles.
2. **Documentar las clases nuevas**: Usar javadoc y comentarios explicativos en cada clase/método importante.
3. **Probar con listas de agentes no vacías**: Antes de hacer cambios mayores, cargar al menos algunos agentes de prueba en `MapLoader` para facilitar el testing.
4. **No modificar la interfaz pública de las clases clave sin consenso**: Discutir cambios estructurales en grupo.
5. **Avisar si se añaden dependencias externas** (librerías nativas, frameworks, etc.), y documentar la forma de compilarlas o integrarlas.

---

### **Advertencias**

* **No llamar a `getRadius()` de un Agent base directamente**: Asegúrate de que cada subclase lo implemente.
* **No confiar en la carga de agentes hasta que MapLoader esté implementado**.
* **El uso de la versión JNI requiere librerías nativas**. En sistemas Windows o Linux, hay que asegurarse de que el `.dll` o `.so` esté en el `PATH` o `LD_LIBRARY_PATH`.

---

## ⚠️ Consejos importantes para Huang, Melissa, Ariana, Ivett

* **Siempre hacer `git pull` antes de comenzar a trabajar**.
* Evitar trabajar todos sobre la misma rama `master`, preferir ramas separadas.
* Usar mensajes de commit claros y descriptivos.
* Si usan ramas: `git checkout -b nombre-de-rama`
* En caso de conflictos: resolver con calma, revisar con `git status` y `git diff`.





