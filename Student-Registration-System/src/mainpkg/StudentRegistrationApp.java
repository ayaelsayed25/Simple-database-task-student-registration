package mainpkg;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.*;
import java.awt.event.ActionEvent;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class StudentRegistrationApp {

	private JFrame frame;
	private JPanel mainPanel;
	private JTextField stname;
	private JTextField stemail;
	private JTextField stcourse;
	private JTable table;
	Connection con;
	PreparedStatement insert;
	PreparedStatement update;
	PreparedStatement delete;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentRegistrationApp window = new StudentRegistrationApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentRegistrationApp() {
		initialize();
		updateJTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 979, 657);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setBounds(100, 100, 450, 300);
		mainPanel.setBackground(new Color(0, 250, 154));
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JLabel title = new JLabel("Student Registration");
		title.setFont(new Font("Tahoma", Font.BOLD, 31));
		title.setForeground(new Color(255, 255, 255));
		title.setBounds(370, 10, 337, 70);
		mainPanel.add(title);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(169, 169, 169));
		panel.setBounds(50, 165, 396, 290);
		mainPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblCourseRegistration = new JLabel("Course Registration:");
		lblCourseRegistration.setBackground(new Color(255, 255, 255));
		lblCourseRegistration.setBounds(34, 10, 254, 40);
		panel.add(lblCourseRegistration);
		lblCourseRegistration.setForeground(new Color(255, 255, 255));
		lblCourseRegistration.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel lblCourseRegistration_1 = new JLabel("Name:");
		lblCourseRegistration_1.setBounds(34, 60, 71, 40);
		panel.add(lblCourseRegistration_1);
		lblCourseRegistration_1.setForeground(new Color(255, 255, 255));
		lblCourseRegistration_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblCourseRegistration_1_1 = new JLabel("Email:");
		lblCourseRegistration_1_1.setForeground(new Color(255, 255, 255));
		lblCourseRegistration_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCourseRegistration_1_1.setBounds(34, 131, 71, 40);
		panel.add(lblCourseRegistration_1_1);
		
		JLabel lblCourseRegistration_1_1_1 = new JLabel("Course:");
		lblCourseRegistration_1_1_1.setForeground(new Color(255, 255, 255));
		lblCourseRegistration_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCourseRegistration_1_1_1.setBounds(34, 200, 82, 40);
		panel.add(lblCourseRegistration_1_1_1);
		
		stname = new JTextField();
		stname.setForeground(new Color(102, 102, 102));
		stname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stname.setBounds(130, 69, 219, 32);
		panel.add(stname);
		stname.setColumns(10);
		
		stemail = new JTextField();
		stemail.setForeground(new Color(102, 102, 102));
		stemail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stemail.setColumns(10);
		stemail.setBounds(130, 140, 219, 32);
		panel.add(stemail);
		
		stcourse = new JTextField();
		stcourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stcourse.setForeground(new Color(102, 102, 102));
		stcourse.setColumns(10);
		stcourse.setBounds(130, 209, 219, 32);
		panel.add(stcourse);
		
		JButton addBtn = new JButton("ADD");
		addBtn.setBackground(new Color(204, 204, 204));
		addBtn.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		addBtn.setForeground(new Color(0, 51, 102));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = stname.getText();
				String email = stemail.getText();
				String course = stcourse.getText();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con =  DriverManager.getConnection("jdbc:mysql://localhost/girrafe", "root", "Ineedyouallah25*");
					insert = con.prepareStatement("INSERT INTO student(name, email, course)VALUES(?, ?, ?)");
					insert.setString(1, name);
					insert.setString(2, email);
					insert.setString(3, course);
					insert.executeUpdate();
					JOptionPane.showMessageDialog(frame, "Record added!");
					stname.setText("");
					stemail.setText("");
					stcourse.setText("");
					updateJTable();
				} catch (ClassNotFoundException e1 ) {
					e1.printStackTrace();
				} catch (SQLException e2 ) {
					e2.printStackTrace();
				}	
			}
			
		});
		addBtn.setBounds(50, 494, 90, 41);
		mainPanel.add(addBtn);
		
		JButton editBtn = new JButton("Edit");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con =  DriverManager.getConnection("jdbc:mysql://localhost/girrafe", "root", "Ineedyouallah25*");
					String stName = stname.getText();
					String stEmail = stemail.getText();
					String stCourse = stcourse.getText();
					int index = table.getSelectedRow();
					DefaultTableModel dm = (DefaultTableModel) table.getModel(); 
					int selectedID = Integer.parseInt(dm.getValueAt(index, 0).toString());
					update = con.prepareStatement("UPDATE student SET name = ?, email = ?, course = ? WHERE id = ?");
					update.setString(1, stName);
					update.setString(2, stEmail);
					update.setString(3, stCourse);
					update.setInt(4, selectedID);
					update.executeUpdate();
					JOptionPane.showMessageDialog(frame, "Record updated!");
					stname.setText("");
					stemail.setText("");
					stcourse.setText("");
					updateJTable();
				} catch (ClassNotFoundException e1 ) {
					e1.printStackTrace();
				} catch (SQLException e2 ) {
					e2.printStackTrace();
				}	
			}
		});
		editBtn.setForeground(new Color(0, 51, 102));
		editBtn.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		editBtn.setBackground(new Color(204, 204, 204));
		editBtn.setBounds(206, 494, 90, 41);
		mainPanel.add(editBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con =  DriverManager.getConnection("jdbc:mysql://localhost/dbName", "root", "password");
					int index = table.getSelectedRow();
					DefaultTableModel dm = (DefaultTableModel) table.getModel(); 
					int selectedID = Integer.parseInt(dm.getValueAt(index, 0).toString());
					delete = con.prepareStatement("DELETE FROM student WHERE id = ?");
					delete.setInt(1, selectedID);
					delete.executeUpdate();
					JOptionPane.showMessageDialog(frame, "Record deleted!");
					stname.setText("");
					stemail.setText("");
					stcourse.setText("");
					updateJTable();
				} catch (ClassNotFoundException e1 ) {
					e1.printStackTrace();
				} catch (SQLException e2 ) {
					e2.printStackTrace();
				}	
			}
		});
		deleteBtn.setForeground(new Color(0, 51, 102));
		deleteBtn.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		deleteBtn.setBackground(new Color(204, 204, 204));
		deleteBtn.setBounds(356, 494, 90, 41);
		mainPanel.add(deleteBtn);
		
		String[] columnNames = {"ID","Name","Email","Course"};
		Object[][] data = {
		};
		table = new JTable(data, columnNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setBorder(new LineBorder(new Color(102, 102, 102), 2, true));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(31, 81, 114, -69);
		table.setFillsViewportHeight(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				System.out.println(index);
				DefaultTableModel dm = (DefaultTableModel) table.getModel(); 
				stname.setText(dm.getValueAt(index, 1).toString());
				stemail.setText(dm.getValueAt(index, 2).toString());
				stcourse.setText(dm.getValueAt(index, 3).toString());
			}
		});
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(471, 90, 484, 445);
		mainPanel.add(tablePanel);
		tablePanel.add(scrollPane);

		
		
	}
	
	private void updateJTable()
	{
		int colNum;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =  DriverManager.getConnection("jdbc:mysql://localhost/girrafe", "root", "Ineedyouallah25*");
			insert = con.prepareStatement("SELECT * FROM student");
			ResultSet rs = insert.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			colNum = rsm.getColumnCount();
			DefaultTableModel dm = new DefaultTableModel(0, 0);
			String header[] = new String[] { "ID", "Name", "Email",
		            "Course"};
		    dm.setColumnIdentifiers(header);
		    table.setModel(dm);

			while(rs.next())
			{
				Vector<String> v = new Vector<String>();
				v.add(rs.getString("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("email"));
				v.add(rs.getString("course"));
				dm.addRow(v);
			}
		} catch (ClassNotFoundException e1 ) {
			e1.printStackTrace();
		} catch (SQLException e2 ) {
			e2.printStackTrace();
		}
	}
	
}
