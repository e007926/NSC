

import java.io.*; //引入java.io類別庫的所有類別
import java.util.*; //引入java.util.Scanner類別


/**
 *
 * @author Lucius & SmallFour
 * 
 * 跑完start_count()
 * 
 * 再執行getTotalCfp()、getTotalHighComplexity()、getTotalHighMethod()、getTotalHighClass()
 * 
 */

public class Countcfp2 {
	final ArrayList<String> methodListAll = new ArrayList<String>(); // 方法名稱
    final ArrayList<Double> methodCfpListAll = new ArrayList<Double>(); // 同名方法平均功能點數
    final ArrayList<Double> methodHComplexityCfpListAll = new ArrayList<Double>();
    final ArrayList<Double> methodHMethodCfpListAll = new ArrayList<Double>();
    final ArrayList<Double> methodHClassCfpListAll = new ArrayList<Double>();
    final ArrayList<Integer> methodNumListAll = new ArrayList<Integer>(); // 同名方法個數
    final ArrayList<Integer> methodFirstLineListAll = new ArrayList<Integer>(); // 第一次出現的行數
    final ArrayList<Double> methodLinesListAll = new ArrayList<Double>(); // 同名方法平均行數
    final  ArrayList<String> classNameAll = new ArrayList<String>();//存放package每方法之類別名稱 
    final ArrayList<Double> partmethodLineListAll = new ArrayList<Double>(); 
    final ArrayList<Double> partmethodCallLineListAll = new ArrayList<Double>(); 
    private double totalLinesAll = 0;
    
    ArrayList<String> methodList = new ArrayList<String>(); // 方法名稱
    ArrayList<Double> methodCfpList = new ArrayList<Double>(); // 同名方法平均功能點數
    ArrayList<Double> methodHComplexityCfpList = new ArrayList<Double>();
    ArrayList<Double> methodHMethodCfpList = new ArrayList<Double>();
    ArrayList<Double> methodHClassCfpList = new ArrayList<Double>();
    ArrayList<Integer> methodNumList = new ArrayList<Integer>(); // 同名方法個數
    ArrayList<Integer> methodFirstLineList = new ArrayList<Integer>(); // 第一次出現的行數
    ArrayList<Double> methodLinesList = new ArrayList<Double>(); // 同名方法平均行數
    ArrayList<Double> partmethodLineList = new ArrayList<Double>();
    ArrayList<Double> partmethodCallLineList = new ArrayList<Double>();
    String fileName;
    private double totalLines = 0;
    
    // total data
    private double cfp = 0;
    private double hComplexityCfp = 0;
    private double hMethodCfp = 0;
    private double hClassCfp = 0;
    private double callmethodLines = 0;
    static int indexAll=0;
    static boolean findmethod=true;
    static boolean continuefind=true;
    static int index=0;
    
    
    
