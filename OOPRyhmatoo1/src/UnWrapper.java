
import java.io.*;
import java.util.List;


/**
 * Created by Rasmus Soome on 9/14/2016.
 * The program removes packages from .java files and compiles them if needed.
 */
public class UnWrapper {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Initialize verbose for printing extra information of the process.
        boolean verbose = false;
        //Initialize compile to know whether or not to also compile the files
        boolean compile = false;
        //Initalize folder in which the files will be looked for from the command line argument entered last
        File folder = new File(args[args.length - 1]);

        //Iterate through command line arguments to set new values for verbose and compile
        for (String arg : args) {
            if (args.length > 1) {
                if (arg.equals("-v")) {
                    verbose = true;
                }
                if (arg.equals("-compile")) {
                    compile = true;
                }
                if (arg.equals("-help")){
                    System.out.println("-help displays this help, -compile also compiles the given files, -v verbose" +
                            " for displaying extra information, the last argument shoud be the root path to files, root" +
                            " path can contain subfolders which will be looked into also.");
                }
            }
        }

        //Create package remover instance and remove packages
        PackageRemover remover = new PackageRemover(verbose);
        remover.remove(folder);

        //If the program is set to compile then create a compiler instance and compile
        if (compile) {
            Compile compiler = new Compile(verbose);
            compiler.findSubFoldersAndCompile(folder);
        }
    }
    //Mõelda, kas saab programmile veel mingit funktsionaalsust juurde lisada.
}
