import core.RepoInfo;
import instrumentor.JSASTInstrumentor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;

import core.JSAnalyzer;
import core.TraceAnalyzer;

public class CoverageCalculator {

	//java -jar target/dist/JSCover-all.jar -ws --proxy --port=3128 --report-dir=target/jscover --log=WARNING --no-instrument=/testAnalysisProject/Leaflet/spec/

	private static String repositoryName;
	private static String htmlTestRunner;
	private static String localhost = "http://localhost:3128";


	private static long wait = 60000;
	
	
	private static WebDriver driver;

	public static void main(RepoInfo repo, String version) throws Exception {

		repositoryName = repo.getRepo_name();
		htmlTestRunner = localhost + "/" + repo.getRepo_name() + repo.getHtml_test_runner_path().substring(repo.getHtml_test_runner_path().indexOf(repo.getRepo_name()) + repo.getRepo_name().length());

		FirefoxOptions options = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.proxy.http", "localhost");
		profile.setPreference("network.proxy.http_port", 3128);
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.no_proxies_on", "");
		options.setProfile(profile);

		driver = new FirefoxDriver(options);

		// Load the html test runner in the browser to get coverage
		try{
			driver.get(htmlTestRunner);
			System.out.println("Loading the URL " + htmlTestRunner);
		}catch (UnhandledAlertException ue)
		{
			try{((JavascriptExecutor) driver).executeScript("window.onbeforeunload = function(e){};");}
			catch(Exception e){System.out.println("Failed to close the popup!" + e);}
			//Alert alert = driver.switchTo().alert();
			//System.out.println(alert.getText());
		}   

		// make sure all tests are finished before getting the trace
		waitForPageToLoad();
		
		
		try {
			Thread.sleep(wait);
		}
		catch (UnhandledAlertException ue)
		{
			try{((JavascriptExecutor) driver).executeScript("window.onbeforeunload = function(e){};");}
			catch(Exception e){System.out.println("Failed to close the popup first!" + e);}
			//Alert alert = driver.switchTo().alert();
			//System.out.println(alert.getText());
		}   
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try{
			if(version.equals("main")){
				((JavascriptExecutor) driver).executeScript("return jscoverage_report('" + repositoryName + "CoverageReport');");
			}else{
				((JavascriptExecutor) driver).executeScript("return jscoverage_report('" + repositoryName + "-" + version + "CoverageReport');");
			}
			System.out.println("Coverage report generated.");
		}
		catch(Exception e){
			System.out.println("Failed to execute function " + e);
		}

		driver.quit();
	}



	public void driverExecute(String javascript) throws Exception {
		((JavascriptExecutor) driver).executeScript(javascript);
	}


	private static void waitForPageToLoad() {  // could be used to make sure the js code execution happens after the page is fully loaded
		String pageLoadStatus = null;
		do {
			pageLoadStatus = (String)((JavascriptExecutor) driver).executeScript("return document.readyState");
			System.out.print(".");
		} while (!pageLoadStatus.equals("complete"));
		//} while (!driver.findElement(By.className("result")).getText().contains("completetd"));// && !pageLoadStatus.equals("complete"));
		System.out.println();
		System.out.println("Page Loaded.");
	}

}
