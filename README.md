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
└── cpp/
    └── CollisionDetector/
        ├── include/
        │   └── CollisionDetector.h
        ├── src/
        │   ├── CollisionDetector.cpp     // OpenCL/SYCL setup
        │   └── kernels.cl                // kernel OpenCL
        └── CMakeLists.txt
```


