package org.jpacman.framework.ui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jpacman.framework.controller.IController;
import org.jpacman.framework.controller.RandomGhostMover;
import org.jpacman.framework.factory.DefaultGameFactory;
import org.jpacman.framework.factory.FactoryException;
import org.jpacman.framework.factory.MapParser;
import org.jpacman.framework.model.Game;
import org.jpacman.framework.view.Animator;
import org.jpacman.framework.view.BoardView;

/**
 * The main user interface for jpacman.
 * 
 * @author Arie van Deursen, TU Delft, Jan 14, 2012
 */
public class MainUI extends JFrame implements Observer, IDisposable {
	
    /**
     * Universal version ID for serialization.
     */
    static final long serialVersionUID = -59470379321937183L;

    /**
     * The underlying game.
     */
	private transient Game theGame;

	/**
	 * Mapping of UI events to model actions.
	 */
	private transient PacmanInteraction pi;

	/**
	 * The main window components.
	 */
	private PointsPanel points;
	private BoardView boardView;
	private ButtonPanel buttonPanel;
	
	/**
	 * Controllers that will trigger certain events.
	 */
	private transient IController ghostController;
	private transient Animator animator;
	

	/**
	 * Create all the ui components and attach appropriate
	 * listeners.
	 * @throws FactoryException If resources for game can't be loaded.
	 */
    private void createUI() throws FactoryException {
    	assert theGame != null;
    	assert ghostController != null;
    	
    	buttonPanel = new ButtonPanel(this);
    	buttonPanel.initialize();
    	
    	pi = new PacmanInteraction(this, theGame);
    	pi.addController(ghostController);   	
    	buttonPanel.setListener(pi);
        this.addKeyListener(new PacmanKeyListener(pi));
    	
      	boardView = createBoardView();
      	animator = new Animator(boardView);
      	pi.addController(animator);
      	
    	points = new PointsPanel();
    	points.initialize(theGame.getPointManager());
    	theGame.attach(points);
    	
    	JPanel mainGrid = new JPanel();
    	mainGrid.setLayout(new BorderLayout());
    	mainGrid.setName("jpacman.topdown");
    	mainGrid.add(points, BorderLayout.NORTH);
    	mainGrid.add(boardView, BorderLayout.CENTER);
    	mainGrid.add(buttonPanel, BorderLayout.SOUTH);
 
        getContentPane().add(mainGrid);
        
        int width = Math.max(boardView.windowWidth(), buttonPanel.getWidth());
        int height = boardView.windowHeight() + buttonPanel.getHeight();
        setSize(width, height);
        setGridSize();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setName("jpacman.main");
        setTitle("JPacman");  
    }
    
    /**
     * Establish the appropriate size of the main window,
     * based on the sizes of the underlying components.
     */
    private void setGridSize() {
        int width = Math.max(boardView.windowWidth(), 
        		buttonPanel.getWidth());
        
        int height = 
        		points.getHeight()
        		+ boardView.windowHeight()
        		+ buttonPanel.getHeight();
        
        setSize(width, height);
    }


    /**
     * The state of the game has changed.
     * Reset button enabling depending on the state.
     * @param o Ignored
     * @param arg Ignored
     */
	@Override
	public void update(Observable o, Object arg) {
    	boardView.repaint();
    }

	/**
	 * Create the controllers and user interface elements.
	 * @throws FactoryException If required resources can't be loaded.
	 */
    public void initialize() throws FactoryException {
        theGame = createModel();
        theGame.attach(this);
        ghostController = new RandomGhostMover(theGame);
      	createUI();   	
    }
    	
    /**
     * Actually start the the controllers, and show the UI.
     */
	public void start()  {
		animator.start();
        setVisible(true);
        requestFocus();
	}
	
	private BoardView createBoardView() throws FactoryException {
		return new BoardView(theGame.getBoard());
	}
	
	private Game createModel() throws FactoryException {
		MapParser parser = new MapParser(
				new DefaultGameFactory());
		return parser.parseFromFile("board.txt");
	}
	
	/**
	 * @return The mapping between keyboard events and model events.
	 */
	public IKeyboardEvents eventHandler() {
		return pi;
	}
		
	/**
	 * Main starting point of the JPacman game.
	 * @param args Ignored
	 * @throws FactoryException If reading game map fails.
	 */
	public static void main(String[] args) throws FactoryException {		
		MainUI ui = new MainUI();
		ui.initialize();
		ui.start();		
	}
}
