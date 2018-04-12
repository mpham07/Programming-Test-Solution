public class MainProg {
	
	enum Type { DNA, RNA }
	public String[] DNA_Char = {"A","T", "G", "C"};
	public String[] RNA_Char = {"A","U", "G", "C"};
	
	public static void main(String[] args) {
		
		MainProg prog = new MainProg();
		String randASCIIString = "cat";
		String strDNA = prog.encodeTo(randASCIIString, Type.DNA);
		String strRNA = prog.encodeTo(randASCIIString, Type.RNA);
		System.out.println("DNA: " + randASCIIString + " -> " + strDNA);
		System.out.println("RNA: " + randASCIIString + " -> " + strRNA);
	}
	
	String encodeTo(String strAscii, Type type) {
		
		String strReturn = "";
		for(int i = 0 ; i < strAscii.length(); i++) {
			char ch = strAscii.charAt(i);
			int ascii_ch = (int) ch;
			int[] num8bit = convertTobinary8bit(ascii_ch);

			
			int j = 0;
			while(j < 8) {
				
				strReturn += convertToDNAorRNAchar(num8bit[j], num8bit[j+1], type); 
				j += 2;
			}
		}
		
		return strReturn;
	}
	
	int[] convertTobinary8bit(int number) {
	    int[] binary = new int[8];
	    for (int i = 7, num = number; i >= 0; i--, num >>>= 1)
	        binary[i] = num & 1;
	    return binary;
	}
	
	String convertToDNAorRNAchar(int first, int second, Type type) {
		String chars[] = DNA_Char;
		if (type == Type.RNA) { chars = RNA_Char; }
		if(first == 0 && second == 0) return chars[0]; 
		if(first == 0 && second == 1) return chars[1]; 
		if(first == 1 && second == 1) return chars[3]; 
		if(first == 1 && second == 0) return chars[2]; 
		return "";
	}
}
