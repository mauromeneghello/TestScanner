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

	private static String repositoryName = "es5-shim1";
	private static String htmlTestRunner = "http://localhost:3128/es5-shim/tests/index.html";

	//private static String repositoryName = "swagger-ui";
	//private static String htmlTestRunner = "http://localhost:8080/swagger-ui/test/e2e-selenium/static/index.html";


	private static long wait = 60000;
	
	
	private static WebDriver driver;

	public static void main(String[] args) throws Exception {

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
			((JavascriptExecutor) driver).executeScript("return jscoverage_report('" + repositoryName + "CoverageReport');");
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
