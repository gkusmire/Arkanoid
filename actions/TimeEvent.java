package actions;


import javax.swing.Timer;

import view.Window;
import model.Model;

public class TimeEvent implements ExtendedActionEvent{
	public void go(final Model model, final Window view, Timer timer)
	{
		model.moveObjects();
	//	System.out.print("aaaa\n");
		//System.out.print(model.getObjectPos().balls.get(0).getXpos() + "\n");
		view.refresh();
	}
}