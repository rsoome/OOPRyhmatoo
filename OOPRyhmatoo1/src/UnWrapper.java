import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Rasmus Soome on 9/14/2016.
 */
public class UnWrapper {
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean verbose = false;
        boolean compile = false;
        for (int i = 0; i < args.length - 1; i++) {
            if (args.length > 1) {
                if (args[i].equals("-v")) {
                    verbose = true;
                }
                if (args[i].equals("-compile")) {
                    compile = true;
                }
            }
        }
        File file = new File(args[args.length - 1]);
        if(verbose) System.out.println(file.toString());
        String[] filelist = file.list();
        if(verbose) System.out.println("Files found in the folder: " + Arrays.toString(filelist));
        String loc = file.getCanonicalPath();
        String files = "";
        String toRemove = "";
        String line;
        for (String el : filelist) {
            String elwloc = loc + "\\" + el;
            if(el.contains(".java")) {
                if (verbose) System.out.println(elwloc);
                BufferedReader br = new BufferedReader(new FileReader(elwloc));
                line = br.readLine();
                while(!line.contains("package") ){
                    if(line.contains("class")){
                        if (verbose) System.out.println("This file does not have a package");
                        line = null;
                        break;
                    }
                    else line = br.readLine();
                }
                files += el + " ";
                if (line != null) {
                    if (verbose) System.out.println("Removing package");
                    Path path = Paths.get(elwloc);
                    Charset charset = StandardCharsets.UTF_8;
                    String content = new String(Files.readAllBytes(path), charset);
                    content = content.replaceAll(line, "");
                    Files.write(path, content.getBytes(charset));
                }
                line = null;
            }
        }

        if(files.length() > 0 && compile) {
            String path = System.getenv("Path").split(";")[0];
            String[] command = {"cmd",};
            Process p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            if (verbose) System.out.println("Changing cmd encoding to UTF-8");
            stdin.println("chcp 65001");
            if (verbose) System.out.println("Changing to drive " + loc.substring(0, 2));
            stdin.println(loc.substring(0, 2));
            if (verbose) System.out.println("Going to " + loc.substring(2, loc.length()));
            stdin.println("cd " + loc.substring(2, loc.length()));
            //stdin.println("path = \"" + path + "\"");
            if (verbose) System.out.println("Compiling files.");
            stdin.println("javac -cp . -encoding utf8 " + files);
            stdin.close();
            int returnCode = p.waitFor();
            System.out.println("Return code = " + returnCode);
        } else System.out.println("There's nothing to compile");
    }
}
