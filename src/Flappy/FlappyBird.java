package Flappy;


import DataBaseMySQL.connectz;
import Login.BangLogin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

public class FlappyBird extends JPanel implements Runnable,KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private ArrayList<Rectangle> columns;
    private Rectangle bird;
    private int positionX = 50;
    private int positionY = 300;
    private int HBird = 25;
    private int WBird = 25;
    private int birdY;
    private int gameSpeed = 10;
    private double score;
    private Random rand;
    private Thread t;
    private boolean gameOver;
    private boolean gamePaused;
    private Image background;
    private Image[] birdImages;
    private Image icon;
    private Image tube;
    private Image end;
    private Image start;
    private Image pause;
    private int currentBirdImageIndex;
    private double birdImageCounter;
    private Rectangle column1;
    private Rectangle column2;
    private String line;
    private InputStream in;
    private Clip clip;
    private Clip clip2;
    private Clip clip3;
    private Clip clip4;
    private AudioInputStream audioInputStream;
    BufferedWriter bw = null;
    FileWriter fw = null;
    private static String currentScore;
    String url = "D:\\Flappy\\src\\HighScore\\HighScore.txt";
    FileInputStream fileInputStream = null;
    BufferedReader bufferedReader = null;
	protected static PreparedStatement pst = null;
	protected static Connection conn = null;
	public static String username;
	public static String      password;
    public FlappyBird() {
    	   BangLogin packageA = new BangLogin();
            username = packageA.getUsername(); // Lấy giá trị username từ package A
           password = packageA.getPassword(); // Lấy giá trị username từ package A
    	    try {
    	    	fileInputStream = new FileInputStream(url);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                line = bufferedReader.readLine();
    	        in = FlappyBird.class.getResourceAsStream("/Music/music.wav");
    	        audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
    	        clip = AudioSystem.getClip();
    	        audioInputStream.mark(Integer.MAX_VALUE);
    	        clip.open(audioInputStream);

    	         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	         DocumentBuilder builder = factory.newDocumentBuilder();
    	         Document doc = builder.parse("src/XML/Bird.xml");
    	         Element bird = (Element) doc.getElementsByTagName("Bird").item(0);//lấy bird trong XML
    	         Element position = (Element) bird.getElementsByTagName("Position").item(0);//lấy position trong XML
    	         positionX = Integer.parseInt(position.getAttribute("x"));
    	         positionY = Integer.parseInt(position.getAttribute("y"));
    	         WBird = Integer.parseInt(position.getAttribute("width"));
    	         HBird = Integer.parseInt(position.getAttribute("height"));
    	         gameSpeed = (int)(gameSpeed / Double.parseDouble(position.getAttribute("speed")));

    	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        frame = new JFrame();
        frame.setTitle("Flappy Bird");
        background = new ImageIcon(FlappyBird.class.getResource("/image/background.png")).getImage();
        
        birdImages = new Image[] {
                new ImageIcon(FlappyBird.class.getResource("/image/bird0.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird0.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird0.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird0.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird0.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird1.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird1.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird1.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird1.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird1.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird2.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird2.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird2.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird2.png")).getImage(),
                new ImageIcon(FlappyBird.class.getResource("/image/bird2.png")).getImage()
                };
        icon = new ImageIcon(FlappyBird.class.getResource("/image/icon.png")).getImage();
        tube = new ImageIcon(FlappyBird.class.getResource("/image/tube.png")).getImage();
        end = new ImageIcon(FlappyBird.class.getResource("/image/gameover.png")).getImage();
        start = new ImageIcon(FlappyBird.class.getResource("/image/playbutton.png")).getImage();
        pause = new ImageIcon(FlappyBird.class.getResource("/image/pause.png")).getImage();
        frame.setIconImage(icon);
        columns = new ArrayList<>();
        rand = new Random();
        bird = new Rectangle(positionX, positionY, WBird, HBird);
        birdY = 0;
        score = 0;
        gameOver = false;	
        gamePaused = false;
        frame.setSize(432, 518);
        frame.setLocation(468,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        setLayout(null);
        
        currentBirdImageIndex = 0;
        birdImageCounter = 1;
    }

    public void startGame() {
        t = new Thread(this);
        t.start();
    }

    public void pauseGame() {
        gamePaused = true;
    }

    public void resumeGame() {
        gamePaused = false;
    }

    public void stopGame() {
        gameOver = true;
    }


    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
     // Vẽ tên người dùng

        g.drawImage(background, 0, -130, null);

        for (Rectangle column : columns) {
            g.drawImage(tube,column.x, column.y, column.width, column.height,null);
        }

        g.drawImage(birdImages[currentBirdImageIndex], bird.x, bird.y, bird.width, bird.height, null);

        g.setColor(Color.black);
        g.drawString("Score: " + (int)score, 10, 20);
        
        g.drawString("Highest Score: " + currentScore, 310, 20);
        g.drawString(username, bird.x + bird.width / 2 - g.getFontMetrics().stringWidth(username) / 2,
                bird.y - g.getFontMetrics().getHeight());
        if (gameOver) {
        	in = FlappyBird.class.getResourceAsStream("/Music/hit.wav");
	        try {
				audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
		        clip3 = AudioSystem.getClip();
		        audioInputStream.mark(Integer.MAX_VALUE);
		        clip3.open(audioInputStream);
			} catch (UnsupportedAudioFileException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        clip3.start();
        	g.drawImage(end, 80, 180, 250, 80,null);
        	g.drawImage(start, 170, 280, 70, 40,null);
        	clip.stop();
   
        		try {
                    FileWriter writer = new FileWriter(url);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(username + currentScore);
                    bufferedWriter.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
        	
        }
        
        if (gamePaused) {
        	g.drawImage(pause, 80, 140, 240, 200,null);
        	clip.stop();
        } else if(!gamePaused && !gameOver) {
        	clip.start();
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gamePaused) {
        	in = FlappyBird.class.getResourceAsStream("/Music/up.wav");
	        try {
				audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
		        clip2 = AudioSystem.getClip();
		        audioInputStream.mark(Integer.MAX_VALUE);
		        clip2.open(audioInputStream);
			} catch (UnsupportedAudioFileException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        FloatControl gainControl = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
	        gainControl.setValue(-10.0f);
	        clip2.start();
            birdY -= 35;
            bird.y = birdY;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        	gamePaused = !gamePaused;
        }
    }
    
    @Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void run() {
        while (!gameOver) {
            if (!gamePaused) {
                update();
                repaint();
            }else {
            	repaint();
            }
            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
    	if(currentBirdImageIndex == 0) {
    		birdImageCounter = 1;
    	}
    	else if(currentBirdImageIndex == 14) {
    		birdImageCounter = -1;
    	}
    	currentBirdImageIndex += birdImageCounter;     
        birdY += 2;
        bird.y = birdY;

        if (bird.y > getHeight()+25  || bird.y <= 0) {
            gameOver = true;
        }

        if (columns.size() == 0 || columns.get(columns.size() - 1).x <= getWidth() - 200) {
            int space = 200;
            int width = 50;
            int height = rand.nextInt(getHeight() - 250);
            column1 = new Rectangle(getWidth() + 50, 0, width, height);
            column2 = new Rectangle(getWidth() + 50, height + space, width, getHeight()- height - space );
            columns.add(column1);
            columns.add(column2);
        }

        for (Rectangle column : columns) {
            column.x -= 1;

            if (column.intersects(bird)) {
                gameOver = true;
                clip.stop();
            }
            
            if(column1.x - 200 == positionX) {
            	score+= 0.5;
            	in = FlappyBird.class.getResourceAsStream("/Music/across.wav");
    	        try {
    				audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
    		        clip4 = AudioSystem.getClip();
    		        audioInputStream.mark(Integer.MAX_VALUE);
    		        clip4.open(audioInputStream);
    			} catch (UnsupportedAudioFileException | IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			} catch (LineUnavailableException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    	        FloatControl gainControl = (FloatControl) clip4.getControl(FloatControl.Type.MASTER_GAIN);
    	        gainControl.setValue(-20.0f);
    	        clip4.start();
            }

	  
        }

        for (int i = 0; i < columns.size(); i++) {
            Rectangle column = columns.get(i);
            column.x -= 1;

            if (column.x + column.width < 0) {
                columns.remove(column);
                i--;
            }

            if (column.intersects(bird)) {
                gameOver = true;
                clip.stop();
            }
        }
       	try {
    				conn = connectz.getJDBCConnection();
    				String sql = "SELECT * FROM `player` WHERE idplayer = ? AND password = ?";
    				pst = conn.prepareStatement(sql);
    				  pst.setString(1, username);
    	                pst.setString(2, password);
                    ResultSet resultSet = pst.executeQuery();
                    if (resultSet.next()) {
                        currentScore = resultSet.getString("score");
                        if (score > Integer.parseInt(currentScore)) {
                            pst = conn.prepareStatement("UPDATE `player` SET `score` = ? WHERE idplayer = ? AND password = ?");
                            pst.setString(1, String.valueOf((int) score));
                            pst.setString(2, username);
                            pst.setString(3, password);
                            int ret = pst.executeUpdate();
                        }
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
    public static void main(String[] args) {
    	 if (conn == null) {
    	        JOptionPane.showMessageDialog(null, "Bạn không thể chơi nếu chưa đăng nhập");
    	        return;
    	    }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		int currentX = e.getX();
		int currentY = e.getY();
		if(gameOver && (currentX >= 177 && currentX <= 245) && (currentY >= 300 && currentY <= 350)) {
			frame.dispose();
			FlappyBird game = new FlappyBird();
	        game.startGame();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
