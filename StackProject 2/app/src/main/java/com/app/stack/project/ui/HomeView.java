package com.app.stack.project.ui;

import com.app.stack.project.model.Item;

import java.util.List;

public interface HomeView {

    void updateUsers(List<Item> users);

    void showError();
}
