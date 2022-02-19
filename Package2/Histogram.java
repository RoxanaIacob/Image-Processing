package Package2;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

// mosteneste clasa ConvertColorToGray
public class Histogram extends ConvertColorToGray {
	
	// reprezentarea histogramei nivelurilor de fri intr-un fisier folosind simbolul *
	public static void createHistogramFile(int[] imgA, FileWriter fw, int val) throws IOException{
		String output = " ";
		
		for(int i = 0; i < 256; i++){
			output = "Val pixel gri: " + i + " Nr aparitii: " + imgA[i] + " " + output;
			
			// deoarece numarul de apartii a nivelului de gri dintr-o imagine poate fi extrem de mare
			// si ar fi ineficent, ar dura mult prea mult scrierea in fisier,
			// am micsorat acest numar pentru a grabi lucrurile
			if(imgA[i]/3000 < 3000 && imgA[i]/3000 > 1000)
				output += "******************";
			else if(imgA[i]/3000 < 1000 && imgA[i]/3000 > 100)
				output += "********";
			else if(imgA[i]/3000 < 100 && imgA[i]/3000 > 0)
				output += "***";
			else if(imgA[i]/3000 == 0)
				output += "*";
			for(int j = 0; j < (imgA[i]/3000); j++){
				output +="*";
				}
			if(imgA[i] == val)
				output +="**(val max)";
			// scrierea in fisier a nivelului de gri cu numarul sau de aparitii reprezentat prin *
			fw.write(output + "\n");
			output = " ";
		}
	}
	
	// functie ce returneaza un vector de aparitii al nivelurilor de gri (0 - 255)
	public static int[] createArray(int[][] m, int h, int w){
		int index = 0;
		int countVal[] = new int[256];
		
		for (int k = 0; k < 256; k++){ // contorizez numarul de aparitii a valorilor de gri
			for (int i = 0; i < h; i++){ // parcurg matricea pentru a gasi valoarea maxima a histogramei
				for (int j = 0; j < w; j++){
					if (m[j][i] == k){ // numar de cate ori apare valoare unui pixel in matrice (valorile histogramei)
						index++; 
					}
				}
			}
			countVal[k] = index; // retin numarul de aparitii pentru fiecare pixel
			index = 0; // numarul de aparitii se reseteaza cand se trece la urmatorul pixel
		}
		return countVal;
	}
	
	// functie ce returneaza val de gri a maximei histogramei
	public static int maxHist(int[][] m, int h, int w, FileWriter fw) throws IOException{
		int maxVal = 0, grayVal = 0; // initializez variabilele de care am nevoie
		int countVal[] = createArray(m, h, w);
		
		for (int k = 0; k < 256; k++){ // parcurg vectorul pentru a gasi valoare de gri a max hstogramei
			if (countVal[k] > maxVal){ // compar nr de aparitii a valorilor de gri ale histogramei
				maxVal = countVal[k]; // salvez valoarea max gasita
				grayVal = k; // salvez valoare pixel ce a apaut de cele mai multe ori in histograma
			}
		}	
		createHistogramFile(countVal, fw, maxVal); // se creaza fisierul cu histograma
		
		return grayVal; // returnez valoarea de gri a maximei histogramei
	} 
	
	// metoda principala pentru Gray Level Histogram of a Gray Scale Image
	public static void GrayImage(BufferedImage img, BufferedImage grayScaleImg, FileWriter fw, int val) throws IOException{
		int[][] imgM = convertColor(img, img.getHeight(), img.getWidth()); // matrice cu valorile de gri ale imaginii 
		
		// imagine niveluri gri
		int maxVal = maxHist(imgM, img.getHeight(), img.getWidth(), fw); // aflu valoarea maxima de gri
		if(maxVal > 250) // valorile sunt intre 0-255, daca e mai ca 250, sa zicem 253 si adaugam +5 => 258 => nu face parte din intervalul corespunzator
			maxVal = 250;
		
		for (int i = 0; i < img.getHeight(); i++){
			for (int j = 0; j < img.getWidth(); j++){
				if(val == 0){
					if(imgM[j][i] < maxVal - 5 || imgM[j][i] > maxVal + 5 ) // daca valoare nu face parte din intervalul +/-5 nivel max 
						imgM[j][i] = 255; // pixelul ia valoarea de 255 (alb)
				}
				
				grayScaleImg.setRGB(j, i, new Color(imgM[j][i], imgM[j][i], imgM[j][i]).getRGB()); // imaginea rezultata de Gray Scale Image (0) /Black White Image (1)
			}
		}
	} 
	
	public void inheritance(String[] args){
		System.out.print("Mostenire multipla: clasa Histogram mosteneste clasa ConvertColorToGray care mosteneste clasa GrayLevel care mosteneste Clasa Interface (3)");
	}

}
