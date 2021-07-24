module YuGiOh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;



    opens client.graphicview to javafx.fxml;
//    opens model.person to com.google.gson;
  //  opens model to com.google.gson;
    exports client.graphicview;
 //   exports model;
//    exports model.person;
    exports server.modell.card;
    exports server.modell.card.monster;
    exports server.modell.card.spell;
    exports server.modell.card.spell.fieldspells;
    exports server.modell.card.trap;
    exports client.view;
}