import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Rasmus Soome on 10/6/2016.
 * Code from: http://stackoverflow.com/questions/4157303/how-to-execute-cmd-commands-via-java
 * This class is for compiling the files in a specified folder.
 */
public class Compile {

    /**
     * The method to compile the specified files.
     * @param toCompile the files to compile
     * @param canonicalPath the folder from which the files can be found
     * @param verbose whether to print extra information
     * @throws IOException
     * @throws InterruptedException
     */
    public void compile(String toCompile, String canonicalPath, boolean verbose) throws IOException, InterruptedException {
        //if there is anything to compile, start compiling
        if (toCompile.length() > 0) {
            //Set the command to start cmd
            String[] command = {"cmd",};
            if (verbose) System.out.println("Getting runtime and compiling files." + System.lineSeparator());
            //Get runtime from system and start cmd
            Process p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            //Set cmd character encoding to UTF-8
            stdin.println("chcp 65001");
            //Change drive to the drive specified in the given path
            stdin.println(canonicalPath.substring(0, 2));
            //Change to directory specified in the given path
            stdin.println("cd " + canonicalPath.substring(2, canonicalPath.length()));
            //Compile the given files
            stdin.println("javac -cp . -encoding utf8 " + toCompile);
            stdin.close();
            int returnCode = p.waitFor();
            if(verbose) System.out.println("Return code = " + returnCode);

            System.out.println("Compiling completed." + System.lineSeparator());
        } else {
            System.out.println("There's nothing to compile" + System.lineSeparator());
        }
    }
}
