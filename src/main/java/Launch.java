import core.RepoInfo;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Launch {

    private static String config_path = "C:/Users/Mauro/IdeaProjects/TestScanner/config/config.json";   //config.json path

    public static void main(String[] args) {

        try {

            RepoInfo repo = load_repo_info();

            //launch
            GetRepoStat.main(repo);
            TestCodePropertyAnalyzer.main(repo);
            CoverageCalculator.main(repo);
            ProductionCodeCoverageAnalyzer.main(repo);

        } catch ( Exception e) {
            e.printStackTrace();
        }
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

        RepoInfo repo = new RepoInfo(name,url,testFolderPath,cloneDirPath, test_runner);

        System.out.println(repo.toString());

        return repo;
    }


}
