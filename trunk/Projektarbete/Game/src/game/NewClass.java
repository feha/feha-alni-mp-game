package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewClass extends JFrame {
	private static final long serialVersionUID = 1L;

	public NewClass() {
		//super("Terraworld GUI Testing Zone");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setResizable(false);

		setContentPane(new TestGamePanel());

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
                new NewClass();
			}
		});
	}
}

class TestGamePanel extends JPanel {
	//private static final long serialVersionUID = 1L;

	TestGraphicsPanel graphicsPanel;
	//GridBagConstraints c;

	public TestGamePanel() {
		//super(new GridBagLayout());
		//setPreferredSize(new Dimension(0, 0));
		//setFocusable(true);
		//requestFocusInWindow();

		//c = new GridBagConstraints();

		//c.gridx = 1;
		//c.gridy = 1;
		graphicsPanel = new TestGraphicsPanel();
		add(graphicsPanel);

		//c.gridx = 1;
		//c.gridy = 2;
//		JTextPane textPane = new JTextPane();
//		textPane.setPreferredSize(new Dimension(500, 500));
//		textPane.setEditable(false);
//		JScrollPane scrollPane = new JScrollPane(textPane);
//		scrollPane.setPreferredSize(new Dimension(500, 100));
//		add(scrollPane, c);

		//c.gridx = 1;
		//c.gridy = 3;
//		JTextField textField = new JTextField(10);
//		add(textField, c);


	}
}

class TestGraphicsPanel extends JPanel {
	//private static final long serialVersionUID = 1L;

	//int curX;
	//int curY;
	//TestGraphicsPanel gp;

	public TestGraphicsPanel() {
		//super();
		//setBorder(null);
		//setPreferredSize(new Dimension(547, 399));
		//gp = this;

		Action downAction = new DownAction("Down"/*, "Down"*/);
		Action upAction = new UpAction("Up"/*, "Up"*/);
		Action rightAction = new RightAction("Right"/*, "Right"*/);
		Action leftAction = new LeftAction("Left"/*, "Left"*/);

		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), downAction.getValue(Action.NAME));
		getActionMap().put(downAction.getValue(Action.NAME), downAction);

		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), upAction.getValue(Action.NAME));
		getActionMap().put(upAction.getValue(Action.NAME), upAction);

		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), rightAction.getValue(Action.NAME));
		getActionMap().put(rightAction.getValue(Action.NAME), rightAction);

		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), leftAction.getValue(Action.NAME));
		getActionMap().put(leftAction.getValue(Action.NAME), leftAction);
	}

	class DownAction extends AbstractAction {
		//private static final long serialVersionUID = 1L;

		public DownAction(String text/*, String desc*/) {
			super(text);
			//putValue(SHORT_DESCRIPTION, desc);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Down");
		}
	}

	class UpAction extends AbstractAction {
		//private static final long serialVersionUID = 1L;

		public UpAction(String text/*, String desc*/) {
			super(text);
			//putValue(SHORT_DESCRIPTION, desc);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Up");
		}
	}

	class RightAction extends AbstractAction {
		//private static final long serialVersionUID = 1L;

		public RightAction(String text/*, String desc*/) {
			super(text);
			//putValue(SHORT_DESCRIPTION, desc);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Right");
		}
	}

	class LeftAction extends AbstractAction {
		//private static final long serialVersionUID = 1L;

		public LeftAction(String text/*, String desc*/) {
			super(text);
			//putValue(SHORT_DESCRIPTION, desc);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Left");
		}
	}
}