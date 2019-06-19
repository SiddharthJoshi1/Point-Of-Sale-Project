import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Edit_Item_Screen extends JFrame {

	protected JFrame frame;
	protected JTextField IDtext;
	protected JTextField Nametext;
	protected JTextField DescriptionText;
	protected JTextField QuantityText;
	protected JTextField SellingText;
	protected JTextField UnitsText;
	protected JTextField CostText;
	protected JTextField CategoryText;
	protected JLabel lblEditItem;
	protected String itemid;
	protected String name;
	protected String Description;
	protected String Quantity;
	protected String Category;
	protected String Costprice;
	protected String units;
	protected String SellingPrice;
	
	
	
	/**
	 * Launch the application.
	 */
	//creating the Multi_Panel_Screen object 
	Multi_Panel_Screen multiscreen = new Multi_Panel_Screen();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_Item_Screen window = new Edit_Item_Screen();
					window.decorate();
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	// creating the connection object
	Connection connect = null;
	
	/**
	 * Create the application.
	 */
	public Edit_Item_Screen() {
		initialize();
		//Connecting the sqlConnection object
		connect = multiscreen.connect;
		
		
	}
	
	public void decorate(){
		
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.setVisible(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(HEIGHT+5, WIDTH+5, HEIGHT+5, WIDTH+5, Color.darkGray));
		
		
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135, 206, 235));
		frame.setBounds(100, 100, 989, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Item ID");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(37, 107, 86, 37);
		frame.getContentPane().add(label);
		
		IDtext = new JTextField();
		IDtext.setToolTipText("ItemID is set automatically after all other fields are filled\r\n");
		IDtext.setEditable(false);
		IDtext.setColumns(10);
		IDtext.setBounds(182, 107, 140, 37);
		frame.getContentPane().add(IDtext);
		
		JLabel label_1 = new JLabel("Name");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_1.setBounds(37, 204, 86, 37);
		frame.getContentPane().add(label_1);
		
		Nametext = new JTextField();
		Nametext.setColumns(10);
		Nametext.setBounds(182, 204, 140, 37);
		frame.getContentPane().add(Nametext);
		
		JLabel label_2 = new JLabel("Description");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_2.setBounds(37, 301, 115, 37);
		frame.getContentPane().add(label_2);
		
		DescriptionText = new JTextField();
		DescriptionText.setColumns(10);
		DescriptionText.setBounds(182, 301, 140, 37);
		frame.getContentPane().add(DescriptionText);
		
		JLabel label_3 = new JLabel("Quantity ");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_3.setBounds(37, 398, 93, 37);
		frame.getContentPane().add(label_3);
		
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
		QuantityText.setBounds(182, 398, 140, 37);
		frame.getContentPane().add(QuantityText);
		
		JLabel label_4 = new JLabel("Selling Price");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_4.setBounds(616, 398, 134, 37);
		frame.getContentPane().add(label_4);
		
		SellingText = new JTextField();
		SellingText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = e.getKeyChar();
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)||(c==KeyEvent.VK_SPACE)||(c==KeyEvent.VK_PERIOD))){
					
					e.consume();
					
				}
				
			}
		});
		SellingText.setColumns(10);
		SellingText.setBounds(773, 398, 140, 37);
		frame.getContentPane().add(SellingText);
		
		UnitsText = new JTextField();
		UnitsText.setColumns(10);
		UnitsText.setBounds(773, 301, 140, 37);
		frame.getContentPane().add(UnitsText);
		
		JLabel label_5 = new JLabel("Units");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_5.setBounds(616, 301, 106, 37);
		frame.getContentPane().add(label_5);
		
		CostText = new JTextField();
		CostText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Text field set to only take in certain key-pressed values and consume the rest by reading each character value entered into text field
				char c = e.getKeyChar();
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)||(c==KeyEvent.VK_SPACE)||(c==KeyEvent.VK_PERIOD))){
					
					e.consume();
					
				}
				
			}
		});
		CostText.setColumns(10);
		CostText.setBounds(773, 204, 140, 37);
		frame.getContentPane().add(CostText);
		
		JLabel label_6 = new JLabel("Cost Price");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_6.setBounds(616, 204, 106, 37);
		frame.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("Category");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_7.setBounds(616, 107, 106, 37);
		frame.getContentPane().add(label_7);
		
		CategoryText = new JTextField();
		CategoryText.setColumns(10);
		CategoryText.setBounds(773, 107, 140, 37);
		frame.getContentPane().add(CategoryText);
		
		JButton ConfirmButton = new JButton("CONFIRM");
		ConfirmButton.setToolTipText("Button pressed to confirm and update the values in the StockTable");
		ConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					//running a query in order to update the values of an item where the condition is set to match the ID which is entered in the IDtext field 
					String UpdateQ = "Update StockTable set ItemID ='"+IDtext.getText()+"', Name = '"+Nametext.getText()+"', Description = '"+DescriptionText.getText()+"', Quantity= '"
					+QuantityText.getText()+"', Category = '"+CategoryText.getText()+"', 'Cost Price' = '"+CostText.getText()+"', Units = '"+UnitsText.getText()+"', 'Selling Price' = '"
							+SellingText.getText()+"' where ItemID ='"+IDtext.getText()+"' ";
					
					PreparedStatement prepstate = connect.prepareStatement(UpdateQ);
					prepstate.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Data Updated");		
					
					
					prepstate.close();
					
					
				}catch(Exception e1){
					
					e1.printStackTrace();
				}
				//A method which is used to update the Inventory table through the use of a query which selects all from the Stock table database
				multiscreen.fetchTable();
				//Once the data is updated, the frame will be disposed to show the previous window
				frame.dispose();
												
			}
		});
		
		ConfirmButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		ConfirmButton.setBounds(773, 24, 140, 55);
		frame.getContentPane().add(ConfirmButton);
		
		lblEditItem = new JLabel("Edit Item");
		lblEditItem.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblEditItem.setBounds(411, 46, 149, 55);
		frame.getContentPane().add(lblEditItem);
		
		JButton btnBack = new JButton("BACK");
		btnBack.setToolTipText("Back button to go back to the Inventory tab");
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnBack.setBounds(37, 24, 140, 55);
		frame.getContentPane().add(btnBack);
	}
}
