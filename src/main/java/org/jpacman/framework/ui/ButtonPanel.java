package org.jpacman.framework.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A panel containing the buttons for controlling
 * JPacman.
 * 
 * @author Arie van Deursen, TU Delft, Jan 21, 2012
 */
public class ButtonPanel extends JPanel {
	
	private static final long serialVersionUID = 5078677478811886963L;

	private IButtonEvents listener;
	
	private final JFrame parent;
	
	/**
	 * Set the listener capable of exercising the
	 * requested events.
	 * @param l 
	 */
	public void setListener(IButtonEvents l) {
		listener = l;
	}
	
    /**
     * @return True iff precisely one of the start/stop buttons is enabled.
     */
    protected boolean invariant() {
    	return 
    		startButton.isEnabled() ^ stopButton.isEnabled();
    }
	
	private JButton startButton;
	private JButton stopButton;
	
	/**
	 * Create a new panel, given the parent's containing frame.
	 * @param parent Containing frame.
	 */
    public ButtonPanel(JFrame parent) {
    	this.parent = parent;
    }
    
    /**
     * Actually create the buttons.
     */
    public void initialize() {    	
    	startButton = new JButton("Start");
    	stopButton = new JButton("Stop");
    	initializeStartButton();
    	initializeStopButton();
    	
    	JButton exitButton = createExitButton();
    	    	
        setName("jpacman.buttonPanel");
        add(startButton);
        add(stopButton);
        add(exitButton);
        
        final int numberOfButtons = 3;
        final int buttonWidth = 80;
        final int buttonHeight = 45;
        setSize(buttonWidth * numberOfButtons, buttonHeight);
    }
    
    /**
     * Create the start button.
     */
    protected void initializeStartButton() {
    	startButton.addActionListener(new ActionListener() {
    		@Override
			public void actionPerformed(ActionEvent e) {
    			assert listener != null : "PRE: Listeners initialized.";
    			assert invariant();
    			listener.start();
    			// ensure the full window has the focus.
    			parent.requestFocusInWindow();
    			stopButton.setEnabled(true);
    			startButton.setEnabled(false);
    			assert invariant();
    		}
    	});
    	startButton.setName("jpacman.start");
    	startButton.requestFocusInWindow();
     }
    
    /**
     * Create the stop button.
     */
    protected void initializeStopButton() {
     	stopButton.setEnabled(false);
    	stopButton.addActionListener(new ActionListener() {
    		@Override
			public void actionPerformed(ActionEvent e) {
    			assert listener != null : "PRE: Listeners initialized.";
    			assert invariant();
    			listener.stop();
    			// ensure the full window has the focus.
    			parent.requestFocusInWindow();
    			startButton.setEnabled(true);
    			stopButton.setEnabled(false);
    			assert invariant();
    		}
    	});
    	stopButton.setName("jpacman.stop");
    	//stopButton.requestFocusInWindow();
    }
    
    /**
     * @return A new button to exit the game.
     */
    protected JButton createExitButton() {
    	JButton exitButton = new JButton("Exit");
    	exitButton.addActionListener(new ActionListener() {
    		@Override
			public void actionPerformed(ActionEvent e) {
    			listener.exit();
    		}
    	});
    	return exitButton;
    }
}
