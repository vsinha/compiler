#!/bin/bash

make clean; make compiler

FILES=$(ls testcases/step3/input/ | sort -k1.5n)

let failCount=0
let passCount=0

for f in $FILES
do
    FILENAME=${f##*/}
    FILENAME=${FILENAME%.micro}
    echo "Runnning $FILENAME"
    OUTPUT=$(java -cp lib/antlr.jar:classes/ Micro testcases/step3/input/$FILENAME.micro)
    echo "Output: $OUTPUT"
    EXPECTEDOUTPUT=$(cat testcases/step3/output/$FILENAME.out)
    echo "Expected Output: $EXPECTEDOUTPUT"
    
    EXPECTEDOUTPUT="${EXPECTEDOUTPUT}"

    if [ "$OUTPUT" = "$EXPECTEDOUTPUT" ] 
    then
        printf "\033[32;1mPASS \033[0m\n"
        ((passCount++))
    else
       printf "\033[31;1mFAIL \033[0m\n"
       ((failCount++))
       exit
    fi
    echo
done


echo "Passed: " $passCount
echo "Failed: " $failCount
