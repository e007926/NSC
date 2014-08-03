public class getClassName {

	String getName(String s) {
		String className = "";
		//boolean isClass = false;
		int pos = 0;

		String sp[] = s.split(" ");
		L: for (int i = 0; i < sp.length; i++) {

			// isClass=false;
			//System.out.println(sp[i]);
			if (sp[i].matches("class") || sp[i].matches(".*[\\W]class")) {
				// isClass=true;
				for (int j = i + 1; j < sp.length; j++) {
					if (!sp[j].equals(" ")) {
						pos = j;
						className = sp[pos];
						
							if(className.charAt(className.length()-1)=='{'){
								className=className.substring(0, className.length()-1);
							}
						
						System.out.println("-----Class Name:" + className+ "------");
								
						return className;
					}
				}
			}

		}

		// }//End of for

		System.out.println("-----Class Name:" + className + "------");
		return className;
	}// End of method

	
}
