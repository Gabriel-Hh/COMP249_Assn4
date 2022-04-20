package pk2;

import java.util.ArrayList;
import java.util.Scanner;

//-----------------------------------------------------
//Assignment 4
//Question: B
//Written by: Gabriel Horth, 40186942
//-----------------------------------------------------


/**
 * CellPhone object, holds details for a serial unique cellphone.
 * @author Gabriel Horth
 * @version 1.0
 * @see pk3.CellList
 *
 */
public class CellPhone {
  
  protected long serialNumber;
  protected String brand;
  protected double price;
  protected int year;
  public static ArrayList<Long> serials= new ArrayList<>();
  
  /**
   * Auto generates a new serial number.
   * @return Unique 7-digit serial number.
   */
  private Long generateSerial() {
	Long unique = (long) (Math.random()*10000000);
	if(serials.contains(unique)) 
	  unique = generateSerial();
	return unique;
  }
  
  /**
   * Full Parametrized constructor.
   * @param serialNumber
   * @param brand
   * @param year
   * @param price
   */
  public CellPhone(long serialNumber, String brand, double price, int year) {
	if(serials.contains(serialNumber)) {
	  this.serialNumber = generateSerial();
	  System.out.println("Warning: Serial number collision: "+ serialNumber +" replaced by: " + this.serialNumber + ".");
	}
	else { this.serialNumber = serialNumber;}
	this.brand = brand;
	this.year = year;
	this.price = price;
	serials.add(this.serialNumber);
  }
  
  /**
   * CellPhone Copy Constructor.
   * @param toCopy
   * @param newSerial
   */
  public CellPhone(CellPhone toCopy, long serialNumber) {
	if(serials.contains(serialNumber)) {
	  this.serialNumber = generateSerial();
	  System.out.println("Warning: Serial number collision: "+ serialNumber +" replaced by: " + this.serialNumber + ".");
	}
	else {this.serialNumber = serialNumber;}
	brand = toCopy.brand;
	year = toCopy.year;
	price = toCopy.price;
  }
  
  /**
   * Manual cloning method, requires user to enter new serial number.
   */
  public CellPhone cloneManual() {
	Scanner key = new Scanner(System.in);
	System.out.print("Enter unique Serial Number (7-digits): ");
	long newSerial = key.nextLong();
	key.nextLine();
	return new CellPhone(this, newSerial);
  }

  /**
   * Auto-cloning method, generate serial automatically.
   */
  public CellPhone clone() {
	return new CellPhone(this,generateSerial());
  }
  /**Getter: serial number.
   * @return the serialNumber
   */
  public long getSerialNumber() {
    return serialNumber;
  }

  /**Setter: serial number.
   * @param serialNumber the serialNumber to set
   */
  public void setSerialNumber(long serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**Getter: brand.
   * @return the brand
   */
  public String getBrand() {
    return brand;
  }

  /**Setter: brand.
   * @param brand the brand to set
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**Getter: year.
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**Setter: year
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**Getter: price.
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**Setter: price.
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Equals method for CellPhone.
   * @return true iff all attributes are equal except the serial number.
   */
  @Override
  public boolean equals(Object obj) {
	if (obj == null)
	  return false;
	if (getClass() != obj.getClass())
	  return false;
	CellPhone other = (CellPhone) obj;
	return brand == other.brand && Double.compare(price,other.price) == 0
		&& year == other.year;
  }
  
  /**
   * ToString, returns formatted attributes.
   */
  @Override
  public String toString() {
	return String.format("[%-8d: %-12s %#.2f$ %d]",serialNumber,brand,price,year);//#-flag forces printing 'float.00' to specified precision
  }
  //////////////////// test ////////////////////
//  public static void main(String[] args) {
//	testToString();
//  }
//  
//  private static void testToString() {
//	CellPhone test1 = new CellPhone(1234567L,"Nokia",99.,2001);
//	System.out.println(test1);
//	CellPhone test2 = new CellPhone(1234567L,"Nokia",99.99,2001);
//	System.out.println(test2);
//	CellPhone test3 = new CellPhone(1234567L,"Nokia",99.9911,2001);
//	System.out.println(test3);
//	CellPhone test4 = null;
//	System.out.println(test4);
//  }
}
