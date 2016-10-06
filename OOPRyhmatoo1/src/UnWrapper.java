import java.io.*;


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
        String canonicalPath = folder.getCanonicalPath();

        //Iterate through command line arguments to set new values for verbose and compile
        for (String arg : args) {
            if (args.length > 1) {
                if (arg.equals("-v")) {
                    verbose = true;
                }
                if (arg.equals("-compile")) {
                    compile = true;
                }
            }
        }

        PackageRemover remover = new PackageRemover(verbose, canonicalPath, folder);
        String toCompile = remover.remove();

        if (compile) {
            Compile compiler = new Compile();
            compiler.compile(toCompile, canonicalPath, verbose);
        }
    }
    //Mõelda, kas saab programmile veel mingit funktsionaalsust juurde lisada.
}
