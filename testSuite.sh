#!/bin/bash

make clean; make compiler

FILES=$(ls testcases/step2/input/ | sort -k1.5n)

for f in $FILES
do
    FILENAME=${f##*/}
    FILENAME=${FILENAME%.micro}
    echo "Runnning $FILENAME"
    OUTPUT=$(java -cp lib/antlr.jar:classes/ Micro testcases/step2/input/$FILENAME.micro)
    echo "Output: $OUTPUT"
    EXPECTEDOUTPUT=$(cat testcases/step2/output/$FILENAME.out)
    echo "Expected Output: $EXPECTEDOUTPUT"
    
    EXPECTEDOUTPUT="${EXPECTEDOUTPUT%?}"

    if [ "$OUTPUT" = "$EXPECTEDOUTPUT" ] 
    then
        echo "PASS"
    else
       echo "FAIL"
    fi
    echo
done
