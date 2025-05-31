/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulation.gpu;

/**
 *
 * @author USUARIO
 */
public class DummyCollisionDetector implements ICollisionDetector {
     @Override
    public int[] detectCollisions(float[] xs, float[] ys, float[] radios, int nAgents) {
        // Implementación temporal (sin colisiones)
        return new int[nAgents];  // simplemente retorna vacío
    }
}
