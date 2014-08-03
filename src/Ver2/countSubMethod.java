
import java.io.FileNotFoundException;
import java.io.FileReader;	
import java.io.BufferedReader;		
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;



	/**
	 * @param args
	 */
	class CountSubMethodTest{
			
		
		String fileName;
	    private double totalLines = 0;
	    static String tt="";
	    ArrayList<String> methodList = new ArrayList<String>(); //方法名稱
	    
	    ArrayList<Integer> methodNumList = new ArrayList<Integer>(); //同名方法個數
	    ArrayList<Integer> methodFirstLineList = new ArrayList<Integer>(); //第一次出現的行數
	    ArrayList<Double> methodLinesList = new ArrayList<Double>(); //同名方法平均行數
	    ArrayList<Double> countSubMethodList=new ArrayList<Double>();//子方法數
	    ArrayList<String> subhighlowList =new ArrayList<String>();//子方法數高低
	    public CountSubMethodTest(){
		}
	    
		public CountSubMethodTest(String fileName) {
	        this.fileName = fileName;
	    }
		
	    String start_count(String methodName) throws Exception {
	        HashSet hs = new HashSet();
	        make_list();
	        
	        return count_subMethods(methodFirstLineList.get(methodList.indexOf(methodName)), methodName, hs);
	    }

	    double total_lines() {
	        return totalLines;
	    }
	    String show_methodName() throws Exception{
	    	 make_list();
	    	String str=String.format("%-48s%-2s%-11s%-5s\n","方法名稱",":","子方法數","方法數高低");
	    	for(int i=0;i<methodList.size();i++){
	    		countSubMethodList.add(Double.parseDouble(start_count(methodList.get(i))));	
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
	        	//boolean isComment = false;//是否為註解
	            String strData = inputFile.readLine();
	            char[] ch = strData.toCharArray();
	            
	            //excludeTest exc = new excludeTest();	            
		    	//Object[] rec;
		    	
		    	
	            for (int i = 0; i < inputFile.getLineNumber(); i++) {
	            	
	            	//排除註解字串字元
	    			/*rec = (Object[]) exc.excludeTest(strData,ch);
	    			isComment=(boolean) rec[1];
	    			strData=(String) rec[0]+"";
	    			ch=strData.toCharArray();*/
	            	for (int j = 0; j < ch.length; j++) {
	            	if (ch[j] == '/') { // 忽略註解
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
                                strData = inputFile.readLine()+"";
                                ch = strData.toCharArray();
                            }
                        }
                    }
	            	else if (ch[j] == '"') { //忽略字串
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
                    } 
	            	}
	    			for (String s : form) {
	                	
	                    if (strData.matches(s + ".*(.*).*") && !strData.matches(".*;.*")) {
	                        tempLines = new ArrayList<String>();
	                       
	                      
	    	    			
	                        for (int j = 0; j < ch.length; j++) {
	                        	if (ch[j] == '/') { // 忽略註解
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
	                                        strData = inputFile.readLine()+"";
	                                        ch = strData.toCharArray();
	                                    }
	                                }
	                            }
	                        	else if (ch[j] == '"') { //忽略字串
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
	                            }  else if (ch[j] == '(') {
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
	                                   
	                                    if (methodList.contains(str)) {
	                                        int k = methodList.indexOf(str);
	                                        int temp = methodNumList.get(k);
	                                        temp++;
	                                        methodNumList.set(k, temp);
	                                      
	                                        methodLinesList.set(k, methodLinesList.get(k) + lines);
	                                    } else {
	                                        methodList.add(str);
	                                        methodNumList.add(1);
	                                        
	                                        methodFirstLineList.add(z + 1);
	                                        methodLinesList.add(lines);
	                                    }
	                                }
	                            }
	                        }
	                        break;
	                    }
	                }
	                strData = inputFile.readLine()+"";
	                ch=strData.toCharArray();
	                
	            
	              
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
	     
	     String count_subMethods(int currentLineNum, String m, HashSet visitedMethods) throws IOException {
	         
	         totalLines += methodLinesList.get(methodList.indexOf(m));
	         
	         tt="";
	         double csubm=0;
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
	                             //cfp++;
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
	                        	 
	         	    			
	         	    			ch=strData.toCharArray();
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
	                             }
	                             else if (ch[j] == '/') { // 忽略註解
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
	                             }
	                             else if (ch[j] == '"') { //忽略字串
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
	                             }  else {
	                                 methContent.push(new Character(ch[j]));
	                             }
	                         }
	                         j = 0;
	                         strData = inputFile.readLine();
	                         i++;
	                     }
	                                                    
		                 		                
		               
	                     for(int z=0;z<submethods.size();z++){
		                	 tt=submethods.get(z)+" "+tt;
		                	 csubm++;
		                 }
	                     
	                     //?
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
	       	         
	         String temp=String.valueOf(csubm);
	         return temp;
	         
	     }
	    
	    private static boolean isValidChar(char ch) {
	        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_';
	    }

	    private static boolean isValidName(String name) {
	        return !(name.equals("if") || name.equals("while") || name.equals("for") || name.equals("switch") || name.equals("catch")||name.equals(""));
	    }
		
	}
	  
		
		

	
	public class countSubMethod {
		
}
