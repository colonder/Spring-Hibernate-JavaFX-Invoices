/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ui_test;

import com.entity.Customer;
import com.service.ICustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Thomas Darimont
 */
@Component
public class ProjectsPresenter {

	@FXML TableView<Customer> customerTableView;

	@FXML TableColumn<Customer, String> lastName;
	@FXML TableColumn<Customer, String> firstName;

	@Autowired
	private ICustomerService customerService;

	@FXML
	public void initialize() {

		configureProjectsTable();
		//configureTasksTable();

		for (Customer customer : customerService.findAll()) {
			customerTableView.getItems().add(customer);
		}

		customerTableView.getSelectionModel().selectFirst();
	}

	/*private void configureTasksTable() {

		taskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
		taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));

		taskStatusColumn.setCellValueFactory(new PropertyValueFactory<Task, Task.Status>("status"));
		taskStatusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Task.Status.values()));

		taskStatusColumn.setOnEditCommit(edit -> {
			edit.getRowValue().setStatus(edit.getNewValue());
			projectTrackingService.save(edit.getRowValue());
		});
	}*/

	private void configureProjectsTable() {

		lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
		firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));

		/*ChangeListener<Customer> projectSelectionChanged = (observable, oldValue, newValue) -> {
			tasksTable.getItems().clear();
			for (Task task : projectTrackingService.findAllTasksByProject(newValue)) {
				tasksTable.getItems().add(task);
			}
		};*/

		//customerTableView.getSelectionModel().selectedItemProperty().addListener(projectSelectionChanged);
	}
}
