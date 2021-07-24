module YuGiOh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;



    opens graphicview to javafx.fxml;
    opens model.person to com.google.gson;
    opens model to com.google.gson;
    exports graphicview;
    exports model;
    exports model.person;
    exports server.model.card;
    exports server.model.card.monster;
    exports server.model.card.spell;
    exports server.model.card.spell.fieldspells;
    exports server.model.card.trap;
    exports view;
}