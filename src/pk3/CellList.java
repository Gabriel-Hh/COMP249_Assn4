package pk3;

import java.util.NoSuchElementException;
import java.util.Scanner;

import pk2.CellPhone;

//-----------------------------------------------------
//Assignment 4
//Question: B
//Written by: Gabriel Horth, 40186942
//-----------------------------------------------------


/**
 * CellList is a custom Linked-List class for CellPhone. Contains basis method to insert, delete, and search CellNodes. CellNodes are defined in inner class.
 * @author Gabriel Horth
 * @verion 1.1
 * @see pk2.CellPhone
 */
public class CellList {
  
  private CellNode head;
  private int size;
  private int iteration = 0; //Used as 'static' variable for CellNode iterative methods. Compared to size to signal error.
  private int listIndex = 0; //Used as 'static' variable for CellNode index methods. Compared to 'index' parameter and size
  //private String listToString; //Used and returned by iterPrint() in CellNode to minimize recursive String creation.
  
/////////////////////// CLASS CELLNODE //////////////////////////////
  /**
   * Inner Class CellNode contains data and pointer to next CellNode.
   * Mostly private methods to manipulate and navigate through a CellList.
   * @author Gabriel Horth
   * @version 1.1
   *
   */
  public class CellNode{
	
	private CellPhone cellPhone;
	private CellNode pointer;
	
	/**
	 * Default Constructor for CellNode.
	 */
	
private CellNode() {
	  cellPhone = null;
	  pointer = null;
	}
	
	/**
	 * Para-constructor for CellNode.
	 */
	private CellNode(CellPhone cellPhone, CellNode pointer) {
	  this.cellPhone = cellPhone;
	  this.pointer = pointer;
	}
	
	/**
	   * Copy Constructor for CellNode.
	   */
	private CellNode(CellNode toCopy) {
	  cellPhone = toCopy.cellPhone.clone();
	  if(toCopy.pointer == null) {
		pointer = null;
		return;
	  }
	  pointer = toCopy.pointer.clone();
	}
	
	/**
	 * CellNode clone() method.
	 */
	protected CellNode clone() {
	  return new CellNode(this);
	}
	
	//TODO
	//!!WARNING: returns reference to a mutable Object.
	// Must be keep public to use clone() in driver.
	// To secure could create a public method that calls this method and clone together
	// and make this method private instead.
	/**Getter for a CellNode's CellPhone.
	 * @return the cellPhone
	 */
	public CellPhone getCellPhone() {
	  return cellPhone;
	}

	/**Setter for a CellNode's CellPhone,
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(CellPhone cellPhone) {
	  this.cellPhone = cellPhone;
	}


	//TODO
	//!!WARNING: returns reference to a mutable Object.
	//However, this method is private and causes no security leak at the driver level.
	/**Getter for a CellNode Pointer to next Node.
	 * @return CellNode pointer.
	 */
	public CellNode getPointer() {
	  return pointer;
	}

	/**Setter for a CellNode's pointer to next CellNode.
	 * @param pointer the pointer to set
	 */
	public void setPointer(CellNode pointer) {
	  this.pointer = pointer;
	}
	
	//TODO
	//!!WARNING: returns reference to mutable object.
	//However, this method is private and causes no security leak at the driver level.
	/**
	 * Iterates accross CellList to desired index.
	 * @param index
	 * @return CellNode at that index.
	 */
	private CellNode moveTo(int index) {
	  CellNode curr = this;
	  while (curr != null) {
		if(index == listIndex) 
		  return curr;
		curr = curr.pointer;
		listIndex++;
	  }
	  return null;
	}
	
	//TODO
	//!!WARNING: returns reference to mutable object.
	//However, this method is private and causes no security leak at the driver level.
	/**
	 * Searches for a CellNode by serial number.
	 * @param serialNumber
	 * @return CellNode if found, null otherwise.
	 */
	private CellNode search(long serialNumber) {
	  CellNode curr = this;
	  while(curr != null && iteration <= size) {
		iteration++; 
		if (curr.cellPhone.getSerialNumber() == serialNumber) return curr;
		 curr = curr.pointer;
	  	}
	 return null; 
	}
	  
	/**
	 * Internal print method, called by CellList's toString() method.
	 * 
	 */
	private String iterPrint() {
	  iteration = 0;
	  String listToString = "";
	  CellNode pos = this;
	  while(pos != null){
		if(getIteration() > size) 
		  return "Error: List contains a loop, toString() failure.";
		if(iteration % 3 == 0) 
		  listToString += "\n";
		listToString += pos.cellPhone.toString() + " ---> ";
		pos = pos.pointer;
		iteration++;
	  }
	  listToString += "X";
	  return listToString;
	}
	
 	
 	/**
 	 * Starting at each head, iterates through links comparing CellPhone equality.
 	 * @param otherHead CellList to compare to.
 	 * @return true if each CellNode contains equal CellPhones.
 	 */
 	private boolean iterEquals(CellNode otherHead) {
 	  CellNode first = head;
	  CellNode other = otherHead;
	  while(first != null) {
		if(!(first.cellPhone.equals(other.cellPhone)))
		  return false;
		first = first.pointer;
		other = other.pointer;
	  }
 	  return true;
 	}
 	
  }///////////////////// END CLASS CELLNODE //////////////////////////////
  
  /**
   * Default Constructor for CellList.
   */
  public CellList() {
	head = null;
	size = 0;
  }
  
  /**
   * Copy Constructor for CellList, returns deep copy with unique serial numbers.
   * @param toCopy
   */
  public CellList(CellList toCopy) {
	head = toCopy.head.clone();
	size = toCopy.size;
  }
  
