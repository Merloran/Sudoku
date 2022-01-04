package pl.comp;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"autor1", "Maciej Poncyleusz"},
                {"autor2", "Maciej Wójcik"},
        };
    }
}
