 
package za.co.ibear.code.gizmo.ui.part;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;

import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.GridData;

import za.co.ibear.code.gizmo.reverse.engineer.ReverseEngineer;

public class ReverseEngineerPart implements IProgressMonitor {
	
	@Inject UISynchronize sync;

	private CTabFolder folder;
	private CTabItem item;
	private StyledText log;
	private ProgressBar progressBar;
	
	private ReverseEngineer re;
	
	@Inject
	public ReverseEngineerPart() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		progressBar = new ProgressBar(parent, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		folder = new CTabFolder(parent, SWT.BOTTOM);
		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		folder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		item = new CTabItem(folder, SWT.NONE);
		item.setText("Reverse Log");
		
		log = new StyledText(folder, SWT.BORDER | SWT.V_SCROLL);
		log.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.stateMask == SWT.CTRL && e.keyCode == 97) { 
					log.selectAll();
				}
			}
		});
		log.setText("Idle:");
		log.setData("style","font-style: italic;color: gray");
		log.setEditable(false);
		item.setControl(log);
		
		setEngineer(new ReverseEngineer());
		
		re.addPropertyChangeListener("file",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				try {
					re.process();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		re.addPropertyChangeListener("input",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				log.setText(re.getOutput());
			}
		});
	}
	
	@PreDestroy
	public void preDestroy() {
	}
	
	
	@Focus
	public void onFocus() {
		folder.setSelection(0);
	}
	
	
	@Persist
	public void save() {
	}

	@Override
	public void beginTask(String name, int totalWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalWorked(double work) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTaskName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subTask(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worked(int work) {
		// TODO Auto-generated method stub
		
	}

	public ReverseEngineer getEngineer() {
		return re;
	}

	public void setEngineer(ReverseEngineer re) {
		this.re = re;
	}
	
}