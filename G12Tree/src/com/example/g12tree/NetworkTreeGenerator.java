package com.example.g12tree;

import java.util.HashMap;
import java.util.Iterator;

import com.hs18.vaadin.addon.graph.GraphJSComponent;
import com.hs18.vaadin.addon.graph.listener.GraphJsLeftClickListener;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NetworkTreeGenerator extends Window {

	private static final String cellLeaderFill = "green";
	private static final String primaryLeaderFill = "yellow";
	private static final String bsLeaderFill = "orange";
	GraphJSComponent graphJSComponent;

	private HashMap<Integer, String> listContacts = new HashMap<Integer, String>();
	private HashMap<String, SQLContainer> sqlContainer = new HashMap<String, SQLContainer>();

	public NetworkTreeGenerator(final HashMap<String, SQLContainer> container,
			Object target) {

		
		setSizeFull();
		sqlContainer = container;
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeUndefined();
		
		setContent(layout);

		graphJSComponent = new GraphJSComponent();
		graphJSComponent.setNodesSize(120, 50);
		graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() {

			@Override
			public void onLeftClick(String id, String type, String parentId) {
				close();
				getUI().addWindow(new NetworkTreeGenerator(container, sqlContainer.get("memberinfo")
						.getContainerProperty(id, "memberId")
						.getValue()));
			}
		});
		graphJSComponent.setImmediate(true);

		String lhtml = "<div id='graph' class='graph' ></div>";// add
																// style='overflow:scroll'
																// if required
		Label graphLabel = new Label(lhtml, Label.CONTENT_XHTML);

		layout.addComponent(graphLabel);
		layout.addComponent(new Label("Legend:"));
		layout.addComponent(new Label("Primary Leader: Yellow"));
		layout.addComponent(new Label("Cell Leader: Green"));
		layout.addComponent(new Label("BS Leader: Orange"));
		layout.addComponent(graphJSComponent);
		prepareNetworkTree(target);
	}

	private void prepareNetworkTree(Object target) {

		try {
			sqlContainer.get("memberinfo").removeAllContainerFilters();
			
			System.out.println("target: " + target);
			System.out.println("contains target: " + sqlContainer.get("memberinfo")
					.getContainerProperty(target, "memberFirstName")
					.getValue().toString());
			String firstName = sqlContainer.get("memberinfo")
					.getContainerProperty(target, "memberFirstName").getValue()
					.toString();
			String lastName = sqlContainer.get("memberinfo")
					.getContainerProperty(target, "memberLastName").getValue()
					.toString();
			String className = sqlContainer.get("memberinfo")
					.getContainerProperty(target, "memberClass").getValue()
					.toString();

			graphJSComponent.addNode(target.toString(), firstName + " "
					+ lastName, null, null, null);
			graphJSComponent.getNodeProperties(target.toString()).put("fill",
					getColorClass(className));

			sqlContainer.get("memberinfo").removeAllContainerFilters();

			graphJSComponent.refresh();
			getContacts(target);

		} catch (Exception e) {
			e.printStackTrace();
		}//

	}

	private String getColorClass(String classification) {
		String color = "";
		if (classification.equals("Network Leader"))
			color = "yellow";
		if (classification.equals("Cell Leader"))
			color = "green";
		if (classification.equals("BS Leader"))
			color = "orange";

		return color;
	}

	private void getContacts(Object target) throws Exception {

		SQLContainer bsContainer = sqlContainer.get("bsrelation");
		

		String firstName = "";
		String lastName = "";
		String className = "";

		bsContainer.addContainerFilter("leaderId", target.toString(), false,
				true);

		int cnt = 1;
		if (bsContainer.size() > 0) {
			for (Iterator i = bsContainer.getItemIds().iterator(); i.hasNext();) {
				RowId iid = (RowId) i.next();
				Item item = bsContainer.getItem(iid);

				if (target
						.toString()
						.trim()
						.equals(item.getItemProperty("leaderId").getValue()
								.toString().trim())) {
					System.out.println();
					RowId memberTarget = new RowId(new Object[] { item
							.getItemProperty("memberId").getValue() });

					firstName = sqlContainer
							.get("memberinfo")
							.getContainerProperty(memberTarget,
									"memberFirstName").getValue().toString();
					lastName = sqlContainer
							.get("memberinfo")
							.getContainerProperty(memberTarget,
									"memberLastName").getValue().toString();
					className = sqlContainer.get("memberinfo")
							.getContainerProperty(memberTarget, "memberClass")
							.getValue().toString();
					
					System.out.println(cnt + " "+ firstName);
					cnt++;
					graphJSComponent.addNode(memberTarget.toString(), firstName
							+ " " + lastName, null, null, target.toString());
					graphJSComponent.getNodeProperties(memberTarget.toString())
							.put("fill", getColorClass(className));

					getContacts(memberTarget);

				}
			}
			
		}
		bsContainer.removeAllContainerFilters();
		

	}

	private void createTree(SQLContainer container) {

	}

	private void prepareGraph() {
		try {
			graphJSComponent.addNode("fruits", "Fruits I Like", "level 1",
					null, null);// Give parent id as null for root node
			// graphJSComponent.getNodeProperties("fruits").put("title",
			// "Fruits I Like");
			graphJSComponent.addNode("watermelon", "Watermelon", "level 2",
					null, "fruits");// first child of node with id fruits
			// graphJSComponent.getNodeProperties("watermelon").put("title",
			// "Its a very juicy fruit.");
			graphJSComponent.addNode("mango", "Mango", "level 2", null,
					"fruits");// second child of node with id fruits
			// graphJSComponent.getNodeProperties("mango").put("title",
			// "Katrina Kaif's favourite.");
			graphJSComponent.addNode("apple", "Apple", "level 2", null,
					"fruits");// third child of node with id fruits
			// graphJSComponent.getNodeProperties("apple").put("title",
			// "One apple a day, keeps the doctor away");
			// graphJSComponent.getNodeProperties("apple").put("fill", "#F00");
			// graphJSComponent.getNodeProperties("mango").put("fill",
			// "yellow");

			graphJSComponent.addNode("5", "Hapoos", "level 3", null, "mango");// child
																				// of
																				// mango
																				// node
																				// graphJSComponent.getNodeProperties("5").put("title",
			// "One of the best mangos");

			graphJSComponent.addNode("6", "Green", "level 3", null,
					"watermelon");// child of watermelon node
			// graphJSComponent.getNodeProperties("6").put("title",
			// "Green from outside, red inside");
			// graphJSComponent.getNodeProperties("6").put("fill", "#0F0");

			// Another Tree in the same graph
			graphJSComponent.addNode("fruitsnotlike", "Fruits I Dont Like",
					"level 1", null, null);// Give parent id as null
			// graphJSComponent.getNodeProperties("fruitsnotlike").put("title",
			// "Another tree in the same graph");
			graphJSComponent.addNode("lichy", "Lichy", "level 2", null,
					"fruitsnotlike");// first child of node with id
										// fruitsnotlike
										// graphJSComponent.getNodeProperties("lichy").put("title",
			// "because its nto easy to eat it.");
			graphJSComponent.addNode("redlichy", "Red Lichy", "level 3", null,
					"lichy");
			// graphJSComponent.getNodeProperties("redlichy").put("title",
			// "red lichy");

			graphJSComponent.refresh();// Call refresh after you are done with
										// your changes
		} catch (Exception e) {
			e.printStackTrace();
		}//

	}

}
