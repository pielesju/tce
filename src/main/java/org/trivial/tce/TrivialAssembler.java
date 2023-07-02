package org.trivial.tce;

import java.util.ArrayList;
import java.util.List;

/**
        * COMMANDS
        * MOVE DATA INTO DATA MEMORY
        * MVD 00000001 ADDR DATA
        * MVR 00000010 R (0000 - 0111) DATA
        * GOTO 00000011 ADDR
        * ADD 00000100 ADDR (R) DATA
        * CMPR 00000101 ADDR(R) DATA ADDR(JMP)
        */
public class TrivialAssembler {

    public TrivialAssembler() {

    }

    public List<CMD> assemble(String assembly) {
        int pc = 0;
        String[] lines = assembly.split("\n");
        String code = "";
        for(String l : lines) {
            if(l.equals("")) {
                continue;
            }
            String[] parts = l.split(";");
            System.out.println(parts[0]);
            code += parts[0] + " ";
        }
        String[] token = code.split("\\s");

        List<CMD> commands = new ArrayList<>();

        for(String t : token) {
            byte data = 0;
            if(t.equals("")) {
                continue;
            } else if(t.equals("MVD")) {
                data = 1;
            }else if(t.equals("MVR")) {
                data = 2;
            }else if(t.equals("GOTO")) {
                data = 3;
            }else if(t.equals("ADD")) {
                data = 4;
            }else if(t.equals("CMPR")) {
                data = 5;
            }else if(t.equals("SUB")) {
                data = 6;
            }else if(t.equals("SUBR")) {
                data = 7;
            }else if(t.equals("END")){
                data = 0;
            }else {
                data = Byte.parseByte(t);
            }
            commands.add(new CMD(data, (byte) pc));
            pc++;
        }

        return commands;
    }


}
