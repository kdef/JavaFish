package fish;

/**
 * Card class that can be used in our Go Fish game but can also be implemented
 * in other games
 * 
 * @author Nate Kozlowski
 * @version 11/04/2011
 * 
 */

public class Card {
    private int RANK;
    private int ID;
    private String NAME;
    private String IMAGE;
    private String BACK;
    private String SIDE;

    public Card(int rank, int id, String name, String image, String back,
            String side) {
        RANK = rank;
        ID = id;
        IMAGE = image;
        BACK = back;
        NAME = name;
        SIDE = side;
    }

    public String getName() {
        return NAME;
    }

    public int getRank() {
        return RANK;
    }

    public int getID() {
        return ID;
    }

    public String getImage() {
        return IMAGE;
    }

    public String getBack() {
        return BACK;
    }

    public String getSide() {
        return SIDE;
    }

}
