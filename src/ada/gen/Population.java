/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.util.ArrayList;
import java.util.BitSet;

/**
 *
 * @author Marek
 */
class Population {

    ArrayList<Individual> individuals;
    Backpack bp;

    public Population(ArrayList<Individual> individuals) {
        this.individuals = individuals;
    }

    public Population(int count) {
        this.individuals = new ArrayList();
    }

    public Population(Population old) {
        individuals = new ArrayList();
        bp = old.getBackpack();
        for (int i = 0; i < old.size(); i++) {
            BitSet bs = new BitSet(bp.getItems().size());
            Individual inew = new Individual(bs, bp.getItems().size());
            inew.setBackpack(bp);
            individuals.add(inew);
        }
    }

    public void saveIndividual(int idx, Individual in) {
        individuals.set(idx, in);
    }
    public void saveIndividual(Individual in) {
        individuals.add(in);
    }

    public Individual getIndividual(int idx) {
        return individuals.get(idx);
    }

    public int size() {
        return individuals.size();
    }

    public void setBackpack(Backpack bp) {
        this.bp = bp;
    }

    public Backpack getBackpack() {
        return this.bp;
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = individuals;
    }

    public Individual getFittest() {
        Individual ret = individuals.get(0);
        //System.out.println("indiv[0]" + ret);
        for (Individual in : individuals) {
            double retfit = Fitness.getFitness(ret, bp), infit = Fitness.getFitness(in, bp);
            if (retfit < infit) {
                ret = in;
            }
        }
        ret.setBackpack(bp);
        return ret;
    }

    public Population getElite(int count) {
        Population ret = new Population(count);
        //System.out.println("indiv[0]" + ret);
        for (int i = 0; i < count ; i++) {
           Individual actualBest = getFittest();
           ret.saveIndividual(i, actualBest);
           individuals.remove(actualBest);
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
