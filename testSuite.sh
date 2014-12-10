#!/bin/bash

if [ -d "testOutput" ]
then
    rm -rf testOutput
    mkdir testOutput
else
    mkdir testOutput
fi

make clean; make compiler

FILES=$(ls testcases/step7/input/ | sort -k1.5n)

let failCount=0
let passCount=0

for f in $FILES
do
    FILENAME=${f##*/}
    FILENAME=${FILENAME%.micro}

    #FILENAME now has the name of an input file stripped of file type

    echo "Compiling $FILENAME"
    java -cp lib/antlr.jar:classes/ Micro testcases/step7/input/$FILENAME.micro > testOutput/$FILENAME.tiny


    #echo "Running $FILENAME in Tiny simulator"


    #tiny_simulator/tiny testOutput/$FILENAME.tiny | tee testOutput/$FILENAME.out
    
    OUTPUT=$(cat testOutput/$FILENAME.tiny)


    # OUTPUT=$(cat temp.out | sed "/STATISTICS/q" | sed '$d')
    # echo -e "Output:\n$OUTPUT"
    EXPECTEDOUTPUT=$(cat testcases/step7/output/$FILENAME.out) 
    # > temp.tiny; tiny_simulator/tiny temp.tiny > temp.out; cat temp.out | sed "/STATISTICS/q" | sed '$d')
    # echo -e "Expected Output:\n$EXPECTEDOUTPUT"
    
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
