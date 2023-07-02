package org.trivial.tce;

public class InputDevice {

    private Memory memory;

    public InputDevice(Memory memory) {
        this.memory = memory;
    }

    public void input(byte data, int addr) {
        this.memory.store(data, addr);
    }
}
