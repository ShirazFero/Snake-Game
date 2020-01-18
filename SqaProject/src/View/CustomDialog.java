package View;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

class CustomDialog {
	private JFrame frame;
    private JDialog dialog;
    
    public CustomDialog(JFrame frame) {
    	this.frame = frame;
    }
    
    public void cretaUI(JPanel pan) {
    	dialog = new JDialog(frame);
    	dialog.setMinimumSize(new Dimension(900, 500));
    	dialog.add(pan);
    	dialog.setLocationRelativeTo(null);
    	dialog.setTitle("Question");
    	
    	WindowListener exitListener = new WindowAdapter() {
    		@Override
    		public void windowClosing(WindowEvent e) {
    			frame.setEnabled(true);
    		}
    	};       
    	dialog.addWindowListener(exitListener);
    	dialog.pack();
    	dialog.setVisible(true);
    }
    
    public void cretaUI2(JPanel pan,int width,int height) {
    	dialog = new JDialog(frame);
    	dialog.setMinimumSize(new Dimension(width, height));
    	dialog.add(pan);
    	dialog.setLocationRelativeTo(null);
    	dialog.setTitle("Question");
    	
    	WindowListener exitListener = new WindowAdapter() {
    		@Override
    		public void windowClosing(WindowEvent e) {
    			frame.setEnabled(true);
    		}
    	};
    	dialog.addWindowListener(exitListener);
    	dialog.pack();
    	dialog.setVisible(true);
    }
    
    public void dispose(){
    	this.dialog.dispose();
    }
}