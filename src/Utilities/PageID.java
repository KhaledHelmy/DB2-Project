package Utilities;

public class PageID {
	private String tableName;
	private String pageNum;
	
	public PageID(String pageNum, String tableName) {
		this.pageNum = pageNum;
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageName() {
		return tableName + "_" + pageNum + ".csv";
	}
}
