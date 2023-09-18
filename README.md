TestScannerEvo
======
TestScannerEvo is a framework designed to analyze and monitor the evolution of coverage and mutation testing across test suites for Javascript libraries.

It was developed as part of my thesis project named "**Sviluppo di un framework per l’esecuzione automatica di test suite: monitoraggio sistematico dell’evoluzione di coverage e mutation testing per librerie Javascript.**"
. This framework is based on [TestScanner](https://github.com/saltlab/TestScanner), a tool created by two researchers, Amin Milani Fard and Ali Mesbah, during their study (JavaScript: The (Un)covered Parts).

## How to run:

+ clone the repo. 
+ build the project with Maven.
+ launch JSCover by executing the following command inside the JSCover directory (JSCover can be downloaded [here](https://tntim96.github.io/JSCover/)):
  ```
  java -jar target/dist/JSCover-all.jar -ws --port=3128 --document-root=..\RepoList --report-dir=target/jscover --log=WARNING
  
+ change the config file with the desired information of the repository to be analyzed. Insert:
  * `name` => name of the repository.
  * `url` => url of the repository.
  * `testFolderPath` => path to the test folder inside the repository.
  * `cloneDirPath` => path to the directory in which cloning the repository.
  * `html_test_runner_path` => path to the html test runner of the repository.
  * `JSCover_path` => path to the JSCover folder.
  * `time_development` => flag to enable/disable past version analysis.
+ execute the `Launch.java` file.

To run the framework without mutation testing, comment line 62 in the `Launch.java` file.
```java
57  public static void launcher(RepoInfo repo, String version) throws Exception {
58          GetRepoStat.main(repo, version);
59          TestCodePropertyAnalyzer.main(repo, version);
60          CoverageCalculator.main(repo, version);
61          ProductionCodeCoverageAnalyzer.main(repo,version);
62          //MutationTesting.main(repo);
63  }
```

## Results:
Results are available inside the `results/` folder in this repository.  Download `results.xlsx` to see coverage results. Open the `mutation.html` files on a browser to see mutation testing results.
