package core;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;


public class RepoCloner {

    /**
     * Java class that clones github repository.
    */


    /**
     * Function that automatically clones the repository.
     * @param repo information of the repository to be cloned.
     */
    public static void cloneRepo(RepoInfo repo) {

        RepoInfo r = new RepoInfo();

        File repoName = new File(repo.getCloning_dir_path() + "/" + repo.getRepo_name());

        if (repoName.exists() && repoName.listFiles().length > 0) {
            System.out.println("Folder already exists, abort cloning!");
        }else {
            try {
                System.out.println("Cloning repository: " + repo.getRepo_url() + " in " + repoName);

                Git.cloneRepository()
                        .setURI(repo.getRepo_url())
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

