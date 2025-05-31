# Trafico X : Simullador de trafico
1. Propósito y enfoque pedagógico-aplicativo
¿Por qué simular tráfico urbano con agentes?
Optimización real de la movilidad
Permite experimentar con estrategias de control de semáforos, detección de atascos y asignación de rutas en tiempo virtual antes de desplegar cambios en la ciudad.
Grandes empresas (Google con su proyecto Waze, Microsoft en Azure Digital Twins) usan simulaciones de “digital twin” para planificar infraestructuras, reducir emisiones y mejorar flujos.
Agentes = Entidades autónomas
Cada vehículo (o peatón, bicicleta, transporte público) se modela como un “agente” con su propia lógica de decisión: velocidad, destino, reacción a señales.
Permite ilustrar conceptos de programación concurrente (cada agente puede ejecutarse en un hilo) y paralelismo masivo en GPU (detectar colisiones, congestión, cálculo de rutas en paralelo).
Enfoque pedagógico
CPU (Java): gestión de agentes, compactación de datos (pack), construcción de índices de rutas (index) y lógica adaptativa de semáforos.
GPU (C++ + OpenCL/SYCL): aceleración de tareas masivas (detección de colisiones, cálculos de distancia, simulación de redes semafóricas a gran escala).
Resuelves un problema urbano real (congestión, emisiones) y aprendes a integrar JNI, ExecutorService y GPU computing.

2. Compatibilidad con las “ideas para simplificar el proyecto”
 | Idea de simplificación            | ¿Compatible? / Ajuste sugerido                                                                   |
| --------------------------------- | ------------------------------------------------------------------------------------------------ |
| **Grid-based broad-phase**        | ✅ Compatible: usar cuadrícula para prefiltrar agentes antes de colisiones.                       |
| **Kernel GPU único y enfocado**   | ✅ Compatible: empieza con un solo kernel `detectCollisionsBroadPhase`.                           |
| **Arrays primitivos vía JNI**     | ✅ Compatible: interfaz JNI simple que reciba `float[] x, float[] y`.                             |
| **Prototipo JNI aislado primero** | ✅ Imprescindible: desarrolla mini-proyecto “sumar 1” en GPU antes.                               |
| **Datos mínimos para colisión**   | ✅ Compatible: sólo `x, y, radio` al GPU (el resto queda en Java).                                |
| **Paralelismo clave en CPU**      | ✅ Compatible: define claramente `PACK()` y `INDEX()` en Java con `ForkJoin` o `ExecutorService`. |
| **Separación de áreas (1–5)**     | ✅ Compatible: cada área puede empezar en modo serial y luego paralelizar.                        |

  
3. Esqueleto de código (Java + C++/OpenCL)  
  Bosquejo de paquetes, clases y métodos

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
        │   ├── CollisionDetector.cpp      // OpenCL/SYCL setup
        │   └── kernels.cl                 // kernel OpenCL
        └── CMakeLists.txt
