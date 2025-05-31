    package com.mycompany.simulation;
    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    /** @author A  */

    import com.mycompany.simulation.data.MapLoader;
    import com.mycompany.simulation.processing.*;
    import com.mycompany.simulation.gpu.CollisionDetectorJNI;
import com.mycompany.simulation.gpu.DummyCollisionDetector;
import com.mycompany.simulation.gpu.ICollisionDetector;
    import com.mycompany.simulation.model.Agent;
    import java.util.List;

    public class MainSimulator {
    public static void main(String[] args) {
        List<Agent> agents = MapLoader.loadAgentsFromOSM("map.osm");
        IndexBuilder indexer = new IndexBuilder();
        AgentManager manager = new AgentManager();

        // Cambia aquí según tu necesidad actual
        //ICollisionDetector gpu = new CollisionDetectorJNI(); // usar en producción/final
        ICollisionDetector gpu = new DummyCollisionDetector(); // usar ahora para desarrollar

        indexer.buildSpatialIndex(agents);

        boolean exitCondition = false;
        float deltaTime = 0.1f;
        
        while (!exitCondition) {
            manager.updateAll(agents, deltaTime);
            int n = agents.size();
            float[] xs = toArray(agents, "x");
            float[] ys = toArray(agents, "y");
            float[] rs = toArray(agents, "radius");

            int[] collisions = gpu.detectCollisions(xs, ys, rs, n);
            // procesar colisiones aquí...

            manager.packAgents(agents);

            // TODO: definir la lógica para 'exitCondition'
            exitCondition = true; // provisional para evitar bucle infinito
        }
    }

    private static float[] toArray(List<Agent> agents, String field) {
        float[] arr = new float[agents.size()];
        for (int i = 0; i < agents.size(); i++) {
            Agent a = agents.get(i);
            switch (field) {
                case "x": arr[i] = a.getX(); break;
                case "y": arr[i] = a.getY(); break;
                case "radius": arr[i] = a.getRadius(); break;
                default: throw new IllegalArgumentException("Campo inválido: " + field);
            }
        }
        return arr;
    }
}

