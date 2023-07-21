package core;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListTagCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;


public class RepoCloner {

    /**
     * Java class that clones github repository.
    */


    /**
     * Function that automatically clones the repository.
     * @param repo information of the repository to be cloned.
     */
    public static void cloneRepo(RepoInfo repo) {

        File repoPath = new File(repo.getCloning_dir_path() + "/" + repo.getRepo_name());

        if (repoPath.exists() && repoPath.listFiles().length > 0) {
            System.out.println("Folder already exists, abort cloning!");
        }else {
            try {
                System.out.println("Cloning repository: " + repo.getRepo_url() + " in " + repoPath);

                Git.cloneRepository()
                        .setURI(repo.getRepo_url())
                        .setDirectory(repoPath)
                        .call();

                Thread.sleep(5000);
                System.out.println("Repository cloned successfully!");

            } catch (GitAPIException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("Failed to clone the repository.");
            }
        }
    }

    public static void change_version(RepoInfo repo,String version) throws GitAPIException {

        String v =  extractVersionWithoutDate(version);

        try (Git git = Git.open(new File(repo.getCloning_dir_path() + "/" + repo.getRepo_name()))) {
            CheckoutCommand checkout = git.checkout();
            checkout.setName(v); // version where I want to move.
            checkout.call();
            System.out.println("Moved to version " + v + " successfully!");
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }


    public static String extractVersionWithoutDate(String version) {
        int indexOfParenthesis = version.indexOf("(");

        if (indexOfParenthesis >= 0) {                              //if there is "(" in version, parse it
            return version.substring(0, indexOfParenthesis);
        }

        return version;
    }


    public static List<String> getImportantVersions(RepoInfo repo) {
        List<String> versionInfoList = new ArrayList<>();

        File repoDir = new File(repo.getCloning_dir_path() + "/" + repo.getRepo_name());
        try {
            // Open the cloned repository
            Git git = Git.open(repoDir);

            // Get the tags (versions) of the repository
            ListTagCommand listTagCommand = git.tagList();
            List<Ref> tags = listTagCommand.call();

            for (Ref tag : tags) {
                // Get the version name (tag)
                String version = tag.getName().replace("refs/tags/", "");

                // Get the commit ID of the tag
                String commitID = tag.getObjectId().getName();

                // Get the commit date (year) for the tag
                String commitDate = getCommitDate(repo.getCloning_dir_path() + "/" + repo.getRepo_name() , commitID);

                String year = "";
                if(commitDate != null) {
                    year = commitDate.substring(0, 4);
                }
                if(year != "") {
                    versionInfoList.add(version + "(" + year + ")");
                }
            }

            git.close();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve version information.");
        }

        return versionInfoList;
    }


    public static String getCommitDate(String repositoryPath, String commitID) {
        try (Git git = Git.open(new File(repositoryPath))) {
            Iterable<RevCommit> commits = git.log().add(git.getRepository().resolve(commitID)).setMaxCount(1).call();
            for (RevCommit commit : commits) {
                // Get the commit timestamp as Instant
                Instant commitTimestamp = Instant.ofEpochSecond(commit.getCommitTime());

                // Convert Instant to LocalDate
                LocalDate commitDate = commitTimestamp.atZone(ZoneId.systemDefault()).toLocalDate();

                // Get the year from the LocalDate
                int year = commitDate.getYear();

                // Return the year as a String
                return Integer.toString(year);
            }
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentBranchOrVersion(RepoInfo repo) {
        try (Git git = Git.open(new File(repo.getCloning_dir_path() + "/" + repo.getRepo_name()))) {
            // Get current branch name (HEAD)
            Ref head = git.getRepository().findRef("HEAD");

            return head.getTarget().getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

