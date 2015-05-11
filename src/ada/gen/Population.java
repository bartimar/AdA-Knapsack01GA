/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.util.BitSet;

/**
 *
 * @author Marek
 */
class Population {

    Individual[] individuals;
    Backpack bp;

    public Population(Individual[] individuals) {
        this.individuals = individuals;
    }

    public Population(Population old) {
        individuals = new Individual[old.size()];
        bp = old.getBackpack();
        for (int i = 0; i < old.size(); i++) {
            BitSet bs = new BitSet(bp.getItems().length);
            individuals[i] = new Individual(bs, bp.getItems().length);
            individuals[i].setBackpack(bp);
        }
    }
    
    public void saveIndividual(int idx, Individual in) {
        individuals[idx] = in;
    }
    
    public Individual getIndividual(int idx) {
        return individuals[idx];
    }

    public int size() {
        return individuals.length;
    }

    public void setBackpack(Backpack bp) {
        this.bp = bp;
    }

    public Backpack getBackpack() {
        return this.bp;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public void setIndividuals(Individual[] individuals) {
        this.individuals = individuals;
    }

    public Individual getFittest() {
        Individual ret = individuals[0];
        System.out.println("indiv[0]" + ret);
        for (Individual in : individuals) {
            if (Fitness.getFitness(in, bp) > Fitness.getFitness(ret, bp)) {
                ret = in;
            }
        }
        ret.setBackpack(bp);
        return ret;
    }

    @Override
    public String toString() {
        int i = 0;
        String toPrint = "";
        for (Individual el : individuals) {
            toPrint += "Human " + ++i + ": ";
            toPrint += el.toString();
            toPrint += "\n";
        }
        return "Population{" + "size=" + size() + "\n" + toPrint + '}';
    }

}
