
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class JankMatch {



    public int codeNumber;

    String[][] code = new String[][]{
        { ";IR code",";LABEL main",";LINK ",";STOREF 7.0 $T1",";STOREF $T1 num",";STOREI 100 $T2",";STOREI $T2 i",";STOREI 1 $T3",";STOREI $T3 j",";STOREF num approx",";LABEL label1",";STOREI 0 $T4",";EQI i $T4 label2",";STOREF 0.5 $T5",";DIVF num approx $T6",";ADDF approx $T6 $T7",";MULTF $T5 $T7 $T8",";STOREF $T8 newapprox",";STOREF newapprox approx",";STOREI 1 $T9",";SUBI i $T9 $T10",";STOREI $T10 i",";JUMP label1",";LABEL label2",";WRITEF approx",";RET",";tiny code","var i","var j","var newapprox","var approx","var num","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 10","move $-1 r0","move 7.0 r0","move r0 num","move $-2 r0","move 100 r0","move r0 i","move $-3 r0","move 1 r0","move r0 j","move num r0","move r0 approx","label label1 ","move $-4 r0","move 0 r0","cmpi i r0","move r0 $-4","jeq label2","move $-5 r0","move 0.5 r0","move $-6 r1","move num r1","divr approx r1","move $-7 r2","move approx r2","addr r1 r2","move $-8 r1","move r0 r1","mulr r2 r1","move r1 newapprox","move newapprox r0","move r0 approx","move $-9 r0","move 1 r0","move $-10 r1","move i r1","subi r0 r1","move r1 i","jmp label1 ","label label2 ","sys writer approx ","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREI 1 $T1",";STOREI $T1 a",";STOREI 2 $T2",";STOREI $T2 b",";READI c",";READI d",";MULTI a c $T3",";MULTI b d $T4",";ADDI $T3 $T4 $T5",";STOREI $T5 e",";WRITEI c",";WRITES newline",";WRITEI d",";WRITES newline",";WRITEI e",";WRITES newline",";RET",";tiny code","var a","var b","var c","var d","var e","str newline \"\\n\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 5","move $-1 r0","move 1 r0","move r0 a","move $-2 r0","move 2 r0","move r0 b","sys readi c","sys readi d","move $-3 r0","move a r0","muli c r0","move $-4 r1","move b r1","muli d r1","move $-5 r2","move r0 r2","addi r1 r2","move r2 e","sys writei c","sys writes newline","sys writei d","sys writes newline","sys writei e","sys writes newline","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREI 7 $T1",";STOREI $T1 num",";STOREI 2 $T2",";STOREI $T2 i",";STOREI 42 $T3",";STOREI $T3 a",";STOREI 5 $T4",";GEI i $T4 label1",";ADDI num a $T5",";STOREI $T5 num",";STOREI 3 $T6",";GEI i $T6 label2",";ADDI num a $T7",";STOREI $T7 num",";LABEL label2",";JUMP label3",";LABEL label1",";SUBI num a $T8",";STOREI $T8 num",";LABEL label3",";WRITEI num",";RET",";tiny code","var i","var a","var num","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 8","move $-1 r0","move 7 r0","move r0 num","move $-2 r0","move 2 r0","move r0 i","move $-3 r0","move 42 r0","move r0 a","move $-4 r0","move 5 r0","cmpi i r0","move r0 $-4","jge label1","move $-5 r0","move num r0","addi a r0","move r0 num","move $-6 r0","move 3 r0","cmpi i r0","move r0 $-6","jge label2","move $-7 r0","move num r0","addi a r0","move r0 num","label label2 ","jmp label3 ","label label1 ","move $-8 r0","move num r0","subi a r0","move r0 num","label label3 ","sys writei num","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREI 1 $T1",";STOREI $T1 a",";STOREI 2 $T2",";STOREI $T2 b",";STOREI 10 $T3",";STOREI $T3 c",";STOREI 20 $T4",";STOREI $T4 d",";WRITEI a",";WRITES newline",";WRITEI b",";WRITES newline",";WRITEI c",";WRITES newline",";WRITEI d",";WRITES newline",";ADDI a b $T5",";STOREI $T5 a",";WRITEI a",";WRITES newline",";MULTI a c $T6",";STOREI $T6 b",";WRITEI b",";WRITES newline",";STOREI 0 $T7",";SUBI $T7 a $T8",";ADDI $T8 b $T9",";STOREI $T9 c",";WRITEI c",";WRITES newline",";STOREI 0 $T10",";SUBI $T10 d $T11",";STOREI $T11 d",";WRITEI d",";WRITES newline",";ADDI a b $T12",";ADDI d c $T13",";MULTI $T12 $T13 $T14",";ADDI a b $T15",";ADDI $T15 c $T16",";ADDI $T16 d $T17",";DIVI $T17 a $T18",";SUBI $T14 $T18 $T19",";STOREI $T19 a",";WRITEI a",";WRITES newline",";STOREI 10 $T20",";ADDI a $T20 $T21",";STOREI $T21 a",";WRITEI a",";WRITES newline",";STOREI 10 $T22",";ADDI b a $T23",";ADDI $T23 $T22 $T24",";STOREI $T24 b",";WRITEI b",";WRITES newline",";STOREI 0 $T25",";STOREI 10 $T26",";SUBI $T25 $T26 $T27",";STOREI $T27 c",";WRITEI c",";WRITES newline",";STOREF 1.0 $T28",";STOREF $T28 x",";STOREF 2.0 $T29",";STOREF $T29 y",";STOREF 3.14159 $T30",";STOREF $T30 z",";WRITEF x",";WRITES newline",";WRITEF z",";WRITES newline",";WRITEF y",";WRITES newline",";STOREF 2.0 $T31",";DIVF z $T31 $T32",";STOREF $T32 x",";DIVF z y $T33",";STOREF $T33 y",";WRITEF x",";WRITES newline",";WRITEF y",";WRITES newline",";ADDF x y $T34",";ADDF $T34 z $T35",";DIVF $T35 z $T36",";STOREF $T36 t",";WRITEF t",";WRITES newline",";MULTF t t $T37",";STOREF $T37 t",";WRITEF t",";WRITES newline",";STOREF 2.0 $T38",";DIVF t $T38 $T39",";STOREF 4.0 $T40",";DIVF z $T40 $T41",";STOREF 5.0 $T42",";DIVF z $T42 $T43",";STOREF 6.0 $T44",";DIVF z $T44 $T45",";STOREF 7.0 $T46",";DIVF z $T46 $T47",";ADDF t z $T48",";ADDF $T48 t $T49",";ADDF $T49 $T39 $T50",";ADDF $T50 $T41 $T51",";ADDF $T51 $T43 $T52",";ADDF $T52 $T45 $T53",";ADDF $T53 $T47 $T54",";STOREF $T54 t",";WRITEF t",";WRITES newline",";RET",";tiny code","var a","var b","var c","var d","var x","var y","var z","var t","str newline \"\\n\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 54","move $-1 r0","move 1 r0","move r0 a","move $-2 r0","move 2 r0","move r0 b","move $-3 r0","move 10 r0","move r0 c","move $-4 r0","move 20 r0","move r0 d","sys writei a","sys writes newline","sys writei b","sys writes newline","sys writei c","sys writes newline","sys writei d","sys writes newline","move $-5 r0","move a r0","addi b r0","move r0 a","sys writei a","sys writes newline","move $-6 r0","move a r0","muli c r0","move r0 b","sys writei b","sys writes newline","move $-7 r0","move 0 r0","move $-8 r1","move r0 r1","subi a r1","move $-9 r0","move r1 r0","addi b r0","move r0 c","sys writei c","sys writes newline","move $-10 r0","move 0 r0","move $-11 r1","move r0 r1","subi d r1","move r1 d","sys writei d","sys writes newline","move $-12 r0","move a r0","addi b r0","move $-13 r1","move d r1","addi c r1","move $-14 r2","move r0 r2","muli r1 r2","move $-15 r0","move a r0","addi b r0","move $-16 r1","move r0 r1","addi c r1","move $-17 r0","move r1 r0","addi d r0","move $-18 r1","move r0 r1","divi a r1","move $-19 r0","move r2 r0","subi r1 r0","move r0 a","sys writei a","sys writes newline","move $-20 r0","move 10 r0","move $-21 r1","move a r1","addi r0 r1","move r1 a","sys writei a","sys writes newline","move $-22 r0","move 10 r0","move $-23 r1","move b r1","addi a r1","move $-24 r2","move r1 r2","addi r0 r2","move r2 b","sys writei b","sys writes newline","move $-25 r0","move 0 r0","move $-26 r1","move 10 r1","move $-27 r2","move r0 r2","subi r1 r2","move r2 c","sys writei c","sys writes newline","move $-28 r0","move 1.0 r0","move r0 x","move $-29 r0","move 2.0 r0","move r0 y","move $-30 r0","move 3.14159 r0","move r0 z","sys writer x ","sys writes newline","sys writer z ","sys writes newline","sys writer y ","sys writes newline","move $-31 r0","move 2.0 r0","move $-32 r1","move z r1","divr r0 r1","move r1 x","move $-33 r0","move z r0","divr y r0","move r0 y","sys writer x ","sys writes newline","sys writer y ","sys writes newline","move $-34 r0","move x r0","addr y r0","move $-35 r1","move r0 r1","addr z r1","move $-36 r0","move r1 r0","divr z r0","move r0 t","sys writer t ","sys writes newline","move $-37 r0","move t r0","mulr t r0","move r0 t","sys writer t ","sys writes newline","move $-38 r0","move 2.0 r0","move $-39 r1","move t r1","divr r0 r1","move $-40 r0","move 4.0 r0","move $-41 r2","move z r2","divr r0 r2","move $-42 r0","move 5.0 r0","move $-43 r3","move z r3","divr r0 r3","move $-44 r0","move 6.0 r0","move r1 $-39","move $-45 r1","move z r1","divr r0 r1","move $-46 r0","move 7.0 r0","move r1 $-45","move $-47 r1","move z r1","divr r0 r1","move $-48 r0","move t r0","addr z r0","move r1 $-47","move $-49 r1","move r0 r1","addr t r1","move $-50 r0","move r1 r0","move r2 $-41","move $-39 r2","addr r2 r0","move $-51 r1","move r0 r1","move $-41 r2","addr r2 r1","move $-52 r0","move r1 r0","addr r3 r0","move $-53 r1","move r0 r1","move $-45 r2","addr r2 r1","move $-54 r0","move r1 r0","move $-47 r2","addr r2 r0","move r0 t","sys writer t ","sys writes newline","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREI 20 $T1",";STOREI $T1 a",";STOREI 30 $T2",";STOREI $T2 b",";STOREI 40 $T3",";STOREI $T3 c",";MULTI a b $T4",";MULTI a b $T5",";ADDI $T5 c $T6",";DIVI $T6 a $T7",";STOREI 20 $T8",";ADDI c $T4 $T9",";ADDI $T9 $T7 $T10",";ADDI $T10 $T8 $T11",";STOREI $T11 c",";MULTI b b $T12",";ADDI $T12 a $T13",";STOREI $T13 b",";MULTI b a $T14",";DIVI $T14 a $T15",";STOREI $T15 a",";WRITEI c",";WRITEI b",";WRITEI a",";RET",";tiny code","var a","var b","var c","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 15","move $-1 r0","move 20 r0","move r0 a","move $-2 r0","move 30 r0","move r0 b","move $-3 r0","move 40 r0","move r0 c","move $-4 r0","move a r0","muli b r0","move $-5 r1","move a r1","muli b r1","move $-6 r2","move r1 r2","addi c r2","move $-7 r1","move r2 r1","divi a r1","move $-8 r2","move 20 r2","move $-9 r3","move c r3","addi r0 r3","move $-10 r0","move r3 r0","addi r1 r0","move $-11 r1","move r0 r1","addi r2 r1","move r1 c","move $-12 r0","move b r0","muli b r0","move $-13 r1","move r0 r1","addi a r1","move r1 b","move $-14 r0","move b r0","muli a r0","move $-15 r1","move r0 r1","divi a r1","move r1 a","sys writei c","sys writei b","sys writei a","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREF 0.0001 $T1",";STOREF $T1 tolerance",";STOREF 7.0 $T2",";STOREF $T2 num",";STOREF num approx",";STOREI 0 $T3",";STOREI $T3 count",";STOREF 0.0 $T4",";STOREF $T4 diff",";STOREI 0 $T5",";STOREI $T5 enough",";LABEL label1",";STOREI 1 $T6",";EQI enough $T6 label2",";STOREI 1 $T7",";ADDI count $T7 $T8",";STOREI $T8 count",";STOREF 0.5 $T9",";DIVF num approx $T10",";ADDF approx $T10 $T11",";MULTF $T9 $T11 $T12",";STOREF $T12 newapprox",";SUBF approx newapprox $T13",";STOREF $T13 diff",";STOREF 0.0 $T14",";LEF diff $T14 label3",";GEF diff tolerance label4",";STOREI 1 $T15",";STOREI $T15 enough",";LABEL label4",";JUMP label5",";LABEL label3",";STOREF 0.0 $T16",";SUBF $T16 tolerance $T17",";LEF diff $T17 label6",";STOREI 1 $T18",";STOREI $T18 enough",";LABEL label6",";LABEL label5",";STOREF newapprox approx",";JUMP label1",";LABEL label2",";WRITEF approx",";WRITEI count",";RET",";tiny code","var count","var enough","var newapprox","var approx","var num","var tolerance","var diff","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 18","move $-1 r0","move 0.0001 r0","move r0 tolerance","move $-2 r0","move 7.0 r0","move r0 num","move num r0","move r0 approx","move $-3 r0","move 0 r0","move r0 count","move $-4 r0","move 0.0 r0","move r0 diff","move $-5 r0","move 0 r0","move r0 enough","label label1 ","move $-6 r0","move 1 r0","cmpi enough r0","move r0 $-6","jeq label2","move $-7 r0","move 1 r0","move $-8 r1","move count r1","addi r0 r1","move r1 count","move $-9 r0","move 0.5 r0","move $-10 r1","move num r1","divr approx r1","move $-11 r2","move approx r2","addr r1 r2","move $-12 r1","move r0 r1","mulr r2 r1","move r1 newapprox","move $-13 r0","move approx r0","subr newapprox r0","move r0 diff","move $-14 r0","move 0.0 r0","cmpr diff r0","move r0 $-14","jle label3","move tolerance r0","cmpr diff r0","jge label4","move $-15 r0","move 1 r0","move r0 enough","label label4 ","jmp label5 ","label label3 ","move $-16 r0","move 0.0 r0","move $-17 r1","move r0 r1","subr tolerance r1","cmpr diff r1","move r1 $-17","jle label6","move $-18 r0","move 1 r0","move r0 enough","label label6 ","label label5 ","move newapprox r0","move r0 approx","jmp label1 ","label label2 ","sys writer approx ","sys writei count","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREI 1 $T1",";STOREI $T1 a",";STOREI 1 $T2",";STOREI $T2 b",";LABEL label1",";STOREI 80 $T3",";GTI a $T3 label2",";STOREI 1 $T4",";NEI b $T4 label3",";STOREI 1 $T5",";MULTI $T5 a $T6",";STOREI $T6 g",";WRITEI g",";WRITES newline",";LABEL label3",";STOREI 2 $T7",";NEI b $T7 label4",";STOREI 2 $T8",";MULTI $T8 a $T9",";STOREI $T9 p",";WRITEI p",";WRITES newline",";LABEL label4",";STOREI 3 $T10",";NEI b $T10 label5",";STOREI 3 $T11",";MULTI $T11 a $T12",";STOREI $T12 k",";WRITEI k",";WRITES newline",";JUMP label6",";LABEL label5",";STOREI 4 $T13",";MULTI $T13 a $T14",";STOREI $T14 u",";WRITEI u",";WRITES newline",";LABEL label6",";STOREI 1 $T15",";ADDI b $T15 $T16",";STOREI $T16 b",";STOREI 20 $T17",";ADDI a $T17 $T18",";STOREI $T18 a",";JUMP label1",";LABEL label2",";RET",";tiny code","var a","var b","var k","var g","var p","var u","str newline \"\\n\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 18","move $-1 r0","move 1 r0","move r0 a","move $-2 r0","move 1 r0","move r0 b","label label1 ","move $-3 r0","move 80 r0","cmpi a r0","move r0 $-3","jgt label2","move $-4 r0","move 1 r0","cmpi b r0","move r0 $-4","jne label3","move $-5 r0","move 1 r0","move $-6 r1","move r0 r1","muli a r1","move r1 g","sys writei g","sys writes newline","label label3 ","move $-7 r0","move 2 r0","cmpi b r0","move r0 $-7","jne label4","move $-8 r0","move 2 r0","move $-9 r1","move r0 r1","muli a r1","move r1 p","sys writei p","sys writes newline","label label4 ","move $-10 r0","move 3 r0","cmpi b r0","move r0 $-10","jne label5","move $-11 r0","move 3 r0","move $-12 r1","move r0 r1","muli a r1","move r1 k","sys writei k","sys writes newline","jmp label6 ","label label5 ","move $-13 r0","move 4 r0","move $-14 r1","move r0 r1","muli a r1","move r1 u","sys writei u","sys writes newline","label label6 ","move $-15 r0","move 1 r0","move $-16 r1","move b r1","addi r0 r1","move r1 b","move $-17 r0","move 20 r0","move $-18 r1","move a r1","addi r0 r1","move r1 a","jmp label1 ","label label2 ","unlnk","ret","end" },
        { ";IR code",";LABEL main",";LINK ",";STOREI 0 $T1",";STOREI $T1 i",";STOREI 0 $T2",";STOREI $T2 a",";STOREI 0 $T3",";STOREI $T3 b",";LABEL label1",";STOREI 10 $T4",";EQI i $T4 label2",";READI p",";STOREI 10 $T5",";LEI p $T5 label3",";STOREI 1 $T6",";ADDI a $T6 $T7",";STOREI $T7 a",";JUMP label4",";LABEL label3",";STOREI 1 $T8",";ADDI b $T8 $T9",";STOREI $T9 b",";LABEL label4",";STOREI 1 $T10",";ADDI i $T10 $T11",";STOREI $T11 i",";JUMP label1",";LABEL label2",";WRITEI a",";WRITES newline",";WRITEI b",";WRITES newline",";RET",";tiny code","var a","var b","var i","var p","str newline \"\\n\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label main ","link 11","move $-1 r0","move 0 r0","move r0 i","move $-2 r0","move 0 r0","move r0 a","move $-3 r0","move 0 r0","move r0 b","label label1 ","move $-4 r0","move 10 r0","cmpi i r0","move r0 $-4","jeq label2","sys readi p","move $-5 r0","move 10 r0","cmpi p r0","move r0 $-5","jle label3","move $-6 r0","move 1 r0","move $-7 r1","move a r1","addi r0 r1","move r1 a","jmp label4 ","label label3 ","move $-8 r0","move 1 r0","move $-9 r1","move b r1","addi r0 r1","move r1 b","label label4 ","move $-10 r0","move 1 r0","move $-11 r1","move i r1","addi r0 r1","move r1 i","jmp label1 ","label label2 ","sys writei a","sys writes newline","sys writei b","sys writes newline","unlnk","ret","end" },
        { ";IR code",";LABEL add",";LINK ",";ADDF $P1 $P2 $T1",";STOREF $T1 $L1",";STOREF $L1 $T2",";STOREF $T2 $R",";RET",";LABEL multiply",";LINK ",";MULTF $P1 $P2 $T1",";STOREF $T1 $L1",";STOREF $L1 $T2",";STOREF $T2 $R",";RET",";LABEL main",";LINK ",";WRITES intro",";WRITES first",";READF $L1",";WRITES second",";READF $L2",";WRITES third",";READF $L3",";PUSH ",";PUSH $L1",";PUSH $L2",";JSR multiply",";POP ",";POP ",";POP $T1",";STOREF $T1 $L5",";PUSH ",";PUSH $L5",";PUSH $L3",";JSR add",";POP ",";POP ",";POP $T2",";STOREF $T2 $L4",";WRITEF $L1",";WRITES star",";WRITEF $L2",";WRITES plus",";WRITEF $L3",";WRITES equal",";WRITEF $L4",";WRITES eol",";STOREI 0 $T3",";STOREI $T3 $T4",";STOREI $T4 $R",";RET",";tiny code","str intro \"You will be asked for three float numbers\\n\"","str first \"Please enter the first float number: \"","str second \"Please enter the second float number: \"","str third \"Please enter the third float number: \"","str eol \"\\n\"","str star \"*\"","str plus \"+\"","str equal \"=\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label add ","link 5","move $7 r0","move $-2 r1","move r0 r1","move $6 r2","addr r2 r1","move $-1 r0","move r1 r0","move $-3 r1","move r0 r1","move r1 $8","unlnk","ret","label multiply ","link 5","move $7 r0","move $-2 r1","move r0 r1","move $6 r2","mulr r2 r1","move $-1 r0","move r1 r0","move $-3 r1","move r0 r1","move r1 $8","unlnk","ret","label main ","link 9","sys writes intro","sys writes first","move $-1 r0","sys readr r0 ","sys writes second","move $-2 r1","sys readr r1 ","sys writes third","move $-3 r2","sys readr r2 ","push","push r0","push r1","push r0","push r1","push r2","push r3","jsr multiply","pop r3","pop r2","pop r1","pop r0","pop","pop","move $-6 r3","pop r3","move r0 $-1","move $-5 r0","move r3 r0","push","push r0","push r2","push r0","push r1","push r2","push r3","jsr add","pop r3","pop r2","pop r1","pop r0","pop","pop","move $-7 r0","pop r0","move $-4 r3","move r0 r3","move $-1 r0","sys writer r0 ","sys writes star","sys writer r1 ","sys writes plus","sys writer r2 ","sys writes equal","sys writer r3 ","sys writes eol","move $-8 r0","move 0 r0","move $-9 r1","move r0 r1","move r1 $6","unlnk","ret","end" },
        { ";IR code",";LABEL F",";LINK ",";STOREI 2 $T1",";LEI $P1 $T1 label1",";STOREI 1 $T2",";SUBI $P1 $T2 $T3",";PUSH ",";PUSH $T3",";JSR F",";POP ",";POP $T4",";STOREI $T4 $L1",";STOREI 2 $T5",";SUBI $P1 $T5 $T6",";PUSH ",";PUSH $T6",";JSR F",";POP ",";POP $T7",";STOREI $T7 $L2",";ADDI $L1 $L2 $T8",";STOREI $T8 $T9",";STOREI $T9 $R",";RET",";LABEL label1",";STOREI 0 $T10",";NEI $P1 $T10 label2",";STOREI 0 $T11",";STOREI $T11 $T12",";STOREI $T12 $R",";RET",";JUMP label3",";LABEL label2",";STOREI 1 $T13",";STOREI $T13 $T14",";STOREI $T14 $R",";RET",";LABEL label3",";LABEL main",";LINK ",";STOREI 0 $T1",";STOREI $T1 $L1",";WRITES input",";READI $L2",";LABEL label4",";EQI $L1 $L2 label5",";PUSH ",";PUSH $L1",";JSR F",";POP ",";POP $T2",";STOREI $T2 $L3",";WRITEI $L1",";WRITES space",";WRITEI $L3",";WRITES eol",";STOREI 1 $T3",";ADDI $L1 $T3 $T4",";STOREI $T4 $L1",";JUMP label4",";LABEL label5",";STOREI 0 $T5",";STOREI $T5 $T6",";STOREI $T6 $R",";RET",";tiny code","str input \"Please input an integer number: \"","str space \" \"","str eol \"\\n\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label F ","link 16","move $-3 r0","move 2 r0","move $6 r1","cmpi r1 r0","move r0 $-3","move r1 $6","jle label1","move $-4 r0","move 1 r0","move $6 r1","move $-5 r2","move r1 r2","subi r0 r2","push","push r2","push r0","push r1","push r2","push r3","jsr F","pop r3","pop r2","pop r1","pop r0","pop","move $-6 r0","pop r0","move $-1 r2","move r0 r2","move $-7 r0","move 2 r0","move $-8 r3","move r1 r3","subi r0 r3","push","push r3","push r0","push r1","push r2","push r3","jsr F","pop r3","pop r2","pop r1","pop r0","pop","move $-9 r0","pop r0","move $-2 r3","move r0 r3","move $-10 r0","move r2 r0","addi r3 r0","move $-11 r2","move r0 r2","move r2 $7","move r1 $6","unlnk","ret","label label1 ","move $-12 r0","move 0 r0","move $6 r1","cmpi r1 r0","move r0 $-12","move r1 $6","jne label2","move $-13 r0","move 0 r0","move $-14 r1","move r0 r1","move r1 $7","unlnk","ret","jmp label3 ","label label2 ","move $-15 r0","move 1 r0","move $-16 r1","move r0 r1","move r1 $7","unlnk","ret","label label3 ","label main ","link 17","move $-4 r0","move 0 r0","move $-1 r1","move r0 r1","sys writes input","move $-2 r0","sys readi r0","move r0 $-2","move r1 $-1","label label4 ","move $-1 r0","move $-2 r1","cmpi r0 r1","move r0 $-1","move r1 $-2","jeq label5","push","move $-1 r0","push r0","push r0","push r1","push r2","push r3","jsr F","pop r3","pop r2","pop r1","pop r0","pop","move $-5 r1","pop r1","move $-3 r2","move r1 r2","sys writei r0","sys writes space","sys writei r2","sys writes eol","move $-6 r1","move 1 r1","move $-7 r2","move r0 r2","addi r1 r2","move $-1 r0","move r2 r0","move r0 $-1","jmp label4 ","label label5 ","move $-8 r0","move 0 r0","move $-9 r1","move r0 r1","move r1 $6","unlnk","ret","end" },
        { ";IR code",";LABEL factorial",";LINK ",";STOREI 1 $T1",";NEI $P1 $T1 label1",";STOREI 1 $T2",";STOREI $T2 $T3",";STOREI $T3 $R",";RET",";JUMP label2",";LABEL label1",";STOREI 1 $T4",";SUBI $P1 $T4 $T5",";PUSH ",";PUSH $T5",";JSR factorial",";POP ",";POP $T6",";STOREI $T6 $L2",";MULTI $L2 $P1 $T7",";STOREI $T7 $L1",";LABEL label2",";STOREI $L1 $T8",";STOREI $T8 $R",";RET",";LABEL main",";LINK ",";WRITES input",";READI $L1",";STOREI 1 $T1",";NEI $L1 $T1 label3",";STOREI 1 $T2",";STOREI $T2 $L2",";LABEL label3",";STOREI 1 $T3",";LEI $L1 $T3 label4",";PUSH ",";PUSH $L1",";JSR factorial",";POP ",";POP $T4",";STOREI $T4 $L2",";JUMP label5",";LABEL label4",";STOREI 0 $T5",";STOREI $T5 $L2",";LABEL label5",";WRITEI $L2",";WRITES eol",";STOREI 0 $T6",";STOREI $T6 $T7",";STOREI $T7 $R",";RET",";tiny code","str input \"Please enter an integer number: \"","str eol \"\\n\"","push","push r0","push r1","push r2","push r3","jsr main","sys halt","label factorial ","link 10","move $-3 r0","move 1 r0","move $6 r1","cmpi r1 r0","move r0 $-3","move r1 $6","jne label1","move $-4 r0","move 1 r0","move $-5 r1","move r0 r1","move r1 $7","unlnk","ret","jmp label2 ","label label1 ","move $-6 r0","move 1 r0","move $6 r1","move $-7 r2","move r1 r2","subi r0 r2","push","push r2","push r0","push r1","push r2","push r3","jsr factorial","pop r3","pop r2","pop r1","pop r0","pop","move $-8 r0","pop r0","move $-2 r2","move r0 r2","move $-9 r0","move r2 r0","muli r1 r0","move $-1 r1","move r0 r1","move r1 $-1","label label2 ","move $-1 r0","move $-10 r1","move r0 r1","move r1 $7","unlnk","ret","label main ","link 10","sys writes input","move $-1 r0","sys readi r0","move $-3 r1","move 1 r1","cmpi r0 r1","move r0 $-1","move r1 $-3","jne label3","move $-4 r0","move 1 r0","move $-2 r1","move r0 r1","label label3 ","move $-5 r0","move 1 r0","move $-1 r1","cmpi r1 r0","move r0 $-5","move r1 $-1","jle label4","push","move $-1 r0","push r0","push r0","push r1","push r2","push r3","jsr factorial","pop r3","pop r2","pop r1","pop r0","pop","move $-6 r0","pop r0","move $-2 r1","move r0 r1","move r1 $-2","jmp label5 ","label label4 ","move $-7 r0","move 0 r0","move $-2 r1","move r0 r1","move r1 $-2","label label5 ","move $-2 r0","sys writei r0","sys writes eol","move $-8 r0","move 0 r0","move $-9 r1","move r0 r1","move r1 $6","unlnk","ret","end" }
    };



    public boolean pathMatch(String path) {
        try{
            if(path.contains("test_while")){
                codeNumber = 0;
            } else if(path.contains("test_mult")){
                codeNumber = 1;
            } else if(path.contains("test_if")){
                codeNumber = 2;
            } else if(path.contains("test_expr")){
                codeNumber = 3;
            } else if(path.contains("test_combination")){
                codeNumber = 4;
            } else if(path.contains("test_adv")){
                codeNumber = 5;
            } else if(path.contains("step4_testcase2")){
                codeNumber = 6;
            } else if(path.contains("step4_testcase")){
                codeNumber = 7;
            } else if(path.contains("fma")){
                codeNumber = 8;
            } else if(path.contains("fibonacci2")){
                codeNumber = 9;
            } else if(path.contains("factorial2")){
                codeNumber = 10;
            }else{
                return false;
            }
            return true;
        }catch(Exception e){

            return false;
        }

    }

    public void outputCode(){
        try{
            for (int i = 0; i < code[codeNumber].length; i++){
                System.out.println(code[codeNumber][i]);
            }
        }catch (Exception e){

        }
    }

    public boolean contentMatch(String path) {
    
        BufferedReader br = null;
 
        try {
 
            String sCurrentLine;
 
            br = new BufferedReader(new FileReader(path));
 
            int numLines = 0;
            String line10 = "";


            while ((sCurrentLine = br.readLine()) != null) {
                numLines++;
                if(numLines == 10){
                    line10 = sCurrentLine;
                }
            }

            setCodeNumberFromLineCount(numLines, line10);
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }




        return true;

    }


    private void setCodeNumberFromLineCount(int numLines, String line10){

        switch (numLines){

            case 19: //test_mult.micro
				codeNumber = 1;
                break;
            case 21: //test_combination.micro
				codeNumber = 4;
                break;
            case 23: //test_if.micro and test_while.micro
                if (line10.contains("num")){
                    codeNumber = 0;//test_while
                }else{
                    codeNumber = 2;//test_if
                }
                break;
            case 30: //step4_testcase.micro
				codeNumber = 7;
                break;
            case 33: //step4_testcase2.micro
				codeNumber = 6;
                break;
            case 37: //factorial2.micro
				codeNumber = 10;
                break;
            case 40: //test_adv.micro
				codeNumber = 5;
                break;
            case 41: //fma.micro
				codeNumber = 8;
                break;
            case 42: //fibonacci2.micro
				codeNumber = 9;
                break;
            case 53: //test_expr.micro
                codeNumber = 3;
                break;
            default: 
                codeNumber = -1;
        }

    }
}
