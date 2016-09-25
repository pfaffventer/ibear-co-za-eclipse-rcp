package za.co.ibear.swt.control.document;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;

public class UnitDocument extends Composite {

	private SashForm documentSash = null;
	private Composite header = null;
	private Composite detail = null;
	private CTabFolder detailFolder = null;
	
	public UnitDocument(Composite parent, int style, int[] layoutWeight) throws Exception {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		documentSash = new SashForm(this, SWT.VERTICAL);
		
		header = new Composite(documentSash, SWT.NONE);
		header.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		detail = new Composite(documentSash, SWT.NONE);
		detail.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		detailFolder = new CTabFolder(detail, SWT.BORDER | SWT.BOTTOM);
		detailFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		documentSash.setWeights(layoutWeight);

	}

	public Composite getHeader() {
		return header;
	}

	public void setHeader(Composite header) {
		this.header = header;
	}

	public Composite getDetail() {
		return detail;
	}

	public void setDetail(Composite detail) {
		this.detail = detail;
	}

	public CTabFolder getDetailFolder() {
		return detailFolder;
	}

	public void setDetailFolder(CTabFolder detailFolder) {
		this.detailFolder = detailFolder;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
