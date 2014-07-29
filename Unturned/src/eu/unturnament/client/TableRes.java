package eu.unturnament.client;

import com.google.gwt.user.cellview.client.CellTable;

public interface TableRes extends CellTable.Resources {
	@Source({CellTable.Style.DEFAULT_CSS, "Unturned.css"})
	TableStyle cellTableStyle();
 
	interface TableStyle extends CellTable.Style {}
}