  /**
   * Adds a CellNode to the start of CellList.
   * @param cellPhone
   */
  public void addToStart(CellPhone cellPhone) {
	if(head == null | size == 0) {
	  head = new CellNode(cellPhone,null);
	  size++;
	}
	else {
	  head = new CellNode(cellPhone,head);
	  size++;
	}
  }
  
  /**
   * Inserts a CellNode a speficed index.
   * @param cellPhone
   * @param index
   */
  public void insertAtIndex(CellPhone cellPhone, int index) {
	try {
	  if(index < 0 | index > (size)) throw new NoSuchElementException();
	}
	catch (NoSuchElementException e) {System.out.println("Error: NoSuchElementException \nProgram closing..");System.exit(1);}
	listIndex = 0;
	if(index == 0) {
	  addToStart(cellPhone);
	  return;
	}
	if(index == getSize()) {
	  head.moveTo(index-2).pointer.setPointer(new CellNode(cellPhone,null));
	  size++;
	  return;
	}
	CellNode before = head.moveTo(index-1);
	CellNode current = before.getPointer();
	CellNode insert = new CellNode(cellPhone,current);
	before.setPointer(insert);
	size++;
  }
 
  //TODO
  //!!WARNING: returns reference to a mutable Object.
  //To migitate must simply change return type to void.
  //However this returned CellNode is useful to move CellNode from one list to another, 
  //without changing CellPhone's serial number.
  /**
   * Deletes CellNode from specified index.
   * @param index
   * @return deleted CellNode
   */
  public CellNode deleteFromIndex(int index) {
	if(head == null)
	  return null;
	try {
	  if(index < 0 | index > (size-1)) throw new NoSuchElementException();
	}
	catch (NoSuchElementException e) {System.out.println("Error: NoSuchElementException \nProgram closing..");System.exit(1);}
	if(index == 0 ) {
	  CellNode toDelete = head;
	  head = head.pointer;
	  size--;
	  return toDelete;
	}
	listIndex = 0;
	CellNode before = head.moveTo(index-1);
	CellNode toDelete = before.getPointer();
	CellNode after = toDelete.getPointer();
	before.setPointer(after);
	size--;
	return toDelete;
  }
  
  //TODO
  //!!WARNING: returns reference to a mutable Object.
  //To migitate must simply change return type to void.
  //However this returned CellNode is useful to move CellNode from one list to another, 
  //without changing CellPhone's serial number.
  /**
   * Deletes CellNode form start of CellList.
   * @return deleted CellNode.
   */
  public CellNode deleteFromStart() {
	
	CellNode toDelete = head;
	if(head != null) {
	  head = head.getPointer();
	  size--;
	}
	return toDelete;
  }
  
  /**
   * Swaps a CellNode with other Node at specified index.
   * @param cellPhone of new CellNode.
   * @param index
   */
  public void replaceAtIndex(CellPhone cellPhone, int index) {
	if (head == null)
	  return;
	if(index < 0 | index >= size)
	  return;
	listIndex = 0;
	CellNode toReplace = head.moveTo(index);
	toReplace.setCellPhone(cellPhone);
  }
  
  //
  //TODO
  //!!WARNING: returns reference to a mutable Object.
  //To migitate must simply change return type to int, returning it's index.
  //However, this return is useful if you want to move or delete CellNode you searched for.
   /**
    * 
    * @param serialNumber
    * @return
    */
  public CellNode find(long serialNumber) {
    iteration = 0;
    if(head == null) 
      return null;
	return head.search(serialNumber);
  }
  
  /**
   * Verifies if CellList contains a serial number.
   * @param serialNumber
   * @return true if serial found.
   */
  public boolean contains(long serialNumber) {
	iteration = 0;
	if (!(head == null)) {
	  if (head.search(serialNumber) != null) {return true;}
	}
	return false;
  }
  
  /**
   * Method to create CellList from text file.
   * @param fr FileReader connected to input file.
   */
  public void fileToCellList(Scanner fr) {
	while(fr.hasNextLine()) {
	  long serial = fr.nextLong();
	  String brand = fr.next();
	  double price = fr.nextDouble();
	  int year = fr.nextInt();
	  if (!contains(serial)) {
		CellPhone newPhone = new CellPhone(serial,brand,price,year);
		addToStart(newPhone);
	  }
	}
	fr.close();
  }
  
  /**
   * ToString method with header and size of CellList.
   */
  public void showContents() {
	System.out.println("The current size of the list is " + size + ". Here are the contents of the list:"
		   + "\n=========================================================================\n"
		   + toString() +"\n");
  }
  
  /**
   * Determines if two CellLists are equal.
   * Checks Type, null reference, and size then the calls CellNode iterEquals() method.
   * @return true if CellLists are equal in content and order, ignores serialNumber.
   */
  @Override
  public boolean equals(Object obj) {
	if(obj == null)
	  return false;
	if(obj.getClass() != getClass()) //???? Does this getClass() comparison take care of the null-check implicitly for an instance method?
	  return false;
	CellList other = (CellList)obj;
	if(this.size != other.size)
	  return false;
	return (this.head).iterEquals(other.head);
  }
  
  
  @Override
  public String toString() {
	if (head == null)
	  return "[X] ---> X";
	return head.iterPrint();
  }

  public int getIteration() {
	return iteration;
  }
  public int getSize() {
	return size;
  }

 
  //TODO
  //!!WARNING: returns reference to a mutable Object.
  //Only used in tandem with the clone method. 
  //To mitigate must make method private and have clone() call it instead.
  /**
   * Getter for a CellList's head node.
   * @return first CellNode
   */
  public CellNode getHead() {
	return head;
  }
}
