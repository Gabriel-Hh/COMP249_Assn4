package pk1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Desktop;

//-----------------------------------------------------
//Assignment 4
//Question: A
//Written by: Gabriel Horth, 40186942
//-----------------------------------------------------


/**
 * Creates a sub-dictionary of words found in an input file.
 * @author Gabriel Horth
 * @version 1.0
 *String
 */
public class subDictionaryCreator {
  
  private final static File sourceDirectory = new File(System.getProperty("user.dir") + "/sourceFiles");
  private final static File outputDirectory = new File(System.getProperty("user.dir") + "/outputFiles");
  private static File outputFile = null;
  private static String sourceFileName;
  private static Scanner key = new Scanner(System.in);
  private static Scanner fr;
  private static PrintWriter pw;
  private static PrintWriter log;
  protected static ArrayList<String> subDictionary = new ArrayList<>();
  
  static int inputWordCount=0;
  
  public static void main(String[] args) {
//	String[] prop = System.getProperties().toString().split(",");
//	for(String line: prop) {System.out.println(line);}
//	
	//Welcome
	System.out.println("\t\t\tWelcome to the Sub-Dictionary Creator:");
	System.out.println("==================================================================================");
	System.out.println();
	
	//SourceFile Selection
	displayAvailableSourceFiles();
	selectSourceFile();
	linkOutputFile();
	
	System.out.println();
	System.out.println("File linked sucessfully.");
	System.out.println("Press ENTER to begin processing..");
	key.nextLine();
	
	pullEligibleWords();
	System.out.println("Input Word count: " + inputWordCount);
	System.out.println("Eligible words: " + subDictionary.size());
	
	sortDictionary();

	subDictionary = deleteDuplicates();
	System.out.println("Sub-Dictionary length: " + subDictionary.size());
	System.out.println();

	writeOutputFile();
	System.out.println("Sub-Dictionary created successfully.");
	System.out.println("Press ENTER to open file.");
	key.nextLine();
	
	openSubDictionary();
	
	System.out.println("Program closing..");
	key.close();
	System.exit(0);
	
  }

  //regex captures, need to be placed in if, else if,... structure need to deal with contains digits first.
  //(( |\n)(a|A|I|i) )|((M|m)(c|C)(²|\^2))|(([a-zA-z]){1,}([?.,:;;'’]))|([a-zA-z]{2,})
  
  /**
   * 
   */
  protected static void displayAvailableSourceFiles() {
	String[] availableFiles = sourceDirectory.list((optionalInnerDirectory, fileInDirectory) -> {
	  return fileInDirectory.toLowerCase().endsWith("txt");
	});
	System.out.println("Available Source File(s):");
	for(String fileName: availableFiles) {System.out.println(fileName);}
	System.out.println();
  }
  
  /**
   * 
   */
  protected static void selectSourceFile() {
	System.out.println("Enter file name to process: ");
	sourceFileName = key.next();
	key.nextLine();
	try {
	  File selectedFile = new File(sourceDirectory + "/" + sourceFileName);
	  fr = new Scanner(new FileInputStream(selectedFile));
	}catch(FileNotFoundException | SecurityException e) {
	  System.out.println("File not found: Check file-name is correct and that file is readable.");
	  selectSourceFile();
	}
  }
  /**
   * 
   */
  protected static void linkOutputFile() {
	outputFile = new File(outputDirectory + "/SubDictionary.txt");
	File exceptionLog = new File(outputDirectory + "/Exceptions.log");
	try {
	  pw = new PrintWriter(new FileOutputStream(outputFile),true);
	  log = new PrintWriter(new FileOutputStream(exceptionLog,true),true);
	  Date logDate = new Date();
	  log.println("///////////////////////"+ logDate + "///////////////////////");
	} 
	catch (FileNotFoundException | SecurityException e) {
	  System.out.println("Error: Output file could not be created."
		+ "\nCheck output directory is correct and writable."
		+ "\nCurrent directory: " + outputDirectory.getAbsolutePath()
		+ "\nProgram closing..");
	  fr.close();
	  key.close();
	  System.exit(1);
	}
  }
  
