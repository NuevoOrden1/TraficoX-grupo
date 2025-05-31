/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulation.processing;

/** @author USUARIO */

import com.mycompany.simulation.model.Agent;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// PACK(): paralelizable
public class AgentManager {
    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    public void packAgents(List<Agent> agents) {
        // TODO: dividir lista en particiones, eliminar agents inactivos en paralelo
    }
    
    public void updateAll(List<Agent> agents, float dt) {
        // TODO: pool.submit(() -> agent.update(dt)) para cada agent
    }
}
