package org.trivial.tce;

/**
 * COMMANDS
 * MOVE DATA INTO DATA MEMORY
 * MVD 00000001 ADDR DATA        cpu.input((byte) 2, 0);
        cpu.input((byte) 0, 1);
        cpu.input((byte) 14, 2);
        cpu.input((byte) 4, 3);
        cpu.input((byte) 0, 4);
        cpu.input((byte) 1, 5);

        // COMPARE
        cpu.input((byte) 5, 6);
        cpu.input((byte) 0, 6);
        cpu.input((byte) 42, 7);
        cpu.input((byte)11, 8);
        cpu.input((byte) 3, 9);
        cpu.input((byte) 3, 10);
        cpu.input((byte) 0, 11);
 * MVR 00000010 R (0000 - 0111) DATA
 * GOTO 00000011 ADDR
 * ADD 00000100 ADDR (R) DATA
 * CMPR 00000101 ADDR(R) DATA ADDR(JMP)
 * SUB 00000110 ADDR(R) DATA
 */
public class CPU {

    private static final int NULL = 0;

    /* DEVICES */
    private Memory programMemory;
    private Memory dataMemory;
    private InputDevice inputDevice;

    /* INTERNAL */

    /* PROGRAM COUNTER */
    private int pc;

    /* REGISTERS */
    private byte r0;
    private byte r1;
    private byte r2;
    private byte r3;
    private byte r4;
    private byte r5;
    private byte r6;
    private byte r7;

    public void input(byte data, int addr) {
        this.inputDevice.input(data, addr);
    }

    public CPU() {
        this.programMemory = new Memory(256);
        this.dataMemory = new Memory(256);
        this.inputDevice = new InputDevice(programMemory);

        this.pc = 0;
        this.r0 = NULL;
        this.r1 = NULL;
        this.r2 = NULL;
        this.r3 = NULL;
        this.r4 = NULL;
        this.r5 = NULL;
        this.r6 = NULL;
        this.r7 = NULL;
    }

    public void run() {
        this.pc = 0;
        boolean halt = execute();

        if(halt) {
            System.out.println("HALT");
            return;
        }
    }

    public boolean execute() {
        while(pc < this.programMemory.getLength() - 1 && this.programMemory.fetch(pc) != NULL) {
            byte command = programMemory.fetch(pc);

            switch(command) {
                case 1: mvd(); break;
                case 2: mvr(); break;
                case 3: gt(); break;
                case 4: add(); break;
                case 5: cmpr(); break;
                case 6: sub(); break;
                case 7: subr(); break;
                default: return true;
            }


        }

        return true;
    }

    private void mvd() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc);
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte d = programMemory.fetch(pc);
        this.dataMemory.store(d, a);
        pc++;

        System.out.println(String.format("MVD %02X %02X", a, d));
    }

    private void mvr() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc);
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte d = programMemory.fetch(pc);

        switch(a) {
            case 0: this.r0 = d; break;
            case 1: this.r1 = d; break;
            case 2: this.r2 = d; break;
            case 3: this.r3 = d; break;
            case 4: this.r4 = d; break;
            case 5: this.r5 = d; break;
            case 6: this.r6 = d; break;
            case 7: this.r7 = d; break;
        }
        pc++;

        System.out.println(String.format("MVR %02X %02X", a, d));
    }

    private void add() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc);
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte d = programMemory.fetch(pc);

        switch(a) {
            case 0: this.r0 += d; break;
            case 1: this.r1 += d; break;
            case 2: this.r2 += d; break;
            case 3: this.r3 += d; break;
            case 4: this.r4 += d; break;
            case 5: this.r5 += d; break;
            case 6: this.r6 += d; break;
            case 7: this.r7 += d; break;
        }
        pc++;

        System.out.println(String.format("ADD %02X %02X", a, d));
    }

    private void cmpr() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc); /* REGISTER */
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte d = programMemory.fetch(pc); /* VALUE */
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte jmp = programMemory.fetch(pc); /* JUMP */

        System.out.printf("CMPR %d %d %d", a, d, jmp);

        byte r = 0;

        switch(a) {
            case 0: r = r0; break;
            case 1: r = r1; break;
            case 2: r = r2; break;
            case 3: r = r3; break;
            case 4: r = r4; break;
            case 5: r = r5; break;
            case 6: r = r6; break;
            case 7: r = r7; break;
        }

        if(r == d) {
            pc = jmp;
        }else {
            pc++;
        }

        System.out.println(String.format("CMPR %02X %02X", a, d));
    }



    private void gt() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc);
        pc = a;
        System.out.println("GOTO " + a);
    }

    public String debug() {
        StringBuilder debug = new StringBuilder("");
        debug.append(String.format("PC: %02X%n", pc));

        debug.append(String.format("R0: %02X%n", r0));
        debug.append(String.format("R1: %02X%n", r1));
        debug.append(String.format("R2: %02X%n", r2));
        debug.append(String.format("R3: %02X%n", r3));
        debug.append(String.format("R4: %02X%n", r4));
        debug.append(String.format("R5: %02X%n", r5));
        debug.append(String.format("R6: %02X%n", r6));
        debug.append(String.format("R7: %02X%n", r7));

        debug.append("\n");

        for(int i = 0; i < 256; i++)  {
            debug.append(String.format("%02X", this.dataMemory.fetch(i)));
            if(i % 15 == 0) debug.append("\n");
        }

        debug.append("\n\n");

        for(int i = 0; i < 256; i++)  {
            debug.append(String.format("%02X", this.programMemory.fetch(i)));
            if(i % 15 == 0) debug.append("\n");
        }

        return debug.toString();
    }

    private void sub() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc);
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte d = programMemory.fetch(pc);

        switch(a) {
            case 0: this.r0 -= d; break;
            case 1: this.r1 -= d; break;
            case 2: this.r2 -= d; break;
            case 3: this.r3 -= d; break;
            case 4: this.r4 -= d; break;
            case 5: this.r5 -= d; break;
            case 6: this.r6 -= d; break;
            case 7: this.r7 -= d; break;
        }
        pc++;

        System.out.println(String.format("SUB %02X %02X", a, d));
    }

    private void subr() {
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a = programMemory.fetch(pc);
        pc++;
        if(this.programMemory.getLength() - 1 < pc);
        byte a2 = programMemory.fetch(pc);

        byte rr1 = 0;
        byte rr2 = 0;

        switch(a2) {
            case 0: rr2 = r1; break;
            case 1: rr2 = r2; break;
            case 2: rr2 = r1; break;
            case 3: rr2 = r1; break;
            case 4: rr2 = r1; break;
            case 5: rr2 = r1; break;
            case 6: rr2 = r1; break;
            case 7: rr2 = r1; break;
        }

        switch(a) {
            case 0: r0 -= rr2; break;
            case 1: r1 -= rr2; break;
            case 2: r2 -= rr2; break;
            case 3: r3 -= rr2; break;
            case 4: r4 -= rr2; break;
            case 5: r5 -= rr2; break;
            case 6: r6 -= rr2; break;
            case 7: r7 -= rr2; break;
        }

        pc++;
    }
}
