package Package2;
import java.awt.image.BufferedImage;

import Package2.GrayLevel;

// mosteneste clasa GrayLevel
public class ConvertColorToGray extends GrayLevel {
	public static int[][] convertColor(BufferedImage img, int height, int width){
		int[][] pixels = new int[width][height]; // creez o matrice avand dimensiunile imaginii
		
		// se converteste fiecare pixel: culoare -> gri
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				pixels[j][i] = (int) Math.round(getGrayLevel(img.getRGB(j, i))); // se obtine matricea cu valorile de gri ale imaginii
			}
		}
		return pixels; // se returneaza matricea creata
	}
	
	public void inheritance(String[] args){
		System.out.println("Mostenire multipla: clasa ConvertColorToGray mosteneste clasa GrayLevel care mosteneste Clasa Interface (2)");
	}
    
}
