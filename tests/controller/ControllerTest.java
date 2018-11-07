package tests.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.Timer;

import model.Model;
import model.GameBoardElements;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import view.Window;
import actions.EndOfGameEvent;
import actions.ExtendedActionEvent;
import actions.LeftKeyPressedEvent;
import actions.ResetEvent;
import actions.RightKeyPressedEvent;
import actions.StartGameEvent;
import actions.StopEvent;
import actions.TimeEvent;
import controller.Controller;

public class ControllerTest {
	
	 @Before
	    public void setUp() {
		 	queue = new LinkedBlockingQueue<ExtendedActionEvent>();
		 	view = Mockito.mock(Window.class);
		 	model = Mockito.mock(Model.class);
		 	timer = Mockito.mock(Timer.class);
	        try {
				controller = new Controller(view, model, queue, timer);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    } 
	
	@Test (expected = Exception.class)
	public void nullArg1Test() throws Exception{
		new Controller(null, model, queue, timer);
	}
	
	@Test (expected = Exception.class)
	public void nullArg2Test() throws Exception{
		new Controller(view, null, queue, timer);
	}
	
	@Test (expected = Exception.class)
	public void nullArg3Test() throws Exception{
		new Controller(view, model, null, timer);
	}
	
	@Test (expected = Exception.class)
	public void nullArg4Test() throws Exception{
		new Controller(view, model, queue, null);
	}
	
	@Test (expected = Exception.class)
	public void CheckIfControllerCallMoveObjectsMethod() throws Exception{
		Mockito.doThrow(new Exception()).when(model).moveObjects();
		queue.put(new TimeEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfControllerCallRefreshMetod() throws Exception{
		Mockito.doThrow(new Exception()).when(view).refresh();
		queue.put(new TimeEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfControllerCallResetMethod() throws Exception{
		Mockito.doThrow(new Exception()).when(model).reset();
		queue.put(new ResetEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfResetEventCallTimerStop() throws Exception{
		Mockito.doThrow(new Exception()).when(timer).stop();
		queue.put(new ResetEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfResetEventCallSetNewDataReference() throws Exception{
		Mockito.doThrow(new Exception()).when(view).setNewDataReference(objects);
		queue.put(new ResetEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfResetEventCallRefresh() throws Exception{
		Mockito.doThrow(new Exception()).when(view).refresh();
		queue.put(new ResetEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfRightKeyEventCallSetDirPlatform() throws Exception{
		Mockito.doThrow(new Exception()).when(model).setDirPlatform(null);
		queue.put(new RightKeyPressedEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfLeftKeyEventCallSetDirPlatform() throws Exception{
		Mockito.doThrow(new Exception()).when(model).setDirPlatform(null);
		queue.put(new LeftKeyPressedEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfStartGameEventCallStartTimer() throws Exception{
		Mockito.doThrow(new Exception()).when(timer).start();
		queue.put(new StartGameEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfStopEventCallStopTimer() throws Exception{
		Mockito.doThrow(new Exception()).when(timer).stop();
		queue.put(new StopEvent());
		controller.readEvent();
	}
	
	@Test (expected = Exception.class)
	public void CheckIfEndOfGameEventCallReset() throws Exception{
		Mockito.doThrow(new Exception()).when(model).reset();
		queue.put(new EndOfGameEvent());
		controller.readEvent();
	}
	
	private Controller controller;
	
	@Mock
	private Timer timer;
	
	@Mock
	private Model model;
	
	@Mock
	private Window view;
	
	private BlockingQueue<ExtendedActionEvent> queue;
	
	@Mock
	private GameBoardElements objects;

}
