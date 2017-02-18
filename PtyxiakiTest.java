import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PtyxiakiTest {

	public static void main(String[] args) throws IOException {		
		//select plains 
		String[] FolderToReadDataFrom = { "C:/Users/Naki/Dropbox/5plains_ALL_Vectors" ,
											"C:/Users/Naki/Dropbox/10plains_ALL_Vectors" };
		System.out.println("Enter the additional number: \n"
							+ "1 - " + FolderToReadDataFrom[0] + "\n"
							+ "2 - " + FolderToReadDataFrom[1]);
		Scanner n = new Scanner (System.in);
		int plains = n.nextInt();

		//diavazw tis vasikes plirofories
		String file = FolderToReadDataFrom[plains-1] + "/synolikes plirofories-1.txt" ;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String[] line = bufferedReader.readLine().split ("\t"); bufferedReader.close();
		int columnNumber = Integer.parseInt (line [0]);
		int Number_0f_ALL_Vectors = Integer.parseInt(line [1]);
				
		//epilogi tou plithous twn epithymitwn dianysmatwn
		System.out.println("Number of all vectors: " + Number_0f_ALL_Vectors);
		System.out.println("Enter the number of vector you want to begin with: ");
		int number_of_vectors_wanted = n.nextInt();
		while (number_of_vectors_wanted>Number_0f_ALL_Vectors || number_of_vectors_wanted<-1 ){
			System.out.println("Enter the number of vector you want to begin with: ");
			number_of_vectors_wanted = n.nextInt();
		}
		n.close();
		
		First_Basic_Step First_Step = new First_Basic_Step(); //create class


//for (int number_of_vectors_wanted = 100; number_of_vectors_wanted<1000 ;
//				number_of_vectors_wanted=number_of_vectors_wanted+100) {
		
		String FolderToSaveALLResults = "C:/Users/Naki/Dropbox/" + columnNumber + "_" + number_of_vectors_wanted;
		First_Step.Create_Folder (FolderToSaveALLResults)
		
		int MergeRound = 0;
		Process_1 First_Read = new Process_1(); 
		
		//ftiaxnw fakelous pou tha xreiastw
		String FolderToSaveDianysmata = FolderToSaveALLResults + "/dianysmata";
		String FolderToSavePairs = FolderToSaveALLResults + "/Merge_Pairs"; 
		String FolderToSaveMergeMIN = FolderToSaveALLResults + "/Merge_MiN";
		String FolderToSaveCentroids = FolderToSaveALLResults + "/Merge_Centroids";
		String FolderToSaveIndexMoL = FolderToSaveALLResults + "/Index_MoL";
		First_Read.Create_Folders ( FolderToSaveDianysmata,  FolderToSavePairs, FolderToSaveMergeMIN, FolderToSaveCentroids, FolderToSaveIndexMoL);
		
		long tStart = System.nanoTime();
		
		//epilogi kai katagrafi twn epithymitwn dianysmatwn apo ta synolika pou dimourgithikansto FIrsst_Step_Of_Clustering.
		First_Read.Record_Vectors(FolderToReadDataFrom[plains-1] + "/dianysmata_-1.txt" ,
									FolderToSaveDianysmata,FolderToSaveALLResults, number_of_vectors_wanted, MergeRound,
									Number_0f_ALL_Vectors, columnNumber);
		System.out.println("Xronos gia oloklirwsi epilogis dianysmatwn: " + First_Read.Time_Clock(tStart) + "minutes");
		
		int Number_0f_Vectors = number_of_vectors_wanted;
		
		tStart = System.nanoTime(); //count time.
		
	//vasiki epanalipsi tou algorithmou. mexri na exw mono 1 omada!
    while (Number_0f_Vectors>1) {
    	System.out.println("PLithos dianysmatwn - (" + MergeRound + ") : " + Number_0f_Vectors);

	    First_Read.Distance_Matrix(FolderToSaveDianysmata,FolderToSaveMergeMIN,FolderToSaveIndexMoL,
	    								columnNumber, MergeRound);
	    
		System.out.println("Time after calculating " + MergeRound + " Distance matrix: " + First_Read.Time_Clock(tStart) + "minutes");

		ArrayList<Integer> Merge_Positions = new ArrayList<Integer>();		
		Merge_Positions = First_Read.Calculate_pairs_to_be_merged_in_clusters
												( FolderToSaveIndexMoL,FolderToSavePairs,columnNumber , MergeRound);
	    
	    String[] Vectors_to_Merge = First_Read.Find_Vectors_That_Will_Be_Merged (FolderToSaveDianysmata , MergeRound, Merge_Positions);

	    Number_0f_Vectors = Number_0f_Vectors -  First_Read.Find_Centroids_and_Write_Them_To_File 
	    														(FolderToSaveCentroids, Vectors_to_Merge, MergeRound);
	    
	    First_Read.Write_New_Vectors (FolderToSaveDianysmata,FolderToSaveCentroids,Merge_Positions, MergeRound);
	    System.out.println("Time til after writing the new vectors - mergeRound ("+ MergeRound + ") : " + First_Read.Time_Clock(tStart) + "minutes");
	    System.out.println("  ");

	    MergeRound++;
    
    }
    
    	//katagrafi vasikwn pliroforiwn.
	    System.out.println("Total Time: " + First_Read.Time_Clock(tStart) + "minutes");

  		FileWriter nm = new FileWriter(FolderToSaveALLResults+"/synolikes plirofories.txt",true);
  		PrintWriter out = new PrintWriter(new BufferedWriter(nm));
  		out.write("Number of Merge Rounds: " + (MergeRound-1) + "\n"
  				+ "Number of Vectors: "+ number_of_vectors_wanted + "\n"
  				+ "Vectors dimensions: " +  columnNumber + "\n"
  				+ "Total time (in minutes): " + First_Read.Time_Clock(tStart));
		out.close();
	    
	    System.out.println("THE END  -   " + number_of_vectors_wanted);		
		
//}    
	    
	    
	}

}
