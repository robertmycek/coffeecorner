
# coffeecorner

### Prereq

To build and test this program download and install:
- java 17
- latest version of maven

### Build & Test

```
$ cd ../coffeecorner
$ mvn test package
```

### Run

```
$ java -jar target/coffeecorner-1.0.0.jar
small coffee with extra milk
<CTRL-Z> (or <CTRL-Z> Windows) followed by <ENTER>
```

To provide your customer stamp card, add extra param like this:
```
$ java -jar target/coffeecorner-1.0.0.jar 3
```
where `3` is count of stumps on your card.
