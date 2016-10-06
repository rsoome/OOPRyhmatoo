
/**
 * Created by Rasmus Soome on 10/5/2016.
 * Interface makes output text coloring human readable and easier to write.
 * Doesn't contain any methods only variables linked to color corresponding to variable name.
 * Color codes and variable names from: http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
 */
public interface ColoredText {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLACK = "\u001B[30m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_PURPLE = "\u001B[35m";
    String ANSI_CYAN = "\u001B[36m";
    String ANSI_WHITE = "\u001B[37m";
}
