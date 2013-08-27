package com.example.g12tree;

import java.util.HashMap;

import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TreeTable;

public class TableCreator extends TreeTable {

	private static final Action SHOW = new Action("Show Network");
	private static final Action EDIT = new Action("Edit Details");

	SQLContainer sqlContainer;
	SQLContainer bsContainer;

	public TableCreator(final HashMap<String, SQLContainer> container,
			final String data) {
		sqlContainer = container.get(data);
		bsContainer = container.get("bsrelation");
		sqlContainer.setPageLength(25);

		setContainerDataSource(sqlContainer);
		setSizeFull();
		setCaption("Member List");
		setPageLength(25);
		setVisibleColumns("memberFirstName", "memberLastName",
				"memberNickname", "solGraduate", "ldiGraduate");
		setColumnHeader("memberFirstName", "First Name");
		setColumnHeader("memberNickname", "Nick Name");
		setColumnHeader("memberLastName", "Last Name");
		setColumnHeader("ldiGraduate", "LDI Graduate");
		setColumnHeader("solGraduate", "SOL Graduate");
		// addContainerProperty("Name", CheckBox.class, "");
		// addContainerProperty("City", String.class, "");
		// addItem(new Object[]{new CheckBox("Root"), "Helsinki"}, 0);

		getMapParents();

		addActionHandler(new Handler() {

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (action == SHOW) {
					getUI().addWindow(
							new NetworkTreeGenerator(container, target));
				}
				if (action == EDIT) {
					getUI().addWindow(new EditPage(container, target));

				}

			}

			@Override
			public Action[] getActions(Object target, Object sender) {
				if (data.equals("memberinfo"))
					return new Action[] { SHOW, EDIT };
				// if (data.equals("phenotype"))
				// return new Action[]{ ADD} ;
				return null;

			}
		});
	}

	private void getMapParents() {
//		setColumnReorderingAllowed(true);
//		setParent(new RowId(new Object[] { 7 }), null);
		System.out.println(getHierarchyColumnId());
//		System.out.println(setParent(new RowId(new Object[] { 1 }), new RowId(new Object[] { 7 })));
		
//		for (int i = 1; i < bsContainer.size(); i++){
//			 System.out.println("member: "+ bsContainer.getContainerProperty(
//						bsContainer.getIdByIndex(i), "memberId")
//						.getValue() + " Leader: "+bsContainer.getContainerProperty(
//								bsContainer.getIdByIndex(i), "leaderId")
//								.getValue());
//			setParent(
//					new RowId(
//							new Object[] { bsContainer.getContainerProperty(
//									bsContainer.getIdByIndex(i), "memberId")
//									.getValue() }),
//					new RowId(
//							new Object[] { bsContainer.getContainerProperty(
//									bsContainer.getIdByIndex(i), "leaderId")
//									.getValue() }));
//		}

		
	}

	public void updateSearch(String string) {
		if (string == null || string.isEmpty())
			sqlContainer.removeAllContainerFilters();
		else {
			SimpleStringFilter firstNameFilter = new SimpleStringFilter(
					"memberFirstName", string, true, false);
			SimpleStringFilter lastNameFilter = new SimpleStringFilter(
					"memberLastName", string, true, false);
			SimpleStringFilter nickNameFilter = new SimpleStringFilter(
					"memberNickname", string, true, false);

			Filter filter = new Or(firstNameFilter, lastNameFilter,
					nickNameFilter);

			sqlContainer.addContainerFilter(filter);

		}

	}

}