    public void countcfpAll(String path) throws Exception{
    	
    	LineNumberReader inputFile = null;
		 String filePath = path;
		 listFile lf = new listFile();
		 String[] allFiles = lf.listFile(filePath);
		 String fileName;		 
		 RegressionAll opt = new RegressionAll();
		 opt.createFile("D:/outputAll2.xls");
		 
		
		 //CyclomaticComplexity cc = new CyclomaticComplexity();
		// cc.CyclomaticComplexity(filePath);
		 cfpAll cfpall = new cfpAll();
		 cfpall.cfpAll(filePath);
		 System.out.println("  methodFirstLineListAll is empty?:"+cfpall.methodFirstLineListAll.isEmpty());
        for(int lfi=0;lfi<allFiles.length;lfi++){	
			 fileName=allFiles[lfi];
			 
			 try{
				 methodList.clear();
				
				 methodHComplexityCfpList.clear();
				 methodHMethodCfpList.clear();
				 methodHClassCfpList.clear();
				 methodNumList.clear();
				 methodFirstLineList.clear();
				 methodLinesList.clear();
				 partmethodCallLineList.clear();
				 
				 this.fileName=filePath+'/'+fileName;
				start_count();
				 System.out.println("----------------------countcfp2 finished------------------------");
				 //ArrayList<String> methodList =this.methodList;//存放每方法之名稱
				 
				/* ArrayList<Double> methodCfpList =this.methodCfpList; // 同名方法平均功能點數
				 ArrayList<Double> methodHComplexityCfpList = this.methodHComplexityCfpList;
				 ArrayList<Double> methodHMethodCfpList = this.methodHMethodCfpList;
				 ArrayList<Double> methodHClassCfpList = this.methodHClassCfpList;
				 ArrayList<Integer> methodNumList = this.methodNumList; // 同名方法個數
				 ArrayList<Integer> methodFirstLineList = this.methodFirstLineList;; // 第一次出現的行數
				 ArrayList<Double> methodLinesList =this.methodLinesList; // 同名方法平均行數
				 ArrayList<Double> partmethodCallLineList = this.partmethodCallLineList;*/
				
				 for(int a=0;a<methodList.size();a++){
                	System.out.println("methodname:"+methodList.get(a));
                }
				 
                     //將本檔案存入計算整個package之ArrayList中
                     for(int y=0;y<methodList.size();y++){
                    	 String namet=methodList.get(y);
                    	 System.out.println( namet);
                    	 double cfpt=methodCfpList.get(y);
                    	 double hccfpt=methodHComplexityCfpList.get(y);
                    	 double hmcfpt=methodHMethodCfpList.get(y);
                    	 double hclasscfpt=methodHClassCfpList.get(y);
                    	 int numt=methodNumList.get(y);
                    	 int flt=methodFirstLineList.get(y);
                    	 double lt=methodLinesList.get(y);
                    	 double pmclt=partmethodCallLineList.get(y);
                    	 
                    	String namett =namet.substring(0,namet.length()-4);
                    	
                    	 methodListAll.add(namett);                    	 
                     	 methodCfpListAll.add(cfpt);
                     	 methodHComplexityCfpListAll.add(hccfpt);
                     	 methodHMethodCfpListAll.add(hmcfpt);
                     	 methodHClassCfpListAll.add(hclasscfpt);
                     	 methodNumListAll.add(numt);
                     	 methodFirstLineListAll.add(flt);
                     	 methodLinesListAll.add(lt);
                     	 partmethodCallLineListAll.add(pmclt);
                     	//totalLinesAll+=(int) partccfp.total_lines();
                     	
                     }
                     System.out.println("  methodFirstLineListAll is empty?:"+methodFirstLineListAll.isEmpty());
                    /* if(lfi==allFiles.length-1){
        				 for(int a=0;a<methodListAll.size();a++){
        					 System.out.println("methodname:"+methodListAll.get(a)+"  methodCfpListAll:"+methodCfpListAll.get(a));
        				 }
        				
        			 }*/
                     //System.out.println("totalLinesAll:"+totalLinesAll);
                     
                     
			 }
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }
    }
   
    
    //public Countcfp2(String fileName) {
        //this.fileName = fileName;
   // }
    ArrayList<String> getMethodListAll(){
    	return this.methodListAll;
    }
    void start_count() throws Exception {
        // Fill all the functional-point variables in the lists with "zero" and all the non-functional-point variables with an appropriate value.
        make_list();
        
        System.out.println("make_list finished.");
       for(int a=0;a<methodList.size();a++){
    	   System.out.println("methodname2:"+methodList.get(a));
       }
        // Calculate the specific variable corresponding to the given method.
        CyclomaticComplexity cc = new CyclomaticComplexity();
        /*for(int a=0;a<cc.methodNameAll.size();a++){
     	   System.out.println("cc.methodname:"+cc.methodNameAll.get(a));
        }*/
        
        for(int a=0, len=methodList.size();a<len;++a){
        	String m=methodList.get(a);
        	String mtemp =m.substring(0,m.length()-4);
        	System.out.println(mtemp+" "+index+" cc.methodNameAll.get(index):" +cc.methodNameAll.get(index));
			 if(!mtemp.equals(cc.methodNameAll.get(index))){
				 
				 methodList.remove(a);
				 methodCfpList.remove(a);
				 methodHComplexityCfpList.remove(a);
				 methodHMethodCfpList.remove(a);
				 methodHClassCfpList.remove(a);
				 methodFirstLineList.remove(a);
				 methodNumList.remove(a);
				 methodLinesList.remove(a);
				 partmethodCallLineList.remove(a);
				 --len;
				 --a;
				// --index;
			 }
			 else{
			 index++;
			 }
        	
        }
        
       
        
        for(String methodName : methodList){
            // The initialization.
            this.cfp = 0;
            this.hComplexityCfp = 0;
            this.hMethodCfp = 0;
            this.hClassCfp = 0;
            this.callmethodLines = 0;
            //findmethod = true;
            //continuefind=true;
            int count = 0;
            HashSet<String> hs = new HashSet<String>();
            
            
            count_cfp(methodFirstLineList.get(methodList.indexOf(methodName)), methodName, hs);
            
            // Fill the specific variables corresponding to the given method.
            
            int index = methodList.indexOf(methodName);
            System.out.println(methodName+" "+ index);
            
           
            this.partmethodCallLineList.set(index, this.callmethodLines);
            this.cfp /= methodNumList.get(index);
            this.hComplexityCfp /= methodNumList.get(index);
            this.hMethodCfp /= methodNumList.get(index);
            this.hClassCfp /= methodNumList.get(index);
            this.methodCfpList.set(index, this.cfp);
            this.methodHComplexityCfpList.set(index, this.hComplexityCfp);
            this.methodHMethodCfpList.set(index, this.hMethodCfp);
            this.methodHClassCfpList.set(index, this.hClassCfp);
           
            this.indexAll++;
          
            System.out.println( methodName + "  call method lines:"+this.callmethodLines);
            System.out.println( methodName + " : count_cfp finished." );
           // }
            
            
        }
        
        System.out.println("start_count finished.");
    }
    double total_lines() {
        return totalLinesAll;
    }
	double getTotalCfp(String methodName){
        return this.methodCfpListAll.get(this.methodListAll.indexOf(methodName));
    }
    double getTotalHighComplexity(String methodName){
        return this.methodHComplexityCfpListAll.get(this.methodListAll.indexOf(methodName));
    }
    double getTotalHighMethod(String methodName){
        return this.methodHMethodCfpListAll.get(this.methodListAll.indexOf(methodName));
    }
    double getTotalHighClass(String methodName){
        return this.methodHClassCfpListAll.get(this.methodListAll.indexOf(methodName));
    }
    
    
    ArrayList<String> getMethodList(){
    	return this.methodList;
    }
    
