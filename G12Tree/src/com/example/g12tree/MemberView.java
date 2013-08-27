package com.example.g12tree;

import java.util.HashMap;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class MemberView extends Window implements View {

	@PropertyId("memberFirstName")
	private TextField firstName;
	@PropertyId("memberLastName")
	private TextField lastName;
	

	SQLContainer container;
	private FieldGroup fieldGroup;
	
	public MemberView(HashMap<String, SQLContainer> sqlContainer, Object target) {
		container = sqlContainer.get("memberinfo");
		enterValue(target.toString());
		FormLayout layout = new FormLayout();
		setContent(layout);

		firstName = new TextField("First Name");
		firstName.setNullRepresentation("");
		layout.addComponent(firstName);
		lastName = new TextField("Last Name");
		lastName.setNullRepresentation("");
		layout.addComponent(lastName);
		
	}

	
	public void enterValue(String id) {
		
		Item item = container.getItem(new RowId(new Object[] { id }));

		fieldGroup = new FieldGroup(item);
		fieldGroup.bindMemberFields(this);

	}


	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
