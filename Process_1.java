import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Process_1 {
	
	public double Time_Clock(double tStart) {
	    double tEnd = System.nanoTime(); //end count time.
	    double tRes = tEnd - tStart; // time in nanoseconds
	    tRes=tRes * 1.66666667 * Math.pow(10, -11);// time in minutes
	    tRes = Math.round(tRes*100.0)/100.0;
	    return tRes;
	}

	public void Create_Folders (String FolderToSaveDianysmata, String FolderToSavePairs,
								String FolderToSaveMergeMIN, String FolderToSaveCentroids, 
								 String FolderToSaveIndexMoL){
	    // Create a directory; all non-existent ancestor directories are automatically created
		//create directory to save results of specific experiment 
		boolean success = (new File(FolderToSaveDianysmata)).mkdirs();
		if (!success) { System.out.println("Directory creation failed"); } //check gia to an egine o fakelos
		
		//create directory to save Merge_Pairs
		success = (new File(FolderToSavePairs)).mkdirs();
		if (!success) { System.out.println("Directory Merge_Pairs creation failed"); } //check gia to an egine o fakelos
	    
		//create directory to save Merge MiN 
		success = (new File(FolderToSaveMergeMIN)).mkdirs();
		if (!success) { System.out.println("Directory Merge MiN creation failed"); } //check gia to an egine o fakelos
	    
		//create directory to save Merge centroids 
		success = (new File(FolderToSaveCentroids)).mkdirs();
		if (!success) { System.out.println("Directory Merge centroids creation failed"); } //check gia to an egine o fakelos
	    
		//create directory to save Index of Min of every line
		success = (new File(FolderToSaveIndexMoL)).mkdirs();
		if (!success) { System.out.println("Directory Index of Min of every line creation failed"); } //check gia to an egine o fakelos
	}
	
	public void Record_Vectors (String file_to_read_from, String folder_to_write_dianysmata , 
								String General_folder, int number_of_vectors_want,int Merge_round , 
								int Total_Number_0f_Vectors,int column_number ) throws IOException{

		//Take random vectors needed
		int[] Lines_To_Read = Take_Random_Vectors ( number_of_vectors_want , Total_Number_0f_Vectors);

		BufferedReader bufferedReader = new BufferedReader(new FileReader(file_to_read_from));
		FileWriter nm = new FileWriter(folder_to_write_dianysmata + "/dianysmata_" + Merge_round + ".txt",true);
		PrintWriter out = new PrintWriter(new BufferedWriter(nm));

		String CurrentLine = null;	int countlines = 0;

		while (((CurrentLine = bufferedReader.readLine()) != null) ) {
			//koitaw an i grammi stin opoia vriskomai einai grammi pou prepei na diavasw
			boolean read_line = false; //arxikopoiw wste na min diavazei tin grammi
			for (int k=0; k<Lines_To_Read.length; k++) {
				if (countlines == Lines_To_Read[k]){
					read_line = true;
					break;//vgainei apo to for
				}
			}
			//an eimai se grammi pouthelw katagrafw to dianysma.
			if (read_line == true) {
				out.write(CurrentLine + "\n"); //grafw to onoma mesa sto antistoixo arxeio
			}
			countlines++;
		}
		bufferedReader.close();
		out.close();

	}

	private int[] Take_Random_Vectors (int number_of_vectors_want , int Total_Number_0f_Vectors) {
		//pairnw tyxaia tosa dianysmata osa thelw.
		ArrayList<Integer> Random_Lines = new ArrayList<Integer>();
		int[] Lines_To_Read = new int[number_of_vectors_want];
		for (int k=0; k<Total_Number_0f_Vectors; k++) {
			Random_Lines.add(new Integer(k));
		}
		Collections.shuffle(Random_Lines);
		for (int k=0; k<number_of_vectors_want; k++) {
			Lines_To_Read[k] = Random_Lines.get(k);
		}		
		return	Lines_To_Read;
	}
	
	public void Distance_Matrix (String folder_to_read_from , String folder_to_write_merge_MIN,
								String folder_to_write_index_MoL, int column_number , int Merge_round) throws IOException{
				
			System.out.println("Begin calculation of " + Merge_round + " distance matrix");
				
			String file_to_read_from = folder_to_read_from+"/dianysmata_" + Merge_round + ".txt"; 
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file_to_read_from));
				
			String line = null;	String column = null;
			float x = 0; double min = 1000 ;
			int count_line = 0 , count_column = 0 ;
			ArrayList<Integer> index_LINE = new ArrayList<Integer>();
			ArrayList<Integer> index_COLUMN = new ArrayList<Integer>();

			//iterate over rows
			while ((line = bufferedReader.readLine()) != null) {
			    String[] WordsInCurrentLine = line.split ("\t"); //xwrizw tin grammi se lekseis
			    float[] dianysma_grammis = new float[WordsInCurrentLine.length];
			    for (int p=0 ; p<WordsInCurrentLine.length ; p++){
			    	dianysma_grammis[p] = Float.parseFloat(WordsInCurrentLine[p]);
			    }
				//anoigw to idio arxeio deuteri fora
				BufferedReader bufferedReader_2 = new BufferedReader(new FileReader(file_to_read_from));
			    column = null;	count_column = 0;

				//iterate over columns
				while ((column = bufferedReader_2.readLine()) != null) {
					if (count_line < count_column){//gia na min ypologizw polles fores tin idia apostasi
						String[] WordsInCurrentLine_2 = column.split("\t");
						//iterate over columns to find the sum of squares
			       		double sum_of_squares = 0;
			       		for ( int coll=0; coll < column_number ; coll++) {
			       			x = dianysma_grammis[coll] - Float.parseFloat(WordsInCurrentLine_2[coll]);
			       		 	sum_of_squares = sum_of_squares + Math.pow( x , 2 );
			       		} //end for
			       		double distance= Math.sqrt(sum_of_squares);
			       		distance = Math.round(distance*100.0)/100.0;

			       		if (distance<min){
							min = distance ; 
							index_LINE.clear(); index_COLUMN.clear();
							index_LINE.add(count_line);	index_COLUMN.add(count_column);
						} 
						else if(distance==min){
							index_LINE.add(count_line); index_COLUMN.add(count_column); 
						}		       		
			       		
					}
					count_column++;
				}
						 
				bufferedReader_2.close();
				count_line++;
			}
			bufferedReader.close();
						
			FileWriter m1 = new FileWriter(folder_to_write_merge_MIN+"/Merge (" + Merge_round + ") - MIN" + ".txt",true); // arxeio gia na grafw to min kathe grammis
			PrintWriter min_of_all_lines = new PrintWriter(new BufferedWriter(m1));
			min_of_all_lines.write(min + "\n");
			min_of_all_lines.close();
			
			FileWriter indmin1 = new FileWriter(folder_to_write_index_MoL+"/Index of all min positions of every line_" + Merge_round + ".txt",true); // arxeio gia na grafw to index twn min kathe grammis
			PrintWriter index_of_mins = new PrintWriter(new BufferedWriter(indmin1));
			for (int g=0 ; g<index_LINE.size() ; g++){ 
				index_of_mins.write(index_LINE.get(g) + "\t" + index_COLUMN.get(g) + "\n");
			}
			index_of_mins.close();
			
		}
		
	public ArrayList<Integer> Calculate_pairs_to_be_merged_in_clusters (String folder_to_read_Index_MoL ,
										String folder_to_write_merge_pairs , int column_number, int Merge_round) throws IOException{
		
		//	calculate pairs to be merged in clusters
		System.out.println("calculate pairs to be merged in clusters");
        String file_Ind_of_Min = folder_to_read_Index_MoL+"/Index of all min positions of every line_" + Merge_round + ".txt";
		FileReader fileReader_2 = new FileReader(file_Ind_of_Min);  
		BufferedReader bufferedReader_2 = new BufferedReader(fileReader_2);
    
		ArrayList<Integer> Merge_Positions = new ArrayList<Integer>(); // gia na krataw ta index twn min kathe grammis
		String line_Index = null; 
		int a1=0, a2=0, c=0 ;

		//vriskw ta zeugaria-theseis pou den dimiourgoun diplotypa
		while ((line_Index = bufferedReader_2.readLine()) != null) {
				String[] WordsInCurrentLine = line_Index.split ("\t");
				c=0;
				a1 = Integer.parseInt(WordsInCurrentLine[0]); //to prwto stoixeio tou zeugariou pou exei to min
				a2 = Integer.parseInt(WordsInCurrentLine[1]);	//to deutero stoixeio tou zeugariou pou exei to min
				for (int w=0; w< Merge_Positions.size() ; w++){
					if (a1==(Merge_Positions.get(w)) || a2==(Merge_Positions.get(w))) {
						c++;
						break; 
					}
				}
				if (c==0){
					Merge_Positions.add(a1);
					Merge_Positions.add(a2);
				}
		}
		bufferedReader_2.close();
				
		//Check gia to an yparxoun ontws diplotypa stin lista
		//Yparxoun_diplotypa_stin_lista_twn_Merge_Position (Merge_Positions);
		
		Write_MERGING_Pairs_to_File ( folder_to_write_merge_pairs, Merge_Positions, Merge_round);
		
		return Merge_Positions;
	}
	
	
	private void Write_MERGING_Pairs_to_File (String folder_to_write_merge_pairs, ArrayList<Integer> Merge_Positions,
												int Merge_round) throws IOException{
				
		FileWriter m1 = new FileWriter(folder_to_write_merge_pairs+"/Merge (" + Merge_round + ") - Merge Pairs" + ".txt",true); // arxeio gia na grafw to min kathe grammis
		PrintWriter minim = new PrintWriter(new BufferedWriter(m1));
		for(int f=0; f<Merge_Positions.size();f=f+2){
			minim.write(Merge_Positions.get(f) + "\t" + Merge_Positions.get(f+1) + "\n");
		}
		minim.close();
		
	}
	
	private void Yparxoun_diplotypa_stin_lista_twn_Merge_Position (ArrayList<Integer> Merge_Positions){
		//Check gia to an yparxoun ontws diplotypa stin lista
		int q=0, p=0, ccc=0;
		for (int w1=0; w1< Merge_Positions.size() ; w1++){
			q = (Merge_Positions.get(w1));
				for (int w2=w1+1; w2< Merge_Positions.size() ; w2++){
					p = (Merge_Positions.get(w2));
					if (q==p){
						ccc++;		       					
					}
				}
		}
		System.out.println("Vrika diplotypa sti lista: " + ccc + " zeugaria");
	}
	
	
	public String[] Find_Vectors_That_Will_Be_Merged (String folder_to_read_dianysmata,	int Merge_round, 
													ArrayList<Integer> Merge_Positions) throws NumberFormatException, IOException {
		
		String Synolo_dianysmatwn_txt = folder_to_read_dianysmata +"/dianysmata_" + Merge_round + ".txt";
		//gemizw ta mona dianysmata pou einai se auksousa seira
		BufferedReader bufferedReader = new BufferedReader(new FileReader(Synolo_dianysmatwn_txt));
		
		String line = null; int f = 0 , count_line = 0;	
		String[] Vectors_to_Merge = new String[Merge_Positions.size()];
		
		while ((line = bufferedReader.readLine()) != null) {
			if ((Merge_Positions.get(f))==count_line){
				Vectors_to_Merge[f]=line;
				if (f<Merge_Positions.size()-2){
					f=f+2;
				}
			}
			count_line++;
		}
		bufferedReader.close();
		
		//gemizw ta zyga dianysmata pou den einai taksinomimena. :(
		for (int d=1 ; d<Vectors_to_Merge.length ; d=d+2){ // loopa gia tis zyges theseis  
			bufferedReader = new BufferedReader(new FileReader(Synolo_dianysmatwn_txt));
			line = null; count_line = 0;
			while ((line = bufferedReader.readLine()) != null) {//psaxnw mesa sto arxeio gia to d stoixeio...
				if ((Merge_Positions.get(d))==count_line){
					Vectors_to_Merge[d]=line;				
					break; //vgainei apo to while
				}
				count_line++;
			}
			bufferedReader.close();
		}
		
//		//check oti einai swsta valmena
//		for(f=0; f<Vectors_to_Merge.length; f=f+2){
//			System.out.println("Merge elements: " + Vectors_to_Merge[f] + " - " + Vectors_to_Merge[f+1]);
//		}
		
		return Vectors_to_Merge;
	}
	
	public int Find_Centroids_and_Write_Them_To_File (String folder_to_write_centroids,	String[] Vectors_to_Merge, int Merge_round) throws IOException {
		
		//vriskw ta centroids kai ta grafw se arxeio
		FileWriter m1 = new FileWriter(folder_to_write_centroids+"/merged_centroids_"+ Merge_round +".txt",true); 
		PrintWriter out = new PrintWriter(new BufferedWriter(m1));
		
		for (int f=0 ;f<Vectors_to_Merge.length ; f=f+2){
			String[] vector_1 = Vectors_to_Merge[f].split ("\t");
			String[] vector_2 = Vectors_to_Merge[f+1].split ("\t");
		 	//ypologizw to centroid ana diastasi
		 	for (int j=0 ; j<vector_1.length ; j++){
		 		double mean = Double.parseDouble(vector_1[j]) + Double.parseDouble(vector_2[j]) ;
		    	mean = mean/2;
		 		mean = Math.round(mean*100.0)/100.0;
	     		out.write( mean + "\t");
		 	}
		 	out.write("\n");
		}
		out.close();
		return Vectors_to_Merge.length/2;
	}
		
	public void Write_New_Vectors (String folder_to_write_dianysmata, String folder_to_read_centroids,
									ArrayList<Integer> Merge_Positions, int Merge_round) throws IOException {
		
		//ftiaxnw to neo arxeio dianysmatwn		
		FileWriter m1 = new FileWriter(folder_to_write_dianysmata+"/dianysmata_"+ (Merge_round+1) + ".txt",true); // arxeio gia na grafw to min kathe grammis
		PrintWriter out = new PrintWriter(new BufferedWriter(m1));
		
		// to arxeio gia na vrw ta dianysmata pou den allaksan
	    String file = folder_to_write_dianysmata+"/dianysmata_"+ Merge_round + ".txt";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		//to arxeio gia ta kainouria dianysmata.
	    String file_2 = folder_to_read_centroids+"/merged_centroids_"+ Merge_round + ".txt";
		BufferedReader bufferedReader_2 = new BufferedReader(new FileReader(file_2));
		
		int count_lines = 0 ;	int eimai_stin_grammi_tou_merge = -1;
		String line=null, line_2 = bufferedReader_2.readLine();
		
		while ((line = bufferedReader.readLine()) != null) { //diavazw to arxeio me ta palia dianysmata.
			eimai_stin_grammi_tou_merge = -1;
			for(int h=0 ; h<Merge_Positions.size() ; h++){
				if (count_lines==(Merge_Positions.get(h))){
					eimai_stin_grammi_tou_merge = h ;
					break; //vgainei apo to for 
				}
			}
			if ( (eimai_stin_grammi_tou_merge==-1) ) { // eimai se asxeti grammi
				out.write( line + "\n");
			}
			else if ( (eimai_stin_grammi_tou_merge % 2 )==0 ) { //eimai sto prwto stoixeio tou zeugariou pou enwnw
				//einai kata auksousa taksinomimena auta pou prosthetw opote den xriazetai na ta psaxnw sto arxeio.
				out.write( line_2 + "\n");
   		 		line_2 = bufferedReader_2.readLine();
			}
			else { // eimai sto deutero stoixeio tou zeugariou pou enwnw ara den xreiazetai na grapsw tpt!!
			}
			count_lines++;
		}
		out.close();
		bufferedReader.close();
		bufferedReader_2.close();
	}
	
		
}