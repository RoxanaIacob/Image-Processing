package Package2;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BufferClass {
	private boolean available = false;
	//argumentele imaginii
	String imgPath; // calea de acces la imagine
	private int height = 1, width = 1; // format imagine: inaltime, latime
	public BufferedImage imageS = null; // pentru citerea fiecurui sfert (1/4) de informatie
	
	public BufferClass(String p){ // constructor
		imgPath = p;
	}
	
	// citirea informatiilor din imaginea sursa (de catre Producer Thread)
	public synchronized void readImg(){
		
		File pathF = new File(this.imgPath);
		try{ // pregatesc imaginea pentru a citi un sfert din ea
			imageS = ImageIO.read(pathF);
			// inaltimea si latimea imaginii
			height = imageS.getHeight();
			width = imageS.getWidth();		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		// cum am facut la laborator
		while (!available) {
            try {
                wait(); // Asteapta Producer sa puna o valoare
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		available = false;
        notifyAll();
	}
	
	// prelucrarea informatiilor din imaginea sursa primite de la Producer Thread de catre Consumer Thread
	public synchronized BufferedImage writeImg(int section) throws IOException{
		// initialuzez un vector in care sa retin secventele
		BufferedImage imgs[] = new BufferedImage[4];
		
		// cum am facut la laborator
		while (available) { // fiecare sectiune este scrisa (sunt 4 sectiuni)
            try {
                wait(); // asteapta Consumer sa preaia valoarea
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		available = true;
        notifyAll();
        
        // se imparte egal imaginea pentru a obtine secventele 
        int subImg_W = width / 2;
		int subImg_H = height / 2;
		int current_img = 0;
        
		BufferedImage imgSection = new BufferedImage(subImg_W, subImg_H, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < 2; i++){
			for (int j = 0; j < 2; j++){
				// se creaza spatiul pentru cele 4 secvente
				imgs[current_img] = new BufferedImage(subImg_W, subImg_H, imageS.getType());
				Graphics2D img_creator = imgs[current_img].createGraphics();
				
				// coordonatele imaginii originale
				int src_first_x = subImg_W * j;
		        int src_first_y = subImg_H * i;
		        
		        // coordonatele secventelor create prin sectionarea imaginii
		        int dst_corner_x = subImg_W * j + subImg_W;
		        int dst_corner_y = subImg_H * i + subImg_H;
		        
		        // crearea secventelor
		        img_creator.drawImage(imageS, 0, 0, subImg_W, subImg_H, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);
		        current_img++;
		        
		        imgSection = imgs[section];
			}
		}
        return imgSection;
	}
	
	//Getter pentru returnarea inaltimii si lungimii
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
}
