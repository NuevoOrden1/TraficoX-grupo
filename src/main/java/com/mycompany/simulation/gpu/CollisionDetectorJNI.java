/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulation.gpu;

/** @author A */
// Buscar una interfaz como JNI entre toras para usar C++ (Open CL / CYCL)
public class CollisionDetectorJNI implements ICollisionDetector {
    static { System.loadLibrary("CollisionDetector"); }
      // recordatorio: proveer CollisionDetector.dll (W_11) / .so (Ubuntu) en el futuro
    
    // recibe arrays planos: posiciones x[], y[], radios[]
    // devuelve matriz NxN booleana o lista de pares
    public native int[] detectCollisions(float[] xs, float[] ys, float[] radios, int nAgents);
}
