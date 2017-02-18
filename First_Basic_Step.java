import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class First_Basic_Step {

	public double Time_Clock(double tStart) {
	    double tEnd = System.nanoTime(); //end count time.
	    double tRes = tEnd - tStart; // time in nanoseconds
	    tRes=tRes * 1.66666667 * Math.pow(10, -11);// time in minutes
	    tRes = Math.round(tRes*100.0)/100.0;
	    return tRes;
	}
	
	public String[] Read_txt_Files_From_Folders(File folder_to_read_from) {
		//find the .txt files in the folder below
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".txt");}};
		
		String[] listOfFiles = folder_to_read_from.list(filter);
		return listOfFiles;
	}
	
	public int[] StandarNumbers(int column_number) {
		int[] standar_numbers = new int[2];
		//otan exw 5 plains
		standar_numbers[0] = 2; //first_element
		standar_numbers[1] = 8; //Words In Current Line
		//otan exw 10 plains
		if (column_number==10){
			standar_numbers[0] = 1; //first_element
			standar_numbers[1] = 11;} //Words In Current Line
		return standar_numbers;
	}
	
	public void First_Data_Read_and_Classification(String filename_to_read_from,String folder_path_to_save_to , int column_number) throws IOException {
		FileReader fileReader = new FileReader(filename_to_read_from);  
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		//vasikes metavlites gia na min vgazei lathi, giati kathe arxeio exei diaforetiko tropo syntaksis
		int first_elements_place = StandarNumbers(column_number)[0];
		int W_I_C_L = StandarNumbers(column_number)[1];

		//metavlites pou xreiazontai gia to iteration
		int countlines=0; String CurrentLine = null , code = null; 
		String[] Numbers= new String [column_number];
     
		while (((CurrentLine = bufferedReader.readLine()) != null) ) { //Reads a line of text every time.
			if (countlines>3) {
				String[] WordsInCurrentLine = CurrentLine.split ("[ ]+"); //split line to words
				if (WordsInCurrentLine.length==W_I_C_L){//gia na apofygw ta lathi stis arxikes grammes
					for (int j=0; j<column_number; j++){
						Numbers[j]=WordsInCurrentLine[j+first_elements_place]; //keep only the vector numbers
					}
					code = Arrays.toString(Numbers); //convert numbers to string
					//create file with vector's name
					FileWriter nm = new FileWriter(folder_path_to_save_to + "/" +code +".txt",true); 
		       		BufferedWriter br = new BufferedWriter(nm);
		       		PrintWriter out = new PrintWriter(br);
		       		out.write(WordsInCurrentLine[0] + "\n"); //write name of specific vector inside the file I've just create
		       		out.close();
				}
			}
			countlines++;
		}
		bufferedReader.close();
		System.out.println("lines of data in text: " + (countlines-4) );
	}
	
	public int Record_ALL_Vectors (File folder_to_read_from,String folder_path_to_write_to ,int column_number , int Merge_round) throws IOException {

		String[] list_Of_Files = Read_txt_Files_From_Folders(folder_to_read_from);
  		
		System.out.println("plithos dianysmatwn pou yparxoun synolika sto arxeio: " + list_Of_Files.length);
  		//create file to write ALL vectors
  		FileWriter nm = new FileWriter(folder_path_to_write_to +"/dianysmata_" + (Merge_round -1) + ".txt",true);
  		BufferedWriter br = new BufferedWriter(nm);
  		PrintWriter out = new PrintWriter(br);
  		
  		String[] dian = null;
  		//iteration over the txt files
  		for (int k = 0; k < list_Of_Files.length; k++) {
  			dian = list_Of_Files[k].split("[^0-9]");
  			for(int b =1; b < dian.length ; b=b+2){
  				out.write(dian[b] + "\t");
  			}
  			out.write("\n");
  		}
  		out.close();
  		return list_Of_Files.length;
	}
	
	public void Create_Folder (String FolderName){
		boolean success = (new File(FolderName)).mkdirs();
		if (!success) { System.out.println("Directory creation failed"); } 
	}


}
