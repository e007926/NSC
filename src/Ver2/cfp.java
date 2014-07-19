package Ver2;



import java.io.*; //引入java.io類別庫的所有類別
import java.util.*; //引入java.util.Scanner類別

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
/**
 *
 * @author Lucius & SmallFour
 * 
 * 跑完start_count()
 * 
 * 再執行getTotalCfp()、getTotalHighComplexity()、getTotalHighMethod()、getTotalHighClass()
 * 
 */

class Countcfp {
    String fileName;
    private double totalLines = 0;
    ArrayList<String> methodList = new ArrayList<String>(); // 方法名稱
    ArrayList<Double> methodCfpList = new ArrayList<Double>(); // 同名方法平均功能點數
    ArrayList<Integer> methodNumList = new ArrayList<Integer>(); // 同名方法個數
    ArrayList<Integer> methodFirstLineList = new ArrayList<Integer>(); // 第一次出現的行數
    ArrayList<Double> methodLinesList = new ArrayList<Double>(); // 同名方法平均行數
    // total data
    private double cfp = 0;
    private double hComplexityCfp = 0;
    private double hMethodCfp = 0;
    private double hClassCfp = 0;
    
    public Countcfp() {
    }

    public Countcfp(String fileName) {
        this.fileName = fileName;
    }
    
    void start_count(String methodName) throws Exception {
        HashSet hs = new HashSet();
        make_list();
        for(int a=0;a<methodCfpList.size();a++){
        	System.out.println("methodCfpList:"+methodCfpList.get(a));
        }
        System.out.println("make_list finish");
        

        count_cfp(methodFirstLineList.get(methodList.indexOf(methodName)), methodName, hs);
        for(int a=0;a<methodCfpList.size();a++){
        	System.out.println("methodCfpList:"+methodCfpList.get(a));
        }
        System.out.println("count_cfp finish");
    }

    double total_lines() {
        return totalLines;
    }
    double getTotalCfp(){
        return this.cfp;
    }
    double getTotalHighComplexity(){
        return this.hComplexityCfp;
    }
    double getTotalHighMethod(){
        return this.hMethodCfp;
    }
    double getTotalHighClass(){
        return this.hClassCfp;
    }
    
    
   /* void getArrayList() throws Exception{
    	CyclomaticComplexity cc=new CyclomaticComplexity();
    	cc.CyclomaticComplexity(fileName);
    	
			for(int i=0;i<cc.methodNameAll.size();i++)
			{
				methodNameAllTemp.add(cc.methodNameAll.get(i));
			}
			
		}*/
    
    
    String show_detail() {
        String str = String.format("%-25s%-7s%-7s%-7s%-7s\n", "方法名稱:", "平均CFP", "同名方法數", "首次出現行數", "同名方法平均行數");
        for (String s : methodList) {
            int index = methodList.indexOf(s);
            str += String.format("%-30s%-10.1f%-10d%-10d%-10f\n", s, methodCfpList.get(index), methodNumList.get(index), methodFirstLineList.get(index), methodLinesList.get(index));
        }
        return str;
    }

