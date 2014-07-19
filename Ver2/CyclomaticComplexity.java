package Ver2;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class CyclomaticComplexity {
	public final static  ArrayList<String> methodNameAll = new ArrayList<String>();//存放package內所有檔案方法之名稱
	public static final ArrayList<Integer> methodListAll = new ArrayList<Integer>();//存放package內所有檔案每方法之最高循環複雜度
	public static	final ArrayList<String>  subHighLowListAll=new ArrayList<String>();//存放package內所有檔案每方法之子方法高低
	public static	final ArrayList<String>  objHighLowListAll=new ArrayList<String>();//存放package內所有檔案每方法之物件數高低
	public void CyclomaticComplexity(String path) throws Exception{
		 String[] form = {".*void ", ".*static double ", ".*static int ", ".*static float ", ".*static long ",
	            ".*static byte ", ".*static short ", ".*static char ", ".*static String ",
	            ".*static boolean ", ".*static ", ".*double ", ".*int ", ".*float ", ".*long ",
	            ".*byte ", ".*short ", ".*char ", ".*String ", ".*boolean ", ".*static "};
		 LineNumberReader inputFile = null;
		 String filePath = path;
		 listFile lf = new listFile();
		 String[] allFiles = lf.listFile(filePath);
		 String fileName;
		 ExeclOutput opt = new ExeclOutput();
 		 opt.createFile("D:/output.xls");
 		 
 		
 		final ArrayList<Double>countSubMethodListAll=new ArrayList<Double>(); //存放package內所有檔案每方法子方法數
 		final ArrayList<Integer> methodObjAll = new ArrayList<Integer>();//存放package內所有檔案每方法呼叫之物件數
 		final ArrayList<String>  methodNameSubAll=new ArrayList<String>(); //暫存放package內所有檔案每方法之子方法名稱
 		final ArrayList<String>  methodNameObjAll=new ArrayList<String>(); //暫存放package內所有檔案每方法之子方法名稱(Obj)
 		final ArrayList<Integer> countPartmethod = new ArrayList<Integer>();
 		
 		
 		/*逐一取得package下之.java檔*/
		 for(int lfi=0;lfi<allFiles.length;lfi++){	
			 fileName=allFiles[lfi];
		 
		 try {//讀取檔案
		        inputFile = new LineNumberReader(new FileReader(filePath+"/"+fileName));
		    } catch (FileNotFoundException ex) {
		 }
		 
		 try {
			    ArrayList<String> methodName = new ArrayList<String>();//存放每方法之名稱
			 	ArrayList<Integer> methodList = new ArrayList<Integer>();//存放每方法之最高循環複雜度
			 	ArrayList<Integer> methodObj = new ArrayList<Integer>();//存放每方法呼叫之物件數
			 	int ttempCount = 0;//暫存循環複雜度
			 	int tempCount = 0;//暫存循環複雜度中較大者
		    	int cCount = 0;//最終循環複雜度
		    	int cObject = 0;//計算物件數目
		    	
		    	Stack<Character> pStack = new Stack<Character>();
		    	boolean isComment = false;//是否為註解
		    	
		    	String strData= inputFile.readLine();
		    	char[] ch = strData.toCharArray();
		    	
		    	countCC ccc = new countCC();
		    	countFileLine cFL = new countFileLine();
		    	exclude exc = new exclude();
		    	countObject cObj = new countObject();
		    	Object[] rec;
		    	
		    	int fileLine = cFL.countLine(filePath+"/"+fileName);
		    	CountSubMethodTest csop=new CountSubMethodTest(filePath+"/"+fileName); //呼叫計算子方法CLASS
                csop.show_methodName();
                ArrayList<Double>countSubMethodList=csop.countSubMethodList; //存放子方法數
                ArrayList<String>  methodNameSub= csop.methodList;    //存放子方法之名稱
                
		    	System.out.println("ClassName:"+fileName+",fileLine: "+fileLine);
		    		int line = 0;//用以計算行數
		    		
			    	while(line<fileLine) {
			    		
			    			//排除註解字串字元
			    			rec = (Object[]) exc.exclude(strData,ch,isComment);
			    			isComment=(boolean) rec[1];
			    			strData=(String) rec[0]+"";
			    			ch=strData.toCharArray();
			    			
			    		for (String s : form) {
			                if (strData.matches(s + ".*(.*).*") && !strData.matches(".*;.*")) {
			                	getMethodName gmn = new getMethodName();
			                	String name =  gmn.getName(ch);
			                	methodName.add(name);                	
			                	do{
			                		for(int j=0;j<ch.length;j++){
			                				if(ch[j]=='{')
				 		            			pStack.push('{');
				 		            		else if(ch[j]=='}')
				 		            			pStack.pop();
			                		}
			                			if(isComment==false){
			                				System.out.println("line:"+line);
			                				
			                				cObject += cObj.countObjNew(0, ch, strData);
			                				cObject += cObj.countObjPara(0, strData, ch);
			                				ttempCount = ccc.countCondition(0, ch, strData);
			                				
			                				
			                				if(ttempCount==-1){//若讀取部分已是巢狀範圍外
			                					if(tempCount>cCount){
			                						cCount=tempCount;
			                						tempCount=0;
			                					}
			                				}
			                				else if(ttempCount==-2){//if前者為if
			                					if(tempCount>cCount){
			                						cCount=tempCount;
			                						tempCount=0;
			                						
			                					}
			                					tempCount = ccc.countCondition(0, ch, strData);
			                				}
			                				else{
			                					tempCount+=ttempCount;
			                				}
			                			}
			                			
			                			line++;
				 		            	strData = inputFile.readLine()+"";
				 		            	ch = strData.toCharArray();
				 		            
				 		            	//排除註解字串字元
				 		            	rec = (Object[]) exc.exclude(strData,ch,isComment);
						    			isComment=(boolean) rec[1];
						    			strData=(String) rec[0]+"";
						    			ch=strData.toCharArray();
				 		            	
				 		            	
			 		            	}while(pStack.size()!=0&&line<fileLine);
				                	if(tempCount>cCount){
		        						cCount=tempCount;
		        					}	 		            	
			                	 	methodList.add(cCount);
			                	 	methodObj.add(cObject);
			                	 	System.out.println("---------addCount:"+cCount);
			                	 	//初始化數值
			                	 	ttempCount=0;
			                	 	tempCount = 0;
			                	 	cCount = 0;
			                	 	cObject = 0;
			                	 	ccc.countCondition(-1, ch, strData);
			                }
			            }
		    		        
				            line++;
				            strData = inputFile.readLine()+"";
				    		ch = strData.toCharArray();
				            
			    	}//End of while
                       
                         //將本檔案存入計算整個package之ArrayList中
                         for(int y=0;y<methodName.size();y++){
                        	 String namet=methodName.get(y);
                        	 String namesubt=methodName.get(y);
                        	 String nameobjt=methodName.get(y);
                        	 
                        	 int countt=methodList.get(y);
                        	 int objt=methodObj.get(y);
                        	 Double csubt=countSubMethodList.get(y);
                        	 
                        	 
                        	 methodNameAll.add(namet);
                        	 methodNameSubAll.add(namesubt);
                        	 methodNameObjAll.add(nameobjt);
                        	 
                        	 methodListAll.add(countt);
                        	 methodObjAll.add(objt);
                        	 countSubMethodListAll.add(csubt);
                         }
                         //將該檔案之方法數存進Arraylist中
                         countPartmethod.add(methodName.size());
                         
                         
		    }catch(IOException IOe){
		    	
		    }
		
		 
	  } //讀檔結束
		 
		 String high="高";
		 String low="低";
		 
		 //以子方法數排序
		 double value;
		 String namesub;
		 
		 double vtemp;
		 String nstemp;
		   for (int m = 0; m < methodNameSubAll.size()-1; m++) {
               
               for (int n = m+1; n < methodNameSubAll.size(); n++) {
            	   value = countSubMethodListAll.get(m);
            	   namesub= methodNameSubAll.get(m);
            	   
            	   double temp_value=countSubMethodListAll.get(n);
            	   String temp_namesub=methodNameSubAll.get(n);
            	   if (value<temp_value){
            		   
            		   vtemp=temp_value;
            		   nstemp=temp_namesub;
            		               		   
            		   countSubMethodListAll.set(n, value);
            		   methodNameSubAll.set(n, namesub);
            		               		   
            		   countSubMethodListAll.set(m, vtemp);
            		   methodNameSubAll.set(m, nstemp);
            		   
            	   }
               }
		   } //
		// 子方法80/20法則，將高低值存入subhighlowListAll中
		   int tValue = (int)(methodNameAll.size()*0.2-1);
           double stempValue = countSubMethodListAll.get(tValue);
           int subValue = (int)stempValue;
           boolean equal = false;
           
           if(subValue==countSubMethodListAll.get(tValue+1)){
        	   equal=true;
           }
           for(int k=0;k<methodNameAll.size();k++){
    		   if((equal==true&&countSubMethodListAll.get(k)>subValue)||(equal==false&&countSubMethodListAll.get(k)>=subValue)){
    			   subHighLowListAll.add(high);
    		   }else{
    			   subHighLowListAll.add(low);
    		   }
    	   }
		   
		//將methodNameAll與methodNameSubAll中的方法名稱做比對並將相同的互換
         double valueall;
		 String highlow;
		 String nameall;
		 
		 double vtempall;
		 String hltemp;
		 String ntempall;
         for(int k=0;k<methodNameAll.size()-1;k++){
        	 for(int l=k;l<methodNameAll.size();l++){
        		 valueall=countSubMethodListAll.get(k);
        		 highlow = subHighLowListAll.get(k);
        		 nameall = methodNameSubAll.get(k);
        		 
        		 double temp_value=countSubMethodListAll.get(l);
        		 String temp_highlow=subHighLowListAll.get(l);
        		 String temp_name=methodNameSubAll.get(l);
        		 if(String.valueOf(methodNameAll.get(k).equals(methodNameSubAll.get(l))).equals("true")){
        			 vtempall=temp_value;
        			 hltemp=temp_highlow;
        			 ntempall=temp_name;
        			 
        			 countSubMethodListAll.set(l,valueall);
        			 subHighLowListAll.set(l, highlow);
        			 methodNameSubAll.set(l, nameall);
        			 
        			 
        			 countSubMethodListAll.set(k,vtempall);
        			 subHighLowListAll.set(k, hltemp);
        			 methodNameSubAll.set(k, ntempall);
        			 break;
        		 }
        	 }
         } //
         
       //以物件數來排序
          
          int a;
          String mObjName;
          
          int tmp;
          String temp;
          
           for (int i = 0; i < methodObjAll.size()-1; i++) {
              
               for (int j = i+1; j < methodObjAll.size(); j++) {
                   a = methodObjAll.get(i);
                   mObjName= methodNameObjAll.get(i);
                                                    
                   int b = methodObjAll.get(j);
                   String mNameTemp = methodNameObjAll.get(j);
                   if (a < b) {
                   
                    tmp = b;
                    temp= mNameTemp;
                    methodObjAll.set(j, a);
                    methodNameObjAll.set(j, mObjName);
                  
                    methodObjAll.set(i, tmp);
                    methodNameObjAll.set(i,temp);
                  
               }
               }
           
           }
 		 
 		// 物件 80/20法則，將高低值存入objbhighlowListAll中
           tValue  = (int)(methodNameAll.size()*0.2-1);
           int objValue = methodObjAll.get(tValue);
           equal = false;
           if(objValue==methodObjAll.get(tValue+1)){
        	   equal=true;
           }
           for(int k=0;k<methodNameAll.size();k++){
    		   if((equal==true&&methodObjAll.get(k)>objValue)||(equal==false&&methodObjAll.get(k)>=objValue)){
    			   objHighLowListAll.add(high);
    		   }else{
    			   objHighLowListAll.add(low);
    		   }
    	   }
           //
 		 
 		//將methodNameAll與methodNameObjAll中的方法名稱做比對並將相同的互換
          int objall;
 		 String objhighlow;
 		 String objnameall;
 		 
 		 int otempall;
 		 String ohltemp;
 		 String ontempall;
          for(int k=0;k<methodNameAll.size()-1;k++){
         	 for(int l=k;l<methodNameAll.size();l++){
         		 objall=methodObjAll.get(k);
         		 objhighlow = objHighLowListAll.get(k);
         		 objnameall = methodNameObjAll.get(k);
         		 
         		 int temp_value=methodObjAll.get(l);
         		 String temp_highlow=objHighLowListAll.get(l);
         		 String temp_name=methodNameObjAll.get(l);
         		 if(String.valueOf(methodNameAll.get(k).equals(methodNameObjAll.get(l))).equals("true")){
         			 otempall=temp_value;
         			 ohltemp=temp_highlow;
         			 ontempall=temp_name;
         			 
         			 methodObjAll.set(l,objall);
         			 objHighLowListAll.set(l, objhighlow);
         			 methodNameObjAll.set(l, objnameall);
         			         			 
         			 methodObjAll.set(k,otempall);
         			 objHighLowListAll.set(k, ohltemp);
         			 methodNameObjAll.set(k, ontempall);
         			 break;
         		 }
         	 }
          } //
          int tempcountmethod=0; //暫存目前取到的methodNameAll數
          
          for(int lfi=0;lfi<allFiles.length;lfi++){ 
         		String  fileNameAll=allFiles[lfi];
         		//opt.addClassName(fileNameAll);
         		 int partmethod=countPartmethod.get(lfi);//存放該檔案之方法數
         		
             	 String objName =fileNameAll.substring(0,fileNameAll.length()-5);
             	
         //輸出整個package中各檔案之資訊
          int count=0;
         	while(count<partmethod){
         		opt.addData(methodNameAll.get(tempcountmethod+count),objName,methodListAll.get(tempcountmethod+count),(methodListAll.get(tempcountmethod+count)>10)?"高":"低", countSubMethodListAll.get(tempcountmethod+count),subHighLowListAll.get(tempcountmethod+count), methodObjAll.get(tempcountmethod+count),objHighLowListAll.get(tempcountmethod+count));
         		//System.out.println(partmethod);
         		//System.out.println(methodNameAll.get(tempcountmethod+count)+countSubMethodListAll.get(tempcountmethod+count));
         		
         		count++;
         	}
         	tempcountmethod+=partmethod;
         
         	
          }//
         
         JOptionPane.showMessageDialog(null, "計算完成!");
		 
	 }
	
}
