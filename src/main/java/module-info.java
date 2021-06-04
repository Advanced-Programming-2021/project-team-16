module YuGiOh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;


    opens graphicview to javafx.fxml;
    opens model.person to com.google.gson;
    opens model to com.google.gson;
    exports graphicview;
}