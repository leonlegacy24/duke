import com.sun.source.tree.IfTree;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        String input ;
        System.out.println("Hello! Welcome to Chat bot! Please Type a command:");
        while(running == true){
            input = scan.next();
            if (input.equals("bye")){
                running = false;
                System.out.println("Thank you for using Chat bot! Goodbye!");

            }else{
                System.out.println(input);
            }
        }
    }
}
