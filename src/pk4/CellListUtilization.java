package pk4;

import pk2.CellPhone;
import pk3.CellList;
import pk3.CellList.CellNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//-----------------------------------------------------
//Assignment 4
//Question: B
//Written by: Gabriel Horth, 40186942
//-----------------------------------------------------


/**
 * Driver to test function and edge cases of a CellList. Equally test method exceptions.
 * @author Gabriel
 * @version 1.1
 *
 */
public  class CellListUtilization {
  private static File inputDirectory = new File(System.getProperty("user.dir") + "/sourceFiles");
  private static Scanner fr[] = null;
  private static Scanner key = new Scanner(System.in);
  private static File[] sourceFiles;

  public static void main(String[] args) {
	System.out.println("\t\tWELCOME TO THE CELL-LIST TESTER by Gabriel Horth");
	System.out.println("=============================================================================");
	
	CellList list1 = new CellList();
	
	
	sourceFiles =  new File[1];
	sourceFiles[0] = new File(inputDirectory + "/Cell_Info.txt");
	
	System.out.println("\nCreating new CellList from data contained in " + sourceFiles[0].getName());
	stall();
	linkSourceFiles();
	
	list1.fileToCellList(fr[0]);
	
	list1.showContents();
	//Exception Tests
	System.out.println("Would you like to test Exceptions now?"
		+ "\n1.Yes"
		+ "\n2.No");
	String testE = key.next();
	key.nextLine();
	if (testE.equalsIgnoreCase("1")|testE.equalsIgnoreCase("yes")) {
	  while(true) {
		System.out.println("Which method would you like to test?"
			+ "\n1. insertAtIndex()"
			+ "\n2. deleteFromIndex()");
		int num = key.nextInt();
		key.nextLine();
		switch(num) {
		case 1: 
		  System.out.print("Enter a index to insert a Sony Phone (0 <= index <= "+ list1.getSize() + "): " );
		  list1.insertAtIndex(list1.getHead().getCellPhone().clone(), key.nextInt());
		  break;
		case 2: 
		  System.out.print("Enter a index to delete (0 <= index <= "+ (list1.getSize()-1) + "): " );
		  list1.deleteFromIndex(key.nextInt());
		  break;
		}
		key.nextLine();
		list1.showContents();
		System.out.println("Test again ('Y' or 'N')? ");
		if(key.next().equalsIgnoreCase("y")) {
		  key.nextLine();
		  continue;
		}
		key.nextLine();
		break;
	  }
	}
	
	System.out.println("Deleting the first element with deleteFromStart()");
	stall();
	CellNode temp = list1.deleteFromStart();
	list1.showContents();
	
	System.out.println("Reinserting the deleted element with addToStart()");
	stall();
	list1.addToStart(temp.getCellPhone());
	temp = null;
	list1.showContents();
	
	while(true) {
	  System.out.print("Enter a serial Number to search for : ");
	  if(list1.find(key.nextLong()) == null) {
		System.out.println("Serial number not found, took " + list1.getIteration() + " iterations.");
	  }
	  else {System.out.println("Serial number found after " + list1.getIteration() + " iterations.");}
	  key.nextLine();
	  System.out.println();
	  System.out.println("Search for another serial?"
		  + "\n1.Yes"
		  + "\n2.No");
	  String choice = key.next();
	  key.nextLine();
	  if(choice.equalsIgnoreCase("1")|choice.equalsIgnoreCase("yes")) 
		continue;
	  break;
	}
	
	System.out.println();
	System.out.println("Creating a new phone with these parameters: 1234567,\"IPHONY\",199.9927,2023");
	
	CellPhone new1 = new CellPhone(1234567,"IPHONY",199.9927,2023);
	
	System.out.println("Adding this phone to list1, second index.");
	stall();
	list1.insertAtIndex(new1, 1);
	list1.showContents();
	
	System.out.println("\nDeleting it and inserting it at the end of the list:");
	stall();
	list1.insertAtIndex(list1.deleteFromIndex(1).getCellPhone(), list1.getSize());
	list1.showContents();
	
	System.out.println("\nDeleting it and inserting it at the head of the list with the insertAtIndex() method:");
	stall();
	list1.insertAtIndex(list1.deleteFromIndex(list1.getSize()-1).getCellPhone(),0);
	list1.showContents();
	
	System.out.println("Creating an empty list and calling deleteFromStart(), deleteFromIndex(0), and replaceAtIndex(1)");
	stall();
	CellList list2 = new CellList();
	list2.showContents();
	list2.deleteFromStart();
	list2.deleteFromIndex(0);
	list2.replaceAtIndex(new1, 1);
	
	System.out.println("\nAdding our 'IPHONY' to the empty list2:");
	stall();
	list2.insertAtIndex(new1, 0);
	list2.showContents();
	
	System.out.println("Manually cloning it this CellPhone and adding it to the list2 with insertAtIndex().");
	System.out.println("CellPhone: " + new1);
	stall();
	CellPhone new1Clone = new1.cloneManual();
	System.out.print("Enter index to insert clone (0 <= index <= " + list2.getSize() + "): ");
	list2.insertAtIndex(new1Clone, key.nextInt());
	key.nextLine();
	System.out.println();
	list2.showContents();
	
	System.out.println("Auto-cloning it this CellPhone and adding it to the end list2.");
	System.out.println("This method will automatically generate a unique serial number.");
	stall();
	list2.insertAtIndex(list2.getHead().getCellPhone().clone(), list2.getSize());
	list2.showContents();
	
	System.out.println("Now we will make a clone of list1, list3 using the the auto-cloning method");
	stall();
	CellList list3 = new CellList(list1);
	list3.showContents();
	
	System.out.println("Are these two lists equal (Same CellPhones in same order)?");
	stall();
	System.out.println(list1.equals(list3));
	
	System.out.println();
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	System.out.println("\t\t\t\tTESTS COMPLETE!");
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
  }
  
  /**
   * Displays to console files available for input.
   * @param directory
   */
  public static void displayAvailableFiles(File directory) {
	String[] availableFiles = directory.list((optionalInnerDirectory, file) -> {
	  return file.toLowerCase().endsWith("txt");
	});
	System.out.println("Available Source File(s):");
	for (String fileName: availableFiles) {System.out.println(fileName);}
	System.out.println();
  }
  
  /**
   * Links source file(s) to Scanner.
   */
  public static void linkSourceFiles() {
	fr = new Scanner[sourceFiles.length];
	int i = 0;
	try {
	  for (; i < fr.length; i++) {
		fr[i] = new Scanner(sourceFiles[i]);
	  }
	}catch (FileNotFoundException e) {
	  System.out.println("Error: File " +sourceFiles[i].getName() + " not found."
	  	+ "\nEnsure file present in: " +inputDirectory.getAbsolutePath() 
	  	+ "Program closing..");
	  closeStreamsAndExit();
	}
  }
  
  /**
   * Closes all streamsand exits program in case of I/O error.
   */
  public static void closeStreamsAndExit() {
	for(Scanner scanner: fr) {scanner.close();}
	  key.close();
	  System.exit(0);
  }

  /**
   * Stalls the console for user input.
   */
  public static void stall() {
	System.out.println("Press ENTER to continue..");
	key.nextLine();
  }

//  /**
//   * Exception tester for main method.
//   * @param list
//   */
//  public static void runException(CellList list) {
//	System.out.println("");
//  }
}

