package Ver2;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SmallFour
 */
public class LinesCountInputList {

    public int countMethodLines(ArrayList<String> calculateLines) {
        int linesCount = 0;
        try {
            @SuppressWarnings("resource")
            String temp;
            String[] tempArr = null;
            z:
            while (!calculateLines.isEmpty()) {
                temp = calculateLines.remove(0);
                temp = temp.trim();
                linesCount += 1;
                tempArr = temp.split("\\s+");
                if (tempArr[0].equals("")) {
                    linesCount -= 1;
                    continue;
                }
                if (tempArr[0].equals("{")) {
                    linesCount -= 1;
                    continue;
                }
                if (tempArr[0].equals("}") || tempArr[0].equals("});")) {
                    linesCount -= 1;
                    if (tempArr.length != 1
                            && (tempArr[1].equals("catch") || tempArr[1].equals("else"))) {
                        linesCount += 1;
                    }
                    continue;
                }
                if (tempArr[0].charAt(0) == '/' && tempArr[0].charAt(1) == '/') {
                    linesCount -= 1;
                    continue;
                }
                if (tempArr[0].equals("/*") || (tempArr[0].charAt(0) == '/' && tempArr[0].charAt(1) == '*')) {
                    linesCount -= 1;
                    if (tempArr[tempArr.length - 1].charAt(tempArr[tempArr.length - 1].length() - 1) == '/' && tempArr[tempArr.length - 1].charAt(tempArr[tempArr.length - 1].length() - 2) == '*') {
                        continue z;
                    }
                    while ((temp = calculateLines.remove(0)) != null) {
                        temp = temp.trim();
                        tempArr = temp.split("\\s+");
                        if (tempArr[tempArr.length - 1].equals("*/")
                                || (tempArr[tempArr.length - 1].charAt(tempArr[tempArr.length - 1].length() - 1) == '/' && tempArr[tempArr.length - 1].charAt(tempArr[tempArr.length - 1].length() - 2) == '*')) {
                            break;
                        }
                    }
                    continue;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return linesCount;
    }
}
