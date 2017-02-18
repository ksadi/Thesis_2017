import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class First_Step_Of_Clustering {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
 //Η διαδικασία γίνεται μια φορά για κάθε σετ οργανισμών.
		
		String[] FolderToReadDataFrom = { "C:/Users/Naki/Dropbox/5plains" , 
											"C:/Users/Naki/Dropbox/10plains" };
		int[] NumberOfColumns = {5,10}; //number of columns in addition to FolderToReadDataFrom
		
		//iteration over the different folders in FolderToReadDataFrom
		for (int p=0 ; p<2 ; p++) {
			long tStart = System.nanoTime(); //count time.

			int columnNumber = NumberOfColumns[p];//columns of the data in the specific folder p
			File folder = new File(FolderToReadDataFrom[p]); //folder to read the data files from
			First_Basic_Step First_Step = new First_Basic_Step(); //create class
			
			// Create a directory. create directory to save the different vectors txt's
			String FolderToSaveVectors = FolderToReadDataFrom[p]+"_vectors" ;
			First_Step.Create_Folder(FolderToSaveVectors);
		
			String[] Files_in_Folder = First_Step.Read_txt_Files_From_Folders(folder); //Read txt files from folders
	
			// Για κάθε φάκελο με οργανισμούς ουσιαστικά ομαδοποιώ τις πρωτείνες που έχουν ίδια φυλογεννετικά προφίλ
			// Το όνομα του αρχείου είναι το φυλογενετικό προφίλ και το περιεχόμενο είναι οι ονομασίες των πρωτεινών 
			// που αντιστοιχούν σε αυτό.
			for (int i = 0; i < Files_in_Folder.length; i++) { //iteration over the txt files in the specific folder p
				System.out.println("  ");
				System.out.println(i+" -> "+Files_in_Folder[i]);
				
				String FileInFolder = FolderToReadDataFrom[p] + "/" + Files_in_Folder[i]; // to onoma tou arxeiou
				try {First_Step.First_Data_Read_and_Classification(FileInFolder ,FolderToSaveVectors,columnNumber); }
				catch(IOException e) {System.out.println("Unable to create "+ FileInFolder +": "+e.getMessage());}//Print the exception that occurred  
			}
			
			int MergeRound = 0 ;
			
			//Για κάθε οργανισμό διαβάζω τα διαφορετικά αρχεία που δημιουργήθηκαν με την παραπάνω διαδικασία
			//και γράφω τα ονόματα των φυλογενετικών προφίλ σε ένα αρχείο
			//Καταγράφω τα διακεκριμένα φυλογενετικά προφίλ που περιέχει κάθε σετ με οργανισμούς
			
			//φάκελος στον οποίο θα βρω τα διαφορετικά φυλογενετικά προφίλ που δημιούργησα παραπάνω.
			File FolderToRead = new File(FolderToSaveVectors); 
			String FolderPathToWrite = FolderToReadDataFrom[p] + "_ALL_Vectors";
			First_Step.Create_Folder(FolderPathToWrite);

			int Number_0f_ALL_Vectors = First_Step.Record_ALL_Vectors(FolderToRead,FolderPathToWrite, columnNumber,MergeRound);

			double time = First_Step.Time_Clock( tStart);
			System.out.println("Time till First Step Of Clustering :" + time);
			
			//πλήθος στηλών //πλήθος διανυσμάτων //χρόνος υλοποίησης
	  		FileWriter nm = new FileWriter(FolderPathToWrite +"/synolikes plirofories" + (MergeRound -1) + ".txt",true);
	  		PrintWriter out = new PrintWriter(new BufferedWriter(nm));
	  		out.write(columnNumber + "\t" + Number_0f_ALL_Vectors + "\t" + time);
			out.close();
	  		
		}
	}
}
