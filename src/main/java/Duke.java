import com.sun.source.tree.IfTree;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] list = new String[100];
        boolean running = true;
        String input ;
        int count = 0;
        System.out.println("Hello! Welcome to Chat bot! Please Type a command:");

        while(running == true){
            input = scan.nextLine();
            if (input.equals("bye")){
                running = false;
                System.out.println("Thank you for using Chat bot! Goodbye!");

            }else if(input.equals("list")){
                System.out.println("Your List:");
                for(int i = 1; i<=count; i++)
                    System.out.println( i + ". " +list[i-1]);

            }else{
                list[count]= input;
                System.out.println("Added:"+ list[count]);
                count++;

            }
        }
    }
}
