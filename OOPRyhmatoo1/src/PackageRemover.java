import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * Created by Rasmus Soome on 10/6/2016.
 * This class is created to remove packages from .java files
 */
public class PackageRemover implements ColoredText{
    private boolean verbose;

    public PackageRemover(boolean verbose) throws IOException, InterruptedException {
        this.verbose = verbose;
    }


    public void remove(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (verbose) System.out.println("The folder in which I'm looking for files to edit: "
                + ANSI_BLUE + canonicalPath + ANSI_RESET + System.lineSeparator());
        String[] wrongEncodingList = file.list();
        String[] fileList = new String[wrongEncodingList.length];
        for (int i = 0; i < wrongEncodingList.length; i++){
            String temp = new String (wrongEncodingList[i].getBytes("UTF-8"));
            System.out.println("EncodedRight: " + temp);
            fileList[i] = temp;
        }
        if (verbose) System.out.println("Files found in the folder: " + arrayToString(fileList) + System.lineSeparator());
        //We create the line variable without a value. The line will only have a value if it contains the line
        // on which the package is stated.
        String line;
        for (int i = 0; i < fileList.length; i++) {
            String currentFile = wrongEncodingList[i];
            String fileWLocation = canonicalPath + "\\" + currentFile;
            //Enter recursively if the current file is a directory.
            File newFile = new File(fileWLocation);
            if (newFile.isDirectory()){
                if(verbose) System.out.println(ANSI_YELLOW + "Found a subdirectory: " + ANSI_BLUE + fileWLocation
                        + ANSI_YELLOW + ". Entering recursively." + ANSI_RESET + System.lineSeparator());
            }
            //If the observed file is .java, see if it needs changing
            if (currentFile.contains(".java")) {
                if (verbose) System.out.println("Looking at: " + ANSI_BLUE + currentFile + ANSI_RESET + System.lineSeparator());
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileWLocation), "UTF-8"));
                line = br.readLine();
                //Check the content of the file to find a line containing "package "
                while (!(line == null)) {
                    if (verbose) System.out.println("Looking at line: " + line);
                    //If we have reached a line containing "class" or "interface" we can be sure that the file is not
                    //in a package and can stop checking further lines.
                    if (line.matches("package .+(\\..*)*")) {

                        if (verbose) System.out.println(ANSI_PURPLE + "Removing package" + ANSI_RESET + System.lineSeparator());
                        Path path = Paths.get(fileWLocation);
                        String content = readFile(br);
                        //Replace the line with an empty string.
                        content = content.replaceAll(line, "");
                        Files.write(path, content.getBytes("UTF-8"));
                        break;
                    } else if(line.matches(".*\\{")  && !line.matches("\\s*/.*")) {
                        if (verbose) System.out.println(ANSI_RED + "This file does not have a package" + ANSI_RESET + System.lineSeparator());
                        break;
                    } else line = br.readLine();
                }
            }
        }
        System.out.println(ANSI_GREEN + "Package removing in " + ANSI_BLUE + canonicalPath + ANSI_GREEN + " completed." +
                ANSI_RESET + System.lineSeparator());
    }

    //The method creates a colored text string from a string array.
    private String arrayToString(String[] array){
        String arrayInString = "";
        for(int i = 0; i < array.length; i++){
            arrayInString += ANSI_BLUE + array[i] + ANSI_RESET;
            if (i != array.length - 1) arrayInString += ", ";
        }
        return arrayInString;
    }

    private String readFile(BufferedReader br) throws IOException {
        String content = "";
        String line = br.readLine();
        while (line != null){
            content += line + System.lineSeparator();
            line = br.readLine();
        }
        return content;
    }
}
