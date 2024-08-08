package com.userAdmin;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.databaseConnection.DbConnection;
import com.employeeManagement.EmployeeManagement;
import com.loginPage.Login;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idText;
	private JTextField userNameText;
	private JTextField passwordText;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashboard frame = new AdminDashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminDashboard() {
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 793, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 228, 181));
		panel.setBounds(10, 11, 757, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel adminLabel = new JLabel("ADMIN DASHBOARD");
		adminLabel.setBounds(292, 24, 181, 22);
		panel.add(adminLabel);
		adminLabel.setFont(new Font("Arial", Font.BOLD, 18));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(176, 224, 230));
		panel_1.setBounds(10, 79, 216, 371);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton employeeButton = new JButton("Employee_Management");
		employeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeManagement em = new EmployeeManagement();
				em.setVisible(true);
			}
		});
		employeeButton.setFont(new Font("Arial", Font.BOLD, 14));
		employeeButton.setBounds(10, 27, 196, 38);
		panel_1.add(employeeButton);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login l = new Login();
				l.setVisible(true);
				
			}
		});
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 13));
		logoutButton.setBounds(56, 335, 110, 25);
		panel_1.add(logoutButton);
		
		JLabel idLabel = new JLabel("Id");
		idLabel.setFont(new Font("Arial", Font.BOLD, 14));
		idLabel.setBounds(246, 104, 19, 17);
		contentPane.add(idLabel);
		
		idText = new JTextField();
		idText.setBounds(330, 103, 86, 20);
		contentPane.add(idText);
		idText.setColumns(10);
		
		JLabel userNameLabel = new JLabel("userName");
		userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
		userNameLabel.setBounds(246, 145, 74, 14);
		contentPane.add(userNameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
		passwordLabel.setBounds(246, 180, 74, 14);
		contentPane.add(passwordLabel);
		
		userNameText = new JTextField();
		userNameText.setBounds(330, 142, 86, 20);
		contentPane.add(userNameText);
		userNameText.setColumns(10);
		
		passwordText = new JTextField();
		passwordText.setBounds(330, 177, 86, 20);
		contentPane.add(passwordText);
		passwordText.setColumns(10);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int id = Integer.parseInt()
				int id  = Integer.parseInt(idText.getText());
				String username = userNameText.getText();
				String password = passwordText.getText();
				
				String SQL = "insert into user (Id, userName, password) values (?,?,?)";
				
				try(Connection con = DbConnection.getConnection();
					    PreparedStatement pstmt = con.prepareStatement(SQL)){
					pstmt.setInt(1, id);
					pstmt.setString(2, username);
					pstmt.setString(3, password);
					pstmt.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "user registered successfull");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "user not reistered");
				}
			}
		});
		registerButton.setFont(new Font("Arial", Font.BOLD, 14));
		registerButton.setBounds(544, 90, 170, 35);
		contentPane.add(registerButton);
		
		JButton manageUserButton = new JButton("Manage User");
		manageUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ManageUser mu = new ManageUser();
				mu.setVisible(true);
			}
		});
		manageUserButton.setFont(new Font("Arial", Font.BOLD, 14));
		manageUserButton.setBounds(544, 145, 170, 35);
		contentPane.add(manageUserButton);
		
		table = new JTable();
		table.setBounds(246, 254, 498, 181);
		contentPane.add(table);
		displayUsers();
	}
	public void displayUsers() {
		String SQL = "select * from user";
		try (Connection con = DbConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(SQL)){
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel dtm = new DefaultTableModel();
			dtm.addColumn("Id");
			dtm.addColumn("userName");
			dtm.addColumn("Password");
			
			while(rs.next()) {
				String id = rs.getString("Id");
				String userName = rs.getString("userName");
				String password = rs.getString("Password");
				dtm.addRow(new Object[] {id, userName, password});

			}
			
			table.setModel(dtm);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
