package Package2;
import Package2.Interface;

// se mosteneste clasa Interface
public class GrayLevel extends Interface {
	// se calculeaza nivelul e gri pentru fiecare pixel
	public static double getGrayLevel(int p){
		// se obtin componentele r, g, b individual
		int r = (p >> 16) & 0x000000FF; // componenta r (red), o valoare cuprinsa intre 0-255 din spatiul RGB 
		int g = (p >> 8) & 0x000000FF;  // componenta g (green), similar r
		int b = p & 0x000000FF; // componenta b (blue), similar r
		
		return 0.299*r + 0.587*g + 0.114*b; // se returneaza nivelul de gri al pixelului calculat dupa formula
		
	}
	
	public void inheritance(String[] args){
		System.out.println("Mostenire multipla: clasa GrayLevel mosteneste Clasa Interface (1)");
	}
	
	
}
