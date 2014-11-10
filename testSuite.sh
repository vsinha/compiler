#!/bin/bash

make clean; make compiler

FILES=$(ls testcases/step5/input/ | sort -k1.5n)

let failCount=0
let passCount=0

for f in $FILES
do
    FILENAME=${f##*/}
    FILENAME=${FILENAME%.micro}
    echo "Runnning $FILENAME"
    OUTPUT=$(java -cp lib/antlr.jar:classes/ Micro testcases/step5/input/$FILENAME.micro > deleteMe.tiny; tiny_simulator/tiny deleteMe.tiny > deleteMe2.out; cat deleteMe2.out | sed "/STATISTICS/q" | sed '$d')
    echo -e "Output:\n$OUTPUT"
    EXPECTEDOUTPUT=$(cat testcases/step5/output/$FILENAME.out > deleteMe.tiny; tiny_simulator/tiny deleteMe.tiny > deleteMe2.out; cat deleteMe2.out | sed "/STATISTICS/q" | sed '$d')
    echo -e "Expected Output:\n$EXPECTEDOUTPUT"
    
    EXPECTEDOUTPUT="${EXPECTEDOUTPUT}"

    if [ "$OUTPUT" = "$EXPECTEDOUTPUT" ] 
    then
        printf "\033[32;1mPASS \033[0m\n"
        ((passCount++))
    else
       printf "\033[31;1mFAIL \033[0m\n"
       ((failCount++))
       #exit
    fi
    echo
done


echo "Passed: " $passCount
echo "Failed: " $failCount
