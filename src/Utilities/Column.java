package Utilities;

import java.util.ArrayList;
import java.util.List;

public class Column {
	private String tableName, colName, colType, reference, referencedTableName,
			referencedColumnName;
	private List<String> referenceBack = new ArrayList<String>();

	public String getReferencedColumnName() {
		return referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

	public String getReferencedTableName() {
		return referencedTableName;
	}

	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	private boolean key, index, hasReference;

	/*
	 * needs to add a type class using reflection and may be a table and column
	 * for references
	 */
	public Column(String col) {
		String temp[] = col.trim().split(",");
		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i].trim();
		}
		setTableName(temp[0]);
		setColName(temp[1]);
		setColType(temp[2]);
		setKey(Boolean.parseBoolean(temp[3]));
		setIndex(Boolean.parseBoolean(temp[4]));
		if (temp.length == 6 && !temp[5].equalsIgnoreCase("null")) {
			setReference(temp[5]);
			hasReference = true;
		} else {
			setReference("");
			hasReference = false;
		}
	}

	public boolean HasReference() {
		return hasReference;
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
		String[] referenceSplit = reference.split("\\.");
		if (referenceSplit.length == 2) {
			setReferencedTableName(referenceSplit[0]);
			setReferencedColumnName(referenceSplit[1]);
		}
		if (reference.isEmpty())
			this.reference = "null";
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

	public void addReferenceBack(String reference) {
		referenceBack.add(reference);
	}

	public List<String> getReferenceBack() {
		return referenceBack;
	}
}
