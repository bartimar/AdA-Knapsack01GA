/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author Marek
 */
public class Individual {

    private BitSet gene;
    private int geneLength;
    private double fitness = 0;
    private Backpack bp;

    public Individual(BitSet gene, int length) {
        this.gene = gene;
        this.geneLength = length;
    }
    
    public Individual(int length) {
        this.gene = new BitSet(length);
        this.geneLength = length;
    }

    public BitSet getGene() {
        return gene;
    }

    public void setBackpack(Backpack bp) {
        this.bp = bp;
    }

    public void setGene(BitSet gene) {
        this.gene = gene;
    }

    @Override
    public String toString() {
        String ret = "Individual{" + "gene=";
        for (int i = 0; i < geneLength; i++) {
            ret += gene.get(i) ? "1" : "0";
        }
        ret += "}";
        return ret;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = Fitness.getFitness(this, bp);
        }
        return fitness;
    }

    public boolean getItem(int index) {
        return gene.get(index);
    }
    
    public void setItem(int index, boolean val) {
        gene.set(index,val);
    }

    public void regenerate() {
        Random randomGenerator = new Random();
        for (;;) {
            int idx = randomGenerator.nextInt(geneLength);
            //System.out.println("idx=" + idx);
            if (gene.get(idx)) {
                gene.clear(idx);
                break;
            }
        }

    }

    public int length() {
        return geneLength;
    }
}
