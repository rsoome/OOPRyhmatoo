import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Rasmus Soome on 10/6/2016.
 * This class is created to remove packages from .java files
 */
public class PackageRemover implements ColoredText{
    private boolean verbose;
    private String canonicalPath;
    private File file;

    public PackageRemover(boolean verbose, String canonicalPath, File file) throws IOException {
        this.verbose = verbose;
        this.canonicalPath = canonicalPath;
        this.file = file;
    }

    /**
     * Method that removes packages from .java files in the given folder
     * @return all the .java files from the folder to be passed on for compiling.
     * @throws IOException
     */
    public String remove() throws IOException {
        if (verbose) System.out.println("The folder in which I'm looking for files to edit: "
                + ANSI_BLUE + file.toString() + ANSI_RESET + System.lineSeparator());
        String[] filelist = file.list();
        if (verbose) System.out.println("Files found in the folder: " + arrayToString(filelist) + System.lineSeparator());
        String filesToCompile = "";
        //We create the line variable without a value. The line will only have a value if it contains the line
        // on which the package is stated.
        String line;
        for (String file : filelist) {
            String fileWLocation = canonicalPath + "\\" + file;
            //If the observed file is .java, see if it needs changing
            if (file.contains(".java")) {
                if (verbose) System.out.println("Looking at: " + ANSI_BLUE + file + ANSI_RESET + System.lineSeparator());
                BufferedReader br = new BufferedReader(new FileReader(fileWLocation));
                line = br.readLine();
                //Check the content of the file to find a line containing package
                while (!line.contains("package")) {
                    //If we have reached a line containing "class" or "interface" we can be sure that the file is not
                    //in a package and can stop checking further lines.
                    if (line.contains("class") || line.contains("interface")) {
                        if (verbose) System.out.println(ANSI_RED + "This file does not have a package" + ANSI_RESET + System.lineSeparator());
                        //The file does not contain a package line so we set the value of the line to null again.
                        line = null;
                        break;
                    } else line = br.readLine();
                }
                //Add the file to a string of .java files in the folder for later compiling
                filesToCompile += file + " ";
                //If the line has a value that means it contains the package line so we remove the line from the file
                if (line != null) {
                    if (verbose) System.out.println(ANSI_PURPLE + "Removing package" + ANSI_RESET + System.lineSeparator());
                    Path path = Paths.get(fileWLocation);
                    Charset charset = StandardCharsets.UTF_8;
                    String content = new String(Files.readAllBytes(path), charset);
                    //Replace the line with an empty string.
                    content = content.replaceAll(line, "");
                    Files.write(path, content.getBytes(charset));
                }
                //We're done with this file, set the line value to null again
                line = null;
            }
        }
        System.out.println("Package removing completed." + System.lineSeparator());
        return filesToCompile;
    }

    //The method creates a colored text string from a string array.
    private String arrayToString(String[] array){
        String arrayInString = "";
        for(int i = 0; i < array.length; i++){
            arrayInString += ANSI_BLUE + array[i] + ANSI_RESET;
            if (i != array.length) arrayInString += ", ";
        }
        return arrayInString;
    }
}
