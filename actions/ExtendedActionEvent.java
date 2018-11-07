package actions;

import javax.swing.Timer;

import model.Model;
import view.Window;

public interface ExtendedActionEvent {
	public void go(Model model, Window view, Timer timer);
}