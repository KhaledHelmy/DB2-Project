package BufferManager;

import Utilities.Page;
import Utilities.PageID;

public class FullPage {
	private Page page;
	private PageID id;
	
	public FullPage(Page page, PageID id) {
		this.page = page;
		this.id = id;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public PageID getId() {
		return id;
	}

	public void setId(PageID id) {
		this.id = id;
	}
}
