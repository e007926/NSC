import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;


public class cfpAll {
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
	public void cfpAll(String path) throws Exception{
	    
	    
	    LineNumberReader inputFile = null;
		 String filePath = path;
		 listFile lf = new listFile();
		 String[] allFiles = lf.listFile(filePath);
		 String fileName;		 
		 RegressionAll opt = new RegressionAll();
 		 opt.createFile("D:/outputAll1.xls");
 		 
		 CyclomaticComplexity cc = new CyclomaticComplexity();
		 cc.CyclomaticComplexity(filePath);
		 for(int lfi=0;lfi<allFiles.length;lfi++){	
			 fileName=allFiles[lfi];
			 
			 try{				 		 
				 
				 Countcfp partccfp = new Countcfp(filePath+"/"+fileName);
				 partccfp.start_count();
				 System.out.println("----------------------countcfp finished------------------------");
				
				 
				 ArrayList<String> methodList =partccfp.getMethodList();//存放每方法之名稱
				 
				 ArrayList<Double> methodCfpList =partccfp.getMethodCfpList(); // 同名方法平均功能點數
				 ArrayList<Double> methodHComplexityCfpList = partccfp.getMethodHComplexityCfpList();
				 ArrayList<Double> methodHMethodCfpList = partccfp.getMethodHMethodCfpList();
				 ArrayList<Double> methodHClassCfpList = partccfp.getMethodHClassCfpList();
				 ArrayList<Integer> methodNumList = partccfp.getMethodNumList(); // 同名方法個數
				 ArrayList<Integer> methodFirstLineList = partccfp.getMethodFirstLineList();; // 第一次出現的行數
				 ArrayList<Double> methodLinesList =partccfp.getMethodLinesList(); // 同名方法平均行數
				 ArrayList<Double> partmethodCallLineList = partccfp.getPartMethodLinesList(); 
				
				/* for(int a=0;a<methodList.size();a++){
                 	System.out.println("methodname:"+methodList.get(a));
                 }*/
				 
                      //將本檔案存入計算整個package之ArrayList中
                      for(int y=0;y<methodList.size();y++){
                     	 String namet=methodList.get(y);
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
                      	totalLinesAll+=(int) partccfp.total_lines();
                      	
                      }
				 
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
		 //ArrayList<Integer> methodEndLineListAll = cc.methodEndLineListAll;
		 
		 
		 
		 //for(int b=0;b<methodListAll.size();b++){
			// System.out.println("--methodListAll size:"+methodListAll.size()+" methodNameAll size:"+cc.methodNameAll.size()+"--");
		 //}
		 for(int a=0, len=methodListAll.size();a<len;++a){
			 if(!methodListAll.get(a).equals(cc.methodNameAll.get(a))){
				 methodListAll.remove(a);
				 methodCfpListAll.remove(a);
				 methodHComplexityCfpListAll.remove(a);
				 methodHMethodCfpListAll.remove(a);
				 methodHClassCfpListAll.remove(a);
				 methodFirstLineListAll.remove(a);
				 --len;
				 --a;
			 }
			
				
				 
			 
		 }
		
		 for(int a=0;a<methodFirstLineListAll.size();a++){
			double methodLines=cc.methodEndLineListAll.get(a)-methodFirstLineListAll.get(a);
			 partmethodLineListAll.add(methodLines+1);
			
		 }
		 System.out.println("-----------------------------------------------");
		
		 int tempcountmethod=0; //暫存目前取到的methodNameAll數
         
         for(int lfi=0;lfi<allFiles.length;lfi++){ 
        		String  fileNameAll=allFiles[lfi];        		
        		
        		 int partmethod=cc.countPartmethod.get(lfi);//存放該檔案之方法數
        		
            	 String objName =fileNameAll.substring(0,fileNameAll.length()-5);
            	
        //輸出整個package中各檔案之資訊
         int count=0;
        	while(count<partmethod){ 
        		
        		opt.addData(cc.methodNameAll.get(tempcountmethod+count),cc.classNameAll.get(tempcountmethod+count),methodCfpListAll.get(tempcountmethod+count),methodHComplexityCfpListAll.get(tempcountmethod+count),methodHMethodCfpListAll.get(tempcountmethod+count), methodHClassCfpListAll.get(tempcountmethod+count),partmethodLineListAll.get(tempcountmethod+count),partmethodCallLineListAll.get(tempcountmethod+count));
        		
        		count++;
        	}
        	tempcountmethod+=partmethod;
        
        	
         }//
         
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
    
    ArrayList<String> getMethodListAll(){
    	return this.methodListAll;
    }
	String show_detail() {
        String str = String.format("%-25s%-7s%-11s%-11s%-11s%-7s%-7s%-7s\n",  "方法名稱:", "平均CFP","高複雜度CFP","高方法數CFP","高類別數CFP", "同名方法數", "首次出現行數", "同名方法平均行數");
        for (String s : methodListAll) {
            int index = methodListAll.indexOf(s);
            
            str += String.format("%-30s%-10.1f%-14.1f%-14.1f%-14.1f%-14d%-11d%-10f\n", s, getTotalCfp(s),getTotalHighComplexity(s),getTotalHighMethod(s),getTotalHighClass(s), methodNumListAll.get(index), methodFirstLineListAll.get(index), partmethodLineListAll.get(index));
        }
        return str;
    }
	
}
