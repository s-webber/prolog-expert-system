# prolog-expert-system

## About

This project demonstrates how an expert system can be constructed by integrating a graphical user interface written in Java with rules written in [Prolog](https://en.wikipedia.org/wiki/Prolog). The open source [Projog](http://projog.org "Prolog interpreter for Java") library is used to integrate Java with Prolog.

The subject of the expert system is the classification of birds. The rules are taken from the [birds.pl](http://www.amzi.com/AdventureInProlog/appendix.php#Birds) program described in the book ["Adventure in Prolog"](http://www.amzi.com/AdventureInProlog/) by Dennis Merritt (ISBN: 978-1520918914). 

Remove the `@Ignore` annotation from `ExpertSystemApplicationTest` to enable the [AssertJ Swing](https://joel-costigliola.github.io/assertj/assertj-swing.html) unit tests.

## Resources

- [Calling Prolog from Java](http://projog.org/calling-prolog-from-java.html)
- [Adding Extra Functionality to Prolog Using Java](http://projog.org/extending-prolog-with-java.html)
- [Adventure in Prolog](http://www.amzi.com/AdventureInProlog/) - contains an example of writing an expert system using Prolog.
- [AssertJ Swing](https://joel-costigliola.github.io/assertj/assertj-swing.html) - used by the unit tests to interact with the UI.
