import java.io.*;
import java.util.Scanner;

public class NaiveBayes {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.out.println("Please enter two arguments in the format" +
			" <trainset_directory> <testset_directory>");
			System.exit(0);
		}
		String train_directory = args[0];
		String test_directory = args[1];
		
		File folder = new File(train_directory + "/English");
		File[] englishTrainFolder = folder.listFiles(); //Create an array of the english files
		folder = new File(train_directory + "/Japanese");
		File[] japaneseTrainFolder = folder.listFiles(); //Create an array of the japanese files
		folder = new File(train_directory + "/Spanish");
		File[] spanishTrainFolder = folder.listFiles(); //Create an array of the spanish files
		
		folder = new File(test_directory + "/English");
		File[] englishTestFolder = folder.listFiles(); //Create an array of the english test files
		folder = new File(test_directory + "/Japanese");
		File[] japaneseTestFolder = folder.listFiles(); //Create an array of the japanese test files
		folder = new File(test_directory + "/Spanish");
		File[] spanishTestFolder = folder.listFiles(); //Create an array of the spanish test files
		
		double nTotal = englishTrainFolder.length + japaneseTrainFolder.length + spanishTrainFolder.length; //total amount of files
		double pEnglish = priorProbability(englishTrainFolder.length, nTotal); //P(English)
		double pJapanese = priorProbability(japaneseTrainFolder.length, nTotal); //P(Japanese)
		double pSpanish = priorProbability(spanishTrainFolder.length, nTotal); //P(Spanish)
		
		double[] conditionalEnglish = conditionalProbability(englishTrainFolder);// holds P(English|Ck)
		double[] conditionalJapanese = conditionalProbability(japaneseTrainFolder); // holds P(Japanese|Ck)
		double[] conditionalSpanish = conditionalProbability(spanishTrainFolder); // holds P(Spanish|Ck)
		int[] englishClassified = new int[3]; //holds the distribution of documents classified
		int[] japaneseClassified = new int[3]; //holds the distribution of documents classified
		int[] spanishClassified = new int[3]; //holds the distribution of documents classified


		englishClassified = testing(conditionalEnglish, conditionalJapanese, conditionalSpanish, englishTestFolder,
							pEnglish, pJapanese, pSpanish, englishClassified); //Classifies the english test docs
		japaneseClassified = testing(conditionalEnglish, conditionalJapanese, conditionalSpanish, japaneseTestFolder,
							pEnglish, pJapanese, pSpanish, japaneseClassified); //Classifies the Japanese test docs
		spanishClassified = testing(conditionalEnglish, conditionalJapanese, conditionalSpanish, spanishTestFolder,
							pEnglish, pJapanese, pSpanish, spanishClassified); //Classifies the Spanish test docs
		
		String[] labels = {"actual English", "actual Japanese", "actual Spanish"};
		
		//Following four lines prints out a formatted confusion matrix
		System.out.println("\t\t" + "Predicted English | Predicted Japanese | Predicted Spanish");
		System.out.println(String.format("%-12s %11d %19d %20d",labels[0], englishClassified[0], englishClassified[1], englishClassified[2]));
		System.out.println(String.format("%-12s %10d %19d %20d",labels[1], japaneseClassified[0], japaneseClassified[1], japaneseClassified[2]));
		System.out.println(String.format("%-12s %11d %19d %20d",labels[2], spanishClassified[0], spanishClassified[1], spanishClassified[2]));

		
		

	}
	
	/**
	 * @param conditionalEnglish - holds conditional english probabilities
	 * @param conditionalJapanese - holds conditional Japanese probabilities
	 * @param conditionalSpanish - holds conditional Spanish probabilities
	 * @param folder - holds the folder of the documents being tested
	 * @param pEnglish - P(English)
	 * @param pJapanese - P(Japanese)
	 * @param pSpanish - P(Spanish)
	 * @param classified - holds the distribution of the classified documents
	 * 
	 * This method does a test on the test documents, classifying each one based on probabilities.
	 */
	public static int[] testing(double[] conditionalEnglish, double[] conditionalJapanese, double[] conditionalSpanish,
			File[] folder, double pEnglish, double pJapanese, double pSpanish, int[] classified) throws FileNotFoundException {
		double probEnglishDoc = pEnglish; //originally store P(English), this will be combined with all the conditional probs
		double probJapaneseDoc = pJapanese; //originally store P(Japanese), this will be combined with all the conditional probs
		double probSpanishDoc = pSpanish; //originally store P(Spanish), this will be combined with all the conditional probs

		Scanner in = null;
		String temp;
		char[] parsedInputs;
		for (File file : folder) {
			in = new Scanner(file);
			while (in.hasNextLine()) {
				temp = in.nextLine();
				parsedInputs = temp.toCharArray();
				for (int i = 0; i < parsedInputs.length; i++) {
					//This switch statement index's over each character in the test document and determines
					//which character it is. It then looks up the corresponding conditional probability and
					//adds that to the overall probability. By the end you will have P(Language|doc) computed
					//for each language.
					switch (parsedInputs[i]) {
					case 'a':
						probEnglishDoc += conditionalEnglish[0];
						probJapaneseDoc += conditionalJapanese[0];
						probSpanishDoc += conditionalSpanish[0];
						break;
					case 'b':
						probEnglishDoc += conditionalEnglish[1];
						probJapaneseDoc += conditionalJapanese[1];
						probSpanishDoc += conditionalSpanish[1];
						break;
					case 'c':
						probEnglishDoc += conditionalEnglish[2];
						probJapaneseDoc += conditionalJapanese[2];
						probSpanishDoc += conditionalSpanish[2];
						break;
					case 'd':
						probEnglishDoc += conditionalEnglish[3];
						probJapaneseDoc += conditionalJapanese[3];
						probSpanishDoc += conditionalSpanish[3];
						break;
					case 'e':
						probEnglishDoc += conditionalEnglish[4];
						probJapaneseDoc += conditionalJapanese[4];
						probSpanishDoc += conditionalSpanish[4];
						break;
					case 'f':
						probEnglishDoc += conditionalEnglish[5];
						probJapaneseDoc += conditionalJapanese[5];
						probSpanishDoc += conditionalSpanish[5];
						break;
					case 'g':
						probEnglishDoc += conditionalEnglish[6];
						probJapaneseDoc += conditionalJapanese[6];
						probSpanishDoc += conditionalSpanish[6];
						break;
					case 'h':
						probEnglishDoc += conditionalEnglish[7];
						probJapaneseDoc += conditionalJapanese[7];
						probSpanishDoc += conditionalSpanish[7];
						break;
					case 'i':
						probEnglishDoc += conditionalEnglish[8];
						probJapaneseDoc += conditionalJapanese[8];
						probSpanishDoc += conditionalSpanish[8];
						break;
					case 'j':
						probEnglishDoc += conditionalEnglish[9];
						probJapaneseDoc += conditionalJapanese[9];
						probSpanishDoc += conditionalSpanish[9];
						break;
					case 'k':
						probEnglishDoc += conditionalEnglish[10];
						probJapaneseDoc += conditionalJapanese[10];
						probSpanishDoc += conditionalSpanish[10];
						break;
					case 'l':
						probEnglishDoc += conditionalEnglish[11];
						probJapaneseDoc += conditionalJapanese[11];
						probSpanishDoc += conditionalSpanish[11];
						break;
					case 'm':
						probEnglishDoc += conditionalEnglish[12];
						probJapaneseDoc += conditionalJapanese[12];
						probSpanishDoc += conditionalSpanish[12];
						break;
					case 'n':
						probEnglishDoc += conditionalEnglish[13];
						probJapaneseDoc += conditionalJapanese[13];
						probSpanishDoc += conditionalSpanish[13];
						break;
					case 'o':
						probEnglishDoc += conditionalEnglish[14];
						probJapaneseDoc += conditionalJapanese[14];
						probSpanishDoc += conditionalSpanish[14];
						break;
					case 'p':
						probEnglishDoc += conditionalEnglish[15];
						probJapaneseDoc += conditionalJapanese[15];
						probSpanishDoc += conditionalSpanish[15];
						break;
					case 'q':
						probEnglishDoc += conditionalEnglish[16];
						probJapaneseDoc += conditionalJapanese[16];
						probSpanishDoc += conditionalSpanish[16];
						break;
					case 'r':
						probEnglishDoc += conditionalEnglish[17];
						probJapaneseDoc += conditionalJapanese[17];
						probSpanishDoc += conditionalSpanish[17];
						break;
					case 's':
						probEnglishDoc += conditionalEnglish[18];
						probJapaneseDoc += conditionalJapanese[18];
						probSpanishDoc += conditionalSpanish[18];
						break;
					case 't':
						probEnglishDoc += conditionalEnglish[19];
						probJapaneseDoc += conditionalJapanese[19];
						probSpanishDoc += conditionalSpanish[19];
						break;
					case 'u':
						probEnglishDoc += conditionalEnglish[20];
						probJapaneseDoc += conditionalJapanese[20];
						probSpanishDoc += conditionalSpanish[20];
						break;
					case 'v':
						probEnglishDoc += conditionalEnglish[21];
						probJapaneseDoc += conditionalJapanese[21];
						probSpanishDoc += conditionalSpanish[21];
						break;
					case 'w':
						probEnglishDoc += conditionalEnglish[22];
						probJapaneseDoc += conditionalJapanese[22];
						probSpanishDoc += conditionalSpanish[22];
						break;
					case 'x':
						probEnglishDoc += conditionalEnglish[23];
						probJapaneseDoc += conditionalJapanese[23];
						probSpanishDoc += conditionalSpanish[23];
						break;
					case 'y':
						probEnglishDoc += conditionalEnglish[24];
						probJapaneseDoc += conditionalJapanese[24];
						probSpanishDoc += conditionalSpanish[24];
						break;
					case 'z':
						probEnglishDoc += conditionalEnglish[25];
						probJapaneseDoc += conditionalJapanese[25];
						probSpanishDoc += conditionalSpanish[25];
						break;
					case ' ':
						probEnglishDoc += conditionalEnglish[26];
						probJapaneseDoc += conditionalJapanese[26];
						probSpanishDoc += conditionalSpanish[26];
						break;
					default:
						//ignore any random characters
					}
					
				}
			}
			if (probEnglishDoc > probJapaneseDoc && probEnglishDoc > probSpanishDoc) {
				classified[0] += 1; //Classify the document as English
			}
			else if (probJapaneseDoc > probEnglishDoc && probJapaneseDoc > probSpanishDoc) {
				classified[1] += 1; //Classify the document as Japanese
			}
			else {
				classified[2] += 1; //classify the document as Spanish
			}
		}
		
		return classified;
	}
	
	/**
	 * @param nLanguage - corresponds to nEnglish/nJapanese/nSpanish
	 * @param nTotal - amount of files total
	 * 
	 * This method calculates P(English), P(Japanese), P(Spanish)
	 */
	public static double priorProbability(double nLanguage, double nTotal) {
		double pLanguage = Math.log((nLanguage / nTotal)); //convert to a log probability
		return pLanguage;
	}
	
	/**
	 * @param folder - holds an array of training docs
	 * 
	 * This method calculates all the conditional probabilities like P(English|Ck)...
	 */
	public static double[] conditionalProbability(File[] folder) throws FileNotFoundException {
		double nCharLanguage = 0;
		double countLanguage[] = new double[27]; //holds count of 'a' - 'z' and 'space'
		Scanner in = null;
		String temp;
		char[] parsedInputs; //holds each individual char of the files for indexing over them.
		for (File file : folder) {
			in = new Scanner(file);
			while (in.hasNextLine()) {
				temp = in.nextLine();
				nCharLanguage += temp.length(); //update the total amount of chars seen
				parsedInputs = temp.toCharArray();
				for (int i = 0; i < parsedInputs.length; i++) {
					switch (parsedInputs[i]) { //go through each char in the file and increment the count of how
											   //many of each char is seen.
					case 'a':
						countLanguage[0] += 1;
						break;
					case 'b':
						countLanguage[1] += 1;
						break;
					case 'c':
						countLanguage[2] += 1;
						break;
					case 'd':
						countLanguage[3] += 1;
						break;
					case 'e':
						countLanguage[4] += 1;
						break;
					case 'f':
						countLanguage[5] += 1;
						break;
					case 'g':
						countLanguage[6] += 1;
						break;
					case 'h':
						countLanguage[7] += 1;
						break;
					case 'i':
						countLanguage[8] += 1;
						break;
					case 'j':
						countLanguage[9] += 1;
						break;
					case 'k':
						countLanguage[10] += 1;
						break;
					case 'l':
						countLanguage[11] += 1;
						break;
					case 'm':
						countLanguage[12] += 1;
						break;
					case 'n':
						countLanguage[13] += 1;
						break;
					case 'o':
						countLanguage[14] += 1;
						break;
					case 'p':
						countLanguage[15] += 1;
						break;
					case 'q':
						countLanguage[16] += 1;
						break;
					case 'r':
						countLanguage[17] += 1;
						break;
					case 's':
						countLanguage[18] += 1;
						break;
					case 't':
						countLanguage[19] += 1;
						break;
					case 'u':
						countLanguage[20] += 1;
						break;
					case 'v':
						countLanguage[21] += 1;
						break;
					case 'w':
						countLanguage[22] += 1;
						break;
					case 'x':
						countLanguage[23] += 1;
						break;
					case 'y':
						countLanguage[24] += 1;
						break;
					case 'z':
						countLanguage[25] += 1;
						break;
					case ' ':
						countLanguage[26] += 1;
						break;
					default:
						//ignore any random characters
					}
					
				}
			}
			
		}
		double tempNum;
		//This for loop calculates each conditional probability, stores it in to an array
		for (int i = 0; i < countLanguage.length; i++) {
			tempNum = (countLanguage[i] + 1) / (nCharLanguage + 27); //add 1 smoothing
			tempNum = Math.log(tempNum); //convert to Log probability
			countLanguage[i] = tempNum; //Store each probability for each character.
		}
		return countLanguage; //the array holds 27 conditional probabilities ('a' - 'z' and SPACE)
	}

}
