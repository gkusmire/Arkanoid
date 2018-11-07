package actions;

import javax.swing.Timer;

import enumTypes.HorizontalDirection;

import view.Window;
import model.Model;

public class LeftKeyPressedEvent implements ExtendedActionEvent{
	public void go(Model model, Window view, Timer timer){
		model.setDirPlatform(HorizontalDirection.LEFT);
	}

}