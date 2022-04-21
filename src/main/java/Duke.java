import com.sun.source.tree.IfTree;
import com.sun.source.util.TaskListener;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class Duke {

        public static ArrayList<Task> tasklist = new ArrayList<Task>();


        public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        boolean running = true;
        String input;
        String file = "data/Task.txt";
        readFile(file);
        File f = new File(file);
        System.out.println("Hello! Welcome to Chat bot! Please Type a command:");
        if(tasklist.size()!= 0){
            System.out.println("You have currently " +tasklist.size()+" Existing Tasks! Use \"list\" to find out what they are!" );

        }



            while(running == true){
            input = scan.nextLine();
            if (input.equals("bye")){
                running = false;
                try {
                    writeToFile(file, tasklist);
                } catch (IOException e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
                System.out.println("Thank you for using Chat bot! Goodbye!");
                System.out.println("Your file is saved at:" +f.getAbsolutePath());

            }else if(input.equals("list")){
                System.out.println("Your List:");
                for(int i = 1; i<=tasklist.size(); i++)
                    System.out.println( i + ". " + tasklist.get(i-1).toString());
                    System.out.println("You have currently " +tasklist.size()+" Tasks!" );


            }
            else if (input.matches(".*unmark.*")){
                try {
                    int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", "")) - 1;
                    tasklist.get(tasknumber).Uncheck();
                    System.out.println("Tasks has been unchecked!:" + tasklist.get(tasknumber).toString());
                } catch (Exception e){
                    System.out.println("Sorry, the task you have entered does not exist!");
                }

            }
            else if (input.matches(".*mark.*")){
                try{
                    int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", ""))-1;
                    tasklist.get(tasknumber).Check();
                    System.out.println("Tasks has been checked!:" + tasklist.get(tasknumber).toString());

                } catch (Exception e) {
                    System.out.println("Sorry, the task you have entered does not exist!");
                }

            }
            else if (input.matches(".*delete.*")){
                try{
                    int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", ""))-1;
                    System.out.println("Tasks has been Removed:" + tasklist.get(tasknumber).toString());
                    tasklist.remove(tasknumber);
                    System.out.println("You have currently " +tasklist.size()+" Tasks!" );
                }catch (Exception e) {
                    System.out.println("Sorry, the task you you are trying to delete does not exist!");
                }
            }


            else if(input.matches(".*todo.*")){
                    try {
                        String ErrorCheck=input.replaceAll("\\s","").substring(5);
                        ToDo t = new ToDo(input.substring(5));
                        System.out.println("Added:" + t.description);
                    }
                    catch (Exception e){
                        System.out.println("Sorry! Your ToDo cannot be empty!");
                    }

            }
            else if(input.matches(".*deadline.*")) {
                try{
                    String[] split = input.split("/");
                    DateTimeFormatter MyFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    LocalDateTime dt = LocalDateTime.parse(split[1].trim(),MyFormat);
                    Deadline d = new Deadline(split[0].substring(9),dt);
                    tasklist.add(d);
                    System.out.println("Added:" + d.description);

                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Sorry! Your Deadline cannot be empty!");
               } catch (Exception e){
                    System.out.println("Sorry! Your deadline Date cannot be empty!");
                }


            }
            else if(input.matches(".*event.*")) {
                try{
                    String[] split = input.split("/");
                    DateTimeFormatter MyFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    LocalDateTime dt = LocalDateTime.parse(split[1].trim(),MyFormat);
                    Event e = new Event(split[0].substring(6),dt);
                    tasklist.add(e);
                    System.out.println("Added:" + e.description);

                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Sorry! Your Event task cannot be empty!");
                } catch (Exception e){
                    System.out.println("Sorry! Your Event Date cannot be empty!");
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
            }
            else if (input.matches(".*find.*")){
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
            }
            else{
                System.out.println("Sorry I do not understand what do you mean");
            }
        }



    }

    private static void writeToFile(String filePath, ArrayList<Task> TaskToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (int i = 0; i < TaskToAdd.size(); i++) {
            fw.write(TaskToAdd.get(i).toString());
            fw.write("\n");
        }
        fw.close();
    }

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
