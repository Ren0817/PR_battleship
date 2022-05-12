

import javafx.scene.Parent;

public class Ship extends Parent {
    public int type;
    public boolean vertical = true;

    private int health;

    public Ship(int type, boolean vertical) {
    	
//        this.type = type;
        this.vertical = vertical;
        health=2;
        if(type==5||type==4) {
    		health=5;
    	}else {
    		if(type==3||type==2) {
        		health=3;
        	}
    	}
        this.type = health;

        /*VBox vbox = new VBox();
        for (int i = 0; i < type; i++) {
            Rectangle square = new Rectangle(30, 30);
            square.setFill(null);
            square.setStroke(Color.BLACK);
            vbox.getChildren().add(square);
        }

        getChildren().add(vbox);*/
    }

    public Ship(String type, boolean vertical) {
        this.vertical = vertical;
        if("AB".contains(type)){
            this.type = 5;
        }else{
            if("DS".contains(type)){
                this.type = 3;
            }else{
                this.type = 2;
            }
        }
        health=this.type;
    }


    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}