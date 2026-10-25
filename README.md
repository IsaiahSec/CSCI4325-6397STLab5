
# CSCI 6397 Software Testing Lab Five - OOT

A Maven/JUnit 5/AssertJ project containing a test suite for a spellcasting combat module (`Spellcaster`, `Wizard`, `Archmage`, `Wand`, `Spell`). The module compiles but contains seeded object-oriented defects. Each challenge targets one fault class:

- **State control faults:** state transition leaks, precondition bypass, class invariants across call sequences
- **Inheritance and polymorphism faults:** Liskov Substitution violations, the "yo-yo" effect, polymorphic substitution
- **Message and parameter passing faults:** same-type parameter swaps, static overload resolution vs. dynamic dispatch
## Authors

- [Tests](https://github.com/IsaiahSec/CSCI4325-6397STLab5/tree/6a0633240d83f444c3f0aa243aaaead70fffb90a/src/test/java): [@IsaiahSec](https://github.com/IsaiahSec)
- [Tests](https://github.com/IsaiahSec/CSCI4325-6397STLab5/tree/6a0633240d83f444c3f0aa243aaaead70fffb90a/src/test/java): [@JacksonShelby](https://github.com/JacksonShelby)
- [Source](https://github.com/jbaarsch-uca/SoftwareTesting/tree/31c45338742fa2a0825b5bb8b8b906ca6bac7cd0/src/main/java/ObjectOriented): [@jbaarsch-uca](https://github.com/jbaarsch-uca)
## Prerequisites

- JDK 27
- Maven: Not required as a separate install if you're using IntelliJ IDEA, which bundles its own Maven distribution
## Installation

Clone the repository:

```bash
git clone https://github.com/IsaiahSec/CSCI4325-6397STLab5.git
cd CSCI4325-6397STLab5
```

Open the project in IntelliJ IDEA (or any IDE with Maven support) - it should auto-detect pom.xml and resolve dependencies automatically
## Running Tests
From the terminal (if Maven is installed and on your PATH):

```bash
mvn test
```

From IntelliJ:

- Open '[Lab5Test.java](https://github.com/IsaiahSec/CSCI4325-6397STLab5/blob/6a0633240d83f444c3f0aa243aaaead70fffb90a/src/test/java/Lab5Test.java)'
- Click the green run arrow next to the class name, or any individual '@Test' method
- Alternatively, use IntelliJ's Maven tool window and double-click the 'test' lifecycle phase
## Documentation

[CSCI4397&6397STLab5_Shelby_Stewart.pdf](https://github.com/IsaiahSec/CSCI4325-6397STLab5/blob/6a0633240d83f444c3f0aa243aaaead70fffb90a/CSCI4397%266397STLab5_Shelby_Stewart.pdf)

Each test case corresponds to the 'Challenge' it maps to from the lab specification, and the documentation follows the same convention