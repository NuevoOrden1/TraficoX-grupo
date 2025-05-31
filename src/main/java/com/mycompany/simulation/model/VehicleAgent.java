/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulation.model;

/** @author a */
public class VehicleAgent extends Agent {
    private float heading;
     private float radius = 1.0f; // Valor por defecto (modificar mas adelante)
    // … estado del vehículo
    
    public VehicleAgent(int id, float x, float y) {
        super(id, x, y);
    }
    @Override
    public void update(float deltaTime) {
        // lógica de movimiento
    }
    
    @Override
    public float getRadius() {
        return radius;
    }
}
