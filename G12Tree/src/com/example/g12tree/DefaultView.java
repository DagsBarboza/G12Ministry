package com.example.g12tree;

import java.util.HashMap;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class DefaultView extends CustomComponent implements View{

	
	private TableCreator tb;
	
	public DefaultView(final HashMap<String, SQLContainer> sqlContainer) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeUndefined();
		layout.setWidth("100%");
		layout.setMargin(true);
		
		tb = new TableCreator(sqlContainer, "memberinfo");
		
		TextField searchField = new TextField("Search");
		searchField.addTextChangeListener(new TextChangeListener() {
			
			@Override
			public void textChange(TextChangeEvent event) {
				tb.updateSearch(event.getText());
				
				
			}
		});
		
		layout.addComponent(searchField);
		layout.addComponent(new Label("<br/>", ContentMode.HTML));
		layout.addComponent(tb); 
		setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
