import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by Rasmus Soome on 10/6/2016.
 * Some of the code is from: http://stackoverflow.com/questions/4157303/how-to-execute-cmd-commands-via-java
 * This class is for compiling the files in a specified folder.
 */


//TODO: Hetkel ei salli kompilaator täpitähtedega packge'id. ülejäänud funktsionaalsus paistab töötavat

public class Compile implements ColoredText{

    private boolean verbose;

    public Compile(boolean verbose) {
        this.verbose = verbose;
        if (verbose){
            System.out.println("Compiler created." + System.lineSeparator());
        }
    }

    public void findSubFoldersAndCompile(File folder) throws IOException, InterruptedException {

        String path = new String(folder.getCanonicalPath().getBytes(Charset.defaultCharset()));
        boolean containsCompilable = false;

        File[] fileList = folder.listFiles();
        if (fileList != null) {
            for (File currentFile : fileList) {
                if (currentFile.isDirectory()) {
                    findSubFoldersAndCompile(currentFile);
                } else {
                    containsCompilable = true;
                }

            }
        } else {
            System.out.println(ANSI_RED + "The specified folder does not exist." + ANSI_RESET);
        }

        if (containsCompilable) compile(path);
    }

    /**
     * The method to compile the specified files.
     * @throws IOException
     * @throws InterruptedException
     */
    private void compile(String path) throws IOException, InterruptedException {

        //Set the command to start cmd
        String[] command = {"cmd",};
        if (verbose){
            System.out.println("Getting runtime and compiling files." + System.lineSeparator());
            System.out.println("Compiling at: " + ANSI_BLUE + path + ANSI_RESET + System.lineSeparator());
        }
        //Get runtime from system and start cmd
        Process p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println("@echo off");
        //Change drive to the drive specified in the given path
        String drive = path.substring(0, 2);
        stdin.println(drive);
        //Change to directory specified in the given path
        String dir = path.substring(2, path.length());
        stdin.println("cd " + dir);
        //Compile the given files
        stdin.println("javac -cp . -encoding utf8 ./*.java 2> output.txt");
        stdin.close();
        int returnCode = p.waitFor();
        if(verbose) System.out.println("Return code = " + returnCode);


        File outputFile = new File(path + "\\output.txt");
        try {
            Scanner output = new Scanner(outputFile);
            if (!(output.hasNextLine())) {
                System.out.println(ANSI_GREEN + "Compiling completed." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Compiling failed.");
                while (output.hasNextLine()) {
                    System.out.println(output.nextLine());
                }
                System.out.println(ANSI_RESET);
            }
            output.close();
            outputFile.delete();
        } catch (Exception e){
            System.out.println(ANSI_RED + "The path " + path + " could not be found." + ANSI_RESET);
        }
    }
}
