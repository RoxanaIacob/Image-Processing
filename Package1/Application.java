package Package1;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Package2.BufferClass;
import Package2.ConsumerClass;
import Package2.Image;
import Package2.ProducerClass;

public class Application {
	
	public static String EnterData(Scanner data, String status){// introduc calea in linie de comanda
		String path = null;
		System.out.println("Enter the path to the image (" + status + "):");
		path = data.nextLine();
		return path;
	}
	
	public static void main(String[] args) throws IOException{
		// Introduc informatii despre imagine
		long startTime = System.currentTimeMillis(); // se masoara timpul executiei
		String pathOriginalImage = null;
		if(args.length == 0){
			System.out.println("Info Image are write to conlose");// se citeste din consola
			Scanner data1 = new Scanner(System.in);
			pathOriginalImage = EnterData(data1, "orifinal image"); // se scrie calea imaginii ce se doreste modificata
		}
		else // se citeste din linie de comanda cu argumente
			pathOriginalImage = args[0];
			
		System.out.println("You introduced this path (original image): " + pathOriginalImage);
		
		long endTime = System.currentTimeMillis(); // pana aici
		System.out.println("Read info about image took " + (endTime - startTime) + " milliseconds"); // afisare cat a durat executia
		
		// Citesc imagine
		Image.uploadImg(pathOriginalImage);
		endTime = System.currentTimeMillis();
		System.out.println("Image reading stage took " + (endTime - startTime) + " milliseconds");
		
		// MultiThreading
		startTime = System.currentTimeMillis();
		
		BufferClass buffer = new BufferClass(pathOriginalImage);
		buffer.imageS = new BufferedImage(Image.getImg().getWidth(), Image.getImg().getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		// cum am facut la laborator
		ProducerClass producer = new ProducerClass(buffer);
		producer.start();
		ConsumerClass consumer = new ConsumerClass(buffer);
		consumer.start();
		
		//se asteapta ca thredurile sa se sfarseasca
		try {//sincronizare
            consumer.join();
        } catch(InterruptedException e) {
        	 System.out.println("Interrupted");
        }
		endTime = System.currentTimeMillis();
		System.out.println("Multithreading + GrayScaleLevel took " + (endTime - startTime) + " milliseconds");
		
		// se creaza fisierul pentru a scrie in el histograma
		File F = new File("fisierHist.txt");
		FileWriter fw = new FileWriter(F);
		
		try {// se verifica daca fisierul este creat deja
			if(F.createNewFile()){ System.out.println("File got created");}
			
		} catch (IOException e) {
			System.out.println("File not got created");
			e.printStackTrace();
		}
		
		// se executa algoritmul pe intreaga imagine
		startTime = System.currentTimeMillis();
		Image.uploadImg(pathOriginalImage); // citire imagine
		
		Image.GrayImage(Image.getImg(), Image.getNewImg(), fw, 1); // Black White Image
		Image.newImg("bwImage.bmp", Image.getNewImg()); // unde se salveaza imaginea alb negru 
		
		fw.write("\n---------------- Histogram ---------------\n");
		
		Image.GrayImage(Image.getImg(), Image.getNewImg(), fw, 0); // Gray Scale Image
		Image.newImg("ImagineNoua.bmp", Image.getNewImg()); // unde se salveaza Noua Imagine si numele ei
		endTime = System.currentTimeMillis();
		System.out.println("Final " + (endTime - startTime) + " milliseconds");
		
		fw.close();
	}
}
