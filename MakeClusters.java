import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MakeClusters {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long tStart = System.nanoTime(); //count time.

		///////dinw tin diastasi twn dinaysmatwn k to posa dianysmata thelw 
		Scanner n = new Scanner (System.in);
		System.out.println("Enter dimension: ");
		int dimension = n.nextInt();
		System.out.println("Enter the number of vectors: ");
		int number_of_vectors = n.nextInt();
		n.close();
		
		MakeCluster_help MK = new MakeCluster_help() ;
		
		/////ftiaxnw ton fakelo pou tha valw mesa ta apotelesmata
		String FolderToSaveResults = "C:/Users/Naki/Documents/Clusters/"+dimension+"_"+number_of_vectors;
		MK.Create_Folder (FolderToSaveResults);

		///ftiaxnw fakelo midenikis epanalipsis
		String Folder_Rep_0 = "C:/Users/Naki/Documents/Clusters/"+dimension+"_"+number_of_vectors+"/0";
		MK.Create_Folder (Folder_Rep_0);
		
		//// diavazw ta arxika dianysmata kai ftiaxnw arxeia arithmitika me tin seira pou periexoun mesa ta dianysmata
		String BasicVectors = "C:/Users/Naki/Documents/Ptyxiaki/"+dimension+"_"+number_of_vectors+"/dianysmata/dianysmata_0.txt";
		MK.Prwto_vima (BasicVectors, Folder_Rep_0);
		
		//////vriskw poses epanalipseis eginan se autin tin dokimi
		String Rounds = "C:/Users/Naki/Documents/Ptyxiaki/"+dimension+"_"+number_of_vectors+"/synolikes plirofories.txt";
		int MergeRounds = MK.Epanalipseis (Rounds)+1;
		System.out.println("Merge Rounds  " + MergeRounds);
		
		int dian = number_of_vectors;
		
		////////vriskw poies omades prepei na enwsw
		ArrayList<Integer> Merge_Positions1 = new ArrayList<Integer>();
		ArrayList<Integer> Merge_Positions2 = new ArrayList<Integer>();
		for (int p=0; p<MergeRounds ; p++) {
			System.out.println("current merge round: " + p );

			System.out.println("Time from start: " + MK.Time_Clock( tStart) );
			
			//fakelos paliwn arxeiwn
			String Folder_From = "C:/Users/Naki/Documents/Clusters/"+ dimension+"_"+number_of_vectors+"/"+p;
			//fakelo epomenis epanalipsis
			String Folder_To = "C:/Users/Naki/Documents/Clusters/"+ dimension+"_"+number_of_vectors+"/"+(p+1);
			MK.Create_Folder (Folder_To);
			
			//arxeio apo to opoio diavazw tis omadopoiiseis pou ginontai se kathe epanalipsi
			String MergePairs = "C:/Users/Naki/Documents/Ptyxiaki/"+dimension+"_"+number_of_vectors+"/Merge_Pairs/Merge ("+p+") - Merge Pairs.txt";
//			//vazw tis omadopoiiseis se listes
			Merge_Positions1.clear();
			Merge_Positions1 = MK.Merge1 ( MergePairs, p);
			Merge_Positions2.clear();
			Merge_Positions2 = MK.Merge2 ( MergePairs);

			//prepei na vriskw mesa ston proigoumeno fakelo ta arxeia pou prepei na synenwsw kai ta grafw
			//se ena arxeio ston kainoyrio fakelo ta ypoloipa apla ta pernaw se kainourio arxeio 
			//me diaforetiko noumero
			String Arxeio1 = null;
			String Arxeio2 = null;
			String NeoArxeio = null;			
			int noumeroOmadas = 0;
			int seirastomerge = 0;
			
			for (int d=0; d<dian;d++){ ///gia oles tis omades
				boolean denprepei = false ; 
				if (seirastomerge<Merge_Positions1.size()){
					//an eimai se omada pou prepei na synenwthei tote:
					if (d==Merge_Positions1.get(seirastomerge)){ 
						Arxeio1 = Folder_From + "/"  + Merge_Positions1.get(seirastomerge) + ".txt" ;
						Arxeio2 = Folder_From + "/"  + Merge_Positions2.get(seirastomerge) + ".txt" ;
						NeoArxeio = Folder_To + "/"  + noumeroOmadas + ".txt" ;

						MK.PalioSeNeoArxeio ( Arxeio1,  NeoArxeio) ;
						MK.PalioSeNeoArxeio ( Arxeio2,  NeoArxeio) ;

						seirastomerge++;
						noumeroOmadas++;
						denprepei = true ; 
					}
				}
				
					//an einai omada pou vrisketaai stin deuteri stili tou meerge den prepei na kane tpt!
					if (denprepei==false){
						for (int i=0; i< Merge_Positions2.size();i++){
							if (d==Merge_Positions2.get(i)){ denprepei = true ; }
						}
					}
				
				//an eimai se klassiki omada pou apla prepei na antigrapsw
				if (denprepei == false){
					Arxeio1 = Folder_From + "/"  + d + ".txt" ;
					NeoArxeio = Folder_To + "/"  + noumeroOmadas + ".txt" ;
					MK.PalioSeNeoArxeio ( Arxeio1,  NeoArxeio) ;
					noumeroOmadas++;
				}
			}
			
			dian = dian -  Merge_Positions1.size();
			
		}

		//gia check
		String ReadLast = "C:/Users/Naki/Documents/Clusters/"+ dimension+"_"+number_of_vectors+"/"+MergeRounds+ "/0.txt";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(ReadLast));
		int count = 0; 
		String line=null;
		while ((line = bufferedReader.readLine()) != null) {
			count++;
		}
		bufferedReader.close();
		System.out.println("eixa arxika dianysmata: " + number_of_vectors);
		System.out.println("dianysmata pou vrika sto telos: " + count);

		System.out.println("TELOS ");

		
		
	}

}
