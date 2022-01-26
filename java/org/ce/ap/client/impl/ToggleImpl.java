package org.ce.ap.client.impl;

import org.ce.ap.client.gui.controller.*;

import java.util.ArrayList;

public class ToggleImpl {
    private final ArrayList<BarController> barControllers;
    private final ArrayList<CreateTweetController> createTweetControllers;
    private final ArrayList<ErrorController> errorControllers;
    private final ArrayList<ListController> listControllers;
    private final ArrayList<LoadingController> loadingControllers;
    private final ArrayList<LoginController> loginControllers;
    private final ArrayList<MainController> mainControllers;
    private final ArrayList<MenuController> menuControllers;
    private final ArrayList<MErrorController> mErrorControllers;
    private final ArrayList<ProfileController> profileControllers;
    private final ArrayList<SearchController> searchControllers;
    private final ArrayList<SignupController> signupControllers;
    private final ArrayList<TabController> tabControllers;
    private final ArrayList<TimeLineController> timeLineControllers;
    private final ArrayList<TweetController> tweetControllers;
    private final ArrayList<NoteController> noteControllers;

    public ToggleImpl() {
        barControllers = new ArrayList<>();
        createTweetControllers = new ArrayList<>();
        errorControllers = new ArrayList<>();
        listControllers = new ArrayList<>();
        loadingControllers = new ArrayList<>();
        loginControllers = new ArrayList<>();
        mainControllers = new ArrayList<>();
        menuControllers = new ArrayList<>();
        mErrorControllers = new ArrayList<>();
        profileControllers = new ArrayList<>();
        searchControllers = new ArrayList<>();
        signupControllers = new ArrayList<>();
        tabControllers = new ArrayList<>();
        timeLineControllers = new ArrayList<>();
        tweetControllers = new ArrayList<>();
        noteControllers = new ArrayList<>();
    }

    public void addController(Object controller) {
        if (controller.getClass().equals(BarController.class)) {
            barControllers.add((BarController) controller);
        } else if (controller.getClass().equals(CreateTweetController.class)) {
            createTweetControllers.add((CreateTweetController) controller);
        } else if (controller.getClass().equals(ErrorController.class)) {
            errorControllers.add((ErrorController) controller);
        } else if (controller.getClass().equals(ListController.class)) {
            listControllers.add((ListController) controller);
        } else if (controller.getClass().equals(LoadingController.class)) {
            loadingControllers.add((LoadingController) controller);
        } else if (controller.getClass().equals(LoginController.class)) {
            loginControllers.add((LoginController) controller);
        } else if (controller.getClass().equals(MainController.class)) {
            mainControllers.add((MainController) controller);
        } else if (controller.getClass().equals(MenuController.class)) {
            menuControllers.add((MenuController) controller);
        } else if (controller.getClass().equals(MErrorController.class)) {
            mErrorControllers.add((MErrorController) controller);
        } else if (controller.getClass().equals(ProfileController.class)) {
            profileControllers.add((ProfileController) controller);
        } else if (controller.getClass().equals(SearchController.class)) {
            searchControllers.add((SearchController) controller);
        } else if (controller.getClass().equals(SignupController.class)) {
            signupControllers.add((SignupController) controller);
        } else if (controller.getClass().equals(TabController.class)) {
            tabControllers.add((TabController) controller);
        } else if (controller.getClass().equals(TimeLineController.class)) {
            timeLineControllers.add((TimeLineController) controller);
        } else if (controller.getClass().equals(TweetController.class)) {
            tweetControllers.add((TweetController) controller);
        } else if (controller.getClass().equals(NoteController.class)) {
            noteControllers.add((NoteController) controller);
        }
    }

    public void toggleSize(int size) {
        for (BarController i : barControllers) {
            i.toggleScreen(size);
        }
        for (CreateTweetController i : createTweetControllers) {
            i.toggleScreen(size);
        }
        for (ErrorController i : errorControllers) {
            i.toggleScreen(size);
        }
        for (ListController i : listControllers) {
            i.toggleScreen(size);
        }
        for (LoadingController i : loadingControllers) {
            i.toggleScreen(size);
        }
        for (LoginController i : loginControllers) {
            i.toggleScreen(size);
        }
        for (MainController i : mainControllers) {
            i.toggleScreen(size);
        }
        for (MenuController i : menuControllers) {
            i.toggleScreen(size);
        }
        for (MErrorController i : mErrorControllers) {
            i.toggleScreen(size);
        }
        for (ProfileController i : profileControllers) {
            i.toggleScreen(size);
        }
        for (SearchController i : searchControllers) {
            i.toggleScreen(size);
        }
        for (SignupController i : signupControllers) {
            i.toggleScreen(size);
        }
        for (TabController i : tabControllers) {
            i.toggleScreen(size);
        }
        for (TimeLineController i : timeLineControllers) {
            i.toggleScreen(size);
        }
        for (TweetController i : tweetControllers) {
            i.toggleScreen(size);
        }
        for (NoteController i : noteControllers) {
            i.toggleScreen(size);
        }
    }

    public void toggleTheme(String mode){
        for (BarController i : barControllers) {
            i.toggleTheme(mode);
        }
        for (CreateTweetController i : createTweetControllers) {
            i.toggleTheme(mode);
        }
        for (ErrorController i : errorControllers) {
            i.toggleTheme(mode);
        }
        for (ListController i : listControllers) {
            i.toggleTheme(mode);
        }
        for (LoadingController i : loadingControllers) {
            i.toggleTheme(mode);
        }
        for (LoginController i : loginControllers) {
            i.toggleTheme(mode);
        }
        for (MainController i : mainControllers) {
            i.toggleTheme(mode);
        }
        for (MenuController i : menuControllers) {
            i.toggleTheme(mode);
        }
        for (MErrorController i : mErrorControllers) {
            i.toggleTheme(mode);
        }
        for (ProfileController i : profileControllers) {
            i.toggleTheme(mode);
        }
        for (SearchController i : searchControllers) {
            i.toggleTheme(mode);
        }
        for (SignupController i : signupControllers) {
            i.toggleTheme(mode);
        }
        for (TabController i : tabControllers) {
            i.toggleTheme(mode);
        }
        for (TimeLineController i : timeLineControllers) {
            i.toggleTheme(mode);
        }
        for (TweetController i : tweetControllers) {
            i.toggleTheme(mode);
        }
        for (NoteController i : noteControllers) {
            i.toggleMode(mode);
        }
    }

}
