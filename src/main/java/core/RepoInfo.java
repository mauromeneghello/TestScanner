package core;

public class RepoInfo {
    public  String repo_name;
    public  String repo_url;
    public  String repo_test_folder_path;
    public  String cloning_dir_path;
    public  String html_test_runner_path;


    public RepoInfo(String repo_name, String repo_url, String repo_test_folder_path, String cloning_dir_path, String html_test_runner_path) {
        this.repo_name = repo_name;
        this.repo_url = repo_url;
        this.repo_test_folder_path = repo_test_folder_path;
        this.cloning_dir_path = cloning_dir_path;
        this.html_test_runner_path = html_test_runner_path;
    }

    public RepoInfo() {
        this.repo_name = "";
        this.repo_url = "";
        this.repo_test_folder_path = "";
        this.cloning_dir_path = "";
        this.html_test_runner_path = "";
    }


    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getRepo_test_folder_path() {
        return repo_test_folder_path;
    }

    public void setRepo_test_folder_path(String repo_test_folder_path) {
        this.repo_test_folder_path = repo_test_folder_path;
    }

    public String getCloning_dir_path() {
        return cloning_dir_path;
    }

    public void setCloning_dir_path(String cloning_dir_path) {
        this.cloning_dir_path = cloning_dir_path;
    }

    public String getHtml_test_runner_path() {
        return html_test_runner_path;
    }

    public void setHtml_test_runner_path(String html_test_runner) {
        this.html_test_runner_path = html_test_runner;
    }

    @Override
    public String toString() {
        return "RepoInfo{" +
                "repo_name='" + repo_name + '\'' +
                ", repo_url='" + repo_url + '\'' +
                ", repo_test_folder_path='" + repo_test_folder_path + '\'' +
                ", cloning_dir_path='" + cloning_dir_path + '\'' +
                ", html_test_runner='" + html_test_runner_path + '\'' +
                '}';
    }
}
