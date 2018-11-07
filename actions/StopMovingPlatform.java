package actions;

import javax.swing.Timer;

import model.Model;
import view.Window;

public class StopMovingPlatform implements ExtendedActionEvent{
	public void go(Model model, Window view, Timer timer){
		model.stopMovingPlatform();
	}
}
