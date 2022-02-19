package Package2;
public class ProducerClass extends ThreadClass {
	private BufferClass buffer;
	
	// constructor
	public ProducerClass(BufferClass b){
		super(true);
		buffer = b;
	}
	
	@Override
	public void ThreadStart(){
		// citesc datele despre imagine si afisez de fiecare date cand s-a citit cate un sfert
		// din imagine
		for (int i = 0; i < 4; i++) {
            buffer.readImg();
            System.out.println("Sectiunea" + i);
        }
	}
}
