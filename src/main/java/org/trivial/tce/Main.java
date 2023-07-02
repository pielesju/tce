package org.trivial.tce;

import java.util.List;

public class Main {
    /*static void bubbleSort(int[] arr) {
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr[j-1] > arr[j]){
                    //swap elements
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }*/

    public static void main(String[] args) {
        System.out.println("Hello world!");

        String assembly2 = """
                MVD 0 8; LIST[0] LISTE INITIALISIEREN (UNSORTIERT)
                MVD 1 2; LIST[1]
                MVD 2 4; LIST[2]
                MVD 3 3; LIST[3]
                MVD 4 1; LIST[4]
                MVD 5 6; LIST[5]
                MVD 6 7; LIST[6]
                MVD 7 5; LIST[7]
                MVD 8 8; LIST.LENGTH
                
                MVR 0 0; I, int i = 0
                MVR 1 1; J, int j = 1
                MVR 2 8; STORAGE FOR N - I
                SUBR 2 0; N - I
                MVR 3 0; STORAGE FOR J - I
                MVR 4 0; TEMP
                
                MVR 5 8; rechnen
                SUBR 5 0; n - i
                CMPR 5 0 (JUMP ENDADDRESS)
                END
                """;

        CPU cpu = new CPU();

        TrivialAssembler asm = new TrivialAssembler();
        String assembly = """
                MVR 0 14;       MOVE VALUE 14 TO REGISTER 0
                ADD 0 1;        ADD VALUE 1 TO VALUE IN REGISTER 0
                CMPR 0 42 12;   CHECK IF VALUE IN R0 = 42 THEN JUMP TO 12 ELSE CONTINUE
                GOTO 3;         GOTO ADD
                END;            HALT ADDRESS 12
                """;


        List<CMD> commands = asm.assemble(assembly2);

        /**
         * CMPR 0 42 11
         *
         */

        for(CMD c : commands) {
            cpu.input(c.getCmd(), c.getAddr());
        }

        System.out.println(cpu.debug());

        cpu.run();
        System.out.println(cpu.debug());
    }
}