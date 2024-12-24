package ru.magdel;

import java.util.Map;

public class MapWireSupplier implements Main.WireSupplier {

    private final Map<String, Main.WireSupplier> allWires;
    private final String name;

    public MapWireSupplier(Map<String, Main.WireSupplier> allWires, String name) {
        this.allWires = allWires;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Main.Wire get() {
        return allWires.get(name).get();
    }

    @Override
    public String toString() {
        return "MapWireSupplier{" +
                "allWires=" + allWires.size() +
                ", name='" + name + '\'' +
                '}';
    }
}
