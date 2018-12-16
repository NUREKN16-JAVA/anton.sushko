package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DaoFactory;
import ua.nure.kh.anton_sushko.usermanagement.db.UserDao;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;

public class MainFrame extends JFrame {
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel  contentPanel;
	private JPanel browsePanel;
	private AddPanel addPanel;
	private UserDao dao;
	private EditPanel editPanel;
	private DeletePanel deletePanel;
	private DetailsPanel detailsPanel;


	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	

	public UserDao getDao() {
		return dao;
	}


	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_nanagement"));
		this.setContentPane(getContentPanel());
		
		
	}

	private Container getContentPanel() {
		if(contentPanel==null) {
			contentPanel= new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(),BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if(browsePanel==null) {
			browsePanel= new BrowsePanel(this);
		}
        ((BrowsePanel)browsePanel).initTable();
		return browsePanel;
	}

	public static void main(String[] args) {
	MainFrame frame = new MainFrame();
	frame.setVisible(true);
	

	}

	public void showAddPanel() {
	showPanel(getAddPanel());	
		
	}

	public void showBrowsePanel() {
		showPanel(getBrowsePanel());
	}

	private void showPanel(JPanel panel) {
		
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		if(addPanel== null) {
		addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	public DeletePanel getDeletePanel() {
		if (deletePanel == null) {
			deletePanel = new DeletePanel(this);
			
		}
		return deletePanel;
		
	}
	
	public void showDeletePanel(User user) {
		DeletePanel deletePanel = getDeletePanel();
        deletePanel.showDeletePanel(user);
        this.showPanel(getDeletePanel());
	}


	private EditPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this);
			
		}
		return editPanel;
	}
	
	public void showEditPanel(User user) {
		showPanel(getEditPanel());
		
	}
	public void showDetailsPanel(User user) {
		DetailsPanel detailsPanel = getDetailsPanel();
		detailsPanel.showDetailsPanel(user);
        this.showPanel(getDetailsPanel());
}



	public DetailsPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new DetailsPanel(this);
			
		}
		return detailsPanel;
	}

}