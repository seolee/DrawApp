package drawappcw;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.Reader;
 import java.util.Scanner;
 import java.util.StringTokenizer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

 public class Parser
 {
   private BufferedReader reader;
   private Main scenegraph;

   public Parser(Reader reader, Main scenegraph){
       this.reader = new BufferedReader(reader);
       this.scenegraph = scenegraph;
   }

   public void parse()
   {
       try{
           String line = reader.readLine();
           if(line != null){
               parseLine(line);
           }
       }
        catch (IOException e)
        {
            scenegraph.postMessage("Parse failed.");
            return;
        }
        catch (ParseException e)
        {
            scenegraph.postMessage("Parse Exception: " + e.getMessage());
            return;
        }
    }
   
   private void parseLine(String line) throws ParseException
   {
     if (line.length() < 2) return;
     String command = line.substring(0, 2);

     if (command.equals("DI")) { displayImage(line.substring(2, line.length())); return; }
     if (command.equals("DT")) { drawTriangle(line.substring(2, line.length())); return; } 
     if (command.equals("FT")) { fillTriangle(line.substring(2, line.length())); return; }
     if (command.equals("DL")) { drawLine(line.substring(2, line.length())); return; }
     if (command.equals("DR")) { drawRect(line.substring(2, line.length())); return; }
     if (command.equals("FR")) { fillRect(line.substring(2, line.length())); return; }
     if (command.equals("SC")) { setColour(line.substring(3, line.length())); return; }
     if (command.equals("GC")) { setGradientColour(line.substring(3)); return; }
     if (command.equals("DS")) { drawString(line.substring(3, line.length())); return; }
     if (command.equals("DA")) { drawArc(line.substring(2, line.length())); return; }
     if (command.equals("DO")) { drawOval(line.substring(2, line.length())); return; }

    throw new ParseException(line + " Unknown drawing command");
}
 
  private void displayImage(String args) throws ParseException
  {
    int x1 = 0;
    int y1 = 0;
    int w = 0;
    int h = 0;
    String fileName = "";

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    w = getInteger(tokenizer);
    h = getInteger(tokenizer);
    int position = args.indexOf("@");
    if (position == -1) throw new ParseException("File name is missing");
    fileName = "file:" + args.substring(position+1,args.length());
    scenegraph.displayImage(x1,y1,w,h,fileName);
  }

  private void drawTriangle(String args) throws ParseException
  {
    double[] coordinates = new double[6];
    
    StringTokenizer tokenizer = new StringTokenizer(args);
    coordinates[0] = getInteger(tokenizer);
    coordinates[1] = getInteger(tokenizer);
    coordinates[2] = getInteger(tokenizer);
    coordinates[3] = getInteger(tokenizer);
    coordinates[4] = getInteger(tokenizer);
    coordinates[5] = getInteger(tokenizer);
    scenegraph.drawTriangle(coordinates);
  }
    
  private void fillTriangle(String args) throws ParseException
  {
    double[] coordinates = new double[6];
    
    StringTokenizer tokenizer = new StringTokenizer(args);
    coordinates[0] = getInteger(tokenizer);
    coordinates[1] = getInteger(tokenizer);
    coordinates[2] = getInteger(tokenizer);
    coordinates[3] = getInteger(tokenizer);
    coordinates[4] = getInteger(tokenizer);
    coordinates[5] = getInteger(tokenizer);
    scenegraph.fillTriangle(coordinates);
  }
    
  private void drawLine(String args) throws ParseException
  {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);

    scenegraph.drawLine(x1,y1,x2,y2);

   }
 
   private void drawRect(String args) throws ParseException
   {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);

    scenegraph.drawRect(x1, y1, x2, y2);
    }
 
   private void fillRect(String args) throws ParseException
{
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    
    scenegraph.fillRect(x1, y1, x2, y2);
  }

  private void drawArc(String args) throws ParseException
{
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    int startAngle = 0;
    int arcAngle = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x = getInteger(tokenizer);
    y = getInteger(tokenizer);
    width = getInteger(tokenizer);
    height = getInteger(tokenizer);
    startAngle = getInteger(tokenizer);
    arcAngle = getInteger(tokenizer);

    scenegraph.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  private void drawOval(String args) throws ParseException
{
    int x1 = 0;
    int y1 = 0;
    int width = 0;
    int height = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    width = getInteger(tokenizer);
    height = getInteger(tokenizer);

    scenegraph.drawOval(x1, y1, width, height);
  }

  private void drawString(String args) throws ParseException
{
    int x = 0;
    int y = 0;
    String s = "";
    StringTokenizer tokenizer = new StringTokenizer(args);
    x = getInteger(tokenizer);
    y = getInteger(tokenizer);
    int position = args.indexOf("@");
    if (position == -1) throw new ParseException("DrawString string is missing");
    s = args.substring(position+1,args.length());
    scenegraph.drawString(x,y,s);
  }

  private void setColour(String colourName) throws ParseException
  {
      Color colour = stringToColour(colourName);
      scenegraph.setColour(colour);
  }
  
  private void setGradientColour(String colourName) throws ParseException
  {
      String[] names = colourName.split(" ");
      Color c1, c2;
      c1 = stringToColour(names[0]);
      c2 = stringToColour(names[1]);
      scenegraph.setGradientColour(c1,c2);
  }
    
  private Color stringToColour(String s) throws ParseException{
      if (s.equals("black")) { return Color.BLACK; }
      if (s.equals("blue")) { return Color.BLUE;}
      if (s.equals("cyan")) { return Color.CYAN;}
      if (s.equals("darkgray")) { return Color.DARKGRAY;}
      if (s.equals("gray")) { return Color.GRAY;}
      if (s.equals("green")) { return Color.GREEN;}
      if (s.equals("lightgray")) { return Color.LIGHTGRAY;}
      if (s.equals("magenta")) { return Color.MAGENTA;}
      if (s.equals("orange")) { return Color.ORANGE;}
      if (s.equals("pink")) { return Color.PINK;}
      if (s.equals("red")) { return Color.RED;}
      if (s.equals("white")) { return Color.WHITE;}
      if (s.equals("yellow")) { return Color.YELLOW;}
        
      throw new ParseException(s + " Invalid colour name");
  }

  private int getInteger(StringTokenizer tokenizer) throws ParseException
  {
    if (tokenizer.hasMoreTokens())
      return Integer.parseInt(tokenizer.nextToken());
    else
      throw new ParseException("Missing Integer value");
  }
}