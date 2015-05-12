/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Marek
 */
public class Algorithm {

    private String inputFile;
    private String outputFile;
    private int generation_count;
    private int individual_count;
    private double mutationRate;
    private int selectionType;
    private int elitism;

    public Algorithm() {
    }

    private void parseArgs(String[] args) {

        if (args.length < 7) {
            System.out.println("Wrong number of arguments. Needed 7, given " + args.length);
            System.exit(1);
        }

        inputFile = args[0];
        outputFile = args[6];

        try {
            generation_count = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[1] + " must be an integer.");
            System.exit(1);
        }

        try {
            individual_count = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[2] + " must be an integer.");
            System.exit(1);
        }

        try {
            mutationRate = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[3] + " must be a double.");
            System.exit(1);
        }

        try {
            selectionType = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[4] + " must be an integer.");
            System.exit(1);
        }

        try {
            elitism = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[5] + " must be an integer.");
            System.exit(1);
        }

        if (elitism > individual_count) {
            System.err.println("Elitism cannot extend the individual count. (" + elitism + " > " + individual_count + ")");
            System.exit(1);
        }

    }

    public void init(String[] args) throws IOException {

        parseArgs(args);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            Scanner scanner = new Scanner(everything);
            double total_capacity = Double.parseDouble(scanner.nextLine());
            int total_packages = Integer.parseInt(scanner.nextLine());
            ArrayList<Item> packages = new ArrayList();
            for (int i = 0; i < total_packages; i++) {
                String[] spl = scanner.nextLine().split(" ");
                double size = Double.parseDouble(spl[0]);
                double value = Double.parseDouble(spl[1]);

                packages.add(new Item(size, value));
            }
            Backpack bp;
            bp = Backpack.getInstance(total_capacity, packages);
            int population_size = Integer.parseInt(scanner.nextLine());
            ArrayList<Individual> population = new ArrayList();
            if (individual_count == 0) {
                // we have to use that from the input file
                individual_count = population_size;
                for (int i = 0; i < population_size; i++) {
                    String[] s = scanner.nextLine().split(" ");
                    BitSet bs = new BitSet(total_packages);
                    for (int j = 0; j < total_packages; j++) {
                        if (s[j].equals("1")) {
                            bs.set(j);
                        }
                    }
                    Individual inew = new Individual(bs, total_packages);
                    inew.setBackpack(bp);
                    population.add(inew);
                }
            } else {
                //we have to generate random population
                Random rand = new Random();
                for (int i = 0; i < individual_count; i++) {
                    BitSet bs = new BitSet(total_packages);
                    for (int j = 0; j < total_packages; j++) {
                        bs.set(j, rand.nextInt(2) == 1 );
                    }
                    Individual inew = new Individual(bs, total_packages);
                    inew.setBackpack(bp);
                    population.add(inew);
                }
            }

            Population pop = new Population(population);
            pop.setBackpack(bp);
            String toPrint = "";
            toPrint += "total capacity: " + total_capacity + "\n";
            toPrint += "total packages: " + total_packages + "\n";
            toPrint += bp.toString();
            toPrint += pop.toString();
            System.out.println(toPrint);

            int generationCount = 100;
            Individual fittest = pop.getFittest();
            GeneticAlgorithm gen = new GeneticAlgorithm(mutationRate, elitism);
            for (int i = 0; i < generationCount; i++) {
                Individual actFit = pop.getFittest();
                System.out.println("Generation: " + (i + 1) + " Fittest: "
                        + actFit.getFitness() + ", " + actFit);
                if (pop.getFittest().getFitness() > fittest.getFitness()) {
                    fittest = pop.getFittest();
                }
                pop = gen.evolvePopulation(pop);
            }

            System.out.println("Fitness of the fittest=" + fittest.getFitness() + ", " + fittest);
        }
    }
}
