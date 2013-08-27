package com.example.g12tree;

import java.util.HashMap;

import com.example.g12tree.utils.DButils;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Main UI class
 */
@Theme("g12treetheme")
@SuppressWarnings("serial")
public class G12treeUI extends UI {

	Window notifications;
	String selectionStr = "";
	
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		setContent(layout);

		HorizontalLayout topBar = new HorizontalLayout();
		topBar.setWidth("100%");
		topBar.addStyleName("topbar");

		MarginInfo m = new MarginInfo(false, true, false, false);

		HorizontalLayout titleBar = new HorizontalLayout();
		Label title = new Label("Lakas Angkan G12");
		title.addStyleName("heading");
		titleBar.addComponent(title);
		titleBar.setMargin(true);

		HorizontalLayout menuBar = new HorizontalLayout();
		menuBar.setMargin(true);
		menuBar.addStyleName("menu-options");

		HorizontalLayout treeLayout = new HorizontalLayout();
		treeLayout.addComponent(new Label("Show G12 Tree"));
		treeLayout.setMargin(m);
		treeLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("");

			}
		});
		menuBar.addComponent(treeLayout);

		HorizontalLayout reportLayout = new HorizontalLayout();
		reportLayout.addComponent(new Label("Report"));
		reportLayout.setMargin(m);
		reportLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				buildNotifications(event);
				getUI().addWindow(notifications);
                notifications.focus();
				
			}
			
			private void buildNotifications(LayoutClickEvent event) {
				notifications = new Window("Notifications");
				VerticalLayout l = new VerticalLayout();
				l.setMargin(true);
				l.setSpacing(true);
				
				
				l.addComponent(new Label("<b>Select Report</b>",ContentMode.HTML));
				
				
				
				VerticalLayout selection1 = new VerticalLayout();
				selection1.addComponent(new Label("<i>Number of Cells</i>", ContentMode.HTML));
				selection1.addLayoutClickListener(new LayoutClickListener() {
					
					@Override
					public void layoutClick(LayoutClickEvent event) {
						selectionStr = "cellNumber";
						getUI().getNavigator().navigateTo("RecordView");
						notifications.close();
						
					}
				});
				
				VerticalLayout selection2 = new VerticalLayout();
				selection2.addComponent(new Label("<i>Training Programs</i>", ContentMode.HTML));
				selection2.addLayoutClickListener(new LayoutClickListener() {
					
					@Override
					public void layoutClick(LayoutClickEvent event) {
						selectionStr = "training";
						getUI().getNavigator().navigateTo("RecordView");
						notifications.close();
						
					}
				});
				
				l.addComponent(selection1);
				l.addComponent(selection2);
				
				l.addComponent(new Label("<br>", ContentMode.HTML));
				
				l.addComponent(new Button("close", new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						notifications.close();
						
					}
				}));
				
				notifications.setContent(l);
				notifications.setWidth("300px");
				notifications.setHeight("300px");
				notifications.addStyleName("Notification");
				notifications.setClosable(false);
				notifications.setResizable(false);
				notifications.setDraggable(false);
				notifications.setPositionX(event.getClientX()
						- event.getRelativeX()-250);
				notifications.setPositionY(event.getClientY()
						- event.getRelativeY()+20);
				notifications.setCloseShortcut(KeyCode.ESCAPE, null);
				
				
				
				
				l.addLayoutClickListener(new LayoutClickListener() {
					
					@Override
					public void layoutClick(LayoutClickEvent event) {
						notifications.close();
						
					}
				});
				
				
				
				
			}
		});
		menuBar.addComponent(reportLayout);
		
		HorizontalLayout accLayout = new HorizontalLayout();
		accLayout.addComponent(new Label("Settings"));
		accLayout.setMargin(m);
		accLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("AccountView");

			}
			
			
		});
		menuBar.addComponent(accLayout);

		topBar.addComponent(titleBar);
		topBar.addComponent(menuBar);

		VerticalLayout bodyContent = new VerticalLayout();
		bodyContent.setSizeFull();

		layout.addComponent(topBar);
		layout.addComponent(bodyContent);
		layout.setExpandRatio(bodyContent, 2);
		getPage().setTitle("Lakas Angkan: G12 Network");

		HashMap<String, SQLContainer> sqlContainer = DButils.createContainer();

		Navigator navigator = new Navigator(this, bodyContent);
		navigator.addView("", new DefaultView(sqlContainer));
		navigator.addView("RecordView", new ReportView(sqlContainer, selectionStr));
		navigator.addView("AccountView", new MyAccountView(sqlContainer));
		

	}

}