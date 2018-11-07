package actions;

import javax.swing.Timer;

import view.Window;
import model.Model;

public class ResetEvent implements ExtendedActionEvent{
	public void go(Model model, Window view, Timer timer){
		timer.stop();
		model.reset();
		view.setNewDataReference(model.getObjectPos());
		view.refresh();
	}

}