package org.trivial.tce;

public class Memory {

    private int size;
    private byte[] memory;

    public Memory(int size) {
        this.size = size;
        this.memory = new byte[size];
    }

    private boolean outOfBounds(int addr) {
        return addr < 0 || addr > this.size;
    }

    public boolean store(byte b, int addr) {
        if(outOfBounds(addr)) return false;

        this.memory[addr] = b;
        return true;
    }

    public byte fetch(int addr) {
        if(outOfBounds(addr)) return 0;

        return this.memory[addr];
    }

    public int getLength() {
        return this.size;
    }
}
