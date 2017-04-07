package rose.project.passmember;

import rose.project.passmember.gui.GUI;

/**
 * Created by Lord Rose on 13/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        final String FILEPATH = "/password.xml";

        System.out.println("PassMember");
        new GUI(Main.class.getResource(FILEPATH).getFile());
        // new GUI();
    }
}
