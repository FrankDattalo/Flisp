cd ~/Documents/flisp/grammar
java -Xmx500M -cp "/home/frank/Documents/antlr-4.7-complete.jar:$CLASSPATH" org.antlr.v4.Tool Flisp.g4 -o ../src/main/java/io/github/frankdattalo/flisp/antlr4 -visitor -no-listener -encoding UTF-8
