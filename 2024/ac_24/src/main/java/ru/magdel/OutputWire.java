package ru.magdel;

public class OutputWire implements Main.WireSupplier {
    private final Main.WireSupplier a;
    private final Main.WireSupplier b;
    private final Main.WT type;
    private final String nameOutput;

    public OutputWire(Main.WireSupplier a, Main.WireSupplier b, Main.WT type, String nameOutput) {
        this.a = a;
        this.b = b;
        this.type = type;
        this.nameOutput = nameOutput;
    }

    @Override
    public String name() {
        return nameOutput;
    }

    @Override
    public Main.Wire get() {
        return switch (type){
            case OR -> new Main.Wire(nameOutput, a.get().value() | b.get().value());
            case XOR -> new Main.Wire(nameOutput, a.get().value() ^ b.get().value());
            case AND -> new Main.Wire(nameOutput, a.get().value() & b.get().value());
        };
    }

    @Override
    public String toString() {
        return "OutputWire{" +
                "a=" + a +
                ", b=" + b +
                ", type=" + type +
                ", nameOutput='" + nameOutput + '\'' +
                '}';
    }
}
