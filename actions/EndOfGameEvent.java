package actions;

import javax.swing.Timer;
import model.Model;
import view.Window;

public class EndOfGameEvent implements ExtendedActionEvent{
	public void go(Model model, Window view, Timer timer){
		model.reset();
	}
}