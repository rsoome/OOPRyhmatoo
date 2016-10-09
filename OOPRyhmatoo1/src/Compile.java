import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Rasmus Soome on 10/6/2016.
 * Some of the code is from: http://stackoverflow.com/questions/4157303/how-to-execute-cmd-commands-via-java
 * This class is for compiling the files in a specified folder.
 */


//TODO: Hetkel ei salli kompilaator täpitähtedega packge'id. ülejäänud funktsionaalsus paistab töötavat

public class Compile implements ColoredText{

    private List<String> toCompile;
    private boolean verbose;

    public Compile(List<String> toCompile, boolean verbose) {
        this.toCompile = toCompile;
        this.verbose = verbose;
        if (verbose) System.out.println("Compiler created." + System.lineSeparator());
    }

    public void iterateThroughFilesAndCompile() throws IOException, InterruptedException {
        for (int i = 0; i < toCompile.size(); i++){
            String file = toCompile.get(i);
            if (file.contains(".java")){
                String path = toCompile.get(i - 1);
                if(verbose) System.out.println("Compiling files in: " + ANSI_BLUE + path + ANSI_RESET + System.lineSeparator());
                String files = "";
                for (int j = i; j < toCompile.size(); j++){
                    file = toCompile.get(j);
                    if (file.contains(".java")) files += file + " ";
                    if (!file.contains(".java") || j == toCompile.size() - 1){
                        i = j;
                        break;
                    }
                }
                compile(path, files);
            }
        }
    }

    /**
     * The method to compile the specified files.
     * @throws IOException
     * @throws InterruptedException
     */
    private void compile(String path, String files) throws IOException, InterruptedException {

        //Set the command to start cmd
        String[] command = {"cmd",};
        if (verbose) System.out.println("Getting runtime and compiling files." + System.lineSeparator());
        //Get runtime from system and start cmd
        Process p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println("@echo off");
        //Set cmd character encoding to UTF-8
        stdin.println("chcp 65001");
        //Change drive to the drive specified in the given path
        String drive = path.substring(0, 2);
        stdin.println(drive);
        //Change to directory specified in the given path
        String dir = path.substring(2, path.length());
        stdin.println("cd " + dir);
        //Compile the given files
        stdin.println("javac -cp . " +  " -encoding utf8 " + files);
        stdin.close();
        int returnCode = p.waitFor();
        if(verbose) System.out.println("Return code = " + returnCode);
        System.out.println(ANSI_GREEN + "Compiling completed." + ANSI_RESET + System.lineSeparator());
    }
}
