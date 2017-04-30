# Flisp

Flisp is a small lisp dialect.  It is currently written in Java. Flisp supports
lexical closures, arbitrarily precise arithmetic, and a module system.

## Flisp Tutorial:

### Hello World
```clojure
(print "Hello World")
```

### Flisp Data Types

Flisp contains two primary data types: the atom and the list.
Lists can contain any Flisp data type. Atoms can be: strings,
numbers, or symbols. Numbers are arbitrarily precise.

```clojure
( ... list contents ... )

"Hello World"

mySymbol

1 2 3 -1 1.00000000000000000000000000000000000000065 4573523523523532123123124124991

(def greet (fn (name)
  (print (concat "Hello " name))))
  
(greet "Frank") Prints "Hello Frank!"
```

## TODO

- Implementation of Sets, Vectors, and Maps.

## Build

```bash
$ git clone <this repo>
$ mvn clean package
$ java -jar target/flisp*.jar
```

