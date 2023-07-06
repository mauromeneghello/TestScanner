package core;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;


public class RepoCloner {

    /*
     * 1) Java class that clones github repository.
    */

    private static String destinationPath = "C:/Users/Mauro/Desktop/Universita/Tesi/RepoList";

    public static void cloneRepo(String repositoryUrl) {

        File repoName = new File(destinationPath + "/" + repositoryUrl.substring(repositoryUrl.lastIndexOf("/") + 1));

        if (repoName.exists() && repoName.listFiles().length > 0) {
            System.out.println("Folder already exists, abort cloning!");
        }else {
            try {
                System.out.println("Cloning repository: " + repositoryUrl + " in " + repoName);

                Git.cloneRepository()
                        .setURI(repositoryUrl)
                        .setDirectory(repoName)
                        .call();

                System.out.println("Repository cloned successfully!");
            } catch (GitAPIException e) {
                e.printStackTrace();
                System.out.println("Failed to clone the repository.");
            }
        }
    }
}