    ArrayList<Double> getMethodCfpList(){
    	return this.methodCfpList;
    }
    ArrayList<Double> getMethodHComplexityCfpList(){
    	return this.methodHComplexityCfpList;
    }
    ArrayList<Double> getMethodHMethodCfpList(){
    	return this.methodHMethodCfpList;
    }
    ArrayList<Double> getMethodHClassCfpList(){
    	return this.methodHClassCfpList;
    }
    ArrayList<Integer> getMethodNumList(){
    	return this.methodNumList;
    } 
    ArrayList<Integer> getMethodFirstLineList (){
    	return this.methodFirstLineList;
    }
    ArrayList<Double> getMethodLinesList(){
    	return this.methodLinesList;
    }
    ArrayList<Double> getPartMethodLinesList(){
    	return this.partmethodCallLineList;
    }
    
    
    String show_detail() {
        String str = String.format("%-25s%-7s%-11s%-11s%-11s%-7s%-7s%-7s\n",  "方法名稱:", "平均CFP","高複雜度CFP","高方法數CFP","高物件數CFP", "同名方法數", "首次出現行數", "同名方法平均行數");
        for (String s : methodListAll) {
        	System.out.println(s);
            int index = methodListAll.indexOf(s);
            str += String.format("%-30s%-10.1f%-14.1f%-14.1f%-14.1f%-14d%-11d%-10f\n", s, getTotalCfp(s),getTotalHighComplexity(s),getTotalHighMethod(s),getTotalHighClass(s), methodNumListAll.get(index), methodFirstLineListAll.get(index), partmethodLineListAll.get(index));
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
                                int count = 0;
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
                                        methodHComplexityCfpList.add(0.0);
                                        methodHMethodCfpList.add(0.0);
                                        methodHClassCfpList.add(0.0);
                                        methodFirstLineList.add(z + 1);
                                        methodLinesList.add(lines);
                                       partmethodCallLineList.add(0.0);
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
	void count_cfp(int currentLineNum, String m, HashSet<String> visitedMethods) throws Exception {
    	
    	
       // totalLines += cfpall.partmethodLineListAll.get(methodList.indexOf(m));
        //double cfp = 0;
        visitedMethods.add(m);
       
        String mtemp =m.substring(0,m.length()-4);
        FileReader fileObject = null;
        
        try {
            fileObject = new FileReader(fileName);
        } catch (FileNotFoundException fe) {
            System.out.println("檔案 \"" + fileName + "\" 不存在");
            System.exit(1);
        }

        LineNumberReader inputFile = new LineNumberReader(fileObject);
        String strData;
        CyclomaticComplexity cc = new CyclomaticComplexity();
        cfpAll cfpall = new cfpAll();
        
        try {
            int i;
            strData = inputFile.readLine();
            for (i = 0; i < currentLineNum - 1; i++) {
                strData = inputFile.readLine();
            }
            L:
            for (; i < inputFile.getLineNumber(); i++) {
                if (cc.methodNameAll.contains(mtemp)) {
                	System.out.println("mtemp:"+mtemp+"----------------------------");
                	//讀字元時 要把讀過的字元存入一個字元堆疊(Stack) 從現在的這行開頭開始逐字元讀取
                    Stack methContent = new Stack<Character>();
                    int j = 0;
                    L1:
                    for (; i < inputFile.getLineNumber(); i++) {
                        if (strData.matches(".*" + m + ".*") && !strData.matches(".*;.*")) {
                            int tempIndex = methodList.indexOf(m);
                            this.cfp += 1;
                            //System.out.println(m);
                            
                            //findmethod = true;   
                            
                           // String mtemp =m.substring(0,m.length()-4);
                                                       
                            int ccindex=cc.methodNameAll.indexOf(mtemp);
                            
                            
                            System.out.println("mtemp:"+mtemp+" ccindex: "+ccindex);
                           /* if(ccindex==-1){
                            	findmethod =false;
                            }
                    		if(findmethod==false){
                    			continuefind=false;
                    		}	*/
                    			 
                    			
                            if(ccindex!=-1){
                            	for(int a=0;a<cc.methodNameAll.size();a++){
                                	if(cc.methodNameAll.get(a).equals(mtemp)&&cc.classNameAll.get(a).equals(cc.classNameAll.get(indexAll))){
                                		 System.out.println("methodNameAll:"+cc.methodNameAll.get(a)+"cc.classNameAll.get(indexAll):"+cc.classNameAll.get(indexAll)+" "+indexAll);
                                		ccindex=a;
                                	}
                                }
                            	System.out.println("  methodFirstLineListAll is empty?:"+methodFirstLineListAll.isEmpty());
                            	//System.out.println(mtemp+"  cc.methodEndLineListAll:"+cc.methodEndLineListAll.get(ccindex)+"  methodFirstLineList:"+methodFirstLineList.get(tempIndex));
                            		callmethodLines +=(cc.methodEndLineListAll.get(ccindex)-methodFirstLineList.get(tempIndex))+1;
                            		
                            		//partmethodLineList.add(callmethodLines+1);
		                            if(cc.methodListAll.get(ccindex)>10){ // 高複雜度
		                                this.hComplexityCfp += 1;   	                                
		                            }
		                            if(cc.subHighLowListAll.get(ccindex).equals("高")){ // 高方法數
		                                this.hMethodCfp += 1;
		                            }
		                            if(cc.objHighLowListAll.get(ccindex).equals("高")){ // 高類別數
		                                this.hClassCfp += 1;
		                            }
                                }
                            //this.lines +=partmethodLineList.indexOf(tempIndex);
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
                    for (String n : submethods) {
                    	System.out.println("submethod:"+n);
                    }
                    
                    //對其下所有子方法逐一做遞迴操作
                    
                    for (String n : submethods) {
                    	String ntemp =n.substring(0,n.length()-4);
                        int index = methodListAll.indexOf(n);
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
        return !(name.equals("if") || name.equals("while") || name.equals("for") || name.equals("switch") || name.equals("catch")||name.equals(""));
    }
}