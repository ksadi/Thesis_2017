import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MakeCluster_help {

	public double Time_Clock(double tStart) {
	    double tEnd = System.nanoTime(); //end count time.
	    double tRes = tEnd - tStart; // time in nanoseconds
	    tRes=tRes * 1.66666667 * Math.pow(10, -11);// time in minutes
	    tRes = Math.round(tRes*100.0)/100.0;
	    return tRes;
	}
	
	public void Create_Folder (String FolderName){
		boolean success = (new File(FolderName)).mkdirs();
		if (!success) { System.out.println("Directory creation failed"); } 
	}
	
	public void Prwto_vima (String Vectors, String FolderToWrite) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(Vectors));
		
		
		String line = null;
		int count_line = 0;
		while ((line = bufferedReader.readLine()) != null) {
			String FiletoWriteVectorsInCluster = FolderToWrite +"/"+count_line+".txt";
			FileWriter m1 = new FileWriter(FiletoWriteVectorsInCluster,true); // arxeio gia na grafw to min kathe grammis
			PrintWriter miden = new PrintWriter(new BufferedWriter(m1));
			miden.write(line + "\n"); //grafw to oliko min sto txt
			miden.close();
			count_line++;
		}
		bufferedReader.close();
	}
	
	public int Epanalipseis (String FileRounds) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(FileRounds));
		String[] lin = bufferedReader.readLine().split(" ");
		int MergeRounds = Integer.parseInt (lin [4]);
		bufferedReader.close();
		return MergeRounds;
	}
	
	public ArrayList<Integer> Merge1 (String ReadMergePairs,int p) throws NumberFormatException, IOException {
		ArrayList<Integer> Merge_Positions = new ArrayList<Integer>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(ReadMergePairs));
		Merge_Positions.clear();
		String line=null;	
		while ((line = bufferedReader.readLine()) != null) {
			String[] WordsInLine = line.split("\t");
			Merge_Positions.add(Integer.parseInt(WordsInLine[0]));
		}
		bufferedReader.close();
		return Merge_Positions;
	}
	
	public ArrayList<Integer> Merge2 (String ReadMergePairs) throws NumberFormatException, IOException {
		ArrayList<Integer> Merge_Position = new ArrayList<Integer>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(ReadMergePairs));
		String line=null;
		while ((line = bufferedReader.readLine()) != null) {
			String[] WordsInLine = line.split("\t");
			Merge_Position.add(Integer.parseInt(WordsInLine[1]));
		}
		bufferedReader.close();
		return Merge_Position;
	}

	public void PalioSeNeoArxeio (String PalioArxeio, String NeoArxeio) throws IOException{
		//grafw to palio arxeio sto neo arxeio
		FileWriter m1 = new FileWriter(NeoArxeio,true); // arxeio gia na grafw to min kathe grammis
		PrintWriter neoarxeio = new PrintWriter(new BufferedWriter(m1));
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(PalioArxeio));	
		String line=null;
		while ((line = bufferedReader.readLine()) != null) {neoarxeio.write( line + "\n");}
		bufferedReader.close();
		neoarxeio.close();

	}

}
