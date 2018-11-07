package actions;

import javax.swing.Timer;

import view.Window;
import model.Model;

public class StartGameEvent implements ExtendedActionEvent{
	public void go(final Model model, final Window view, Timer timer)
	{
		timer.start();
	}
}