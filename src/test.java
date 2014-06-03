import java.util.ArrayList;
import java.util.List;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.StringComparator;

@SuppressWarnings("unchecked")
public class test {
	public static void main(String[] args) throws Exception {
		RecordManager rec = RecordManagerFactory.createRecordManager("Test");
		long id = rec.getNamedObject("Testing");
		System.out.println(id);
		BTree tree = null;
		if (id != 0) {
			tree = BTree.load(rec, id);
			List<String> res = (List<String>) tree.find("Mussab");
			res.addAll((List<String>) tree.find("Omar"));
			for (String s : res)
				System.out.println(s);
		} else {
			tree = BTree.createInstance(rec, new StringComparator());
			List<String> res = new ArrayList<String>();
			tree.insert("Mussab", res, false);
			for (int i = 0; i < 1000; i++) {
				res.add("ElDash");
				res.add("Omar");
				res.add("Ahmed");
				res.add("Rami");
				res.add("Mohab");
			}
			List<String> res1 = new ArrayList<String>();
			tree.insert("Omar", res1, false);
			for (int i = 0; i < 1000; i++) {
				res1.add("ElDash");
				res1.add("Azazy");
				res1.add("Moataz");
				res1.add("Khalil");
				res1.add("Gahnim");
			}
			rec.setNamedObject("Testing", tree.getRecid());
			System.out.println(tree.getRecid());
			rec.commit();
		}
	}
}
