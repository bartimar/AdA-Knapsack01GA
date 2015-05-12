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
public class GeneticAlgorithm {


    /* GA parameters */
    private static final double uniformRate = 0.1;
    private double mutationRate;
    private static final int tournamentSize = 2;
    private int elitism;

    public GeneticAlgorithm(double mutationRate, int elitism) {
        this.mutationRate = mutationRate;
        this.elitism = elitism;
    }

    /* Public methods */
    // Evolve a population
    public Population evolvePopulation(Population pop) {

        Population newPopulation = new Population(pop);

        if (elitism > 0) {
            Population elites = pop.getElite(elitism);
            // Keep our best individual
            for (Individual elite : elites.individuals) {
                newPopulation.saveIndividual(elite);
            }
        }

        // Crossover population
        // Loop over the population size and create new individuals with crossover
        for (int i = elitism; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitism; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual(indiv1.length());
        // Loop through genes
        for (int i = 0; i < indiv1.length(); i++) {
            // Crossover
            if (Math.random() <= uniformRate) {
                newSol.setItem(i, indiv1.getItem(i));
            } else {
                newSol.setItem(i, indiv2.getItem(i));
            }
        }
        //System.out.println("New gene: "+ newSol.toString());
        return newSol;
    }

    // Mutate an individual
    private void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.length(); i++) {
            if (Math.random() <= mutationRate) {
                // Flip random gene
                indiv.getGene().flip(i);
            }
        }
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(pop);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            //System.out.println("Tournament("+pop.size() +"): save i, rand - "+  i + "," + randomId);
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }

}
