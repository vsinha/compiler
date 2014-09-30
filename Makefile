LIB_ANTLR := lib/antlr.jar
ANTLR_SCRIPT := Micro.g4

all: group compiler

group:
	@echo "Viraj Sinha, Jack Hammons"
compiler:
	rm -rf build
	mkdir build
	java -cp $(LIB_ANTLR) org.antlr.v4.Tool -o build $(ANTLR_SCRIPT)
	rm -rf classes
	mkdir classes
	javac -cp $(LIB_ANTLR) -Xlint:unchecked -d classes src/*.java build/*.java
clean:
	rm -rf classes build

.PHONY: all group compiler clean
