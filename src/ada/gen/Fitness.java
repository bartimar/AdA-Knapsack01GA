/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

/**
 *
 * @author Marek
 */
public class Fitness {

    private Backpack bp;

    public Fitness(Backpack bp) {
        this.bp = bp;
    }

    public double getFitness(Individual gene) {
        double fitness = 0, total_size = 0;
        for (int i = 0; i < gene.length(); i++) {
            if (gene.getItem(i)) {
                if (total_size + bp.getSize(i) > bp.getCapacity()) {
                    System.out.println("Backpack is full, size=" + total_size + ", value=" + fitness);
                    gene.regenerate();
                    System.out.println("New gen=" + gene.toString());
                }
                fitness += bp.getValue(i);
                total_size += bp.getSize(i);
            }
        }

        return fitness;
    }

    public double CalcFitness(Population pop) {
        double fitness = 0;
        for (Individual gene : pop.getIndividuals()) {
            fitness += getFitness(gene);
        }
        fitness /= pop.size();
        return fitness;
    }
}