package Utilities;
public class Column {
	private String tableName, colName, colType, reference;
	private boolean key, index;

	/*
	 * needs to add a type class using reflection and may be a table and column
	 * for references
	 */
	public Column(String col) {
		String temp[] = col.trim().split(",");
		setTableName(temp[0]);
		setColName(temp[1]);
		setColType(temp[1]);
		setKey(Boolean.getBoolean(temp[3]));
		setIndex(Boolean.parseBoolean(temp[4]));
		if(temp.length == 5){
			setReference("");
		}
		else{
			setReference(temp[5]);
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String toMeta() {
		return tableName + ", " + colName + ", " + colType + ", "
				+ Boolean.toString(key) + ", " + Boolean.toString(index) + ", "
				+ reference;
	}
}
