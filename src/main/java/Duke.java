import com.sun.source.tree.IfTree;
import com.sun.source.util.TaskListener;

import java.util.Scanner;
import java.util.ArrayList;


public class Duke {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> tasklist = new ArrayList<Task>();
        boolean running = true;
        String input;
        int count = 0;
        System.out.println("Hello! Welcome to Chat bot! Please Type a command:");

        while(running == true){
            input = scan.nextLine();
            if (input.equals("bye")){
                running = false;
                System.out.println("Thank you for using Chat bot! Goodbye!");

            }else if(input.equals("list")){
                System.out.println("Your List:");
                for(int i = 1; i<=tasklist.size(); i++)
                    System.out.println( i + ". [" +tasklist.get(i-1).getStatusIcon() + "]" + tasklist.get(i-1).description);


            }
            else if (input.matches(".*unmark.*")){
                int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", ""))-1;
                tasklist.get(tasknumber).Uncheck();
                System.out.println("Tasks has been unchecked!: [" + tasklist.get(tasknumber).getStatusIcon() +"]" + tasklist.get(tasknumber).description);
            }
            else if (input.matches(".*mark.*")){
                int tasknumber = Integer.parseInt(input.replaceAll("[^0-9]", ""))-1;
                tasklist.get(tasknumber).Check();
                System.out.println("Tasks has been checked!: [" + tasklist.get(tasknumber).getStatusIcon() +"]" + tasklist.get(tasknumber).description);

            }
            else {
                Task t = new Task(input);
                tasklist.add(t);
                System.out.println("Added:" + t.description);
            }
        }
    }
}
