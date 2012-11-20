package drawappcw;

import java.io.InputStreamReader;
import java.io.Reader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Jin
 */
public class Main extends Application {

    Group root = new Group();
    Group drawing = new Group();
    Group button = new Group();
    Group text = new Group();
    Color colour = Color.BLACK;
    LinearGradient gradient = null;

    @Override
    public void start(final Stage primaryStage) {

        Reader reader = new InputStreamReader(System.in);
        final Parser parser = new Parser(reader,this);
        
        Scene scene = new Scene(root,500,500);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        
        Button btnStep = new Button("Step forward");
        btnStep.setPrefWidth(90);
        btnStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                parser.parse();
            }
        });
        
        Button close = new Button("Close window");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        close.setLayoutX(100);

        button.getChildren().addAll(btnStep,close);
        Translate btnPosition = new Translate(155,460);
        button.getTransforms().add(btnPosition);
        
        Translate textPosition = new Translate(10,430);
        text.getTransforms().add(textPosition);

        root.getChildren().addAll(drawing,button,text);
        primaryStage.show();
    }
    
    public void setBackgroundColour(Color colour)
    {
        root.getScene().setFill(colour);
    }
        
    public void setColour(Color colour)
    {
        this.colour = colour;
    }
    
    public void setGradientColour(Color c1, Color c2){
        Stop[] stops = new Stop[] { new Stop(0,c1), new Stop(1,c2)};
        gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    }
        
    public void displayImage(int x1, int y1, int w, int h, String fileName)
    {
        Image image = new Image(fileName,w,h,false,false);
        ImageView view = new ImageView(image);
        Translate position = new Translate(x1,y1);
        view.getTransforms().add(position);
        drawing.getChildren().add(view);
    }
    
    public void drawTriangle(double[] coordinates)
    {
        Polygon polygon = new Polygon(coordinates);
        polygon.setFill(null);
        polygon.setStroke(Color.BLACK);
        drawing.getChildren().add(polygon);
    }
    
    public void fillTriangle(double[] coordinates)
    {
        Polygon fillTriangle = new Polygon(coordinates);
        if(gradient != null){
            fillTriangle.setFill(gradient);
            gradient = null;
        }
        else{
            fillTriangle.setFill(colour);
        }
        drawing.getChildren().add(fillTriangle);
    }
    
    public void drawLine(int x1, int y1, int x2, int y2)
    {
        Line line = new Line(x1, y1, x2, y2);
        line.setFill(colour);
        drawing.getChildren().add(line);
    }
    
    public void drawRect(int x1, int y1, int x2, int y2)
    {
        Rectangle rect = new Rectangle(x1,y1,x2,y2);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);
        drawing.getChildren().add(rect);
    }
    
    public void fillRect(int x1, int y1, int x2, int y2)
    {
        Rectangle fillRect = new Rectangle(x1,y1,x2,y2);
        if(gradient != null){
            fillRect.setFill(gradient);
            gradient = null;
        }
        else{
            fillRect.setFill(colour);
        }
        drawing.getChildren().add(fillRect);
    }

    public void drawString(int x, int y, String s)
    {
        Text string = new Text(x,y,s);
        drawing.getChildren().add(string);
    }
    
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
    {
        Arc arc = new Arc(x,y,width/2,height/2,startAngle,arcAngle);
        arc.setFill(null);
        arc.setStroke(Color.BLACK);
        drawing.getChildren().add(arc);
    }

    public void drawOval(int x, int y, int width, int height)
    {
        int radX = width/2;
        int radY = height/2;
        Ellipse oval = new Ellipse(x+radX,y+radY,radX,radY);
        oval.setFill(null);
        oval.setStroke(Color.BLACK);
        drawing.getChildren().add(oval);
    }
    
    public void postMessage(final String s){
        Text string = new Text(0,0,s);
        text.getChildren().add(string);
    }

    public double getSampleWidth() { return 400; } 
    public double getSampleHeight() { return 400; }
    
    public static void main(String[] args) {
        launch(args);
    }
}