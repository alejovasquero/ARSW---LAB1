# Arquitecturas de Software
# Laboratorio 1 Paralelismo y Concurrencia

## Integrantes
- David Alejandro Vasquez Carreño
- Michael Jefferson Ballesteros Coca

____________

## PARTE 1 - Introduction to threads in JAVA

1. In agreement with the lectures, complete the classes CountThread, so that they define the life cycle of a thread that prints the numbers between A and B on the screen.
  
  ```
Codigo
  ```

2. Complete the main method of the CountMainThreads class so that: 
  1. Create 3 threads of type CountThread, assigning the first interval [0..99], the second [99..199], and the third [200..299]. 
  
  ```
Codigo
  ```
  
  2. Start the three threads with start(). Run and check the output on the screen. 
  
![hilo3](https://us-prod.asyncgw.teams.microsoft.com/v1/objects/0-eus-d6-3f3f78121f80490c797c4f85d0b79d05/views/imgo)
![hilo4](https://us-prod.asyncgw.teams.microsoft.com/v1/objects/0-eus-d8-c43fec24eddee66a980f147066153f21/views/imgo)
  
  
  
  3. Change the beginning with start() to run(). How does the output change? Why?
  
![hilo1](https://us-prod.asyncgw.teams.microsoft.com/v1/objects/0-eus-d1-ce3c2ec702875173bcce5447151ecd53/views/imgo)
![hilo2](https://us-prod.asyncgw.teams.microsoft.com/v1/objects/0-eus-d4-90ff2c8492e87b0e3ab64fc02c5d227c/views/imgo)
________________

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc