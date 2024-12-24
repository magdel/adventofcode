package ru.magdel;

public class ValueWireSupplier implements Main.WireSupplier {
    private final String name;
    private final Main.Wire wire;

    public ValueWireSupplier(String name, Main.Wire wire) {
        this.name = name;
        this.wire = wire;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Main.Wire get() {
        return wire;
    }

    @Override
    public String toString() {
        return "WireSupplierImpl{" +
                "name='" + name + '\'' +
                ", wire=" + wire +
                '}';
    }
}
