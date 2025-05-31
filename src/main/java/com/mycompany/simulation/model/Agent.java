/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulation.model;
/** @author USUARIO */

public abstract class Agent {
    protected int id;
    protected float x, y;
    protected float speed;
    // … otros atributos (destino, estado)
    
    public Agent(int id, float x, float y) {
        this.id = id; this.x = x; this.y = y;
    }
    public abstract void update(float deltaTime);
    
    // getters/setters
    public float getX() {
    return x;
    }

    public float getY() {
    return y;
    }

// Si tus agentes tienen un atributo 'radius' en alguna subclase, 
// deberías agregar en Agent:
    public float getRadius() {
    // Si el radioAestá solo en la subclase, puedes hacer que este método sea abstracto:
    // return 0f; // valor por defecto o
    throw new UnsupportedOperationException("Not implemented in base class");
    }
       
}

