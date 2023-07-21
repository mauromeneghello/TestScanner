import core.RepoCloner;
import core.RepoInfo;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Launch {

    private static String config_path = "C:/Users/Mauro/IdeaProjects/TestScanner/config/config.json";   //config.json path

    public static void main(String[] args) {

        try {

            //load repo info in config.json
            RepoInfo repo = load_repo_info();

            //clone the repository because TestCodePropertyAnalyzer.java and CoverageCalculator.java need it
            RepoCloner.cloneRepo(repo);
            String current_branch = RepoCloner.getCurrentBranchOrVersion(repo);
            //System.out.println(current_branch);


            //launch
            launcher(repo,"main");                   //launch actual ("main") version analysis

            if(repo.getTime_development()){                 //launch other versions analysis


                List<String> versions = RepoCloner.getImportantVersions(repo);
                for (String v : versions) {
                    //System.out.println(v);
                    RepoCloner.change_version(repo,v);                          //change version and analyse it
                    launcher(repo,v);
                }

                if (current_branch != null) {
                    RepoCloner.change_version(repo,current_branch);            //back to main (current) version
                }
            }


        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public static void launcher(RepoInfo repo, String version) throws Exception {
        GetRepoStat.main(repo, version);
        TestCodePropertyAnalyzer.main(repo, version);
        CoverageCalculator.main(repo, version);
        ProductionCodeCoverageAnalyzer.main(repo,version);
    }

    public static RepoInfo load_repo_info() throws IOException {

        // read config.json
        String content = new String(Files.readAllBytes(Paths.get(config_path)));

        // create json object with config info
        JSONObject jsonConfig = new JSONObject(content);

        // parse repository informations
        JSONObject repoInfo = jsonConfig.getJSONObject("repo_info");
        String name = repoInfo.getString("name");
        String url = repoInfo.getString("url");
        String testFolderPath = repoInfo.getString("testFolderPath");
        String cloneDirPath = repoInfo.getString("cloneDirPath");
        String test_runner = repoInfo.getString("html_test_runner_path");
        String JSCoverPath = repoInfo.getString("JSCover_path");
        boolean time_dev = repoInfo.getBoolean("time_development");

        //create object RepoInfo
        RepoInfo repo = new RepoInfo(name,url,testFolderPath,cloneDirPath, test_runner, JSCoverPath, time_dev);

        System.out.println(repo.toString());

        return repo;
    }


}
