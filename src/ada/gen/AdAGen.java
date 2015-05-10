/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;

/**
 *
 * @author Marek
 */
public class AdAGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
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
            Item packages[] = new Item[total_packages];
            for (int i = 0; i < total_packages; i++) {
                String[] spl = scanner.nextLine().split(" ");
                double size = Double.parseDouble(spl[0]);
                double value = Double.parseDouble(spl[1]);

                packages[i] = new Item(size, value);
            }
            Backpack bp = new Backpack(total_capacity, packages);
            int population_size = Integer.parseInt(scanner.nextLine());
            Individual population[] = new Individual[population_size];
            for (int i = 0; i < population_size; i++) {
                String[] s = scanner.nextLine().split(" ");
                BitSet bs = new BitSet(total_packages);
                for (int j = 0; j < total_packages; j++) {
                    if (s[j].equals("1")) {
                        bs.set(j);
                    }
                }
                population[i] = new Individual(bs, total_packages);
            }
            Population pop = new Population(population);
            String toPrint = "";
            toPrint += "total capacity: " + total_capacity + "\n";
            toPrint += "total packages: " + total_packages + "\n";
            toPrint += bp.toString();
            toPrint += pop.toString();
            System.out.println(toPrint);

            Fitness fit = new Fitness(bp);
            double fitness = fit.CalcFitness(pop);
            System.out.println("Fitness=" + fitness);
        }
    }
}
