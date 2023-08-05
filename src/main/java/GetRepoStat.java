import core.*;
import instrumentor.JSASTInstrumentor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;

import core.RepoCloner;
import core.RepoInfo;
import org.json.JSONObject;
import org.json.JSONException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GetRepoStat {
	private static WebDriver driver;


	public static void main(RepoInfo repo_info, String version) throws Exception {
		try {
			driver = new FirefoxDriver();
			String[] stats = new String[9];

			stats = new String[]{"", "", "", "", "", "", "", "", ""};

			// Load the html test runner in the browser to get coverage
			driver.get(repo_info.getRepo_url());
			System.out.print("Loading the URL " + repo_info.getRepo_url());

			// make sure all tests are finished before getting the trace
			waitForPageToLoad();

			List<WebElement> socials = driver.findElements(By.xpath("//a[@class='Link Link--muted']"));
			stats[0] = socials.get(1).getText().substring(0, socials.get(1).getText().indexOf(" ") );
			System.out.println(socials.get(0).getText() + "\t");   //stars
			stats[1] = socials.get(2).getText().substring(0, socials.get(2).getText().indexOf(" ") );
			System.out.println(socials.get(1).getText() + "\t");   //watching
			stats[2] = socials.get(3).getText().substring(0, socials.get(3).getText().indexOf(" ") );
			System.out.println(socials.get(2).getText() + "\t");   //forks

			List<WebElement> nums = driver.findElements(By.xpath("//a[@class='Link--primary no-underline Link']"));
			System.out.println(nums.get(0).getText() + "\t");    //realeses
			stats[3] = nums.get(0).getText().substring(nums.get(0).getText().lastIndexOf(" ") + 1 );
			//System.out.println(nums.get(1).getText() + "\t");     //used by
			//stats[4] = nums.get(1).getText().substring(nums.get(1).getText().lastIndexOf(" ") + 1 );


			List<WebElement> other = driver.findElements(By.xpath("//a[@class='ml-3 Link--primary no-underline']"));
			System.out.println(other.get(0).getText() + "\t");   //branches
			stats[5] = other.get(0).getText().substring(0, other.get(0).getText().indexOf(" ") );
			System.out.println(other.get(1).getText() + "\t");   //tags
			stats[6] = other.get(1).getText().substring(0, other.get(1).getText().indexOf(" ") );

			List<WebElement> commits = driver.findElements(By.xpath("//span[@class='d-none d-sm-inline']"));
			System.out.println(commits.get(1).getText() + "\t");   //commits
			stats[7] = commits.get(1).getText().substring(0, commits.get(1).getText().indexOf(" ") );

			List<WebElement> contributors = driver.findElements(By.xpath("//span[@class='Counter ml-1']"));
			System.out.println(contributors.get(1).getText() + " contributors\t");   //contributors
			stats[8] = contributors.get(1).getText();


			SaveResults.WriteResultToExcel_GetRepoStat(repo_info.getRepo_name(),repo_info.getRepo_url(), version, stats );
			System.out.println("Results saved succesfully!");


			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void driverExecute(String javascript) throws Exception {
		((JavascriptExecutor) driver).executeScript(javascript);
	}


	private static void waitForPageToLoad() {  // could be used to make sure the js code execution happens after the page is fully loaded
		String pageLoadStatus = null;
		do {
			pageLoadStatus = (String)((JavascriptExecutor) driver).executeScript("return document.readyState");
			//System.out.print(".");
		} while (!pageLoadStatus.equals("complete"));
		System.out.println();
		System.out.println("Page Loaded.");
	}

}
