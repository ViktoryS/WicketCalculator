package com.wicket.calculator;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Calculator extends WebPage {

	private TextField field;
	private List<SimpleButton> buttons = new ArrayList<SimpleButton>();
	private List<ÑommandButton> commandButtons = new ArrayList<ÑommandButton>();
	private List<String> listView = new ArrayList<>();
	private Form form;
	private Label label;
	private double number;
	private char command = 0;
	private String result = "";

	public Calculator() {
		form = new Form("form");

		field = new TextField("textField", new Model(""));
		form.add(field);

		label = new Label("message", new Model(""));
		form.add(label);
		
		form.add(new Button("clean"){
			public void onSubmit(){
				field.setDefaultModelObject("");
				number = 0;
				command = 0;
				result = "";
			}
		});

		createSimpleButtons();
		buttons.forEach((button) -> form.add(button));

		createCommandButtons();
		commandButtons.forEach((button) -> form.add(button));

		form.add(new Button("=") {
			public void onSubmit() {
				count();
				result += " = " + number;
				listView.add(result);
				result = new String("");
				field.setDefaultModelObject("" + number);
			}
		});
		
		form.add(new ListView<String>("listview", listView) {
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("result", item.getDefaultModelObjectAsString()));
				item.add(new Button("use"){
					public void onSubmit(){
						String s = item.getDefaultModelObjectAsString();
						field.setDefaultModelObject(s.substring(s.indexOf('=')+2));
						command = 0;
					}
				});
			}
		});
		
		add(form);
	}

	public void createSimpleButtons() {
		for (int i = 0; i < 10; i++) {
			buttons.add(new SimpleButton("" + i));
		}
		buttons.add(new SimpleButton("."));
	}

	public void createCommandButtons() {
		commandButtons.add(new ÑommandButton("/"));
		commandButtons.add(new ÑommandButton("+"));
		commandButtons.add(new ÑommandButton("-"));
		commandButtons.add(new ÑommandButton("*"));
	}

	public void count() {
		String value = (String) field.getModelObject();
		double pNumber = 0;
		try {
			pNumber = Double.parseDouble(value);
			result += " " + pNumber;
			switch (command) {
			case '+':
				number += pNumber;
				break;
			case '-':
				number -= pNumber;
				break;
			case '*':
				number *= pNumber;
				break;
			case '/':
				number /= pNumber;
				break;
			case '=':
				number = pNumber;
				break;
			}
		} catch (NumberFormatException e) {
			label.setDefaultModelObject("Error! Value isn't a number!");
		}
	}

	private class SimpleButton extends Button {

		public SimpleButton(String id) {
			super(id);
		}

		public void onSubmit() {
			String value = (String) field.getModelObject();
			if (value != null) {
				value += this.getInputName();
			} else {
				value = this.getInputName();
			}
			field.setModelObject(value);
		}
	}

	private class ÑommandButton extends Button {

		public ÑommandButton(String id) {
			super(id);
		}

		public void onSubmit() {
			if (command == 0) {
				String value = (String) field.getModelObject();
				try {
					number = Double.parseDouble(value);
					result += number;
				} catch (NumberFormatException e) {
					label.setDefaultModelObject("Error! Value isn't a number!");
				}
			}else{
				count();
			}
			command = this.getInputName().charAt(0);
			result += " " + command;
			field.setModelObject("");
		}
	}

}
