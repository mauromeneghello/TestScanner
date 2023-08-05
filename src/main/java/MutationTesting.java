import core.RepoInfo;

import java.io.*;

public class MutationTesting {

    public static void main(RepoInfo repo) {
        String projectDir = repo.getCloning_dir_path() + "/" + repo.getRepo_name();
        try {
            // command
            //String[] command = { "cmd.exe", "/c", "start", "/D", "\"" + projectDir + "\"", "cmd.exe", "/K", "stryker init && exit"};
            String[] command = { "cmd.exe", "/c", "start", "/D", "\"" + projectDir + "\"", "cmd.exe", "/C", "npm install && npm install -g stryker-cli && stryker init && stryker run" };
            // process creation
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // output and input redirection
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);

            // process start
            Process process = processBuilder.start();

            // waiting for the process to terminate
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