    void make_list() {
        String[] form = {".*void ", ".*static double ", ".*static int ", ".*static float ", ".*static long ",
            ".*static byte ", ".*static short ", ".*static char ", ".*static String ",
            ".*static boolean ", ".*static ", ".*double ", ".*int ", ".*float ", ".*long ",
            ".*byte ", ".*short ", ".*char ", ".*String ", ".*boolean ", ".*static "};
        LineNumberReader inputFile = null;
        try {
            inputFile = new LineNumberReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
        }
        ArrayList<String> tempLines = new ArrayList<String>();
        try {
            String strData = inputFile.readLine();
            for (int i = 0; i < inputFile.getLineNumber(); i++) {
                for (String s : form) {
                    if (strData.matches(s + ".*(.*).*") && !strData.matches(".*;.*")) {
                        tempLines = new ArrayList<String>();
                        char[] ch = strData.toCharArray();
                        for (int j = 0; j < ch.length; j++) {
                            if (ch[j] == '"') { //忽略字串
                                j++;
                                for (; j < ch.length; j++) {
                                    if (ch[j] == '"') {
                                        int c = 0;
                                        for (int k = j - 1; ch[k] == '\\'; k--) {
                                            c++;
                                        }
                                        if (c % 2 == 0) {
                                            break;
                                        }
                                    }
                                }
                            } else if (ch[j] == '\'') { // 忽略字元
                                j += 2;
                                if (ch[j + 1] == '\'') {
                                    j++;
                                }
                            } else if (ch[j] == '/') { // 忽略註解
                                j++;
                                if (ch[j] == '/') {
                                    break;
                                } else if (ch[j] == '*') {
                                    j++;
                                    L2:
                                    for (; i < inputFile.getLineNumber(); i++) {
                                        for (j = 0; j < ch.length; j++) {
                                            if (ch[j] == '/' && j > 0 && ch[j - 1] == '*') {
                                                break L2;
                                            }
                                        }
                                        strData = inputFile.readLine();
                                        ch = strData.toCharArray();
                                    }
                                }
                            } else if (ch[j] == '(') {
                                String str = "";
                                for (int k = j - 1; k >= 0; k--) {
                                    if (isValidChar(ch[k])) {
                                        str = ch[k] + str;
                                        if (k - 1 >= 0 && !isValidChar(ch[k - 1])) {
                                            break;
                                        }
                                    }
                                }
                                int z = i;
                                int count = 1;
                                tempLines.add(strData);
                                while (count != 0) {
                                    i++;
                                    strData = inputFile.readLine();
                                    tempLines.add(strData);
                                    if ((strData.matches(".*\\}.*\\{") & !strData.matches(".*\\\\\\}.*\\\\\\{.*") & !strData.matches(".*\'}\'.*") & !strData.matches("\\s*if.*\\{")) || (strData.matches(".*\\}.*\\{\\s*//.*") & !strData.matches(".*\\\\\\}.*\\\\\\{.*\\s*//.*") & !strData.matches(".*\'}\'.*\\s*//.*") & !strData.matches("\\s*if.*\\{\\s*//.*"))) {
                                    } else if (strData.matches(".*\\{") || strData.matches(".*\\{\\s*//.*")) {
                                        count += 1;
                                    } else if (strData.matches(".*\\}") || strData.matches(".*\\}\\);") || strData.matches(".*\\}\\s*while.*;") || strData.matches(".*\\}\\s*//.*") || strData.matches(".*\\}\\);\\s*//.*") || strData.matches(".*\\}\\s*while.*;\\s*//.*")) {
                                        count -= 1;
                                    }
                                }
                                double lines = new LinesCountInputList().countMethodLines(tempLines);
                                if (isValidName(str)) {
                                    str += "(.*)";
                                    if (methodList.contains(str)) {
                                        int k = methodList.indexOf(str);
                                        int temp = methodNumList.get(k);
                                        temp++;
                                        methodNumList.set(k, temp);
                                        double temp2 = methodCfpList.get(k);
                                        temp2++;
                                        methodCfpList.set(k, temp2);
                                        methodLinesList.set(k, methodLinesList.get(k) + lines);
                                    } else {
                                        methodList.add(str);
                                        methodNumList.add(1);
                                        methodCfpList.add(0.0); //預設初始平均功能點數為0.0
                                        methodFirstLineList.add(z + 1);
                                        methodLinesList.add(lines);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                strData = inputFile.readLine();
            }
            inputFile.close();
        } catch (IOException IOe) {
            System.out.println("輸入輸出錯誤");
            System.exit(1);
        }

        for (int i = 0; i < methodList.size(); i++) {
            methodLinesList.set(i, methodLinesList.get(i) / methodNumList.get(i));
        }
        

    }

    @SuppressWarnings("static-access")
	void count_cfp(int currentLineNum, String m, HashSet visitedMethods) throws Exception {
        totalLines += methodLinesList.get(methodList.indexOf(m));
        //double cfp = 0;
        visitedMethods.add(m);
        FileReader fileObject = null;
        
        try {
            fileObject = new FileReader(fileName);
        } catch (FileNotFoundException fe) {
            System.out.println("檔案 \"" + fileName + "\" 不存在");
            System.exit(1);
        }

        LineNumberReader inputFile = new LineNumberReader(fileObject);
        String strData;
        CyclomaticComplexity cc=new CyclomaticComplexity();
    	//cc.CyclomaticComplexity("H:/Ver2");
        
        try {
            int i;
            strData = inputFile.readLine();
            for (i = 0; i < currentLineNum - 1; i++) {
                strData = inputFile.readLine();
            }
            
           
            L:
            for (; i < inputFile.getLineNumber(); i++) {
                if (methodList.contains(m)) {
                    //讀字元時 要把讀過的字元存入一個字元堆疊(Stack) 從現在的這行開頭開始逐字元讀取
                    Stack methContent = new Stack<Character>();
                    int j = 0;
                    L1:
                    for (; i < inputFile.getLineNumber(); i++) {
                        if (strData.matches(".*" + m + ".*") && !strData.matches(".*;.*")) {
                            int tempIndex = methodList.indexOf(m);
                            
                            this.cfp += methodCfpList.get(tempIndex);            
                            String mtemp =m.substring(0,m.length()-4);  
                            
                        	//System.out.println(mtemp);
                           int ccindex= cc.methodNameAll.indexOf(mtemp);
                          // System.out.println(ccindex);
                                                     
                          System.out.println("tttt");
                            if(cc.methodListAll.get(ccindex)>10){ // 高複雜度
                                this.hComplexityCfp += methodCfpList.get(tempIndex);                            	 
                            }
                            //System.out.println("hComplexityCfp"+hComplexityCfp);
                            
                            if(cc.subHighLowListAll.get(ccindex).equals("高")){ // 高方法數
                                this.hMethodCfp += methodCfpList.get(tempIndex);
                            }
                            //System.out.println("hMethodCfp"+hMethodCfp);
                            if(cc.objHighLowListAll.get(ccindex).equals("高")){ // 高類別數
                                this.hClassCfp += methodCfpList.get(tempIndex);
                            }
                            //ystem.out.println("hClassCfp"+hClassCfp);
                            System.out.println("ggggg");
                            for (; i < inputFile.getLineNumber(); i++) {
                                char[] ch = strData.toCharArray();
                                for (j = 0; j < ch.length; j++) {
                                    if (ch[j] == '{') {
                                        methContent.push(new Character(ch[j]));
                                        j++;
                                        break L1;
                                    }
                                }
                                strData = inputFile.readLine();
                            }
                        }
                        strData = inputFile.readLine();
                    }
                    //讀到第一個左大括號時 開始把該方法的程式碼化為字元放進Stack
                    //遇到右括號 就一直往回找到對應的左括號
                    //一旦match到".*(.*)"這樣的字元序列
                    //就丟進一個佇列(Queue)或陣列串列(ArrayList)之中
                    ArrayList<String> submethods = new ArrayList<String>();
                    L5:
                    while (i < inputFile.getLineNumber() && !methContent.empty()) {
                        char[] ch = strData.toCharArray();
                        for (; j < ch.length; j++) {
                            if (ch[j] == '}') {
                                while (methContent.pop().toString().toCharArray()[0] != '{');
                                if (methContent.empty()) {
                                    break L5;
                                }
                            } else if (ch[j] == ')') {
                                while (methContent.pop().toString().toCharArray()[0] != '(');
                                String temp = "";
                                while (isValidChar(methContent.peek().toString().toCharArray()[0])) {
                                    temp = methContent.pop().toString().toCharArray()[0] + temp;
                                }
                                if (isValidName(temp) && !visitedMethods.contains(temp + "(.*)")) {
                                    submethods.add(temp + "(.*)");
                                }
                            } else if (ch[j] == '"') { //忽略字串
                                j++;
                                for (; j < ch.length; j++) {
                                    if (ch[j] == '"') {
                                        int c = 0;
                                        for (int k3 = j - 1; ch[k3] == '\\'; k3--) {
                                            c++;
                                        }
                                        if (c % 2 == 0) {
                                            break;
                                        }
                                    }
                                }
                            } else if (ch[j] == '\'') { // 忽略字元
                                j += 2;
                                if (ch[j + 1] == '\'') {
                                    j++;
                                }
                            } else if (ch[j] == '/') { // 忽略註解
                                j++;
                                if (ch[j] == '/') {
                                    break;
                                } else if (ch[j] == '*') {
                                    j++;
                                    L2:
                                    for (; i < inputFile.getLineNumber(); i++) {
                                        for (j = 0; j < ch.length; j++) {
                                            if (ch[j] == '/' && j > 0 && ch[j - 1] == '*') {
                                                break L2;
                                            }
                                        }
                                        strData = inputFile.readLine();
                                        ch = strData.toCharArray();
                                    }
                                }
                            } else {
                                methContent.push(new Character(ch[j]));
                            }
                        }
                        j = 0;
                        strData = inputFile.readLine();
                        i++;
                    }
                    //對其下所有子方法逐一做遞迴操作
                    for (String n : submethods) {
                        int index = methodList.indexOf(n);
                        if (index != -1) {
                            count_cfp(methodFirstLineList.get(index), n, visitedMethods);
                        }
                    }
                    if (methodNumList.get(methodList.indexOf(m)) == 1) {
                        break L;
                    }
                }
                strData = inputFile.readLine();
            }
            inputFile.close();
        } catch (IOException IOe) {
            System.out.println("輸入輸出錯誤");
            System.exit(1);
        }
        visitedMethods.remove(m);
        
    }

    private static boolean isValidChar(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_';
    }

    private static boolean isValidName(String name) {
        return !(name.equals("if") || name.equals("while") || name.equals("for") || name.equals("switch") || name.equals("catch"));
    }
}