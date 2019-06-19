import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.util.*;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.Window;
import java.awt.Button;
import javax.swing.JScrollPane;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import com.opencsv.*;

public class Multi_Panel_Screen extends JTabbedPane {
	// protected access modifier used so that the frame method can be accessed
	// in Login_Screen class
	protected JFrame frame;
	private JTextField IDtext;
	private JTextField NameText;
	private JTextField DescriptionText;
	private JTextField QuantityText;
	private JTextField CostText;
	private JTextField UnitsText;
	private JTextField SellingText;
	private JTextField CategoryText;
	protected JTable InventoryTable;
	private JTextField POSQuantityTxt;
	private JTable POStable;
	private JTextField TotalText;
	private JTextField ItemsText;
	private JTextField AmountText;
	private JTextField ChangeText;
	private JButton RecieptButton;
	private JButton btnAdd;
	private JButton ChartBtn;
	private JButton ExportBtn;
	/**
	 * Launch the application.
	 */
	// Global Variables Created in order to access values from and pass values
	// to different methods
	String value;
	String value2;
	boolean check;
	String item;
	String Catselect;
	String NameSelect;
	String Catselect1;
	String NameSelect1;
	String Itemsum;
	String ItemCount;
	double change;
	String bill;
	String update;
	String Date;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creating a new object of the class in order to access and call methods from the class 
					Multi_Panel_Screen window = new Multi_Panel_Screen();
					window.frame.setVisible(true);
					window.frame.setTitle("Main Screen");
					window.fetchTable();
					window.fillTable2();
					window.fillTable3();
					window.getNumOfItems();
					window.getSum();
					window.salesReportTotal();
					window.salesReportCheck();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Method which is used to fill the inventory table
	public void fetchTable() {
		try {
			// Everything is selected from the StockTable in order to be shown
			// in table
			String query = "Select * from StockTable";
			// The query is set into a prepared Statement
			PreparedStatement prepstate = connect.prepareStatement(query);
			// The query is run and stored in a result set
			ResultSet rs = prepstate.executeQuery();
			// Through the use of a external library called rs2xml the result
			// set of the query was used to set the model of the table and fill
			// the table
			InventoryTable.setModel(DbUtils.resultSetToTableModel(rs));
			// PreparedStatement and ResultSet objects are set to close
			prepstate.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method which is used to get the selected item's coordinates from the
	// Inventory table
	public void getRow() {
		// A mouse listener is used in order to get the cell which is selected
		// using the mouse
		InventoryTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				try {
					// When the cell is selected the corresponding row and
					// column values are stored in the i and j respectively
					int i = InventoryTable.getSelectedRow();
					int j = InventoryTable.getSelectedColumn();
					// The coordinates from i and j are then taken and inserted
					// into the getValueAt function in order to get the value
					// within that cell
					// The value in that cell is then converted to a string
					// value since it will be used in a query further on in the
					// program
					value = (String) InventoryTable.getValueAt(i, j);

				} catch (Exception e1) {

					e1.printStackTrace();
				}

			}

		});

	}

	// Method which is used to get the select item's coordinates from the Point
	// of Sale table
	public void getRow2() {

		POStable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e1) {
				try {
					// When the cell is selected the corresponding row and
					// column values are stored in the i and j respectively
					int i = POStable.getSelectedRow();
					int j = POStable.getSelectedColumn();
					// The coordinates from i and j are then taken and inserted
					// into the getValueAt function in order to get the value
					// within that cell
					// The value in that cell is then converted to a string
					// value since it will be used in a query further on in the
					// program
					value2 = String.valueOf(POStable.getValueAt(i, j));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Method used to fill the category combo box
	public void fillComboCat() {
		// Default Combo Box model created in order to store custom values from
		// the SQLite database table
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		try {
			// Query run to retrieve only distinct category values from StockTable
			String CatQuery = "Select Distinct Category From StockTable";
			// Query set to a prepared statement and executed using a result set
			PreparedStatement prepstate = connect.prepareStatement(CatQuery);
			// Result set is used in order to store each individual category
			// value
			ResultSet rs = prepstate.executeQuery();
			// None element added to the Combo Box as default element
			model.addElement("None");
			// Looping through the result set
			while (rs.next()) {
				// While the result set is being looped through, the values of
				// category are being added to the ComboBox Model object
				String cat = rs.getString("Category");
				model.addElement(cat);

			}
			// The model is then set to the Category Combo Box
			CategoryCB.setModel(model);
			prepstate.close();
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// Method used in order to fill the name combo box
	public void fillComboN() {
		// Default Combo Box model created in order to store custom values from
		// the SQLite database table
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		try {
			// Query run to retrieve name value from StockTable
			String NQuery = "Select Name From StockTable";
			// Query set to a prepared statement and executed using a result set
			PreparedStatement prepstate = connect.prepareStatement(NQuery);
			// Result set is used in order to store each individual name value
			ResultSet rs = prepstate.executeQuery();
			// None element added to the Combo Box as default element
			model.addElement("None");
			while (rs.next()) {
				// While the result set is being looped through, the name values
				// are being added to the ComboBox Model object
				String name = rs.getString("Name");
				model.addElement(name);

			}
			// The model is then set to the Name Combo Box
			NameCB.setModel(model);
			prepstate.close();
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// Method used to fill the Name Combo box in the point of sale screen
	public void fillComboN1() {
		// Default Combo Box model created in order to store custom values from
		// the SQLite database table
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		try {
			// Query run to retrieve name value from StockTable
			String NQuery = "Select Name From StockTable";
			// Query set to a prepared statement and executed using a result set
			PreparedStatement prepstate = connect.prepareStatement(NQuery);
			// Result set is used in order to store each individual name value
			ResultSet rs = prepstate.executeQuery();
			// None element added to the Combo Box as default element
			model.addElement("None");
			while (rs.next()) {
				// While the result set is being looped through, the name values
				// are being added to the ComboBox Model object
				String name = rs.getString("Name");
				model.addElement(name);

			}
			// The model is then set to the Name Combo Box
			NameCombo1.setModel(model);
			prepstate.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method used to fill the Category Combo box in the point of sale screen
	public void fillComboCat1() {
		// Default Combo Box model created in order to store custom values from
		// the SQLite database table
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		try {
			// Query run to retrieve only distinct Category values from StockTable
			String NQuery = "Select Distinct Category From StockTable";
			// Query set to a prepared statement and executed using a result set
			PreparedStatement prepstate = connect.prepareStatement(NQuery);
			// Result set is used in order to store each individual name value
			ResultSet rs = prepstate.executeQuery();
			// None element added to the Combo Box as default element
			model.addElement("None");
			while (rs.next()) {
				// While the result set is being looped through, the category values
				// are being added to the ComboBox Model object
				String cat = rs.getString("Category");
				model.addElement(cat);

			}
			// The model is then set to the Category Combo Box
			CatCombo1.setModel(model);
			prepstate.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void fillDateCombo(){
		// Default Combo Box model created in order to store custom values from
				// the SQLite database table
				DefaultComboBoxModel model = new DefaultComboBoxModel();
				try {
					// Query run to retrieve only distinct Category values from StockTable
					String Query = "Select Distinct [Sales Date] From ChartInfo";
					// Query set to a prepared statement and executed using a result set
					PreparedStatement prepstate = connect.prepareStatement(Query);
					// Result set is used in order to store each individual name value
					ResultSet rs = prepstate.executeQuery();
					// None element added to the Combo Box as default element
					model.addElement("None");
					while (rs.next()) {
						// While the result set is being looped through, the category values
						// are being added to the ComboBox Model object
						String date = rs.getString("Sales Date");
						model.addElement(date);

					}
					// The model is then set to the Category Combo Box
					DateCombo.setModel(model);
					prepstate.close();
					rs.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

	}
	
// Method used to retrieve values from the stock table and insert them into the POStable
	public void getValues() {

		try {
			//Query to insert values into the point of sale table by retrieving values from the Stock Table where the condition is based on the name or category chosen 
			String Q1 = "Insert Into POStable(ID, Name, SellingPrice, Category) Select ItemID, Name, [Selling Price], Category From StockTable Where Name=?";
			PreparedStatement prepstate = connect.prepareStatement(Q1);
			prepstate.setString(1, NameSelect);
			prepstate.executeUpdate();
			prepstate.close();
			//Query to update the existing values of the point of sale table with the quantity value where the condition is based on the name or category chosen
			String Q2 = "Update POStable set Quantity =? where Name =?";
			PreparedStatement prepstate2 = connect.prepareStatement(Q2);
			prepstate2.setString(1, POSQuantityTxt.getText());
			prepstate2.setString(2, NameSelect);
			prepstate2.executeUpdate();
			prepstate2.close();
			// Query to update the selling price column in the POStable, once again the condition is based on the name or category which is chosen
			String Q3 = "Update POStable set SellingPrice = Quantity * SellingPrice where [Name] =?";
			PreparedStatement prepstate3 = connect.prepareStatement(Q3);
			prepstate3.setString(1, NameSelect);
			prepstate3.executeUpdate();
			prepstate3.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method which is used to fill the Point of sale table
	public void fillTable2() {
		try {
			// Query used to pull all values from the point of sale table
			String tableQ = "Select * from POStable";
			PreparedStatement prepstate = connect.prepareStatement(tableQ);
			//Query Stored in a result set
			ResultSet rs = prepstate.executeQuery();
			//Result set used to set the table model, using a external library called rs2xml
			POStable.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			prepstate.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method used to fill the sales report table 
	public void fillTable3(){
		
		// Query used to pull all values from the sales report table
		String tableQ = "Select * from ChartInfo";
		try {
			PreparedStatement prepstate = connect.prepareStatement(tableQ);
			ResultSet rs = prepstate.executeQuery();
			//Using result set to set the values to the table using an external library called rs2xml
			SalesReportTable.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			prepstate.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	//Method used to get the total amount purchased from values in the sales report table
	public void salesReportTotal(){
		//Getting the number of rows currently in the table
		int rowCount = SalesReportTable.getRowCount();
		//Creating a variable to store the total
		double total = 0;
		//Looping through the table to get the total 
		for(int i = 0; i < rowCount; i++){
			//Summing each value together 
			total = total + Double.parseDouble(SalesReportTable.getValueAt(i, 1).toString());
			String sum = String.valueOf(total);
			//Setting the value to the text field
			TotalTxtField.setText(sum);
			
		}
		
	}
	
	// Method used to get the sum of the sales price values from the point of sale table 
	public void getSum() {
		// variable row count used to store the amount of rows in POStable
		int rowCount = POStable.getRowCount();
		//creating a variable to store the value of sum
		double sum = 0;
		//for loop to loop through the table
		for (int i = 0; i < rowCount; i++) {
			// getting the sales price value from the POStable and adding it to itself until the loop ends to get the total
			sum = sum + Double.parseDouble(POStable.getValueAt(i, 4).toString());
			// Converting the value to a string so that it can be set to the text field
			Itemsum = String.valueOf(sum);
			// Setting the sum value to the text field
			TotalText.setText(Itemsum);
		}

	}
	
	// Method used to get the total number of items bought by the customer
	public void getNumOfItems() {
		// variable row count used to store the amount of rows in POStable 
		int rowCount = POStable.getRowCount();
		// creating a variable to store the value of sum
		int sum = 0;
		// looping through the table
		for (int i = 0; i < rowCount; i++) {
			 // getting the quantity value from the table and adding all the values from each row
			sum = sum + (int) (POStable.getValueAt(i, 3));
			// converting the value to a string so that it can be set to the text field
			ItemCount = String.valueOf(sum);
			// Setting the value to the text field
			ItemsText.setText(ItemCount);
		}
		
		

	}
	
	
// Method used to create the bill of sale that will be printed after a sale is made
	public void Bill() {
		// the creation of date and time objects in order to set the data and time formats 
		DateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd");
		DateFormat timeformat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		Date time = new Date();
		// Date and time values converted to string values in order to be used in the Bill String
		Date = String.valueOf(dateformat.format(date));
		String Time = timeformat.format(time);
		// Creating the header of the bill of sale with the date, time and corresponding table headers
		String Header = 	"===============================================\n" 
							+ "Date: " + Date + "                 Time "+ Time + "\n" 
							+ "===============================================\n"
							+ "Name          Quantity          Price($)               \n";
		// Setting the header to the bill String
		bill = Header;
		// Creating the body of the bill of sale
		// Looping through the point of sale table and retrieving the values for name, Quantity, and sales price
		int count = POStable.getRowCount();
		for (int i = 0; i < count; i++) {

			String name = String.valueOf(POStable.getValueAt(i, 1));
			String Quantity = String.valueOf(POStable.getValueAt(i, 3));
			String sp = String.valueOf(POStable.getValueAt(i, 4));
			// The values which were retrieved are now added to a string called items
			String items = name + "\t" + Quantity + "\t" + sp + "\n";
			// The bill is then updated with the item string
			bill = bill + items;
		}
		// Getting the amount the customer paid
		String paid = AmountText.getText();
		// Creating the footer of the bill
		String footer = "===============================================\n" 
						+ "\nItems Purchased = " + ItemCount + " \n"
						+ "Total Amount = " + Itemsum + " \n" + "Amount Paid = " + paid + "\n" 
						+ "Change = " + change + "\n"
						+ "===============================================";

		//Updating the bill string with footer value
		bill = bill + footer;
		}
	
	// Method used to set the bill string to a separate text area and print the text area
	public void printBill() {
		//creating a object of the 
		BillScreen bs = new BillScreen();
		//setting the text of the text area with the bill string
		bs.RecieptTextArea.setText(bill);
		//Setting the bill screen frame to be visible
		bs.decorate();
		try {
			//Printing the textarea using the inbuilt print function
			bs.RecieptTextArea.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}
	
	//Method used to update the quantity of an item after a purchase is made
	public void updateQuantity() {
		// Creating 2 queries
		// The first query will be used to retrieve the entire POStable
		// The Second query will be used to update the quantity value by check the current ID value 
		String Q1 = "Select * from POStable";
		String Q2 = "Update StockTable set Quantity = (Select Quantity from StockTable where ItemID =? ) - (Select Quantity from POStable where ID =?) where ItemID =?";
		
		try {
			//Executing the first query
			PreparedStatement prepstate = connect.prepareStatement(Q1);
			ResultSet rs = prepstate.executeQuery();
			//looping through the result set of the first query
			while(rs.next()) {
				// getting the current ID value from the first query
				String ID = rs.getString(1);
				// Executing the second query 
				PreparedStatement prepstate1 = connect.prepareStatement(Q2);
				//Setting the ID value to be the current ID value at which the result set pointer is currently on
				prepstate1.setString(1, ID);
				prepstate1.setString(2, ID);
				prepstate1.setString(3, ID);
				// Executing the query
				prepstate1.executeUpdate();
				prepstate1.close();
			}
			
			prepstate.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//Method used to insert information into the ChartInfo table
	public void chartinfo() {

		try {
			//Query that inserts the Amount Purchased by customer and the date at which the items were purchased 
			String chartQ = "Insert into ChartInfo ('Amount Purchased', 'Sales Date') values (?,?)";
			PreparedStatement prepstate = connect.prepareStatement(chartQ);
			//Setting the values of the query
			prepstate.setString(1, Itemsum);
			prepstate.setString(2, Date);
			//Executing the query
			prepstate.execute();
			prepstate.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//Method used to convert the result set of the chartInfo table into a CSV file 
	public void chartToCsv() {

		try {
			//Using an external library called Opencsv in order to write the data from the result set of a query to a CSV file
			// Creating a new CSVWriter object in order to write to the file called SalesInfo
			CSVWriter writer = new CSVWriter(new FileWriter("SalesInfo.csv"));
			// String[] header = "Amount Purchased, Sales Date".split(";");
			// writer.writeNext(header);
			//Setting the headers value to true so that headers are available in the csv file
			boolean headers = true;
			//Writing the query in order to retrieve all the data from ChartInfo table
			String SalesQ = "Select [Sales Date], Sum([Amount Purchased]) from ChartInfo group by [Sales Date] ";
			PreparedStatement prepstate = connect.prepareStatement(SalesQ);
			//Executing the query
			ResultSet rs = prepstate.executeQuery();
			//Using the write all function to write all the values of the result set to the csv file
			// Where the headers value is true, the trim value is false and the applyQuotes value is true
			writer.writeAll(rs, headers, false, true);
			writer.close();
			prepstate.close();
			rs.close();

		} catch (IOException e) {

			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	
	
	
	// Method used to clear the point of sale table after a transaction occurs
	public void clearTable(){
		try{
		// Query that deletes everything from the point of sale table
		String deleteQ = "Delete from POStable";
		PreparedStatement prepstate = connect.prepareStatement(deleteQ);
		// Executing the query
		prepstate.executeUpdate();
		prepstate.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//Method used to fill the text fields of the Edit item screen with the values corresponding to the item ID that was selected in the table using the mouse clicked event
	public void setEditValues(){
		//Method to get the value of the selected item in the table
		getRow();
		//Creating an instance of the Edit Item Screen class in order to manipulate the values within it
		Edit_Item_Screen EI = new Edit_Item_Screen();
		//Creating the query in order to filter the values from the database where the Item ID equals the one which is supposed to be selected from the table
		String Query = "Select * from StockTable where ItemID =?";
		
		try{
			//Creating a prepared statement in order to prepare and execute the query
			PreparedStatement prepstate = connect.prepareStatement(Query);
			//Setting the selected value from the table to the query
			prepstate.setString(1,value);
			//Storing the query into a result set
			ResultSet rs = prepstate.executeQuery();
			//Looping through the values of the result set
			while(rs.next()){
				//Altering the variables from the Edit item screen class
				//Storing the values obtained from the result set to the variables
				EI.itemid = rs.getString("ItemID");
				EI.name = rs.getString("Name");
				EI.Description = rs.getString("Description");
				EI.Quantity = rs.getString("Quantity");
				EI.Category = rs.getString("Category");
				EI.Costprice = rs.getString("Cost Price");
				EI.units = rs.getString("Units");
				EI.SellingPrice = rs.getString("Selling Price");	
			}
			
			// Setting the variables to the text boxes in edit item screen
			EI.IDtext.setText(EI.itemid);
			EI.Nametext.setText(EI.name);
			EI.DescriptionText.setText(EI.Description);
			EI.QuantityText.setText(EI.Quantity);
			EI.SellingText.setText(EI.SellingPrice);
			EI.UnitsText.setText(EI.units);
			EI.CostText.setText(EI.Costprice);
			EI.CategoryText.setText(EI.Category);
			prepstate.close();
			rs.close();
			// Setting the screen to the 
			EI.decorate();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	//Method used to check if the print reciept button should be enabled
	public void enable(){
		//if statement to check to see if the POStable has any values and if the amount text is pressed
		if(!(POStable.getRowCount() == 0)&&!(AmountText.getText().isEmpty())){
			RecieptButton.setEnabled(true);
			
		}
	}
	//Method used to check if the Create Chart button and the Export data button should be enabled
	public void salesReportCheck(){
		//If statement used to check to see if the Sales report table contains values 
		if(SalesReportTable.getRowCount() == 0){
			ChartBtn.setEnabled(false);
			ExportBtn.setEnabled(false);
		}else{
			ChartBtn.setEnabled(true);
			ExportBtn.setEnabled(true);
		}
		
	}
	//Method used to check the quantity level of the items within the Stock Table
	public void QuantityCheck(){
		try{
		//Query used pull quantity based on the name selected in the combo box
		String query = "Select Quantity from StockTable where Name =?";
		PreparedStatement prepstate = connect.prepareStatement(query);
		prepstate.setString(1, NameSelect);
		ResultSet rs = prepstate.executeQuery();
		//If the quantity value is less than 20 or 50 a message is displayed in order to notify the user 
		if(rs.getInt(1) < 20){
			JOptionPane.showMessageDialog(null, "Quantity for " + NameSelect + " is critically low");
		} else if(rs.getInt(1) < 50){
			JOptionPane.showMessageDialog(null, "Quantity for " + NameSelect + " is low");
		}
		prepstate.close();
		rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//Method used to check if there is already a value with the same ID, and if there is update it
	public void checkAdd(){ 
		
		try{
			//Query written to be used to select quantity based on a specific name
			String q2 = "Select Quantity from POStable where Name =? ";
			//Query written to be used to update the quantity of an item with a specific name
			String q3 = "Update POStable set Quantity = ? where Name = ?";
			PreparedStatement ps1 = connect.prepareStatement(q2);
			ps1.setString(1, NameSelect);
			ResultSet rs1 = ps1.executeQuery();
			//Getting the quantity value from the query and setting it to a variable
			int Qvalue = rs1.getInt(1);
			//Adding the quantity value from the query to the quantity value from the quantity text box
			int Quantity = Qvalue + Integer.parseInt(POSQuantityTxt.getText());
			ps1.close();
			rs1.close();
			PreparedStatement ps2 = connect.prepareStatement(q3);
			ps2.setString(1, String.valueOf(Quantity));
			ps2.setString(2, NameSelect);
			ps2.executeUpdate();
			ps2.close();
			//Updating the selling price value in order to match the updated quantity value
			//Query used to multiply the quantity value with the original selling price value based on name in order to update the selling price
			String Q3 = "Update POStable set 'SellingPrice' = ((Select Quantity from POStable where Name =?) * (Select [Selling Price] from StockTable where Name =?)) where Name =?";
			PreparedStatement prepstate3 = connect.prepareStatement(Q3);
			prepstate3.setString(1, NameSelect);
			prepstate3.setString(2, NameSelect);
			prepstate3.setString(3, NameSelect);
			prepstate3.executeUpdate();
			prepstate3.close();
		
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
	}
	
	
	//Creating the connection object
	Connection connect = null;

	/**
	 * Create the application.
	 */
	public Multi_Panel_Screen() {
		initialize();
		//Assigning the connection object to the connection class
		connect = sqlConnection.dbConnector();
		getRow();
		getRow2();
		fillComboCat();
		fillComboN1();
		fillComboN();
		fillComboCat1();
		fillDateCombo();
		enable();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	//Creating a tabbed pane object in order to provide tab functionality
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JComboBox CategoryCB;
	private JComboBox NameCB;
	private JComboBox CatCombo1;
	private JComboBox NameCombo1;
	private JComboBox DateCombo;
	private JTable SalesReportTable;
	private JTextField TotalTxtField;
	
	

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 989, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
		tabbedPane.setBackground(new Color(244, 164, 96));

		tabbedPane.setBounds(0, 0, 971, 478);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Add Item", null, panel, null);
		panel.setLayout(null);

		JLabel lblAddItem = new JLabel("Add Item");
		lblAddItem.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblAddItem.setBounds(365, 8, 236, 104);
		panel.add(lblAddItem);

		JLabel lblItemId = new JLabel("Item ID");
		lblItemId.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblItemId.setBounds(27, 96, 86, 37);
		panel.add(lblItemId);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(27, 193, 86, 37);
		panel.add(lblNewLabel);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDescription.setBounds(27, 290, 115, 37);
		panel.add(lblDescription);

		JLabel lblQuantity = new JLabel("Quantity ");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblQuantity.setBounds(27, 387, 93, 37);
		panel.add(lblQuantity);

		IDtext = new JTextField();
		IDtext.setToolTipText("ID is set manually after filling out all other fields");
		IDtext.setEditable(false);
		IDtext.setBounds(172, 96, 140, 37);
		panel.add(IDtext);
		IDtext.setColumns(10);

		NameText = new JTextField();
		NameText.setColumns(10);
		NameText.setBounds(172, 193, 140, 37);
		panel.add(NameText);

		DescriptionText = new JTextField();
		DescriptionText.setColumns(10);
		DescriptionText.setBounds(172, 290, 140, 37);
		panel.add(DescriptionText);

		QuantityText = new JTextField();
		QuantityText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)
						|| (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_PERIOD))) {

					e.consume();

				}
			}
		});
		QuantityText.setColumns(10);
		QuantityText.setBounds(172, 387, 140, 37);
		panel.add(QuantityText);

		JLabel lblCostPrice = new JLabel("Cost Price");
		lblCostPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCostPrice.setBounds(606, 193, 106, 37);
		panel.add(lblCostPrice);

		JLabel lblUnits = new JLabel("Units");
		lblUnits.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUnits.setBounds(606, 290, 106, 37);
		panel.add(lblUnits);

		JLabel lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSellingPrice.setBounds(606, 387, 134, 37);
		panel.add(lblSellingPrice);

		CostText = new JTextField();
		CostText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)
						|| (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_PERIOD))) {

					e.consume();

				}

			}
		});
		CostText.setColumns(10);
		CostText.setBounds(763, 193, 140, 37);
		panel.add(CostText);

		UnitsText = new JTextField();
		UnitsText.setColumns(10);
		UnitsText.setBounds(763, 290, 140, 37);
		panel.add(UnitsText);

		SellingText = new JTextField();
		SellingText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent et) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = et.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)
						|| (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_PERIOD))) {

					et.consume();

				}

			}
		});
		SellingText.setColumns(10);
		SellingText.setBounds(763, 387, 140, 37);
		panel.add(SellingText);

		JButton ConfirmButton = new JButton("CONFIRM");
		ConfirmButton.setToolTipText("Confirm button adds the values from the text box to the Stock Table");
		ConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					//Creating the ID by Combining the first 2 letters of the Name and the first 2 letters of the Category
					String Name = NameText.getText().substring(0, 2);
					String Cat = CategoryText.getText().substring(0, 2);
					String CatName = Name+Cat;
					//Setting the ID value to the ID text box
					IDtext.setText(CatName);
					//Creating a query in order to insert the values from the text fields into the stock table
					String addQuery = "Insert into StockTable (ItemID, Name, Description, Quantity, Category, Units, 'Cost Price' , 'Selling Price') values (?,?,?,?,?,?,?,?)";
					PreparedStatement prepstate = connect.prepareStatement(addQuery);
					//Setting the values from the text fields to the query
					prepstate.setString(1, IDtext.getText());
					prepstate.setString(2, NameText.getText());
					prepstate.setString(3, DescriptionText.getText());
					prepstate.setString(4, QuantityText.getText());
					prepstate.setString(5, CategoryText.getText());
					prepstate.setString(6, UnitsText.getText());
					prepstate.setString(7, CostText.getText());
					prepstate.setString(8, SellingText.getText());
					//Executing the query
					prepstate.execute();
					//The following methods are called here in order for their values to be updated after a value is added
					fetchTable();
					fillComboCat();
					fillComboN();
					fillComboN1();
					fillComboCat1();
					JOptionPane.showMessageDialog(null, "Your data has been saved!");
					prepstate.close();
					
					IDtext.setText(null);
					NameText.setText(null);
					DescriptionText.setText(null);
					QuantityText.setText(null);
					CategoryText.setText(null);
					UnitsText.setText(null);
					CostText.setText(null);
					SellingText.setText(null);

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		});
		
		ConfirmButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		ConfirmButton.setBounds(763, 13, 140, 55);
		panel.add(ConfirmButton);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCategory.setBounds(606, 96, 106, 37);
		panel.add(lblCategory);

		CategoryText = new JTextField();
		CategoryText.setColumns(10);
		CategoryText.setBounds(763, 96, 140, 37);
		panel.add(CategoryText);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Inventory", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lblSearchCategory = new JLabel("Search Category");
		lblSearchCategory.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSearchCategory.setBounds(12, 13, 129, 36);
		panel_1.add(lblSearchCategory);

		JLabel lblSearchName = new JLabel("Search Name");
		lblSearchName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSearchName.setBounds(272, 13, 129, 36);
		panel_1.add(lblSearchName);

		JButton AddButton = new JButton("ADD");
		AddButton.setToolTipText("Opens the Add item tab");
		AddButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//When the ADD button will be pressed the tab at index 0 will be set visible 
				tabbedPane.setSelectedIndex(0);

			}
		});
		AddButton.setBounds(546, 13, 106, 43);
		panel_1.add(AddButton);

		JButton btnRemove = new JButton("REMOVE");
		btnRemove.setToolTipText("Removes a selected value from the table");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//When the REMOVE button is pressed the ID value which is selected using the getrow method's coordinates will be removed using a query 
					getRow();
					String RemoveQuery = "Delete from StockTable where ItemID = ?";
					PreparedStatement prepstate = connect.prepareStatement(RemoveQuery);
					prepstate.setString(1, value);
					prepstate.executeUpdate();
					// The following methods are called here in order for the values to be updated after a value is deleted from the stock table
					fetchTable();
					prepstate.close();
					fillComboCat();
					fillComboN();
					fillComboN1();
					fillComboCat1();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
		btnRemove.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRemove.setBounds(692, 15, 106, 41);
		panel_1.add(btnRemove);

		JButton btnEdit = new JButton("EDIT");
		btnEdit.setToolTipText("Edits a selected value from the table");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//When the EDIT button is pressed the edit item screen will pop up
				setEditValues();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnEdit.setBounds(827, 14, 106, 42);
		panel_1.add(btnEdit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(12, 62, 942, 330);
		panel_1.add(scrollPane);

		InventoryTable = new JTable();
		InventoryTable.setBackground(new Color(255, 255, 255));
		InventoryTable.setFont(new Font("Tahoma", Font.BOLD, 15));
		InventoryTable.setToolTipText("Click on an ID in order to add, remove or edit an Item's value\r\n");
		scrollPane.setViewportView(InventoryTable);

		JButton RefreshButton = new JButton();
		RefreshButton.setToolTipText("Refresh the table");
		RefreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//When the refresh button is pressed the following methods are called to make sure the components are up to date with the correct inventory table values
				fetchTable();
				fillComboCat();
				fillComboN();
				fillComboCat1();
				fillComboN1();
			}
		});
		RefreshButton.setBackground(Color.WHITE);
		Image img = new ImageIcon(this.getClass().getResource("/reset-icon.png")).getImage();
		RefreshButton.setIcon(new ImageIcon(img));
		RefreshButton.setBounds(22, 396, 48, 39);
		panel_1.add(RefreshButton);

		CatCombo1 = new JComboBox();
		CatCombo1.setToolTipText("Filter using chosen category");
		CatCombo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Used to get the current selected item from the Category combo box
				Catselect1 = (String) CatCombo1.getSelectedItem();
				try {
					// Query run in order to filter through the table using the value which is currently selected in the combo box
					String CatQuery = "Select  * from StockTable where Category =?";
					PreparedStatement prepstate = connect.prepareStatement(CatQuery);
					prepstate.setString(1, Catselect1);
					ResultSet rs = prepstate.executeQuery();
					//using the result set of query to overwrite the model of the inventory table in order to show the filtered values
					InventoryTable.setModel(DbUtils.resultSetToTableModel(rs));
					prepstate.close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				DefaultComboBoxModel model = new DefaultComboBoxModel();
				try {
					// Query run to retrieve name value from StockTable depending on category value
					String NQuery = "Select Name From StockTable where Category=?";
					// Query set to a prepared statement and executed using a result set
					PreparedStatement prepstate = connect.prepareStatement(NQuery);
					prepstate.setString(1, Catselect1);
					// Result set is used in order to store each individual name value
					ResultSet rs = prepstate.executeQuery();
					// None element added to the Combo Box as default element
					model.addElement("None");
					while (rs.next()) {
						// While the result set is being looped through, the name values
						// are being added to the ComboBox Model object
						String name = rs.getString("Name");
						model.addElement(name);
					}
					// The model is then set to the Name Combo Box
					NameCombo1.setModel(model);
					prepstate.close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});

		CatCombo1.setBounds(144, 15, 116, 35);
		panel_1.add(CatCombo1);

		NameCombo1 = new JComboBox();
		NameCombo1.setToolTipText("Filter using name");
		NameCombo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Used to get the current selected item from the Name combo box
				NameSelect1 = (String) NameCombo1.getSelectedItem();
				try {
					// Query run in order to filter through the table using the value which is currently selected in the combo box
					String NameQuery = "Select  * from StockTable where Name =?";
					PreparedStatement prepstate = connect.prepareStatement(NameQuery);
					prepstate.setString(1, NameSelect1);
					ResultSet rs = prepstate.executeQuery();
					//using the result set of query to overwrite the model of the inventory table in order to show the filtered values
					InventoryTable.setModel(DbUtils.resultSetToTableModel(rs));
					prepstate.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();

				}
			}
		});
		NameCombo1.setBounds(386, 15, 116, 35);
		panel_1.add(NameCombo1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Point of Sale", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel label = new JLabel("Search Category");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(12, 13, 129, 36);
		panel_2.add(label);

		JLabel label_1 = new JLabel("Search Name");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBounds(272, 13, 129, 36);
		panel_2.add(label_1);

		JLabel lblQuantity_1 = new JLabel("Quantity");
		lblQuantity_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQuantity_1.setBounds(525, 13, 107, 36);
		panel_2.add(lblQuantity_1);

		POSQuantityTxt = new JTextField();
		POSQuantityTxt.setToolTipText("Quantity of a certain item bought by customer");
		POSQuantityTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)
						|| (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_PERIOD))) {
 
					e.consume();

				}		
			}
		});
		POSQuantityTxt.setBounds(601, 14, 116, 36);
		panel_2.add(POSQuantityTxt);
		POSQuantityTxt.setColumns(10);

		btnAdd = new JButton("ADD");
		btnAdd.setToolTipText("Adds the values to the table ");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(POSQuantityTxt.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter a value in the quantity text field");
				}else{
				
				//Implemented a check to see if the there was already an existing value with the same name as the one trying to be added to the table
				try{
					//Query used to select ID based on name from the point of sale table
					String q = "Select ID from POStable where Name =?";
					PreparedStatement prepstate = connect.prepareStatement(q);
					prepstate.setString(1, NameSelect);
					ResultSet rs = prepstate.executeQuery();
					//Counter variable set to 0
					int x = 0;
					//looping through the result set to check if it contains a value with the same name as the one trying to be added
					while(rs.next()){
					 x++;
					}
					//if there was a duplicate then these methods would be executed
					if(x == 1){
						checkAdd();
						fillTable2();
						getNumOfItems();
						getSum();
						QuantityCheck();
					}
					//if there wasn't a duplicate then these would be executed 
					else{
						getValues();
						fillTable2();
						getNumOfItems();
						getSum();
						QuantityCheck();
					}
					prepstate.close();
					rs.close();
					POSQuantityTxt.setText(null);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnAdd.setBounds(771, 13, 116, 36);
		panel_2.add(btnAdd);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(22, 62, 932, 273);
		panel_2.add(scrollPane_1);

		POStable = new JTable();
		POStable.setFont(new Font("Tahoma", Font.BOLD, 15));
		POStable.setToolTipText("Select ID in order to delete an item from the table");
		scrollPane_1.setViewportView(POStable);
		POStable.setAutoscrolls(true);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.setToolTipText("Deletes a selected item from the table");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//When the REMOVE button is pressed the ID value which is selected, using the getrow2 method's coordinates, will be removed using a query 
					getRow2();
					String RemoveQuery = "Delete from POStable where ID = ?";
					PreparedStatement prepstate = connect.prepareStatement(RemoveQuery);
					prepstate.setString(1, value2);
					prepstate.executeUpdate();
					prepstate.close();
					fillTable2();
					//Implemented a check to see if the Point of sale table had values when deleting
					//If the row count was 0 then the text fields were cleared
					if(POStable.getRowCount() == 0){
						TotalText.setText(null);
						ItemsText.setText(null);
						
					}
					// The following methods are called in order for their values to be updated after the delete has taken place
					getNumOfItems();
					getSum();
					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDelete.setBounds(22, 372, 116, 36);
		panel_2.add(btnDelete);

		JLabel lblNewLabel_1 = new JLabel("TOTAL");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1.setBounds(218, 348, 161, 30);
		panel_2.add(lblNewLabel_1);

		JLabel lblItemsPurchased = new JLabel("Items Purchased");
		lblItemsPurchased.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblItemsPurchased.setBounds(218, 391, 161, 44);
		panel_2.add(lblItemsPurchased);

		TotalText = new JTextField();
		TotalText.setToolTipText("Total cost of items purchased");
		TotalText.setEditable(false);
		TotalText.setBounds(385, 348, 116, 22);
		panel_2.add(TotalText);
		TotalText.setColumns(10);

		ItemsText = new JTextField();
		ItemsText.setToolTipText("Number of items purchased");
		ItemsText.setEditable(false);
		ItemsText.setColumns(10);
		ItemsText.setBounds(385, 404, 116, 22);
		panel_2.add(ItemsText);

		JButton lblChange = new JButton("Change");
		lblChange.setToolTipText("Button to calculate th change");
		lblChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enable();
				//Once the enter key is pressed the change is calculated by subtracting the 2 values from the AmountText and the TotalText text fields
				change = Double.parseDouble(AmountText.getText().toString())
						- Double.parseDouble(TotalText.getText().toString());
				//The change value is converted to a string and set to the text field
				String ChangeR = String.valueOf(change);
				ChangeText.setText(ChangeR);
				
			}
			
		});
		lblChange.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblChange.setBounds(549, 391, 116, 44);
		panel_2.add(lblChange);

		JLabel lblAmountPaid = new JLabel("Amount Paid");
		lblAmountPaid.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAmountPaid.setBounds(549, 348, 161, 30);
		panel_2.add(lblAmountPaid);

		AmountText = new JTextField();
		AmountText.setToolTipText("Amount paid by the customer");
		AmountText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)
						|| (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_PERIOD))) {

					e.consume();

				}
			}
		});

		AmountText.setColumns(10);
		AmountText.setBounds(670, 348, 116, 30);
		panel_2.add(AmountText);

		ChangeText = new JTextField();
		ChangeText.setEditable(false);
		ChangeText.setColumns(10);
		ChangeText.setBounds(670, 404, 116, 22);
		panel_2.add(ChangeText);

		RecieptButton = new JButton("Print\nReceipt ");
		RecieptButton.setToolTipText("Prints the receipt after a purchase is made");
		RecieptButton.setEnabled(false);
		RecieptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//When the Print receipt button is pressed the following methods will be executed
				Bill();
				printBill();
				chartinfo();
				updateQuantity();
				clearTable();
				fillTable2();
				fillTable3();
				salesReportTotal();
				salesReportCheck();
				
				POSQuantityTxt.setText(null);
				TotalText.setText(null);
				ItemsText.setText(null);
				AmountText.setText(null);
				ChangeText.setText(null);
				
				

			}
		});
		RecieptButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		RecieptButton.setBounds(798, 347, 156, 88);
		panel_2.add(RecieptButton);

		CategoryCB = new JComboBox();
		CategoryCB.setToolTipText("Filters the name drop down list by category");
		CategoryCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Retrieving the currently selected value from the category combo box
				Catselect = (String) CategoryCB.getSelectedItem();
				DefaultComboBoxModel model = new DefaultComboBoxModel();
				try {
					// Query run to retrieve name value from StockTable
					String NQuery = "Select Name From StockTable where Category=?";
					// Query set to a prepared statement and executed using a result set
					PreparedStatement prepstate = connect.prepareStatement(NQuery);
					prepstate.setString(1, Catselect);
					// Result set is used in order to store each individual name value
					ResultSet rs = prepstate.executeQuery();
					// None element added to the Combo Box as default element
					model.addElement("None");
					while (rs.next()) {
						// While the result set is being looped through, the name values
						// are being added to the ComboBox Model object
						String name = rs.getString("Name");
						model.addElement(name);

					}
					// The model is then set to the Name Combo Box
					NameCB.setModel(model);
					prepstate.close();
					rs.close();

				} catch (Exception e) {

					e.printStackTrace();
				}
				

			}
		});
		CategoryCB.setBounds(144, 19, 116, 30);
		panel_2.add(CategoryCB);

		NameCB = new JComboBox();
		NameCB.setToolTipText("Select an item based on name");
		NameCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Retrieving the currently selected value from the name combo box
				NameSelect = (String) NameCB.getSelectedItem();
			}
		});
		NameCB.setBounds(385, 19, 116, 30);
		panel_2.add(NameCB);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Sales Report", null, panel_3, null);
		panel_3.setLayout(null);

		ExportBtn = new JButton("Export Data");
		ExportBtn.setToolTipText("Exports Data to csv");
		ExportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exporting the data to the csv file when the Export data button is pressed
				chartToCsv();
			}
		});
		ExportBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		ExportBtn.setBounds(522, 171, 375, 44);
		panel_3.add(ExportBtn);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 60, 475, 303);
		panel_3.add(scrollPane_2);
		
		SalesReportTable = new JTable();
		SalesReportTable.setFont(new Font("Tahoma", Font.BOLD, 15));
		scrollPane_2.setViewportView(SalesReportTable);
		
		ChartBtn = new JButton("Create Chart");
		ChartBtn.setToolTipText("Creates chart of total sales vs date");
		ChartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//When this button is clicked a line chart will be created to show how the sales have progressed
				try{
					//Query used to get the sales date and the sum of the amount purchased all grouped by the sales date
					String query = "Select [Sales Date], Sum([Amount Purchased]) from ChartInfo group by [Sales Date]";
					//Using the query to create data set in order for it to be used for the chart
					JDBCCategoryDataset dataset = new JDBCCategoryDataset(connect,query);
					//Using an external library called JFreeChart in order to create the line chart using the method createlinechart
					JFreeChart chart = ChartFactory.createLineChart("Sales Progress Chart", "Date", "Sales Amount", dataset, PlotOrientation.VERTICAL, false, true, true);
					//Setting the renderer and the categoryplot to null
					BarRenderer renderer = null;
					CategoryPlot plot = null;
					renderer = new BarRenderer();
					//Setting the title for the chart and setting it visible
					ChartFrame Cframe = new ChartFrame("Sales Progress Chart", chart);
					Cframe.setVisible(true);
					Cframe.setSize(700, 700);
					
					
					
				}catch(Exception e){
					
				}
				
			}
		});
		ChartBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		ChartBtn.setBounds(522, 105, 375, 38);
		panel_3.add(ChartBtn);
		
		JLabel lblTotal = new JLabel("TOTAL");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblTotal.setBounds(264, 376, 90, 38);
		panel_3.add(lblTotal);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblDate.setBounds(41, 376, 90, 38);
		panel_3.add(lblDate);
		
		DateCombo = new JComboBox();
		DateCombo.setToolTipText("Drop down list to sort sales by date");
		DateCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Getting the selected value from the date combo box
				String DateSelect = (String) DateCombo.getSelectedItem();
				//Filling the table using the values within the date combo box
				//Query used to select certain dates and then group them by the sales date and sum the amount purchased
				String DateQuery = "Select [Sales Date],Sum([Amount Purchased]) from ChartInfo where [Sales Date] =? group by [Sales Date] ";
				try{
				PreparedStatement prepstate = connect.prepareStatement(DateQuery);
				prepstate.setString(1, DateSelect);
				ResultSet rs = prepstate.executeQuery();
				//Setting the model of the SalesReportTable using the result set
				SalesReportTable.setModel(DbUtils.resultSetToTableModel(rs));
				rs.close();
				prepstate.close();
				
				}catch(Exception e){
					e.printStackTrace();
				}
				salesReportTotal();
				
			}
		});
		DateCombo.setBounds(111, 383, 109, 35);
		panel_3.add(DateCombo);
		
		
		TotalTxtField = new JTextField();
		TotalTxtField.setEditable(false);
		TotalTxtField.setBounds(355, 380, 116, 34);
		panel_3.add(TotalTxtField);
		TotalTxtField.setColumns(10);
		
		JButton btnRefreshChart = new JButton("Refresh Table");
		btnRefreshChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button pressed to refresh the Sales report table and related components 
				fillTable3();
				fillDateCombo();
				salesReportTotal();
			}
		});
		btnRefreshChart.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnRefreshChart.setBounds(522, 239, 375, 38);
		panel_3.add(btnRefreshChart);
		
		JLabel lblSalesLog = new JLabel("Sales Log");
		lblSalesLog.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblSalesLog.setBounds(179, 13, 119, 34);
		panel_3.add(lblSalesLog);
		
		

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Logout", null, panel_4, null);
		panel_4.setLayout(null);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// When the logout button is pressed the current screen is disposed and the login screen is set visible
				Login_Screen lg = new Login_Screen();
				lg.frame.setVisible(true);
				frame.dispose();

			}
		});
		btnLogout.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 85));
		btnLogout.setBounds(307, 132, 352, 185);
		panel_4.add(btnLogout);

	}
}
