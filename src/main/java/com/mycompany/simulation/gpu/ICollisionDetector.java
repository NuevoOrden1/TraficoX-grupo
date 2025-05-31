/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.simulation.gpu;

/**
 *
 * @author USUARIO
 */
public interface ICollisionDetector {
    int[] detectCollisions(float[] xs, float[] ys, float[] radios, int nAgents);
}
