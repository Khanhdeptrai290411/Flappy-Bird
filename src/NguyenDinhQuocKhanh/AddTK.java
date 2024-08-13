package NguyenDinhQuocKhanh;

import java.awt.EventQueue;
import DataBaseMySQL.connectz;
import Login.BangLogin;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
public class AddTK extends JFrame {

	private JPanel contentPane;
	private JLabel txtAdd;
	private JPasswordField textPass;
	protected static PreparedStatement pst = null;
	

	/**
	 * Create the frame.
	 */
	public AddTK() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddTK.class.getResource("/image/bird0.png")));
		setResizable(false);
		setTitle("Create  Account");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 434);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtAdd = new JLabel("Create Account");
		txtAdd.setForeground(Color.RED);
		txtAdd.setFont(new Font("Tahoma", Font.PLAIN, 29));
		txtAdd.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdd.setBounds(196, 10, 286, 64);
		contentPane.add(txtAdd);
		
		JLabel txtUser = new JLabel("USERNAME :");
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtUser.setBounds(68, 110, 134, 46);
		contentPane.add(txtUser);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(224, 118, 351, 29);
		contentPane.add(textPane);
		
		textPass = new JPasswordField();
		textPass.setBounds(224, 184, 351, 29);
		contentPane.add(textPass);
		
		
		JLabel textMess = new JLabel("");
		textMess.setForeground(Color.RED);
		textMess.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textMess.setBackground(Color.RED);
		textMess.setBounds(35, 324, 571, 36);
		contentPane.add(textMess);
		
		JLabel txtPass = new JLabel("Password:");
		txtPass.setFont(new Font("Stencil", Font.PLAIN, 16));
		txtPass.setBounds(68, 184, 120, 29);
		contentPane.add(txtPass);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement ps = null;
			     String usermame = textPane.getText();
			     String password = String.valueOf(textPass.getPassword());
		 
		         
					try {
						conn = connectz.getJDBCConnection();
						Statement stmt = conn.createStatement();
						 String query = "SELECT idplayer FROM player WHERE idplayer = '" + usermame + "'";
				            ResultSet rs = stmt.executeQuery(query);
				            if(rs.next()) {
				            	textMess.setText("Đã Có Tài Khoản");
				            }else {
				            
								
					
			           
			           
						 if(usermame.equals("")&& password.equals(""))
							{
						    textMess.setText("Lỗi,Hãy Thử Lại!!");
							}else if(usermame.equals("")) {
								textMess.setText("Hãy Nhập Tên Người Dùng !!!");
							}else if(password.equals("")) {
								textMess.setText("Nhập Mật Khẩu !!!");
							}else if(password.length()<=4) {
								textMess.setText("Mật Khẩu Phải Lớn Hơn 4 Kí Tự");
							}
							else  {
								conn = connectz.getJDBCConnection();
								ps = conn.prepareStatement("INSERT INTO `player`(`idplayer`, `password`,`score`) VALUES (?,?,?)");
								ps.setString(1, usermame);
								ps.setString(2, password);
								ps.setString(3, "0");
								int ret = ps.executeUpdate();
							textMess.setText("Tạo Tài Khoản Thành Công!");
						}
			            }
					}
				catch (SQLException ex) {
		            Logger.getLogger(AddTK.class.getName()).log(Level.SEVERE, null, ex);
		        }
					finally {
						if (pst != null) {
		                    try {
		                        pst.close();
		                    } catch (SQLException ex) {
		                        Logger.getLogger(BangLogin.class.getName()).log(Level.SEVERE, null, ex);
		                    }
		                }
		                
		                if (conn !=null) {
		                    try {
		                        conn.close();
		                    } catch (SQLException ex) {
		                        Logger.getLogger(BangLogin.class.getName()).log(Level.SEVERE, null, ex);
		                    }
		                }
					}
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAdd.setBackground(Color.LIGHT_GRAY);
		btnAdd.setBounds(391, 268, 184, 46);
		contentPane.add(btnAdd);
		
		JButton back = new JButton("Back");
		back.setBackground(new Color(192, 192, 192));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BangLogin bl = new BangLogin();
				bl.setVisible(true);
				dispose();
			}
		});
		back.setFont(new Font("Tahoma", Font.PLAIN, 15));
		back.setBounds(10, 10, 95, 46);
		contentPane.add(back);
		
		JLabel lblNewLabel = new JLabel("");

		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(-20, 0, 677, 410);
		contentPane.add(lblNewLabel);
	
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTK frame = new AddTK();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}

