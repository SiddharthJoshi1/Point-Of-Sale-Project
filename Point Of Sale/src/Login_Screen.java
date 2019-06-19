import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.Color;

public class Login_Screen extends JFrame{

	protected JFrame frame;
	private JTextField UsernameText;
	private JPasswordField passwordField;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Screen window = new Login_Screen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Creating a connection object from the Connection class
	Connection connect = null;
	
	/**
	 * Create the application.
	 */
	public Login_Screen() {
		initialize();
		//Connecting the connection object with the database connector
		connect = sqlConnection.dbConnector();
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
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 75));
		lblNewLabel.setBounds(319, 13, 333, 122);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("USERNAME");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(223, 182, 220, 49);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PASSWORD");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(224, 293, 220, 49);
		frame.getContentPane().add(lblNewLabel_2);
		
		UsernameText = new JTextField();
		UsernameText.setFont(new Font("Tahoma", Font.BOLD, 15));
		UsernameText.setToolTipText("Enter username in this textbox");
		UsernameText.setBounds(533, 189, 220, 49);
		frame.getContentPane().add(UsernameText);
		UsernameText.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 17));
		passwordField.setEchoChar('*');
		passwordField.setToolTipText("Enter password in this textbox");
		passwordField.setBounds(533, 293, 220, 49);
		frame.getContentPane().add(passwordField);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.setToolTipText("Press login button to login into the program\r\n");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			Multi_Panel_Screen multiscreen = new Multi_Panel_Screen();	
			boolean check = false;
			
					
				try{
					//Writing a query to select everything from the LoginInfo table with the condition where the username and password will be inserted by the user
					String loginquery = "SELECT * FROM LoginInfo WHERE Username=? AND Password=?";
					//the query String is put into the prepared statement object
					PreparedStatement prepstate = connect.prepareStatement(loginquery);
					//The ? values of the query are inserted using the setString function by getting the values from the Username and Password Text fields
					prepstate.setString(1, UsernameText.getText());
					prepstate.setString(2, passwordField.getText());
					//The query is converted into a result set and is executed
					ResultSet rs = prepstate.executeQuery();
					// The increment variable will be used to check how many times the result set is looped through
					int increment = 0;
					
					
					//Looping through the result set of the loginquery
					while(rs.next()){
						//checking if the value in the 3rd column of the result set contains the value of Admin
						if(rs.getString(3).equals("Admin")){
							check = true;
							
						}else{
							check = false;
						}
					//Increment is increased by one every time the result set point moves to a new row					
					increment++;
					}
					
					
					//Checking if there is only one row hence the increment is only one 
					if(increment == 1){
					// When the increment is one, thus the login details are correct, the program will check the value of check from earlier to determine if the sales tab should be disabled	
					if(check == false){
						//multiscreen.tabbedPane.setSelectedIndex(3);
						System.out.println(multiscreen.tabbedPane.indexOfTab("Sales Report"));
						//Sales report tab is disabled using the setEnabledAt method
						multiscreen.tabbedPane.setEnabledAt(3, false);
						multiscreen.fetchTable();
						multiscreen.fillTable3();
						
					}
					// When increment is one then the login is successful 
					 JOptionPane.showMessageDialog(null, "Username and password are correct");
					//the current Login_Screen is disposed if the condition is met
						frame.dispose();
					//the Multi_Panel_Screen is set visible 
						multiscreen.frame.setVisible(true);
						multiscreen.fetchTable();
						multiscreen.fillTable3();
						multiscreen.salesReportCheck();
						
						
						
					}else if(increment> 1){
						//If the increment is more than one then it can be assumed that there are duplicate values in the LoginInfo table 
						JOptionPane.showMessageDialog(null, "Duplicate username and password");
					}else{
						//If the increment is less than one then it can be assumed there are no values for the inserted username and password values
						JOptionPane.showMessageDialog(null, "Username and password is not correct, Please try again");
					}
					
					rs.close();
					prepstate.close();
				}catch(Exception e){
					
					JOptionPane.showMessageDialog(null, e); 
					
				}
				
				
			}
		});
		LoginButton.setFont(new Font("Tahoma", Font.BOLD, 40));
		LoginButton.setBounds(399, 366, 175, 69);
		frame.getContentPane().add(LoginButton);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
				
			}
			
		});
		

	}
	
	
	
}
