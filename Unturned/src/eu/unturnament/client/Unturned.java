package eu.unturnament.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Unturned implements EntryPoint {

	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private CellTable.Resources tableRes = GWT.create(TableRes.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		String url = "http://unturned-servers.net/api/?object=servers&element=voters&key=ql2hpzp9v57se41gb3kmkjlc9o5n3z2jz2&month=current&format=json";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					// Couldn't connect to server (could be timeout, SOP violation, etc.)     
				}
				@Override
				public void onResponseReceived(Request request, Response response) {
					
					if (200 == response.getStatusCode()) {
					
						// create table with columns
					    CellTable<Player> table = new CellTable<Player>(10, tableRes);
	
					    TextColumn<Player> nameColumn = new TextColumn<Player>() {
					    	@Override
					    	public String getValue(Player player) {
					    		return player.getName();
					    	}
					    };
	
					    TextColumn<Player> voteColumn = new TextColumn<Player>() {
					    	@Override
					    	public String getValue(Player player) {
					    		return String.valueOf(player.getVotes());
					    	}
					    };
	
					    table.addColumn(nameColumn, "Name");
					    table.addColumn(voteColumn, "Votes");
	
					    ListDataProvider<Player> dataProvider = new ListDataProvider<Player>();
					    dataProvider.addDataDisplay(table);
	
					    List<Player> list = dataProvider.getList();
					    
					    // parse JSON
				    	JSONArray jsonArray = JSONParser.parseStrict(response.getText()).isObject().get("voters").isArray();
				    	for (int i = 0; i < jsonArray.size(); i++) {
				    		JSONObject jsonObject = jsonArray.get(i).isObject();
				    		list.add(new Player(jsonObject.get("nickname").isString().stringValue(),
				    				Integer.parseInt(jsonObject.get("votes").isString().stringValue())));
				    	}
	
					    RootPanel.get().add(table);
				    
			        } else {
			          System.out.println("Couldn't retrieve JSON (" + response.getStatusText() + ")");
			        }
					
				}
			});
		} catch (RequestException e) {
			// Couldn't connect to server
		}
	}
}
