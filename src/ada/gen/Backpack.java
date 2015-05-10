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
public class Backpack {

    private double capacity;
    private Item[] items;

    public Backpack(double capacity, Item[] items) {
        this.capacity = capacity;
        this.items = items;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public double getSize(int index) {
        return items[index].getSize();
    }

    public double getValue(int index) {
        return items[index].getValue();
    }

    @Override
    public String toString() {
        int i = 0;
        String toPrint = "\n";
        for (Item el : items) {
            toPrint += "Package " + ++i + ": " + el.toString() + "\n";
        }
        return "Backpack{" + "capacity=" + capacity + toPrint + "}\n";
    }
}
