package Login;
import java.awt.EventQueue;
import DataBaseMySQL.connectz;
import Flappy.FlappyBird;
import NguyenDinhQuocKhanh.AddTK;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Toolkit;
import javax.swing.ImageIcon;


public class BangLogin extends JFrame {
	public static String username;
	public static String password;
	protected static PreparedStatement pst = null;
	protected static Connection conn = null;
	private JPanel contentPane;
	private JPasswordField textPass; 
	public BangLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BangLogin.class.getResource("/image/bird0.png")));
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("Login Admin");
		setBounds(100, 100, 724, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel textpane = new JLabel("LOGIN ");
		textpane.setFont(new Font("Tahoma", Font.PLAIN, 50));
		textpane.setHorizontalAlignment(SwingConstants.CENTER);
		textpane.setBounds(194, 23, 320, 72);
		textpane.setForeground(Color.RED);
		contentPane.add(textpane);
		
		JLabel txtUser = new JLabel("UserName:");
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUser.setBounds(71, 121, 120, 29);
		contentPane.add(txtUser);
		
		JLabel txtPass = new JLabel("Password:");
		txtPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPass.setBounds(71, 171, 120, 29);
		contentPane.add(txtPass);

		JLabel textMess = new JLabel("");
		textMess.setHorizontalAlignment(SwingConstants.CENTER);
		textMess.setForeground(new Color(255, 0, 0));
		textMess.setBackground(new Color(255, 0, 0));
		textMess.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textMess.setBounds(172, 312, 412, 36);
		contentPane.add(textMess);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(194, 121, 351, 29);
		contentPane.add(textPane);
		
		textPass = new JPasswordField();
		textPass.setBounds(194, 170, 351, 29);
		contentPane.add(textPass);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 username = textPane.getText();
				 password = String.valueOf(textPass.getPassword());
			
				if(username.equals("") && password.equals("")) {
            		textMess.setText("Nhập Tên Người Dùng Và Mật Khẩu !!!");
            	}
				else if(username.equals(""))
				{
					textMess.setText("Nhập Tên Người Dùng !!!");
				}else if(password.equals(""))
				{
					textMess.setText("Nhập Mật Khẩu !!!");
				}
				
				else
				{
				try {
					conn = connectz.getJDBCConnection();
					String sql = "SELECT * FROM `player` WHERE idplayer = ? AND password = ?";
					pst = conn.prepareStatement(sql);
	                pst.setString(1, username);
	                pst.setString(2, password);
	                ResultSet resultSet = pst.executeQuery();
	                if(resultSet.next()) {
	                		 
	                		 String ss = resultSet.getString("idplayer");
	 	                	if( ss.equalsIgnoreCase(username)) {
	 	                		textMess.setText("Đăng Nhập Thành Công!!");
	 	                       FlappyBird game = new FlappyBird();
	 	                      game.startGame();
	 		                	dispose();
	                	}
	                }
	                 
	                else
                	{
                		textMess.setText("Đăng Nhập Thất Bại !!!");
                	}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
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
			}});
		
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogin.setBounds(277, 263, 184, 42);
		contentPane.add(btnLogin);		
		
		JButton btnCrAcc = new JButton("Create Account");
		btnCrAcc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddTK atk = new AddTK();
				atk.setVisible(true);
				dispose();
			}
		});
		btnCrAcc.setBackground(new Color(255, 255, 255));
		btnCrAcc.setBounds(277, 358, 184, 21);
		contentPane.add(btnCrAcc);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(-29, 10, 770, 439);
		contentPane.add(lblNewLabel);
	}

	private static void setIconImages(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Launch the application.
	 */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BangLogin frame = new BangLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println(username);
	}
}
