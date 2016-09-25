package za.co.ibear.timer.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import za.co.ibear.property.model.PropertyModel;

public class ServiceStatus extends PropertyModel {

	private SimpleDateFormat stampFormat = new SimpleDateFormat("yyyy-MM-dd (hh:mm:ss)");
	private Date startTime;
	private Date lastAction;
	private String message;
	private String logMessage;
	private boolean active;

	private int INTERVAL = 5000;
	private int BATCH_COUNT = 5;

	private boolean clearScheduled = false;

	public ServiceStatus(Date startTime, Date lastAction, String message) {
		super();
		this.startTime = startTime;
		this.lastAction = lastAction;
		this.message = message;
	}

	public void acivate(final UISynchronize sync,final EModelService modelService,final MApplication application,final EPartService partService) {

		Job job = new Job("AUTOMATION_TIMER") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {

				if(clearScheduled) {
					firePropertyChange("error",null,null);
				}

				firePropertyChange("logMail",this,null);
				firePropertyChange("hourlyRun",this,null);

				StatusMessage message;
				Set<StatusMessage> jobMessages = new HashSet<StatusMessage>();
				setMessage("Job scheduled.");
				monitor.internalWorked(0);
				try {
					Thread.sleep(INTERVAL);
				} catch (Exception e) {
					firePropertyChange("error",e,null);	
				}
				if(monitor.isCanceled()) {
					monitor.internalWorked(99);
					return Status.OK_STATUS;
				}
				jobMessages = new HashSet<StatusMessage>();
				int current = 1;
				monitor.beginTask("Executing automation batches.", BATCH_COUNT+3);

				try {	//TODO START	1. DO TIMED TASK

					monitor.subTask("Doing task 1.");
					monitor.worked(current++);

					message = new StatusMessage(stampFormat.format(new Date().getTime()),"TASK","Transmit out-bound files.");
					firePropertyChange("push",message,null);

					Thread.sleep(3000);

					firePropertyChange("pop",message,null);

				} catch (final Exception e) {
					firePropertyChange("error",e,null);
				}	//TODO END	1. DO TIMED TASK

				for(StatusMessage m:jobMessages) {
					firePropertyChange("pop",m,null);
				}
				monitor.done();
				if(monitor.isCanceled()) {
					monitor.internalWorked(99);
				}
				return Status.OK_STATUS;
			}
		};

		IJobManager manager = Job.getJobManager();
		Object widget = partService.findPart("za.co.ibear.timer.service.part.serviceStatusPart").getObject();
		final IProgressMonitor progress = (IProgressMonitor) widget;
		ProgressProvider provider = new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return progress;
			}
		};
		manager.setProgressProvider(provider);

		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					setMessage("Job completed successfully.");
				}
			}
		});
		job.schedule(); 	
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getLastAction() {
		return lastAction;
	}
	public void setLastAction(Date lastAction) {
		this.lastAction = lastAction;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "ServerStatus [startTime=" + startTime + ", lastAction="	+ lastAction + ", message=" + message + "]";
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
		firePropertyChange("logMessage",this.logMessage,null);
	}

	public void setLogMessage() {
		this.logMessage = "Ftp Not Connected:";
		firePropertyChange("logMessage",this.logMessage,null);
	}

	public boolean isClearScheduled() {
		return clearScheduled;
	}

	public void setClearScheduled(boolean clearScheduled) {
		this.clearScheduled = clearScheduled;
	}

}
