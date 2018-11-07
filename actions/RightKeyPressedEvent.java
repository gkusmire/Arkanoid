package actions;


import javax.swing.Timer;

import enumTypes.HorizontalDirection;

import view.Window;
import model.Model;

public class RightKeyPressedEvent implements ExtendedActionEvent{
	public void go(Model model, Window view, Timer timer){
		model.setDirPlatform(HorizontalDirection.RIGHT);
	}
}