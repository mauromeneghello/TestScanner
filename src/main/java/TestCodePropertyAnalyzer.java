import core.RepoInfo;
import core.SaveResults;
import instrumentor.JSASTInstrumentor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;

import core.JSAnalyzer;
import core.TraceAnalyzer;

public class TestCodePropertyAnalyzer {

	private static String testsFramework = "qunit";  // {"qunit", "jasmine", "mocha", "nodeunit"}
	private static String testsFolder;
	private static String[] excludeFolders = {"assets", "coverage", "lib", "libs", "casper", "lcov-report"	};		
	

	private static JSAnalyzer codeAnalyzer;
	private static int NumTests;
	private static int NumAsyncTests;
	private static int NumAssertions;
	private static int NumFunCallTest = 0;
	private static int NumFunCallTestModule = 0;
	private static int MaxFunCall = 0;
	private static float AveFunCall = 0;
	private static int NumTriggerInTest;
	private static int NumObjCreate;
	private static String repo_name;
	private static String previous_version = null;

	public static void main(RepoInfo repo, String version) throws Exception {

		testsFolder = repo.getRepo_test_folder_path();
		repo_name = repo.getRepo_name();

		codeAnalyzer = new JSAnalyzer(new JSASTInstrumentor(), testsFolder, null);		

		// For each .js test file
		System.out.println("Test framework: " + testsFramework);
		File[] files = new File(testsFolder).listFiles();
		if (files==null){
			System.out.println("No file found in directory: " + testsFolder);
			return;
		}
		for (File file : files) {
			processFile(file, version);
		}
	}

	public static void processFile(File file, String version) throws IOException, Exception {
		if (ArrayUtils.contains(excludeFolders, file.getName())){
			System.out.println("*** Analysis excluded for: " + file.getName());
			return;
		}
		if (file.isDirectory()){
			System.out.println("*** Analysing directory: " + file.getAbsolutePath().replace(testsFolder, ""));
			File[] files = file.listFiles();
			if (files==null){
				System.out.println("No test file found in directory: " + file.getAbsolutePath().replace(testsFolder, ""));
				return;
			}
			for (File innerFile : files)
				processFile(innerFile, version);
		}
		if (file.isFile()) {
			String fileName = file.getName();
			//if (fileName.endsWith(".js")){
			if (!fileName.contains("qunit") && fileName.endsWith(".js") && !fileName.contains("jquery")) {
				//&& !fileName.equals("es.js") && !fileName.equals("helpers.js") && !fileName.equals("karma.conf.js") && !fileName.equals("es.js") && !fileName.equals("library.js")
				//){
				analyseJSTestFile(file.getCanonicalPath(), version);
			}
		}
		
	}
	
	

	private static void analyseJSTestFile(String canonicalPath, String version) throws Exception {
		/*
	 	NumTests: Number of tests
		NumAsyncTests: Number of async tests
		NumAssertions: Number of assertions
		MaxFunCall: Maximum number of unique function calls per test
		AveFunCall: Average number of unique function calls per test
		NumDOMFixture: Number of DOM fixtures in the test suite
		NumTriggerTest: Number of tests with event triggering methods
		NumObjCreate: Number of objects creation in the test suite
		*/



		if(!version.equals(previous_version)){
			NumTests = 0;
			NumAsyncTests = 0;
			NumAssertions = 0;
			NumFunCallTest = 0;
			MaxFunCall = 0;
			AveFunCall = 0;
			NumTriggerInTest = 0;
			NumObjCreate = 0;
		}
		previous_version = version;

		String[] stats =new String[8];

		File jsFile = new File(canonicalPath);
		String fileName = jsFile.getName();

		System.out.println(canonicalPath);
		codeAnalyzer.setJSFileName(fileName);
		codeAnalyzer.setJSAddress(canonicalPath);
		codeAnalyzer.setTestFramework(testsFramework);
		//codeAnalyzer.setJSAddress(testsFolder + "/" + fileName);
		System.out.println("Analysing the test suite in file " + fileName);
		codeAnalyzer.analyzeTestCodeProperties();
		
		
		NumTests += codeAnalyzer.getNumTests();
		NumAsyncTests += codeAnalyzer.getNumAsyncTests();
		NumAssertions += codeAnalyzer.getNumAssertions();
		NumFunCallTest += codeAnalyzer.getNumFunCallTest();
		NumFunCallTestModule += codeAnalyzer.getNumFunCallTestModule();
		if (codeAnalyzer.getMaxFunCallTest() > MaxFunCall)
			MaxFunCall = codeAnalyzer.getMaxFunCallTest();
		AveFunCall  = (float)NumFunCallTest/(float)NumTests;
		NumTriggerInTest += codeAnalyzer.getNumTriggerTest();
		NumObjCreate += codeAnalyzer.getNumObjCreate();
		
		
		System.out.println("==========================");
		System.out.println("++++ NumTests: " + NumTests);
		System.out.println("++++ NumAsyncTests: " + NumAsyncTests);
		System.out.println("++++ NumAssertions: " + NumAssertions);
		System.out.println("++++ NumFunCall: " + NumFunCallTest);
		System.out.println("++++ MaxFunCall: " + MaxFunCall);
		System.out.println("++++ AveFunCall: " + AveFunCall);
		System.out.println("++++ NumTriggerInTest: " + NumTriggerInTest);
		System.out.println("++++ NumObjCreate: " + NumObjCreate);
		System.out.println("==========================");

		System.out.println(NumTests + "\t" + NumAsyncTests + "\t" + NumAssertions + "\t" + NumFunCallTest + "\t" + MaxFunCall + "\t" + AveFunCall + "\t" + NumTriggerInTest + "\t" + NumObjCreate);
		stats[0] = "" + NumTests;
		stats[1] = "" + NumAsyncTests;
		stats[2] = "" + NumAssertions;
		stats[3] = "" + NumFunCallTest;
		stats[4] = "" + MaxFunCall;
		stats[5] = "" + AveFunCall;
		stats[6] = "" + NumTriggerInTest;
		stats[7] = "" + NumObjCreate;

		//System.out.println(testsFolder.substring(testsFolder.indexOf("RepoList/") + "RepoList/".length(), testsFolder.indexOf("/", testsFolder.indexOf("RepoList/") + "RepoList/".length())));

		SaveResults.WriteResultToExcel(2,repo_name,"", version, stats );
		System.out.println("Results saved succesfully!");
	}


}


