package Ver2;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class CyclomaticComplexity {
	public final static  ArrayList<String> methodNameAll = new ArrayList<String>();//�s��package���Ҧ��ɮפ�k���W��
	public static final ArrayList<Integer> methodListAll = new ArrayList<Integer>();//�s��package���Ҧ��ɮרC��k���̰��`��������
	public static	final ArrayList<String>  subHighLowListAll=new ArrayList<String>();//�s��package���Ҧ��ɮרC��k���l��k���C
	public static	final ArrayList<String>  objHighLowListAll=new ArrayList<String>();//�s��package���Ҧ��ɮרC��k������ư��C
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
 		 
 		
 		final ArrayList<Double>countSubMethodListAll=new ArrayList<Double>(); //�s��package���Ҧ��ɮרC��k�l��k��
 		final ArrayList<Integer> methodObjAll = new ArrayList<Integer>();//�s��package���Ҧ��ɮרC��k�I�s�������
 		final ArrayList<String>  methodNameSubAll=new ArrayList<String>(); //�Ȧs��package���Ҧ��ɮרC��k���l��k�W��
 		final ArrayList<String>  methodNameObjAll=new ArrayList<String>(); //�Ȧs��package���Ҧ��ɮרC��k���l��k�W��(Obj)
 		final ArrayList<Integer> countPartmethod = new ArrayList<Integer>();
 		
 		
 		/*�v�@���opackage�U��.java��*/
		 for(int lfi=0;lfi<allFiles.length;lfi++){	
			 fileName=allFiles[lfi];
		 
		 try {//Ū���ɮ�
		        inputFile = new LineNumberReader(new FileReader(filePath+"/"+fileName));
		    } catch (FileNotFoundException ex) {
		 }
		 
		 try {
			    ArrayList<String> methodName = new ArrayList<String>();//�s��C��k���W��
			 	ArrayList<Integer> methodList = new ArrayList<Integer>();//�s��C��k���̰��`��������
			 	ArrayList<Integer> methodObj = new ArrayList<Integer>();//�s��C��k�I�s�������
			 	int ttempCount = 0;//�Ȧs�`��������
			 	int tempCount = 0;//�Ȧs�`�������פ����j��
		    	int cCount = 0;//�̲״`��������
		    	int cObject = 0;//�p�⪫��ƥ�
		    	
		    	Stack<Character> pStack = new Stack<Character>();
		    	boolean isComment = false;//�O�_������
		    	
		    	String strData= inputFile.readLine();
		    	char[] ch = strData.toCharArray();
		    	
		    	countCC ccc = new countCC();
		    	countFileLine cFL = new countFileLine();
		    	exclude exc = new exclude();
		    	countObject cObj = new countObject();
		    	Object[] rec;
		    	
		    	int fileLine = cFL.countLine(filePath+"/"+fileName);
		    	CountSubMethodTest csop=new CountSubMethodTest(filePath+"/"+fileName); //�I�s�p��l��kCLASS
                csop.show_methodName();
                ArrayList<Double>countSubMethodList=csop.countSubMethodList; //�s��l��k��
                ArrayList<String>  methodNameSub= csop.methodList;    //�s��l��k���W��
                
		    	System.out.println("ClassName:"+fileName+",fileLine: "+fileLine);
		    		int line = 0;//�ΥH�p����
		    		
			    	while(line<fileLine) {
			    		
			    			//�ư����Ѧr��r��
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
			                				
			                				
			                				if(ttempCount==-1){//�YŪ�������w�O�_���d��~
			                					if(tempCount>cCount){
			                						cCount=tempCount;
			                						tempCount=0;
			                					}
			                				}
			                				else if(ttempCount==-2){//if�e�̬�if
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
				 		            
				 		            	//�ư����Ѧr��r��
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
			                	 	//��l�Ƽƭ�
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
                       
                         //�N���ɮצs�J�p����package��ArrayList��
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
                         //�N���ɮפ���k�Ʀs�iArraylist��
                         countPartmethod.add(methodName.size());
                         
                         
		    }catch(IOException IOe){
		    	
		    }
		
		 
	  } //Ū�ɵ���
		 
		 String high="��";
		 String low="�C";
		 
		 //�H�l��k�ƱƧ�
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
		// �l��k80/20�k�h�A�N���C�Ȧs�JsubhighlowListAll��
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
		   
		//�NmethodNameAll�PmethodNameSubAll������k�W�ٰ����ñN�ۦP������
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
         
       //�H����ƨӱƧ�
          
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
 		 
 		// ���� 80/20�k�h�A�N���C�Ȧs�JobjbhighlowListAll��
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
 		 
 		//�NmethodNameAll�PmethodNameObjAll������k�W�ٰ����ñN�ۦP������
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
          int tempcountmethod=0; //�Ȧs�ثe���쪺methodNameAll��
          
          for(int lfi=0;lfi<allFiles.length;lfi++){ 
         		String  fileNameAll=allFiles[lfi];
         		//opt.addClassName(fileNameAll);
         		 int partmethod=countPartmethod.get(lfi);//�s����ɮפ���k��
         		
             	 String objName =fileNameAll.substring(0,fileNameAll.length()-5);
             	
         //��X���package���U�ɮפ���T
          int count=0;
         	while(count<partmethod){
         		opt.addData(methodNameAll.get(tempcountmethod+count),objName,methodListAll.get(tempcountmethod+count),(methodListAll.get(tempcountmethod+count)>10)?"��":"�C", countSubMethodListAll.get(tempcountmethod+count),subHighLowListAll.get(tempcountmethod+count), methodObjAll.get(tempcountmethod+count),objHighLowListAll.get(tempcountmethod+count));
         		//System.out.println(partmethod);
         		//System.out.println(methodNameAll.get(tempcountmethod+count)+countSubMethodListAll.get(tempcountmethod+count));
         		
         		count++;
         	}
         	tempcountmethod+=partmethod;
         
         	
          }//
         
         JOptionPane.showMessageDialog(null, "�p�⧹��!");
		 
	 }
	
}
