
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Duke {

    public static final String fileDir = "TaskBot";
    public static final String fileName = "Task.txt";
    public static ArrayList<Task> tasklist = new ArrayList<Task>(); // creates an Arraylist of class Task

    public static void PrintLines(){
            System.out.println("=================================================================");
    }
    public static void Commands(){
        System.out.println("Here are the commands:");
        System.out.println("list- List out your current task ");
        System.out.println("commands- List out the commands ");
        System.out.println("todo - Adds a task to todo (eg. todo do homework)");
        System.out.println("event - Adds a task to event using the following format: event/ dd-mm-yyyy hhmm  (eg. Musical/ 22-01-2022 1900)");
        System.out.println("deadline -  Adds a task to deadline using the following format: deadline/ dd-mm-yyyy hhmm  (eg. Project Work/ 22-01-2022 1900)");
        System.out.println("Rec -  Adds a Recurring task using the following format: Recurring/ Every when (eg. Rec Gym/ Monday)");
        System.out.println("find - find a keyword in your task list");
        System.out.println("Mark/Unmark - Marks or Unmarks a task based on the number list");
        System.out.println("delete - deletes a task based on the number list");
        PrintLines();
    }
    public static void main(String[] args) throws FileNotFoundException {
        PrintLines();
        System.out.println("Hello! I am Alice, your task Assistant bot. Please Type a command:");
        PrintLines();
        Scanner scan = new Scanner(System.in);
        String input;
        boolean running = true;
        String fullDir = fileDir+"/"+fileName;
        try {
            readFile(fullDir);
        } catch (IOException e){
            System.out.println("Looks Like its your first Time using this Bot!");
            PrintLines();
            Commands();
        }
        File f = new File(fullDir);
        if(tasklist.size()!= 0){
            System.out.println("You have currently " +tasklist.size()+" Existing Tasks! Use \"list\" to find out what they are or use \"commands\" to see the available commands" );
            PrintLines();
        }
        while(running == true){ //set the Program to run infinitely in a while loop until user types 'bye'
            input = scan.nextLine(); // Takes User input
            if (input.equals("bye")){
                running = false;
                try {
                    writeToFile(fileDir,fileName,tasklist);
                } catch (IOException e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
                System.out.println("Thank you for using Chat bot! Goodbye!");
                System.out.println("Your file is saved at:" +f.getAbsolutePath());
            }else if (input.matches(".*find.*")) { // Find Command Code
                String find = input.substring(5).trim();
                int counter =0;
                for(int i = 1; i<=tasklist.size(); i++)
                {
                    if(tasklist.get(i-1).toString().contains(find)){
                        System.out.println( i + ". " + tasklist.get(i-1).toString());
                        counter++;
                    }
                }
                System.out.println("Found " +counter+" task that contains "+find);
            }else if(input.equals("list")){ // List out the item in the arraylist Code
                System.out.println("Your List:");
                for(int i = 1; i<=tasklist.size(); i++)
                    System.out.println( i + ". " + tasklist.get(i-1).toString());
                System.out.println("You have currently " +tasklist.size()+" Tasks!" );
                PrintLines();
            }else if(input.equals("commands")){ // List out the item in the arraylist Code
               Commands();
            }else if (input.matches(".*unmark.*")){ // Mark Items in Arraylist
                try {
                    int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", "")) - 1; //Get the Task Number to mark
                    tasklist.get(tasknumber).Uncheck();
                    System.out.println("Tasks has been unchecked!:" + tasklist.get(tasknumber).toString());
                    PrintLines();
                } catch (Exception e){
                    System.out.println("Sorry, the task you have entered does not exist!");
                }
            } else if (input.matches(".*mark.*")){ // unMark Items in Arraylist
                try{
                    int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", ""))-1; //Get the Task Number to ummark
                    tasklist.get(tasknumber).Check();
                    System.out.println("Tasks has been checked!:" + tasklist.get(tasknumber).toString());
                    PrintLines();
                } catch (Exception e) {
                    System.out.println("Sorry, the task you have entered does not exist!");
                }

            } else if (input.matches(".*delete.*")){ //Delete Items in Arraylist
                try{
                    int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", ""))-1; //Get the Task Number to remove
                    System.out.println("Tasks has been Removed:" + tasklist.get(tasknumber).toString());
                    PrintLines();
                    tasklist.remove(tasknumber);
                    System.out.println("You have currently " +tasklist.size()+" Tasks!" );
                }catch (Exception e) {
                    System.out.println("Sorry, the task you you are trying to delete does not exist!");
                }
            } else if(input.matches(".*todo.*")){
                try {
                    String ErrorCheck=input.replaceAll("\\s","").substring(5);
                    ToDo t = new ToDo(input.substring(5));
                    tasklist.add(t);
                    System.out.println("Added:" + t);
                    PrintLines();
                }
                catch (Exception e){
                    System.out.println("Sorry! Your ToDo cannot be empty!");
                }
            } else if(input.matches(".*deadline.*")) {
                try{
                    String[] split = input.split("/");
                    DateTimeFormatter MyFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    LocalDateTime dt = LocalDateTime.parse(split[1].trim(),MyFormat);
                    LocalDateTime now = LocalDateTime.now();
                    if (now.isAfter(dt)){
                        System.out.println("Deadline cannot be in the past!");
                    }else {
                        Deadline d = new Deadline(split[0].substring(9), dt);
                        tasklist.add(d);
                        System.out.println("Added:" + d);
                        PrintLines();
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Sorry! Your Deadline cannot be empty!");
                }catch (DateTimeParseException e){
                    System.out.println("Sorry! Your deadline Date Seems to be in wrong format! Please use dd-mm-yyyy hhmm");
                } catch (Exception e){
                    System.out.println("Sorry! Your deadline Date cannot be empty!");
                }
            } else if(input.matches(".*event.*")) {
                try{
                    String[] split = input.split("/");
                    DateTimeFormatter MyFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    LocalDateTime dt = LocalDateTime.parse(split[1].trim(),MyFormat);
                    LocalDateTime now = LocalDateTime.now();
                    if (now.isAfter(dt)){
                        System.out.println("Event cannot be in the past!");
                    }else {
                        Event e = new Event(split[0].substring(6),dt);
                        tasklist.add(e);
                        System.out.println("Added:" + e);
                        PrintLines();
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Sorry! Your Event task cannot be empty!");
                } catch (DateTimeParseException e){
                    System.out.println("Sorry! Your Event Date Seems to be in wrong format! Please use dd-mm-yyyy hhmm");
                } catch (Exception e) {
                    System.out.println("Sorry! There is something wrong with your format. Please use event <your event>/dd-mm-yyyy hhmm");
                }
            }
            else if (input.matches(".*rec.*")){
                try{
                    String[] split = input.split("/");
                    Recurring r = new Recurring(split[0].substring(4).trim(),split[1].trim());
                    System.out.println("Added:" + r.description);
                    tasklist.add(r);
                }catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Sorry! Your Reccuring Task cannot be empty! ");
                } catch (Exception e){
                    System.out.println("Sorry! Your reccurring date or time!");
                }
            } else{
                System.out.println("Sorry I do not understand what do you mean");
            }
        }
    }

    /**
     * Write to a text file based on the file path.
     * If file or directory doesn't exist, then program will create one
     *
     * @param fileDir Directory of the file
     * @param fileName Name of the file
     * @param TaskToAdd The arraylist of the tasks
     * @throws IOException If file cant be created.
     */
    public static void writeToFile(String fileDir,String fileName, ArrayList<Task> TaskToAdd) throws IOException {
        //create Directory if it doesnt exist
        File dir = new File(fileDir);
        if (! dir.exists()){
            dir.mkdir();
        }
        //Write all the task into the .txt file
        FileWriter fw = new FileWriter(fileDir+"/"+fileName);
        for (int i = 0; i < TaskToAdd.size(); i++) {
            fw.write(TaskToAdd.get(i).toString());
            fw.write("\n");
        }
        fw.close();
    }
    /**
     * Read the text file based on the file path
     *
     * @param filePath File path of the file
     * @param TaskToAdd The arraylist of the tasks
     * @throws FileNotFoundException If  file is not found.
     *
     */

    public static void readFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        DateTimeFormatter MyFormat = DateTimeFormatter.ofPattern("MMM d yyyy hh:mma");
        while (s.hasNext()){
            String line = s.nextLine();
            if(line.matches(".*[T].*")){
                ToDo t = new ToDo(line.substring(6));
                tasklist.add(t);
                if(line.matches(".*[X].*")){
                    t.Check();
                }
            }else if(line.matches(".*[R].*")) {
                int LocationOfBy2 = line.indexOf("(");
                Recurring r = new Recurring(line.substring(6, LocationOfBy2-1),line.substring(LocationOfBy2+8,line.length()-1));
                tasklist.add(r);
                if (line.matches(".*[X].*")) {
                    r.Check();
                }
            }else if(line.matches(".*[E].*")){
                int LocationOfBy = line.indexOf("(");
                LocalDateTime dt = LocalDateTime.parse(line.substring(LocationOfBy+5,line.length()-1),MyFormat);
                Event e = new Event(line.substring(6,LocationOfBy-1),dt);
                tasklist.add(e);
                if(line.matches(".*[X].*")){
                    e.Check();
                }
            }else if(line.matches(".*[D].*")) {
                int LocationOfBy2 = line.indexOf("(");
                LocalDateTime dt = LocalDateTime.parse(line.substring(LocationOfBy2 +5, line.length() - 1),MyFormat);
                Deadline d = new Deadline(line.substring(6, LocationOfBy2-1),dt);
                tasklist.add(d);
                if (line.matches(".*[X].*")) {
                    d.Check();
                }
            }

        }

    }
}
