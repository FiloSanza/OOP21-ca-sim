# Cellular Automaton Simulator

[![Build](https://github.com/FiloSanza/ca-sim/actions/workflows/gradle.yml/badge.svg)](https://github.com/FiloSanza/ca-sim/actions/workflows/gradle.yml)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

ca-sim is a cellular automaton simulator, it supports:
- [x] [Brian's Brain](https://en.wikipedia.org/wiki/Brian's_Brain)
- [x] [CoDi](https://en.wikipedia.org/wiki/CoDi)
- [x] [Conway's Game Of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
- [x] [Langton's Ant](https://en.wikipedia.org/wiki/Langton%27s_ant)
- [x] [Predators and Prey](https://en.wikipedia.org/wiki/Wa-Tor)
- [x] [Rule 110](https://en.wikipedia.org/wiki/Rule_110)
- [ ] [Empire](https://github.com/Hopson97/Empire)
- [ ] [WireWorld](https://en.wikipedia.org/wiki/Wireworld)

The following features will be implemented:
- [x] Automatic simulation
- [x] Manual simulation (the user will decide when to show the next iteration)
- [x] Stats about the simulation (i.e. States, number of iterations)
- [x] Random generated initial grid
- [ ] User generated initial grid
- [ ] Save/Load initial state from file
- [ ] Zoom

This software is developed by:
- [Raul Chiasserini](https://github.com/RaulChiasso) 
- [Lorenzo Drudi](https://github.com/LorenzoDrudi)
- [Filippo Sanzani](https://github.com/FiloSanza)
- [Simone Zama](https://github.com/TorioCrema) 

For the university course in OOP.

## Build

You can build ca-sim using Gradle, first you have to clone the repo:

```bash
git clone git@github.com:FiloSanza/ca-sim.git
```

You can then build it on linux or mac-os with:

```bash
cd ca-sim
./gradlew build
```

If you are on Windows you can use:

```ps
cd "ca-sim"
gradlew.bat build
```

The gradle scripts automatically executes additional checks using PMD, SpotBugs and CheckStyle, if you want to disable them you can run (on linux and mac-os):

```bash
./gradle build -x check       #to disable all the checks
./gradle build -x [checkName] #checkName can be checkstyle[Main|Test] spotbugs[Main|Test]
```

If you are on Windows you can use:

```ps
gradlew.bat build -x check       #to disable all the checks
gradlew.bat build -x [checkName] #checkName can be checkstyle[Main|Test] spotbugs[Main|Test]
```
