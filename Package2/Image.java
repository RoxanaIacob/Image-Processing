package Package2;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Package2.Histogram;

import java.io.File;


public class Image extends Histogram{
	private static BufferedImage img = null, grayScaleImg = null;
	
	// Metoda incarcare imagine 
	public static void uploadImg(String pathImg){
		File f = new File(pathImg);
		try{
			img = ImageIO.read(f);
			grayScaleImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	        System.out.println("Reading complete.");
	    }catch(IOException e){
	        System.out.println("Error uploadImg: " + e);
	    }
	}
	
	// Noua imagine creata
	public static void newImg(String pathImg, BufferedImage grayScaleImg){
		File f = new File(pathImg);
		try{
			ImageIO.write(grayScaleImg, "bmp", f);
	        System.out.println("Writing complete.");
	    }catch(IOException e){
	        System.out.println("Error saveImg: " + e);
	    }
	}
	
	// getter ce returneaza poza originala
	public static BufferedImage getImg(){
		return img;
	}
	
	// getter ce returneaza noua imagine creata
	public static BufferedImage getNewImg(){
		return grayScaleImg;
	}
	
	// Metoda de mosteire Interfata
	public void inheritance(String[] args){
		for(String a : args){
			System.out.println("Mostenire multipla: Image -> Histogram -> ConvertColorToGray -> GrayLevel -> Inteface => Start Application!");
		}
	}

}
