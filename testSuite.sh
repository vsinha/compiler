#!/bin/bash

make clean; make compiler

FILES=$(ls testcases/step6/input/ | sort -k1.5n)

let failCount=0
let passCount=0

for f in $FILES
do
    FILENAME=${f##*/}
    FILENAME=${FILENAME%.micro}
    echo "Runnning $FILENAME"
    OUTPUT=$(java -cp lib/antlr.jar:classes/ Micro testcases/step6/input/$FILENAME.micro > temp.tiny; tiny_simulator/tiny temp.tiny > temp.out; cat temp.out | sed "/STATISTICS/q" | sed '$d')
    echo -e "Output:\n$OUTPUT"
    EXPECTEDOUTPUT=$(cat testcases/step6/output/$FILENAME.out > temp.tiny; tiny_simulator/tiny temp.tiny > temp.out; cat temp.out | sed "/STATISTICS/q" | sed '$d')
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
