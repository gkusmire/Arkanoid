package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;

import enumTypes.Keys;
import model.GameBoardElements;
import actions.ExtendedActionEvent;
import actions.LeftKeyPressedEvent;
import actions.ResetEvent;
import actions.RightKeyPressedEvent;
import actions.StartGameEvent;
import actions.StopEvent;
import actions.StopMovingPlatform;

public class Window {
	
	public Window(final GameBoardElements objPositions, BlockingQueue<ExtendedActionEvent> queue) {
		blockingQueue = queue;
		createFrame(objPositions);
	}
	public void refresh()
	{
		frame.repaint();
	}
	public void setNewDataReference(GameBoardElements objPos) {
		frame.setNewDataReference(objPos);
	}
	private void createFrame(final GameBoardElements obj){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame = new MainFrame(obj);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	private class MainFrame extends JFrame {
		private static final long serialVersionUID = -8838992264311872505L;
		public MainFrame(GameBoardElements obj){
			
			super("Arkanoid");
			setFrameParameters(obj);
			createButtons(obj.mapSize.getWidth());
			try{
				createNewPanel(obj);
			} catch(IOException e){
				System.err.println("Blad odczytu obrazka");
				System.exit(ERROR);
			}
			addButtonsToPanel(panel);
			isLeftKeyPressed = false;
			isRightKeyPressed = false;
		}
		public void setNewDataReference(GameBoardElements objPos) {
			panel.setNewDataReference(objPos);
		}
		private void createButtons(int width){
			startButton = addNewStartButton(width, 40);
			resetButton = addNewResetButton(width, 85);
			stopButton = addNewStopButton(width, 130);
		}
		private void addButtonsToPanel(DrawingPanel panel){
			panel.add(startButton);
			panel.add(resetButton);
			panel.add(stopButton);
		}
		private void setFrameParameters(GameBoardElements obj){
			setLocation(100, 100);
			setSize(obj.mapSize.getWidth()*50+200,obj.mapSize.getHeight()*50+20);
			setBackground(Color.decode("#000080"));
			setResizable(false);
		}
		private void createNewPanel(GameBoardElements obj) throws IOException{
			panel = new DrawingPanel("block.png",  obj);
			panel.setLayout(null);
			add(panel);
		}
		private JButton addNewResetButton(int mapWidth, int yPos){
			JButton b = new JButton("Reset Game");
			b.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try {
							blockingQueue.put((ExtendedActionEvent)new ResetEvent());
						} catch (InterruptedException e1) {
							System.err.print("BLAD dodania elementu do kolejki blokującej");
							System.exit(ERROR);
						}
					}		
			});
			b.addKeyListener(this.addKeyListener());
			b.setBounds(mapWidth*50 + 25, yPos, 150, 40);
			return b;
		}
		private JButton addNewStopButton(int mapWidth, int yPos){
			JButton b = new JButton("PAUSE");
			b.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try {
							blockingQueue.put((ExtendedActionEvent)new StopEvent());
						} catch (InterruptedException e1) {
							System.err.print("BLAD dodania elementu do kolejki blokującej");
							System.exit(ERROR);
						}
					}		
			});
			b.addKeyListener(this.addKeyListener());
			b.setBounds(mapWidth*50 + 25, yPos, 150, 40);
			return b;
		}
		private JButton addNewStartButton(int mapWidth, int yPos){
			JButton b = new JButton("START");
			b.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try {
							blockingQueue.put((ExtendedActionEvent)new StartGameEvent());
						} catch (InterruptedException e1) {
							System.err.print("BLAD dodania elementu do kolejki blokującej");
							System.exit(ERROR);
						}
					}		
			});
			b.addKeyListener(this.addKeyListener());
			b.setBounds(mapWidth*50 + 25, yPos, 150, 40);
			return b;
		}
		private KeyListener addKeyListener(){
			return new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}
				public void keyReleased(KeyEvent e) {
					try {
						if(e.getKeyCode() == Keys.LEFT_KEY.getValue()){
							blockingQueue.put(new StopMovingPlatform());
							isLeftKeyPressed = false;
							if(isRightKeyPressed == true){
								blockingQueue.put(new RightKeyPressedEvent());
							}
						}
						if(e.getKeyCode() == Keys.RIGHT_KEY.getValue()) {
							blockingQueue.put(new StopMovingPlatform());
							isRightKeyPressed = false;
							if(isLeftKeyPressed == true){
								blockingQueue.put(new LeftKeyPressedEvent());
							}
						}
					} catch (InterruptedException e1) {
						System.err.print("BLAD dodania elementu do kolejki blokującej");
						System.exit(ERROR);
					}
				}
				
				public void keyPressed(KeyEvent e) {
					try {
							if(e.getKeyCode() == Keys.LEFT_KEY.getValue()){
								blockingQueue.put(new LeftKeyPressedEvent());
								isLeftKeyPressed = true;
							}
							if(e.getKeyCode() == Keys.RIGHT_KEY.getValue()) {
								blockingQueue.put(new RightKeyPressedEvent());
								isRightKeyPressed = true;
							}
							if(e.getKeyChar() == Keys.RESET_KEY.getValue()){
								blockingQueue.put(new ResetEvent());
							}
							if(e.getKeyChar() == Keys.START_KEY.getValue()){
								blockingQueue.put(new StartGameEvent());
							}
							if(e.getKeyChar() == Keys.PAUSE_KEY.getValue()){
								blockingQueue.put(new StopEvent());
							}
					} catch (InterruptedException e1) {
						System.err.print("BLAD dodania elementu do kolejki blokującej");
						System.exit(ERROR);
					}
					
				}
			};
		}
	
		private JButton resetButton, stopButton, startButton;
		private DrawingPanel panel;
		private boolean isLeftKeyPressed, isRightKeyPressed;
	}
	private MainFrame frame;
	private BlockingQueue<ExtendedActionEvent> blockingQueue;
}