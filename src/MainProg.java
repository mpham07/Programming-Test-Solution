import java.util.ArrayList;

public class MainProg {
	
	enum Type { DNA, RNA }
	public String[] DNA_Char = {"A","T", "G", "C"};
	public String[] RNA_Char = {"A","U", "G", "C"};
	
	public static void main(String[] args) {
		
		MainProg prog = new MainProg();
		String randASCIIString = "cat";
		String strDNA = prog.encodeASCIIToDNAorRNA(randASCIIString, Type.DNA);
		String strRNA = prog.encodeASCIIToDNAorRNA(randASCIIString, Type.RNA);
		System.out.println("DNA: " + randASCIIString + " -> " + strDNA);
		System.out.println("RNA: " + randASCIIString + " -> " + strRNA);
		System.out.println();
		
		String randComplementaryStrandsString = "ACTGACTAAGAT";
		String originalDNA = prog.convertCompStrandsToDNA(randComplementaryStrandsString);
		System.out.print("Complementary strands: " + randComplementaryStrandsString + " -> " + originalDNA);
		String strASCII = prog.convertDNAtoASCII(originalDNA);
		System.out.println(" -> " + strASCII);
		System.out.println("Done!");
	}
	
	// Objective 1 & 2
	String encodeASCIIToDNAorRNA(String strAscii, Type type) {
		
		String strReturn = "";
		for(int i = 0 ; i < strAscii.length(); i++) {
			char ch = strAscii.charAt(i);
			int ascii_ch = (int) ch;
			int[] num8bit = convertIntegerTobinary8bit(ascii_ch);

			int j = 0;
			while(j < 8) {
				
				strReturn += convertASCIIToDNAorRNAchar(num8bit[j], num8bit[j+1], type); 
				j += 2;
			}
		}
		
		return strReturn;
	}
	
	int[] convertIntegerTobinary8bit(int number) {
	    int[] binary = new int[8];
	    for (int i = 7, num = number; i >= 0; i--, num >>>= 1)
	        binary[i] = num & 1;
	    return binary;
	}
	
	String convertASCIIToDNAorRNAchar(int first, int second, Type type) {
		String chars[] = DNA_Char;
		if (type == Type.RNA) { chars = RNA_Char; }
		if(first == 0 && second == 0) return chars[0]; 
		if(first == 0 && second == 1) return chars[1]; 
		if(first == 1 && second == 1) return chars[3]; 
		if(first == 1 && second == 0) return chars[2]; 
		return "";
	}
	
	// Objective 3 & 4
	String convertCompStrandsToDNA(String randComStrands) {
		
		String originalDNA = "";
		for(int i = 0; i< randComStrands.length(); i++) {
			String ch = randComStrands.charAt(i) + "";
			String newCh = "";
			if (DNA_Char[0].equals(ch)) { newCh = DNA_Char[1]; }
			if (DNA_Char[1].equals(ch)) { newCh = DNA_Char[0]; }
			if (DNA_Char[2].equals(ch)) { newCh = DNA_Char[3]; }
			if (DNA_Char[3].equals(ch)) { newCh = DNA_Char[2]; }
			
			originalDNA += newCh;
		}
		return originalDNA;
	}
	
	String convertBin8BitToASCII(ArrayList<int[]> bins) {
		
		String strASCII = "";
		for(int i=0; i < bins.size(); i++) {
			int[] bin = bins.get(i);
			
			int sum = 0;
			for(int j = bin.length - 1; j >= 0; j--) {
				sum += Math.pow(2.0 , 7 - j) * bin[j] ;
			}
			
			char chASCII = (char) sum;
			strASCII += chASCII;
		}

		return strASCII;
	}
	
	String convertDNAtoASCII(String strDNA) {
		
		String strASCII = convertBin8BitToASCII(convertDNAToBin8bit(strDNA)); 
		return strASCII;
	}
	
	ArrayList<int[]> convertDNAToBin8bit(String strDNA) {
		
		ArrayList<int[]> binDNAs = new ArrayList<>();
		int[] newBinary = new int[8];
		
		int counting = 0;
		for (int i = 0; i < strDNA.length(); i++) {
			String ch = strDNA.charAt(i) + "";
			
			for (int j = 0 ; j < DNA_Char.length ; j++) {
				if (DNA_Char[j].equals(ch)) {
					
					int first = 0, second = 0;
					if (j == 0) { 
						first = 0 ;
						second = 0;
					}else if (j == 1) { 
						first = 0 ;
						second = 1;
					}else if (j == 2) { 
						first = 1 ;
						second = 0;
					}else if (j == 3) { 
						first = 1 ;
						second = 1;
					}
					
					newBinary[counting * 2] = first;
					newBinary[counting * 2 + 1] = second;
					break;
				}
			}
			
			counting++;
			
			if (counting == 4) {
				counting = 0;
				binDNAs.add(newBinary);
				newBinary = new int[8];
			}
		}
		
		return binDNAs;
	}
}
