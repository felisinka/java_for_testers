package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() throws IOException {
        if (app==null){
            app = new ApplicationManager();
        }
       // app.init("chrome");
        var properties = new Properties();
        properties.load(new FileReader(System.getProperty("target","local.properties")));
        app.init(System.getProperty("browser","chrome"),properties);

    }

    @AfterEach
    void checkDatabaseConsistency(){
        app.jdbc().checkConsistency();
    }

}
