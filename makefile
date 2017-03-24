# super simple makefile
# copy and paste from MVC example.
# no academic integrity offence here since the prof said its ok to use the lecture material
# (assumes a .java extension)

all:
	@echo "Compiling..."
	javac *.java Model/*.java View/*.java

run: all
	@echo "Running..."
	java Doodle

clean:
	rm -rf *.class
	rm -rf View/*.class
	rm -rf Model/*.class
