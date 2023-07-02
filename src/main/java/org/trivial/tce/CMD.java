package org.trivial.tce;

public class CMD {

    private byte cmd;
    private byte addr;

    public CMD(byte cmd, byte addr) {
        this.cmd = cmd;
        this.addr = addr;
    }

    public byte getCmd() {
        return this.cmd;
    }

    public byte getAddr() {
        return this.addr;
    }
}