  /**
   * 
   */
  protected static void pullEligibleWords() {
	String line = null;
	String[] lineArray;
	 
	
	while(fr.hasNextLine()) {
	  line = fr.nextLine();
	  lineArray = line.split("\s");
	  inputWordCount += lineArray.length;
	  for(String word: lineArray) {
		
//NOT WORKING	if (word.matches("mc²")) { //Einstein special ((M|m)(c|C)(²|^2)) // NOT WORKING
//		 subDictionary.add(word.substring(0,2).toUpperCase() + word.substring(2)); 
//		}
	
		if (word.matches(("([a-zA-z-]){1,}([?.,:;’'])"))) {// ([a-zA-z-]){1,}([?.,:;'’]) Words ending with special characters
		   word = word.substring(0, word.length()-1);
		  subDictionary.add(word.toUpperCase());
		} 
//NOT WORKING else if(word.matches("([0-9]+)")) {log.println("number: " + word);} // Excludes words with digits
		
	else if (specialCharMatch(word)) {
		  pw.println("matcher: "+ word);
		  subDictionary.add(word.toUpperCase());
		}
		else if (word.matches("(a|A|I|i)")) { // permitted 1-letter words
		  subDictionary.add(word.toUpperCase()); 
		}
		else if (word.matches("([a-zA-z-]{1})")) {log.println("One invalid letter: " + word);}
		else if (word.matches("([a-zA-z-]{2,})")) { // Regular words
		  subDictionary.add(word.toUpperCase());
		}
//UNNEEDED		else if (word.length() > 2 && (word.matches("([a-zA-z-]+)"))) {
//		  word = word.substring(0, word.length()-2);
//		  pw.println("Apostrophe? : " + word);
//		  subDictionary.add(word.toUpperCase());
//		}
		else if (word.contains("’") && word.length()> 2) {
		  word = word.substring(0, word.length()-2);
		  subDictionary.add(word.toUpperCase());
		}
		else if (word.regionMatches(0, "mc²", 0, 3)) {
		  word = word.substring(0, 3);
		  subDictionary.add(word.toUpperCase());
		}
		else {
		  log.println("other : " + word);
		}
	  } 
	}
	fr.close();
  }
 
  /**
   * 
   */
  protected static void sortDictionary() {
	subDictionary.sort(String.CASE_INSENSITIVE_ORDER);
  }
  
  /**
   * Removes all duplicates from Sub-Dictionary.
   * @return ArrayList<String> Duplicate-free Sub-Dictionary.
   */
  protected static ArrayList<String> deleteDuplicates() {
	ArrayList<String> singleEntryDictionary = new ArrayList<>();
	int i=0;
	singleEntryDictionary.add(subDictionary.get(i));
	for(i = 1; i < subDictionary.size();) {
	  try {
		while(subDictionary.get(i).equals(subDictionary.get(i-1))){
		  i++;
		}
		singleEntryDictionary.add(subDictionary.get(i));
		i++;
	  }catch (IndexOutOfBoundsException e) {}; // If the last item is a duplicate the while() and add() will throw Exception.
	}										   // You can check bounds each time before, or just catch it here if it happens.
	singleEntryDictionary.trimToSize();
	return singleEntryDictionary;
  }
  
  /**
   * 
   */
  protected static void writeOutputFile() {
	pw.println("Sub-Dictionary of " +sourceFileName+"."
		+ "\nContains " + subDictionary.size() + " entries.");
	int length = subDictionary.size();
	int i = 0;
	while(i < length) {
	  char header = subDictionary.get(i).charAt(0);
	  pw.println("\n" + header + "\n==");
	  while(i < length && subDictionary.get(i).charAt(0) == header) {
		pw.println(subDictionary.get(i));
		i++;
	  }
	}
	pw.close();
  }
  
  /**
   * 
   */
  protected static void openSubDictionary() {
	try {
	  Desktop.getDesktop().open(outputFile);
	} catch (IOException e) {
	  System.out.println("Error: " + outputFile.getName() + " could be opened.");
	  };
  }
  
  /**
   * 
   * @param word
   * @return
   */
  
  protected static boolean specialCharMatch(String word) {
	Pattern specialChar = Pattern.compile("mc²"); 
	Matcher matcher = specialChar.matcher(word);
	return matcher.matches();
  }
}
