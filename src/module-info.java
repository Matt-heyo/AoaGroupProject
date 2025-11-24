module AoaProject {
    requires transitive java.desktop;
    requires java.logging;

    exports com.application;
    exports com.application.controller;
    exports com.application.model;
    exports com.application.view;
}
