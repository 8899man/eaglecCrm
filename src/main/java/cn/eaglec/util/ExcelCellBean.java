package cn.eaglec.util;

public class ExcelCellBean {
	private String title;
	
	private Object value;
	
	private int seq;

	public ExcelCellBean() {
	}
	
	public ExcelCellBean(String title, Object value, int seq) {
		super();
		this.title = title;
		this.value = value;
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
