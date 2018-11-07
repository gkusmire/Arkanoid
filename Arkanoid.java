import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.Timer;
import controller.Controller;
import model.Model;
import model.GameBoardElements;
import view.Window;
import actions.ExtendedActionEvent;
import actions.TimeEvent;

public class Arkanoid {	
	public static void main(String[] args)
	{
		final BlockingQueue<ExtendedActionEvent> blockingQueue = 
				new LinkedBlockingQueue<ExtendedActionEvent>();
		
		final GameBoardElements objects = new GameBoardElements();
		try{
			Model model = new Model(objects, blockingQueue);
			Window view = new Window(model.getObjectPos(), blockingQueue);
			final Timer timer = newTimer(17, blockingQueue);
			Controller controller = new Controller(view, model, blockingQueue, timer);
			controller.readEvent();
		} catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	private static Timer newTimer(int time, final BlockingQueue<ExtendedActionEvent> blockingQueue)
	{
		return new Timer(time, new ActionListener()
		{
			public void actionPerformed(ActionEvent event){
				try
				{
					blockingQueue.put((ExtendedActionEvent)new TimeEvent());
				}
				catch(Exception e)
				{
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});
	}
}