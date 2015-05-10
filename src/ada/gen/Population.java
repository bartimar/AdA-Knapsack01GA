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
class Population {

    Individual[] individuals;

    public Population(Individual[] individuals) {
        this.individuals = individuals;
    }

    public int size() {
        return individuals.length;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public void setIndividuals(Individual[] individuals) {
        this.individuals = individuals;
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
        return "Population{" + "size=" +size()+"\n" + toPrint + '}';
    }

}
