package Utilities;

import java.util.List;

import jdbm.btree.BTree;

public class OurBPlusTree extends BTree{
	public static OurBPlusTree ourCreate(List<Record> records){
		throw new UnsupportedOperationException();
	}
	
	public static OurBPlusTree ourCreate(){
		throw new UnsupportedOperationException();
	}
	
	public Object ourInsert(){
		throw new UnsupportedOperationException();
	}
	
	public void ourDelete(){
		throw new UnsupportedOperationException();
	}
	
	public List<Record> ourSearch(String value){
		throw new UnsupportedOperationException();
	}
}
