import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BillScreen {

	protected JFrame frame;
	protected JTextArea RecieptTextArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillScreen window = new BillScreen();
					window.decorate();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BillScreen() {
		initialize();
	}
	
	public void decorate(){
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.setVisible(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.darkGray));
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135, 206, 235));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton CloseButton = new JButton("Close");
		CloseButton.setToolTipText("Closes the text area ");
		CloseButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		CloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		CloseButton.setBounds(324, 227, 89, 39);
		frame.getContentPane().add(CloseButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 412, 203);
		frame.getContentPane().add(scrollPane);
		
		RecieptTextArea = new JTextArea();
		scrollPane.setViewportView(RecieptTextArea);
		RecieptTextArea.setEditable(false);
	}
}
