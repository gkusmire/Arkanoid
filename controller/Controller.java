package controller;


import java.util.concurrent.BlockingQueue;
import javax.swing.Timer;
import view.Window;
import actions.ExtendedActionEvent;
import model.Model;

public class Controller {
	public Controller(Window view, Model model, 
			BlockingQueue<ExtendedActionEvent> queue, Timer timer) throws Exception{
		checkIfCorrectArgs(view, model, queue, timer);
		this.view = view;
		this.model = model;
		blockingQueue = queue;
		this.timer = timer;
	}
	public void readEvent(){
		while(true){
			try
			{
				ExtendedActionEvent event = blockingQueue.take();
				event.go(model, view, timer);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	private void checkIfCorrectArgs(Window view, Model model, 
			BlockingQueue<ExtendedActionEvent> queue, Timer timer) throws Exception{
		if(view == null || model == null || queue == null || timer == null){
			throw new Exception("ERROR: Incorrect arguments");
		}
	}
	private BlockingQueue<ExtendedActionEvent> blockingQueue;
	private Model model;
	private Window view;
	private Timer timer;
}