# Sobre Tráfico X
## Objetivo del Proyecto

Realizar una **macro-simulación de tráfico urbano** enfocada en evaluar y demostrar cómo la programación paralela (usando GPU o múltiples CPUs simulados) mejora el rendimiento respecto a la ejecución secuencial. El resultado del proyecto será una herramienta que simule millones de agentes (vehículos) en tiempo real o cerca del tiempo real.

---

## Tipo de Simulación Elegido

**Macro-simulación**:

* Modela el flujo agregado del tráfico: densidad vehicular, velocidad promedio, congestiones generales.
* Permite simular grandes cantidades de agentes, desde **miles hasta millones**.
* Menos detalle individual, pero muy eficiente para evaluar rendimiento del paralelismo.

Justificación:

* La macro-simulación es ideal para demostrar claramente los beneficios de paralelizar procesos debido a su escala masiva y alta demanda computacional.
* Es más adecuado para las restricciones hardware disponibles (GPU AMD RX6600).

---

## Requerimientos y Características Específicas

* Simulación que pueda escalar hasta **miles o millones de agentes**.
* Capacidad de ejecución en GPU o múltiples CPUs simuladas (aprox. 1000 núcleos virtuales).
* No es necesaria una precisión extremadamente alta en cada interacción individual, sino mostrar claramente la ganancia de rendimiento.
* ¿Què se puede paralelizar del proyecto? ELegì las colisiones como parte del diseño inicial. Esto es modificable. (Ponernos de acuerdo)
---

## Tecnologías Seleccionadas

### Herramientas de programación paralela

* **OpenCL**:

  * Seleccionada por su compatibilidad con GPUs AMD (como la RX6600 disponible) y también Nvidia e Intel.
  * Excelente para ilustrar programación heterogénea (uso simultáneo de CPU y GPU).
  * Permite explotar eficazmente hardware disponible.

### Lenguajes y herramientas complementarias

* **Java**:

  * Para lógica general y orquestación de simulación.
* **JNI (Java Native Interface)**:

  * Para interactuar entre Java y código OpenCL/C++.
* **C/C++**:

  * Para kernels OpenCL altamente optimizados.

---

## Estructura General del Proyecto

La estructura básica del proyecto debe contener:

* **Data Loader** (`MapLoader.java`)

  * Parseo eficiente de mapas OSM para generación inicial de agentes.
* **Modelo de Agentes** (`Agent.java`)

  * Simplificado para almacenar posiciones y velocidades agregadas.
* **Procesamiento Paralelo**

  * Código OpenCL para detectar colisiones y movimientos agregados.
* **Interfaz GPU (JNI)** (`CollisionDetectorJNI.java`)

  * Vincula el código Java con código paralelo en GPU.
* **Simulador Principal** (`MainSimulator.java`)

  * Administra iteraciones, actualizaciones y recolección de estadísticas.

---

## Cómo Contribuir al Proyecto

Cada colaborador debe:

1. **Entender claramente** la estructura propuesta.
2. **Elegir áreas específicas** de contribución (ver más abajo).
3. **Usar GitHub** para manejar contribuciones y versiones (branches, forks y pull requests).
4. **Realizar pruebas continuas** en paralelo y secuencial para evaluar claramente la ventaja de rendimiento del paralelismo.

Áreas específicas para contribuir:

* **Implementación del Parser OSM**

  * Mejorar rendimiento del parser para grandes datasets.
* **Optimización Kernels OpenCL**

  * Implementar y optimizar algoritmos paralelos eficientes para movimiento agregado y detección de colisiones.
* **Pruebas y benchmarks**

  * Desarrollar métodos claros para medir desempeño (comparación GPU vs CPU secuencial).
* **Visualización y reportes**

  * Opcionalmente, desarrollar visualizaciones que muestren resultados y mejoras del rendimiento.

---

## Recursos de Hardware

¿Què harware tienen? 
 alguien tiene GPU NVDIA ?         
 Tengo una GPU AMD RX6600 

* **Simulación Virtualizada de múltiples núcleos (CPU)**

  * Usar técnicas de virtualización o simulación de múltiples núcleos si se desea demostrar un entorno con 1000 CPUs virtuales.
* **GPU AMD RX6600**

  * Suficiente para demostrar mejoras significativas usando OpenCL.
* **Opciones Gratuitas Online**

  * Considerar plataformas gratuitas en la nube como Google Colab o Kaggle para pruebas adicionales si se requiere hardware más robusto.

---

## Indicadores Clave para Evaluar Resultados

* **Tiempos de ejecución**: Comparar secuencial vs paralelo (GPU).
* **Escalabilidad**: Medir cómo se comporta el rendimiento conforme aumentan los agentes.
* **Uso de recursos**: Evaluar utilización de memoria y núcleos.

---

## Conclusión y Horizonte del Proyecto

El proyecto apunta claramente a mostrar beneficios de la programación paralela en simulaciones de gran escala. Es fundamental la colaboración efectiva, enfoque en el rendimiento, y dominio claro de las herramientas seleccionadas (OpenCL, Java, JNI).

## Videos ùtiles  
Computación Paralela con Java y OpenCL
https://www.youtube.com/watch?v=oRCARo_pAlo&ab_channel=ByteCode

## Escribir aqui las dudas o sugerencias ...
